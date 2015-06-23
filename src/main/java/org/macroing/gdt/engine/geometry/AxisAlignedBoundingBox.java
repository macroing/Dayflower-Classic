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
 * A {@link BoundingVolume} in the form of an axis-aligned bounding box.
 * <p>
 * This class is immutable and therefore also thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class AxisAlignedBoundingBox implements BoundingVolume {
	private final Point maximum;
	private final Point minimum;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private AxisAlignedBoundingBox(final Point point) {
		this.maximum = point;
		this.minimum = point;
	}
	
	private AxisAlignedBoundingBox(final Point point0, final Point point1) {
		final double maximumX = Math.max(point0.getX(), point1.getX());
		final double maximumY = Math.max(point0.getY(), point1.getY());
		final double maximumZ = Math.max(point0.getZ(), point1.getZ());
		final double minimumX = Math.min(point0.getX(), point1.getX());
		final double minimumY = Math.min(point0.getY(), point1.getY());
		final double minimumZ = Math.min(point0.getZ(), point1.getZ());
		
		this.maximum = new Point(maximumX, maximumY, maximumZ);
		this.minimum = new Point(minimumX, minimumY, minimumZ);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, this {@code AxisAlignedBoundingBox} contains {@code point}, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point the {@link Point} to check for containment
	 * @return {@code true} if, and only if, this {@code AxisAlignedBoundingBox} contains {@code point}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point point) {
		final boolean containsX = point.getX() >= this.minimum.getX() && point.getX() <= this.maximum.getX();
		final boolean containsY = point.getY() >= this.minimum.getY() && point.getY() <= this.maximum.getY();
		final boolean containsZ = point.getZ() >= this.minimum.getZ() && point.getZ() <= this.maximum.getZ();
		final boolean contains = containsX && containsY && containsZ;
		
		return contains;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code AxisAlignedBoundingBox}, and that instance is equal to this instance, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare against for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code AxisAlignedBoundingBox}, and that instance is equal to this instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof AxisAlignedBoundingBox)) {
			return false;
		} else if(!Objects.equals(this.maximum, AxisAlignedBoundingBox.class.cast(object).maximum)) {
			return false;
		} else if(!Objects.equals(this.minimum, AxisAlignedBoundingBox.class.cast(object).minimum)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code AxisAlignedBoundingBox} intersects with {@code boundingVolume}, {@code false} otherwise.
	 * <p>
	 * If {@code boundingVolume} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an unsupported {@link BoundingVolume} is supplied, an {@code UnsupportedOperationException} will be thrown.
	 * <p>
	 * The currently supported {@code BoundingVolume}s are {@code AxisAlignedBoundingBox} and {@link BoundingSphere}.
	 * 
	 * @param boundingVolume the {@code BoundingVolume} to check for intersection with this {@code AxisAlignedBoundingBox} instance
	 * @return {@code true} if, and only if, this {@code AxisAlignedBoundingBox} intersects with {@code boundingVolume}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code boundingVolume} is {@code null}
	 * @throws UnsupportedOperationException thrown if, and only if, an unsupported {@code BoundingVolume} is supplied
	 */
	@Override
	public boolean intersects(final BoundingVolume boundingVolume) {
		if(boundingVolume instanceof AxisAlignedBoundingBox) {
			final AxisAlignedBoundingBox axisAlignedBoundingBox = AxisAlignedBoundingBox.class.cast(boundingVolume);
			
			final boolean intersectsX = this.minimum.getX() <= axisAlignedBoundingBox.maximum.getX() && this.maximum.getX() >= axisAlignedBoundingBox.minimum.getX();
			final boolean intersectsY = this.minimum.getY() <= axisAlignedBoundingBox.maximum.getY() && this.maximum.getY() >= axisAlignedBoundingBox.minimum.getY();
			final boolean intersectsZ = this.minimum.getZ() <= axisAlignedBoundingBox.maximum.getZ() && this.maximum.getZ() >= axisAlignedBoundingBox.minimum.getZ();
			
			return intersectsX && intersectsY && intersectsZ;
		} else if(boundingVolume instanceof BoundingSphere) {
			return boundingVolume.intersects(this);
		} else {
			throw new UnsupportedOperationException(String.format("Unsupported intersection test: %s.intersects(%s)", AxisAlignedBoundingBox.class.getSimpleName(), boundingVolume.getClass().getSimpleName()));
		}
	}
	
	/**
	 * Returns a {@link BoundingSphere} that completely encapsulates this {@code AxisAlignedBoundingBox} instance.
	 * 
	 * @return a {@code BoundingSphere} that completely encapsulates this {@code AxisAlignedBoundingBox} instance
	 */
	public BoundingSphere getBoundingSphere() {
		final Point position = this.minimum.copyAndMultiply(0.5D).add(this.maximum.copyAndMultiply(0.5D));
		
		final double radius = position.distance(this.maximum);
		
		return BoundingSphere.newInstance(radius, position);
	}
	
	/**
	 * Returns the surface area of this {@code AxisAlignedBoundingBox}.
	 * 
	 * @return the surface area of this {@code AxisAlignedBoundingBox}
	 */
	@Override
	public double getSurfaceArea() {
		final Vector vector = this.maximum.copyAndSubtract(this.minimum).toVector();
		
		final double x = vector.getX();
		final double y = vector.getY();
		final double z = vector.getZ();
		final double surfaceArea = 2.0D * (x * y + x * z + y * z);
		
		return surfaceArea;
	}
	
	/**
	 * Returns the volume of this {@code AxisAlignedBoundingBox}.
	 * 
	 * @return the volume of this {@code AxisAlignedBoundingBox}
	 */
	@Override
	public double getVolume() {
		final Vector vector = this.maximum.copyAndSubtract(this.minimum).toVector();
		
		final double x = vector.getX();
		final double y = vector.getY();
		final double z = vector.getZ();
		final double volume = x * y * z;
		
		return volume;
	}
	
	/**
	 * Returns a hash-code for this {@code AxisAlignedBoundingBox} instance.
	 * 
	 * @return a hash-code for this {@code AxisAlignedBoundingBox} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.maximum, this.minimum);
	}
	
	/**
	 * Returns the center {@link Point} in this {@code AxisAlignedBoundingBox}.
	 * 
	 * @return the center {@code Point} in this {@code AxisAlignedBoundingBox}
	 */
	@Override
	public Point getCenter() {
		final double maximumX = this.maximum.getX();
		final double maximumY = this.maximum.getY();
		final double maximumZ = this.maximum.getZ();
		final double minimumX = this.minimum.getX();
		final double minimumY = this.minimum.getY();
		final double minimumZ = this.minimum.getZ();
		final double x = minimumX + (maximumX - minimumX) * 0.5D;
		final double y = minimumY + (maximumY - minimumY) * 0.5D;
		final double z = minimumZ + (maximumZ - minimumZ) * 0.5D;
		
		final Point point = new Point(x, y, z);
		
		return point;
	}
	
	/**
	 * Returns the {@link Point} inside this {@code AxisAlignedBoundingBox} that is the closest to {@code point}.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@code Point}
	 * @return the {@code Point} inside this {@code BoundingVolume} that is the closest to {@code point}
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public Point getClosestPointTo(final Point point) {
		final double maximumX = this.maximum.getX();
		final double maximumY = this.maximum.getY();
		final double maximumZ = this.maximum.getZ();
		
		final double minimumX = this.minimum.getX();
		final double minimumY = this.minimum.getY();
		final double minimumZ = this.minimum.getZ();
		
		double x = point.getX();
		double y = point.getY();
		double z = point.getZ();
		
		x = x < minimumX ? minimumX : x > maximumX ? maximumX : x;
		y = y < minimumY ? minimumY : y > maximumY ? maximumY : y;
		z = z < minimumZ ? minimumZ : y > maximumZ ? maximumZ : z;
		
		return new Point(x, y, z);
	}
	
	/**
	 * Returns the {@link Point} with the maximum X-, Y- and Z-axes.
	 * <p>
	 * Modifying the returned {@code Point} will not affect this {@code AxisAlignedBoundingBox} instance.
	 * 
	 * @return the {@code Point} with the maximum X-, Y- and Z-axes
	 */
	public Point getMaximum() {
		return this.maximum.copy();
	}
	
	/**
	 * Returns the {@link Point} with the minimum X-, Y- and Z-axes.
	 * <p>
	 * Modifying the returned {@code Point} will not affect this {@code AxisAlignedBoundingBox} instance.
	 * 
	 * @return the {@code Point} with the minimum X-, Y- and Z-axes
	 */
	public Point getMinimum() {
		return this.minimum.copy();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code AxisAlignedBoundingBox} instance.
	 * 
	 * @return a {@code String} representation of this {@code AxisAlignedBoundingBox} instance
	 */
	@Override
	public String toString() {
		return String.format("AxisAlignedBoundingBox: [Minimum=%s], [Maximum=%s]", this.minimum, this.maximum);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code AxisAlignedBoundingBox} instance given the two {@link Point}s {@code Point.maximum()} and {@code Point.minimum()}.
	 * 
	 * @return a new {@code AxisAlignedBoundingBox} instance given the two {@code Point}s {@code Point.maximum()} and {@code Point.minimum()}
	 */
	public static AxisAlignedBoundingBox newInstance() {
		return newInstance(Point.maximum(), Point.minimum());
	}
	
	/**
	 * Returns a new {@code AxisAlignedBoundingBox} instance given a copy of {@code point} as both minimum and maximum {@link Point}.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point the {@code Point} to be used as both minimum and maximum {@code Point}
	 * @return a new {@code AxisAlignedBoundingBox} instance given a copy of {@code point} as both minimum and maximum {@code Point}
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public static AxisAlignedBoundingBox newInstance(final Point point) {
		return new AxisAlignedBoundingBox(point.copy());
	}
	
	/**
	 * Returns a new {@code AxisAlignedBoundingBox} instance given a copy of both {@code point0} and {@code point1} as two {@link Point}s from which to calculate the minimum and maximum {@code Point}s.
	 * <p>
	 * If either {@code point0} or {@code point1} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point0 one of the two {@code Point}s used for calculating the minimum and maximum {@code Point}s
	 * @param point1 one of the two {@code Point}s used for calculating the minimum and maximum {@code Point}s
	 * @return a new {@code AxisAlignedBoundingBox} instance given a copy of both {@code point0} and {@code point1} as two {@code Point}s from which to calculate the minimum and maximum {@code Point}s
	 * @throws NullPointerException thrown if, and only if, either {@code point0} or {@code point1} are {@code null}
	 */
	public static AxisAlignedBoundingBox newInstance(final Point point0, final Point point1) {
		return new AxisAlignedBoundingBox(point0.copy(), point1.copy());
	}
	
	/**
	 * Returns an {@code AxisAlignedBoundingBox} instance that corresponds to the union of {@code boundingVolume0} and {@code boundingVolume1}.
	 * <p>
	 * If either {@code boundingVolume0} or {@code boundingVolume1} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If any of the two {@link BoundingVolume}s are not supported, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Currently, the only supported {@code BoundingVolume}s are instances of the {@code AxisAlignedBoundingBox} class.
	 * 
	 * @param boundingVolume0 one of the two {@code BoundingVolume} instances to create a union from
	 * @param boundingVolume1 one of the two {@code BoundingVolume} instances to create a union from
	 * @return an {@code AxisAlignedBoundingBox} instance that corresponds to the union of {@code boundingVolume0} and {@code boundingVolume1}
	 */
	public static AxisAlignedBoundingBox union(final BoundingVolume boundingVolume0, final BoundingVolume boundingVolume1) {
		if(boundingVolume0 instanceof AxisAlignedBoundingBox && boundingVolume1 instanceof AxisAlignedBoundingBox) {
			return union(AxisAlignedBoundingBox.class.cast(boundingVolume0), AxisAlignedBoundingBox.class.cast(boundingVolume1));
		}
		
		throw new IllegalArgumentException(String.format("boundingVolume0 instanceof AxisAlignedBoundingBox == %s, boundingVolume1 instanceof AxisAlignedBoundingBox == %s", Boolean.valueOf(boundingVolume0 instanceof AxisAlignedBoundingBox), Boolean.valueOf(boundingVolume1 instanceof AxisAlignedBoundingBox)));
	}
	
	/**
	 * Returns an {@code AxisAlignedBoundingBox} instance that corresponds to the union of {@code axisAlignedBoundingBox0} and {@code axisAlignedBoundingBox1}.
	 * <p>
	 * If either {@code axisAlignedBoundingBox0} or {@code axisAlignedBoundingBox1} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param axisAlignedBoundingBox0 one of the two {@code AxisAlignedBoundingBox} instances to create a union from
	 * @param axisAlignedBoundingBox1 one of the two {@code AxisAlignedBoundingBox} instances to create a union from
	 * @return an {@code AxisAlignedBoundingBox} instance that corresponds to the union of {@code axisAlignedBoundingBox0} and {@code axisAlignedBoundingBox1}
	 * @throws NullPointerException thrown if, and only if, either {@code axisAlignedBoundingBox0} or {@code axisAlignedBoundingBox1} are {@code null}
	 */
	public static AxisAlignedBoundingBox union(final AxisAlignedBoundingBox axisAlignedBoundingBox0, final AxisAlignedBoundingBox axisAlignedBoundingBox1) {
		final double maximumX = Math.max(axisAlignedBoundingBox0.maximum.getX(), axisAlignedBoundingBox1.maximum.getX());
		final double maximumY = Math.max(axisAlignedBoundingBox0.maximum.getY(), axisAlignedBoundingBox1.maximum.getY());
		final double maximumZ = Math.max(axisAlignedBoundingBox0.maximum.getZ(), axisAlignedBoundingBox1.maximum.getZ());
		final double minimumX = Math.min(axisAlignedBoundingBox0.minimum.getX(), axisAlignedBoundingBox1.minimum.getX());
		final double minimumY = Math.min(axisAlignedBoundingBox0.minimum.getY(), axisAlignedBoundingBox1.minimum.getY());
		final double minimumZ = Math.min(axisAlignedBoundingBox0.minimum.getZ(), axisAlignedBoundingBox1.minimum.getZ());
		
		final Point maximum = new Point(maximumX, maximumY, maximumZ);
		final Point minimum = new Point(minimumX, minimumY, minimumZ);
		
		return new AxisAlignedBoundingBox(maximum, minimum);
	}
	
	/**
	 * Returns an {@code AxisAlignedBoundingBox} instance that corresponds to the union of {@code axisAlignedBoundingBox} and {@code point}.
	 * <p>
	 * If either {@code axisAlignedBoundingBox} or {@code point} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param axisAlignedBoundingBox the {@code AxisAlignedBoundingBox} to be used in the process
	 * @param point the {@link Point} to be used in the process
	 * @return an {@code AxisAlignedBoundingBox} instance that corresponds to the union of {@code axisAlignedBoundingBox} and {@code point}
	 * @throws NullPointerException thrown if, and only if, either {@code axisAlignedBoundingBox} or {@code point} are {@code null}
	 */
	public static AxisAlignedBoundingBox union(final AxisAlignedBoundingBox axisAlignedBoundingBox, final Point point) {
		final double maximumX = Math.max(axisAlignedBoundingBox.maximum.getX(), point.getX());
		final double maximumY = Math.max(axisAlignedBoundingBox.maximum.getY(), point.getY());
		final double maximumZ = Math.max(axisAlignedBoundingBox.maximum.getZ(), point.getZ());
		final double minimumX = Math.min(axisAlignedBoundingBox.minimum.getX(), point.getX());
		final double minimumY = Math.min(axisAlignedBoundingBox.minimum.getY(), point.getY());
		final double minimumZ = Math.min(axisAlignedBoundingBox.minimum.getZ(), point.getZ());
		
		final Point maximum = new Point(maximumX, maximumY, maximumZ);
		final Point minimum = new Point(minimumX, minimumY, minimumZ);
		
		return new AxisAlignedBoundingBox(maximum, minimum);
	}
}