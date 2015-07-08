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

import org.macroing.gdt.engine.geometry.Point;
import org.macroing.gdt.engine.geometry.Ray;
import org.macroing.gdt.engine.geometry.RayDifferential;
import org.macroing.gdt.engine.geometry.Transform;
import org.macroing.gdt.engine.geometry.Vector;
import org.macroing.gdt.engine.sampler.Sample;
import org.macroing.gdt.engine.util.MonteCarlo;

/**
 * A {@code ProjectiveCamera} implementation that takes perspective into account.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PerspectiveCamera extends ProjectiveCamera {
	/**
	 * The default field of view value.
	 */
	public static final double DEFAULT_FIELD_OF_VIEW = 90.0D;
	
	/**
	 * The default Z-far value.
	 */
	public static final double DEFAULT_Z_FAR = 1000.0D;
	
	/**
	 * The default Z-near value.
	 */
	public static final double DEFAULT_Z_NEAR = 1.0e-2D;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final double CENTER_OF_PROJECTION = -2.0D;
	private static final double MAXIMUM_X = 2.0D;
	private static final double MAXIMUM_Y = 2.0D;
	private static final double MINIMUM_X = -2.0D;
	private static final double MINIMUM_Y = -2.0D;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private double fieldOfView = DEFAULT_FIELD_OF_VIEW;
	private double x;
	private double xOffset;
	private double y;
	private double yOffset;
	private double zFar = DEFAULT_Z_FAR;
	private double zNear = DEFAULT_Z_NEAR;
	private final Point origin = new Point(0.0D, 0.0D, 2.0D);
	private final Vector cameraX = Vector.zero();
	private final Vector cameraY = Vector.zero();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PerspectiveCamera() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the field of view set for this {@code PerspectiveCamera} instance.
	 * 
	 * @return the field of view set for this {@code PerspectiveCamera} instance
	 */
	public double getFieldOfView() {
		return this.fieldOfView;
	}
	
	/**
	 * Returns the Z-far set for this {@code PerspectiveCamera} instance.
	 * 
	 * @return the Z-far set for this {@code PerspectiveCamera} instance
	 */
	public double getZFar() {
		return this.zFar;
	}
	
	/**
	 * Returns the Z-near set for this {@code PerspectiveCamera} instance.
	 * 
	 * @return the Z-near set for this {@code PerspectiveCamera} instance
	 */
	public double getZNear() {
		return this.zNear;
	}
	
	/**
	 * Returns a newly generated {@code Ray} given a {@code Sample}.
	 * <p>
	 * If {@code sample} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param sample the {@code Sample} that guides the generation of the {@code Ray}
	 * @return a newly generated {@code Ray} given a {@code Sample}
	 * @throws NullPointerException thrown if, and only if, {@code sample} is {@code null}
	 */
	@Override
	public Ray newRay(final Sample sample) {
		final double x = MINIMUM_X + sample.getX() * this.x + this.xOffset;
		final double y = MAXIMUM_Y - sample.getY() * this.y - this.yOffset;
		final double z = -CENTER_OF_PROJECTION;
		
		final Vector direction = new Vector(x, y, z).normalize();
		
		Ray ray = new Ray(0, this.origin, direction);
		ray.setTime(sample.getTime());
		
//		The following is used for depth of field:
		if(isDepthOfFieldEnabled()) {
			final double[] lensUV = MonteCarlo.toConcentricSampleDisk(sample.getU(), sample.getV());
			
			final double lensRadius = getLensRadius();
			final double lensU = lensUV[0] * lensRadius;
			final double lensV = lensUV[1] * lensRadius;
			final double focalDistance = getFocalDistance() / ray.getDirection().getZ();
			
			final Point focusPoint = ray.getPointAt(focalDistance);
			
			ray.setOrigin(new Point(lensU, lensV, 0.0D));
			ray.setDirection(focusPoint.subtract(ray.getOrigin()).toVector().normalize());
		}
		
		ray = getCameraToWorld().transform(ray);
		
		return ray;
	}
	
	/**
	 * Returns a newly generated {@code RayDifferential} given a {@code Sample}.
	 * <p>
	 * If {@code sample} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param sample the {@code Sample} that guides the generation of the {@code RayDifferential}
	 * @return a newly generated {@code RayDifferential} given a {@code Sample}
	 * @throws NullPointerException thrown if, and only if, {@code sample} is {@code null}
	 */
	@Override
	public RayDifferential newRayDifferential(final Sample sample) {
		final Ray ray = newRay(sample);
		
		final
		RayDifferential rayDifferentail = RayDifferential.newInstance(ray);
		rayDifferentail.setDifferentials(true);
		
//		The following is used for depth of field:
		if(isDepthOfFieldEnabled()) {
			final double[] lensUV = MonteCarlo.toConcentricSampleDisk(sample.getU(), sample.getV());
			
			final double focalDistance = getFocalDistance();
			final double distance = focalDistance / ray.getDirection().getZ();
			final double lensRadius = getLensRadius();
			final double lensU = lensUV[0] * lensRadius;
			final double lensV = lensUV[1] * lensRadius;
			
			final Point focusPoint = ray.getPointAt(distance);
			
			ray.setOrigin(new Point(lensU, lensV, 0.0D));
			ray.setDirection(focusPoint.subtract(ray.getOrigin()).toVector().normalize());
			
			final Vector deltaX = this.cameraX.copyAndNormalize();
			final Vector deltaY = this.cameraY.copyAndNormalize();
			
			final double focalDistanceX = focalDistance / deltaX.getZ();
			final double focalDistanceY = focalDistance / deltaY.getZ();
			
			final Point focusPointX = deltaX.multiply(focalDistanceX).toPoint();
			final Point focusPointY = deltaY.multiply(focalDistanceY).toPoint();
			
			rayDifferentail.setOriginX(new Point(lensU, lensV, 0.0D));
			rayDifferentail.setOriginY(new Point(lensU, lensV, 0.0D));
			
			rayDifferentail.setDirectionX(focusPointX.copyAndSubtract(rayDifferentail.getOriginX()).toVector().normalize());
			rayDifferentail.setDirectionY(focusPointY.copyAndSubtract(rayDifferentail.getOriginY()).toVector().normalize());
		} else {
			rayDifferentail.setOriginX(rayDifferentail.getRay().getOrigin());
			rayDifferentail.setOriginY(rayDifferentail.getRay().getOrigin());
			rayDifferentail.setDirectionX(this.cameraX.copyAndNormalize());
			rayDifferentail.setDirectionY(this.cameraY.copyAndNormalize());
		}
		
		return rayDifferentail;
	}
	
	/**
	 * Call this method whenever you have changed any aspects of this class or its super-classes.
	 */
	@Override
	public void configure() {
		this.x = (MAXIMUM_X - MINIMUM_X) / getWidth();
		this.xOffset = this.x * 0.5D;
		
		this.y = (MAXIMUM_Y - MINIMUM_Y) / getHeight();
		this.yOffset = this.y * 0.5D;
		
		final Transform perspective = Transform.perspective(this.fieldOfView, this.zNear, this.zFar);
		final Transform cameraToScreen = getCameraToScreen();
		final Transform rasterToCamera = getRasterToCamera();
		
		cameraToScreen.set(perspective);
		
		super.configure();
		
		this.cameraX.set(rasterToCamera.transform(Point.x()).copyAndSubtract(rasterToCamera.transform(Point.zero())).toVector());
		this.cameraY.set(rasterToCamera.transform(Point.y()).copyAndSubtract(rasterToCamera.transform(Point.zero())).toVector());
	}
	
	/**
	 * Sets a new field of view for this {@code PerspectiveCamera} instance.
	 * 
	 * @param fieldOfView the new field of view
	 */
	public void setFieldOfView(final double fieldOfView) {
		this.fieldOfView = fieldOfView;
	}
	
	/**
	 * Sets a new Z-far for this {@code PerspectiveCamera} instance.
	 * 
	 * @param zFar the new Z-far
	 */
	public void setZFar(final double zFar) {
		this.zFar = zFar;
	}
	
	/**
	 * Sets a new Z-near for this {@code PerspectiveCamera} instance.
	 * 
	 * @param zNear the new Z-near
	 */
	public void setZNear(final double zNear) {
		this.zNear = zNear;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new default {@code PerspectiveCamera} instance.
	 * <p>
	 * The resolution will be set to {@code 800 x 600} by default.
	 * <p>
	 * The {@code configure()} method of the returned {@code PerspectiveCamera} instance will be called by this method.
	 * 
	 * @return a new default {@code PerspectiveCamera} instance
	 */
	public static PerspectiveCamera newInstance() {
		final
		PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
		perspectiveCamera.setResolution(1024, 768);
		perspectiveCamera.configure();
		
		return perspectiveCamera;
	}
}