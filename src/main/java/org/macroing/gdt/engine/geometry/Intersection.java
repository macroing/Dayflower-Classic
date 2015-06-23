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
package org.macroing.gdt.engine.geometry;

/**
 * An instance of this {@code Intersection} class contains information about the intersection of a {@link Ray} and a {@link Shape}.
 * <p>
 * This class is mutable and therefore not suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Intersection {
	private double distance = Constants.INFINITY;
	private double refractiveIndex = Material.REFRACTIVE_INDEX_AIR;
	private Point surfaceIntersectionPoint;
	private Ray ray;
	private Scene scene;
	private Shape shape;
	private Vector surfaceNormal;
	private Vector surfaceNormalProperlyOriented;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Intersection() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the distance to the closest {@link Shape}.
	 * 
	 * @return the distance to the closest {@code Shape}
	 */
	public double getDistance() {
		return this.distance;
	}
	
	/**
	 * Returns the refractive index of the medium the {@link Ray} is passing through to get to the intersected {@link Shape}.
	 * 
	 * @return the refractive index of the medium the {@code Ray} is passing through to get to the intersected {@code Shape}
	 */
	public double getRefractiveIndex0() {
		return this.refractiveIndex;
	}
	
	/**
	 * Returns the refractive index of the {@link Material} provided by the {@link Shape} being intersected.
	 * 
	 * @return the refractive index of the {@code Material} provided by the {@code Shape} being intersected
	 */
	public double getRefractiveIndex1() {
		return this.shape.getMaterial().getRefractiveIndex();
	}
	
	/**
	 * Returns the {@link Point} on the surface of the intersected {@link Shape}.
	 * 
	 * @return the {@code Point} on the surface of the intersected {@code Shape}
	 */
	public Point getSurfaceIntersectionPoint() {
		return this.surfaceIntersectionPoint;
	}
	
	/**
	 * Returns the {@link Ray} that is potentially intersecting a {@link Shape}.
	 * 
	 * @return the {@code Ray} that is potentially intersecting a {@code Shape}
	 */
	public Ray getRay() {
		return this.ray;
	}
	
	/**
	 * Returns the {@link Scene} provided by this {@code Intersection} instance.
	 * 
	 * @return the {@code Scene} provided by this {@code Intersection} instance
	 */
	public Scene getScene() {
		return this.scene;
	}
	
	/**
	 * Returns the {@link Shape} that was intersected.
	 * 
	 * @return the {@code Shape} that was intersected
	 */
	public Shape getShape() {
		return this.shape;
	}
	
	/**
	 * Returns a copy of the surface normal {@link Vector} of the intersection.
	 * 
	 * @return a copy of the surface normal {@code Vector} of the intersection
	 */
	public Vector getSurfaceNormal() {
		return this.surfaceNormal.copy();
	}
	
	/**
	 * Returns a copy of the properly oriented surface normal {@link Vector} of the intersection.
	 * <p>
	 * By properly oriented means that it takes into account for refraction.
	 * 
	 * @return a copy of the properly oriented surface normal {@code Vector} of the intersection
	 */
	public Vector getSurfaceNormalProperlyOriented() {
		return this.surfaceNormalProperlyOriented.copy();
	}
	
	/**
	 * Calculates the surface intersection {@link Point}.
	 */
	public void calculateSurfaceIntersectionPoint() {
		setSurfaceIntersectionPoint(this.ray.getPointAt(this.distance));
	}
	
	/**
	 * Calculates the surface normal.
	 */
	public void calculateSurfaceNormal() {
		setSurfaceNormal(this.surfaceIntersectionPoint.toVector().subtract(this.shape.getPosition().toVector()).normalize());
	}
	
	/**
	 * Calculates the properly oriented surface normal.
	 */
	public void calculateSurfaceNormalProperlyOriented() {
		setSurfaceNormalProperlyOriented(this.surfaceNormal.dotProduct(this.ray.getDirection()) < 0.0D ? this.surfaceNormal.copy() : this.surfaceNormal.copyAndMultiply(-1.0D));
	}
	
	/**
	 * Sets the distance to the closest {@link Shape}.
	 * 
	 * @param distance the new distance
	 */
	public void setDistance(final double distance) {
		this.distance = distance;
	}
	
	/**
	 * Sets the current {@link Ray}.
	 * 
	 * @param ray the new {@code Ray}
	 */
	public void setRay(final Ray ray) {
		this.ray = ray;
	}
	
	/**
	 * Sets the current {@link Scene}.
	 * 
	 * @param scene the new {@code Scene}
	 */
	public void setScene(final Scene scene) {
		this.scene = scene;
	}
	
	/**
	 * Sets the closest {@link Shape} being intersected.
	 * 
	 * @param shape the new {@code Shape}
	 */
	public void setShape(final Shape shape) {
		this.shape = shape;
	}
	
	/**
	 * Sets the current surface intersection {@link Point}.
	 * 
	 * @param surfaceIntersectionPoint the new surface intersection {@code Point}
	 */
	public void setSurfaceIntersectionPoint(final Point surfaceIntersectionPoint) {
		this.surfaceIntersectionPoint = surfaceIntersectionPoint;
	}
	
	/**
	 * Sets the new surface normal.
	 * 
	 * @param surfaceNormal the new surface normal
	 */
	public void setSurfaceNormal(final Vector surfaceNormal) {
		this.surfaceNormal = surfaceNormal;
	}
	
	/**
	 * Sets the new properly oriented surface normal.
	 * <p>
	 * By properly oriented means that it takes into account for refraction.
	 * 
	 * @param surfaceNormalProperlyOriented the new properly oriented surface normal
	 */
	public void setSurfaceNormalProperlyOriented(final Vector surfaceNormalProperlyOriented) {
		this.surfaceNormalProperlyOriented = surfaceNormalProperlyOriented;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new empty {@code Intersection} instance.
	 * 
	 * @return a new empty {@code Intersection} instance
	 */
	public static Intersection newInstance() {
		return new Intersection();
	}
}