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

import java.util.Objects;

/**
 * A {@link BoundingVolume} in the form of a bounding sphere.
 * <p>
 * This class is immutable and therefore also thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class BoundingSphere implements BoundingVolume {
	private final double radius;
	private final Point center;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BoundingSphere(final double radius, final Point center) {
		this.radius = radius;
		this.center = center;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, this {@code BoundingSphere} contains {@code point}, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point the {@link Point} to check for containment
	 * @return {@code true} if, and only if, this {@code BoundingSphere} contains {@code point}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point point) {
		return this.center.distanceSquared(point) < this.radius * this.radius;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code BoundingSphere}, and that instance is equal to this instance, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare against for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code BoundingSphere}, and that instance is equal to this instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof BoundingSphere)) {
			return false;
		} else if(!Objects.equals(Double.valueOf(this.radius), Double.valueOf(BoundingSphere.class.cast(object).radius))) {
			return false;
		} else if(!Objects.equals(this.center, BoundingSphere.class.cast(object).center)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code BoundingSphere} intersects with {@code boundingVolume}, {@code false} otherwise.
	 * <p>
	 * If {@code boundingVolume} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an unsupported {@link BoundingVolume} is supplied, an {@code UnsupportedOperationException} will be thrown.
	 * <p>
	 * The currently supported {@code BoundingVolume}s are {@link AxisAlignedBoundingBox} and {@code BoundingSphere}.
	 * 
	 * @param boundingVolume the {@code BoundingVolume} to check for intersection with this {@code BoundingSphere} instance
	 * @return {@code true} if, and only if, this {@code BoundingSphere} intersects with {@code boundingVolume}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code boundingVolume} is {@code null}
	 * @throws UnsupportedOperationException thrown if, and only if, an unsupported {@code BoundingVolume} is supplied
	 */
	@Override
	public boolean intersects(final BoundingVolume boundingVolume) {
		if(boundingVolume instanceof AxisAlignedBoundingBox) {
			return contains(boundingVolume.getClosestPointTo(this.center)) || boundingVolume.contains(getClosestPointTo(boundingVolume.getCenter()));
		} else if(boundingVolume instanceof BoundingSphere) {
			return contains(boundingVolume.getClosestPointTo(this.center));
		} else {
			throw new UnsupportedOperationException(String.format("Unsupported intersection test: %s.intersects(%s)", BoundingSphere.class.getSimpleName(), boundingVolume.getClass().getSimpleName()));
		}
	}
	
	/**
	 * Returns the diameter of this {@code BoundingSphere} instance.
	 * 
	 * @return the diameter of this {@code BoundingSphere} instance
	 */
	public double getDiameter() {
		return this.radius * 2.0D;
	}
	
	/**
	 * Returns the radius of this {@code BoundingSphere} instance.
	 * 
	 * @return the radius of this {@code BoundingSphere} instance
	 */
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * Returns the surface area of this {@code BoundingSphere}.
	 * 
	 * @return the surface area of this {@code BoundingSphere}
	 */
	@Override
	public double getSurfaceArea() {
		return 4.0D * Math.PI * Math.pow(this.radius, 3.0D);
	}
	
	/**
	 * Returns the volume of this {@code BoundingSphere}.
	 * 
	 * @return the volume of this {@code BoundingSphere}
	 */
	@Override
	public double getVolume() {
		return 4.0D / 3.0D * Math.PI * Math.pow(this.radius, 3.0D);
	}
	
	/**
	 * Returns a hash-code for this {@code BoundingSphere} instance.
	 * 
	 * @return a hash-code for this {@code BoundingSphere} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.radius), this.center);
	}
	
	/**
	 * Returns the center {@link Point} in this {@code BoundingSphere}.
	 * <p>
	 * This method returns a copy of the center {@code Point}.
	 * 
	 * @return the center {@code Point} in this {@code BoundingSphere}
	 */
	@Override
	public Point getCenter() {
		return this.center.copy();
	}
	
	/**
	 * Returns the {@link Point} inside this {@code BoundingSphere} that is the closest to {@code point}.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@code Point}
	 * @return the {@code Point} inside this {@code BoundingSphere} that is the closest to {@code point}
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public Point getClosestPointTo(final Point point) {
		final Vector direction = point.copyAndSubtract(this.center).toVector().normalize();
		
		final Point surfaceIntersectionPoint = this.center.copyAndAdd(direction.multiply(this.radius));
		
		final double distance0 = this.center.distanceSquared(surfaceIntersectionPoint);
		final double distance1 = this.center.distanceSquared(point);
		
		return distance0 <= distance1 ? surfaceIntersectionPoint : point.copy();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code BoundingSphere} instance.
	 * 
	 * @return a {@code String} representation of this {@code BoundingSphere} instance
	 */
	@Override
	public String toString() {
		return String.format("BoundingSphere: [Center=%s], [Radius=%s]", this.center, Double.toString(this.radius));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code BoundingSphere} given a radius and {@code Point.zero()} as center.
	 * 
	 * @param radius the radius for the {@code BoundingSphere}
	 * @return a new {@code BoundingSphere} given a radius and {@code Point.zero()} as center
	 */
	public static BoundingSphere newInstance(final double radius) {
		return new BoundingSphere(radius, Point.zero());
	}
	
	/**
	 * Returns a new {@code BoundingSphere} given a radius and {@code center} as center.
	 * <p>
	 * The current implementation will copy {@code center} and use the copy internally.
	 * <p>
	 * If {@code center} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param radius the radius for the {@code BoundingSphere}
	 * @param center the center {@link Point} for the {@code BoundingSphere}
	 * @return a new {@code BoundingSphere} given a radius and {@code center} as center
	 * @throws NullPointerException thrown if, and only if, {@code center} is {@code null}
	 */
	public static BoundingSphere newInstance(final double radius, final Point center) {
		return new BoundingSphere(radius, Objects.requireNonNull(center, "center == null").copy());
	}
}