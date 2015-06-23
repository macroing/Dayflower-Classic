/**
 * Copyright 2009 - 2015 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.gdt.engine.
 * 
 * org.macroing.gdt.engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.gdt.engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.gdt.engine. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.gdt.engine.camera;

import org.macroing.gdt.engine.geometry.Transform;

/**
 * An abstract implementation of {@code Camera} that adds projective transformations.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class ProjectiveCamera extends Camera {
	/**
	 * A constant providing the default focal distance.
	 */
	public static final double DEFAULT_FOCAL_DISTANCE = 1.0e30D;
	
	/**
	 * A constant providing the default lens radius.
	 */
	public static final double DEFAULT_LENS_RADIUS = 0.0D;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private double focalDistance = DEFAULT_FOCAL_DISTANCE;
	private double lensRadius = DEFAULT_LENS_RADIUS;
	private int height;
	private int width;
	private final Transform cameraToScreen = Transform.newInstance();
	private final Transform rasterToCamera = Transform.newInstance();
	private final Transform rasterToScreen = Transform.newInstance();
	private final Transform screenToRaster = Transform.newInstance();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ProjectiveCamera} instance.
	 */
	protected ProjectiveCamera() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, depth of field is enabled, {@code false} otherwise.
	 * <p>
	 * By default, depth of field is disabled. To enable it, set a lens radius greater than {@code 0.0D}, by calling {@code setLensRadius(double)}.
	 * 
	 * @return {@code true} if, and only if, depth of field is enabled, {@code false} otherwise
	 */
	public final boolean isDepthOfFieldEnabled() {
		return this.lensRadius > 0.0D;
	}
	
	/**
	 * Returns the current focal distance.
	 * <p>
	 * By default, {@code ProjectiveCamera.DEFAULT_FOCAL_DISTANCE} is used.
	 * 
	 * @return the current focal distance
	 */
	public final double getFocalDistance() {
		return this.focalDistance;
	}
	
	/**
	 * Returns the current lens radius.
	 * <p>
	 * By default, {@code ProjectiveCamera.DEFAULT_LENS_RADIUS} is used.
	 * 
	 * @return the current lens radius
	 */
	public final double getLensRadius() {
		return this.lensRadius;
	}
	
	/**
	 * Returns the current height.
	 * 
	 * @return the current height
	 */
	public final int getHeight() {
		return this.height;
	}
	
	/**
	 * Returns the current width.
	 * 
	 * @return the current width
	 */
	public final int getWidth() {
		return this.width;
	}
	
	/**
	 * Returns the {@code Transform} that transforms from camera-space to screen-space.
	 * 
	 * @return the {@code Transform} that transforms from camera-space to screen-space
	 */
	public final Transform getCameraToScreen() {
		return this.cameraToScreen;
	}
	
	/**
	 * Returns the {@code Transform} that transforms from raster-space to camera-space.
	 * 
	 * @return the {@code Transform} that transforms from raster-space to camera-space
	 */
	public final Transform getRasterToCamera() {
		return this.rasterToCamera;
	}
	
	/**
	 * Returns the {@code Transform} that transforms from raster-space to screen-space.
	 * 
	 * @return the {@code Transform} that transforms from raster-space to screen-space
	 */
	public final Transform getRasterToScreen() {
		return this.rasterToScreen;
	}
	
	/**
	 * Returns the {@code Transform} that transforms from screen-space to raster-space.
	 * 
	 * @return the {@code Transform} that transforms from screen-space to raster-space
	 */
	public final Transform getScreenToRaster() {
		return this.screenToRaster;
	}
	
	/**
	 * Call this method whenever you have changed any aspects of this class, its subclasses or its super-class.
	 */
	@Override
	public void configure() {
		this.screenToRaster.set(doCreateScreenToRaster(this.width, this.height));
		this.rasterToScreen.set(this.screenToRaster.inverse());
		this.rasterToCamera.set(this.cameraToScreen.inverse().multiply(this.rasterToScreen));
	}
	
	/**
	 * Sets a new width and height for this {@code ProjectiveCamera} instance.
	 * 
	 * @param width the new width
	 * @param height the new height
	 */
	public final void setResolution(final int width, final int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Sets a new focal distance for this {@code ProjectiveCamera} instance.
	 * <p>
	 * By default, {@code ProjectiveCamera.DEFAULT_FOCAL_DISTANCE} is used.
	 * 
	 * @param focalDistance the new focal distance
	 */
	public final void setFocalDistance(final double focalDistance) {
		this.focalDistance = focalDistance;
	}
	
	/**
	 * Sets a new height for this {@code ProjectiveCamera} instance.
	 * 
	 * @param height the new height
	 */
	public final void setHeight(final int height) {
		this.height = height;
	}
	
	/**
	 * Sets a new lens radius for this {@code ProjectiveCamera} instance.
	 * <p>
	 * By default, {@code ProjectiveCamera.DEFAULT_LENS_RADIUS} is used.
	 * <p>
	 * Use a value greater than {@code 0.0D} if you want to enable depth of field. By default it is disabled.
	 * 
	 * @param lensRadius the new lens radius
	 */
	public final void setLensRadius(final double lensRadius) {
		this.lensRadius = lensRadius;
	}
	
	/**
	 * Sets a new width for this {@code ProjectiveCamera} instance.
	 * 
	 * @param width the new width
	 */
	public final void setWidth(final int width) {
		this.width = width;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Transform doCreateScreenToRaster(final double width, final double height) {
		final double aspectRatio = width / height;
		final double x0 = aspectRatio > 1.0D ? -aspectRatio : -1.0D;
		final double y0 = aspectRatio > 1.0D ? -1.0D : -1.0D / aspectRatio;
		final double x1 = aspectRatio > 1.0D ? aspectRatio : 1.0D;
		final double y1 = aspectRatio > 1.0D ? 1.0D : 1.0D / aspectRatio;
		
		return doCreateScreenToRaster(width, height, x0, y0, x1, y1);
	}
	
	private static Transform doCreateScreenToRaster(final double width, final double height, final double x0, final double y0, final double x1, final double y1) {
		final Transform transform0 = Transform.scale(width, height, 1.0D);
		final Transform transform1 = Transform.scale(1.0D / (x1 - x0), 1.0D / (y0 - y1), 1.0D);
		final Transform transform2 = Transform.translate(-x0, -y1, 0.0D);
		final Transform transform3 = transform0.multiply(transform1).multiply(transform2);
		
		return transform3;
	}
}