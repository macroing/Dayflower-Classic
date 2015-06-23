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

import java.lang.reflect.Field;//TODO: Fix this class and remove this comment once done. Add Javadocs etc.

/**
 * A model of a ray differential.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class RayDifferential {
	private boolean hasDifferentials;
	private final Point originX = Point.zero();
	private final Point originY = Point.zero();
	private final Ray ray;
	private final Vector directionX = Vector.zero();
	private final Vector directionY = Vector.zero();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private RayDifferential(final Ray ray) {
		this.ray = ray;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, this {@code RayDifferential} has differentials, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code RayDifferential} has differentials, {@code false} otherwise
	 */
	public boolean hasDifferentials() {
		return this.hasDifferentials;
	}
	
	/**
	 * Returns the origin X of this {@code RayDifferential}.
	 * <p>
	 * This method will return a copy of the underlying {@code Point}.
	 * 
	 * @return the origin X of this {@code RayDifferential}
	 */
	public Point getOriginX() {
		return this.originX.copy();
	}
	
	/**
	 * Returns the origin Y of this {@code RayDifferential}.
	 * <p>
	 * This method will return a copy of the underlying {@code Point}.
	 * 
	 * @return the origin Y of this {@code RayDifferential}
	 */
	public Point getOriginY() {
		return this.originY.copy();
	}
	
	/**
	 * Returns the {@code Ray} of this {@code RayDifferential}.
	 * <p>
	 * This method will return a copy of the underlying {@code Ray}.
	 * 
	 * @return the {@code Ray} of this {@code RayDifferential}
	 */
	public Ray getRay() {
		return this.ray.copy();
	}
	
	/**
	 * Scales the differentials of this {@code RayDifferential} using {@code scalar}.
	 * <p>
	 * Returns this {@code RayDifferential}, such that method chaining is possible.
	 * 
	 * @param scalar the scalar to scale with
	 * @return this {@code RayDifferential}, such that method chaining is possible
	 */
	public RayDifferential scaleDifferentials(final double scalar) {
		final Point origin = this.ray.getOrigin();
		
		final Vector direction = this.ray.getDirection();
		
		this.originX.subtract(origin).multiply(scalar).add(origin);
		this.originY.subtract(origin).multiply(scalar).add(origin);
		
		this.directionX.subtract(direction).multiply(scalar).add(direction);
		this.directionY.subtract(direction).multiply(scalar).add(direction);
		
		return this;
	}
	
	/**
	 * Sets whether this {@code RayDifferential} has differentials.
	 * <p>
	 * Returns this {@code RayDifferential}, such that method chaining is possible.
	 * 
	 * @param hasDifferentials whether this {@code RayDifferential} has differentials
	 * @return this {@code RayDifferential}, such that method chaining is possible
	 */
	public RayDifferential setDifferentials(final boolean hasDifferentials) {
		this.hasDifferentials = hasDifferentials;
		
		return this;
	}
	
	/**
	 * Sets a new direction X for this {@code RayDifferential}.
	 * <p>
	 * Returns this {@code RayDifferential}, such that method chaining is possible.
	 * <p>
	 * If {@code directionX} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param directionX the new direction X
	 * @return this {@code RayDifferential}, such that method chaining is possible
	 * @throws NullPointerException thrown if, and only if, {@code directionX} is {@code null}
	 */
	public RayDifferential setDirectionX(final Vector directionX) {
		this.directionX.set(directionX);
		
		return this;
	}
	
	/**
	 * Sets a new direction Y for this {@code RayDifferential}.
	 * <p>
	 * Returns this {@code RayDifferential}, such that method chaining is possible.
	 * <p>
	 * If {@code directionY} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param directionY the new direction Y
	 * @return this {@code RayDifferential}, such that method chaining is possible
	 * @throws NullPointerException thrown if, and only if, {@code directionY} is {@code null}
	 */
	public RayDifferential setDirectionY(final Vector directionY) {
		this.directionY.set(directionY);
		
		return this;
	}
	
	/**
	 * Sets a new origin X for this {@code RayDifferential}.
	 * <p>
	 * Returns this {@code RayDifferential}, such that method chaining is possible.
	 * <p>
	 * If {@code originX} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param originX the new origin X
	 * @return this {@code RayDifferential}, such that method chaining is possible
	 * @throws NullPointerException thrown if, and only if, {@code originX} is {@code null}
	 */
	public RayDifferential setOriginX(final Point originX) {
		this.originX.set(originX);
		
		return this;
	}
	
	/**
	 * Sets a new origin Y for this {@code RayDifferential}.
	 * <p>
	 * Returns this {@code RayDifferential}, such that method chaining is possible.
	 * <p>
	 * If {@code originY} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param originY the new origin Y
	 * @return this {@code RayDifferential}, such that method chaining is possible
	 * @throws NullPointerException thrown if, and only if, {@code originY} is {@code null}
	 */
	public RayDifferential setOriginY(final Point originY) {
		this.originY.set(originY);
		
		return this;
	}
	
	/**
	 * Sets a new {@code Ray} for this {@code RayDifferential}.
	 * <p>
	 * Returns this {@code RayDifferential}, such that method chaining is possible.
	 * <p>
	 * If {@code ray} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param ray the new {@code Ray}
	 * @return this {@code RayDifferential}, such that method chaining is possible
	 * @throws NullPointerException thrown if, and only if, {@code ray} is {@code null}
	 */
	public RayDifferential setRay(final Ray ray) {
		this.ray.set(ray);
		
		return this;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code RayDifferentials} instance.
	 * 
	 * @return a {@code String} representation of this {@code RayDifferentials} instance
	 */
	@Override
	public String toString() {
		return String.format("RayDifferential: [Ray=%s], [OriginX=%s], [OriginY=%s], [DirectionX=%s], [DirectionY=%s], [HasDifferentials=%s]", this.ray, this.originX, this.originY, this.directionX, this.directionY, Boolean.toString(this.hasDifferentials));
	}
	
	/**
	 * Returns the direction X of this {@code RayDifferential}.
	 * <p>
	 * This method will return a copy of the underlying {@code Vector}.
	 * 
	 * @return the direction X of this {@code RayDifferential}
	 */
	public Vector getDirectionX() {
		return this.directionX.copy();
	}
	
	/**
	 * Returns the direction Y of this {@code RayDifferential}.
	 * <p>
	 * This method will return a copy of the underlying {@code Vector}.
	 * 
	 * @return the direction Y of this {@code RayDifferential}
	 */
	public Vector getDirectionY() {
		return this.directionY.copy();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code RayDifferential} given a {@code Ray}.
	 * <p>
	 * This method will copy {@code ray}.
	 * <p>
	 * If {@code ray} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param ray the {@code Ray} to use by this {@code RayDifferential}
	 * @return a new {@code RayDifferential} given a {@code Ray}
	 * @throws NullPointerException thrown if, and only if, {@code ray} is {@code null}
	 */
	public static RayDifferential newInstance(final Ray ray) {
		return new RayDifferential(ray.copy());
	}
}