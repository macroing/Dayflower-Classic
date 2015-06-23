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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A class that models a point in space with double-precision floating-point format and three axes (X, Y and Z).
 * <p>
 * This class is mutable and therefore not suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Point {
	/**
	 * The index of the X-axis.
	 */
	public static final int AXIS_X = 0;
	
	/**
	 * The index of the Y-axis.
	 */
	public static final int AXIS_Y = 1;
	
	/**
	 * The index of the Z-axis.
	 */
	public static final int AXIS_Z = 2;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private double x;
	private double y;
	private double z;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Point} instance with its X-, Y- and Z-axes set to {@code x}, {@code y} and {@code z}, respectively.
	 * 
	 * @param x the position on the X-axis
	 * @param y the position on the Y-axis
	 * @param z the position on the Z-axis
	 */
	public Point(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Point}, and that instance is equal to this instance, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare against for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Point}, and that instance is equal to this instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Point)) {
			return false;
		} else if(Double.compare(this.x, Point.class.cast(object).x) != 0) {
			return false;
		} else if(Double.compare(this.y, Point.class.cast(object).y) != 0) {
			return false;
		} else if(Double.compare(this.z, Point.class.cast(object).z) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the distance between this {@code Point} instance and {@code point}.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point the {@code Point} to check the distance to
	 * @return the distance between this {@code Point} instance and {@code point}
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public double distance(final Point point) {
		return Math.sqrt(distanceSquared(point));
	}
	
	/**
	 * Returns the squared distance between this {@code Point} instance and {@code point}.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point the {@code Point} to check the squared distance to
	 * @return the squared distance between this {@code Point} instance and {@code point}
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public double distanceSquared(final Point point) {
		final double deltaX = point.getX() - this.x;
		final double deltaY = point.getY() - this.y;
		final double deltaZ = point.getZ() - this.z;
		final double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
		
		return distanceSquared;
	}
	
	/**
	 * Returns the value associated with the axis given by {@code axis}.
	 * <p>
	 * If {@code axis} is invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param axis the index of the axis
	 * @return the value associated with the axis given by {@code axis}
	 * @throws IllegalArgumentException thrown if, and only if, {@code axis} is invalid
	 */
	public double get(final int axis) {
		switch(axis) {
			case AXIS_X:
				return this.x;
			case AXIS_Y:
				return this.y;
			case AXIS_Z:
				return this.z;
			default:
				throw new IllegalArgumentException(String.format("The axis %s does not refer to any of X, Y or Z.", Integer.toString(axis)));
		}
	}
	
	/**
	 * Returns the position along the X-axis.
	 * 
	 * @return the position along the X-axis
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Returns the position along the Y-axis.
	 * 
	 * @return the position along the Y-axis
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Returns the position along the Z-axis.
	 * 
	 * @return the position along the Z-axis
	 */
	public double getZ() {
		return this.z;
	}
	
	/**
	 * Returns a hash-code for this {@code Point} instance.
	 * 
	 * @return a hash-code for this {@code Point} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z));
	}
	
	/**
	 * Adds the scalar values {@code x}, {@code y} and {@code z} to this {@code Point} instance.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndAdd(x, y, z)} instead.
	 * 
	 * @param x a scalar value to be added to the X-axis
	 * @param y a scalar value to be added to the Y-axis
	 * @param z a scalar value to be added to the Z-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Point add(final double x, final double y, final double z) {
		return set(this.x + x, this.y + y, this.z + z);
	}
	
	/**
	 * Adds {@code point} to this {@code Point} instance.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndAdd(point)} instead.
	 * 
	 * @param point the {@code Point} to add to this {@code Point} instance
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Point add(final Point point) {
		return add(point.x, point.y, point.z);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param vector
	 * @return
	 * @throws NullPointerException
	 */
	public Point add(final Vector vector) {
		return add(vector.getX(), vector.getY(), vector.getZ());
	}
	
	/**
	 * Returns a new copy of this {@code Point} instance.
	 * 
	 * @return a new copy of this {@code Point} instance
	 */
	public Point copy() {
		return valueOf(this.x, this.y, this.z);
	}
	
	/**
	 * Copies and adds the scalar values {@code x}, {@code y} and {@code z} to the new {@code Point} instance.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code add(x, y, z)} instead.
	 * 
	 * @param x a scalar value to be added to the X-axis of a copy of this {@code Point} instance
	 * @param y a scalar value to be added to the Y-axis of a copy of this {@code Point} instance
	 * @param z a scalar value to be added to the Z-axis of a copy of this {@code Point} instance
	 * @return a copy of this {@code Point} instance
	 */
	public Point copyAndAdd(final double x, final double y, final double z) {
		return copy().add(x, y, z);
	}
	
	/**
	 * Copies and adds {@code point} to the new {@code Point} instance.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code add(point)} instead.
	 * 
	 * @param point the {@code Point} to add to the new {@code Point} instance
	 * @return a copy of this {@code Point} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Point copyAndAdd(final Point point) {
		return copy().add(point);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param vector
	 * @return
	 * @throws NullPointerException
	 */
	public Point copyAndAdd(final Vector vector) {
		return copy().add(vector);
	}
	
	/**
	 * Copies and divides the new {@code Point} instance with the scalar value {@code scalar}.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code divide(scalar)} instead.
	 * 
	 * @param scalar a scalar value to divide the X-, Y- and Z-axes with
	 * @return a copy of this {@code Point} instance
	 */
	public Point copyAndDivide(final double scalar) {
		return copy().divide(scalar);
	}
	
	/**
	 * Copies and divides the new {@code Point} instance with the scalar values {@code x}, {@code y} and {@code z}.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code divide(x, y, z)} instead.
	 * 
	 * @param x a scalar value to divide the X-axis with
	 * @param y a scalar value to divide the Y-axis with
	 * @param z a scalar value to divide the Z-axis with
	 * @return a copy of this {@code Point} instance
	 */
	public Point copyAndDivide(final double x, final double y, final double z) {
		return copy().divide(x, y, z);
	}
	
	/**
	 * Copies and divides the new {@code Point} instance with {@code point}.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code divide(point)} instead.
	 * 
	 * @param point the {@code Point} to divide the copy of this {@code Point} instance with
	 * @return a copy of this {@code Point} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Point copyAndDivide(final Point point) {
		return copy().divide(point);
	}
	
	/**
	 * Copies and multiplies the new {@code Point} instance with the scalar value {@code scalar}.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code multiply(scalar)} instead.
	 * 
	 * @param scalar a scalar value to multiply the X-, Y- and Z-axes with
	 * @return a copy of this {@code Point} instance
	 */
	public Point copyAndMultiply(final double scalar) {
		return copy().multiply(scalar);
	}
	
	/**
	 * Copies and multiplies the new {@code Point} instance with the scalar values {@code x}, {@code y} and {@code z}.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code multiply(x, y, z)} instead.
	 * 
	 * @param x a scalar value to multiply the X-axis with
	 * @param y a scalar value to multiply the Y-axis with
	 * @param z a scalar value to multiply the Z-axis with
	 * @return a copy of this {@code Point} instance
	 */
	public Point copyAndMultiply(final double x, final double y, final double z) {
		return copy().multiply(x, y, z);
	}
	
	/**
	 * Copies and multiplies the new {@code Point} instance with {@code point}.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code multiply(point)} instead.
	 * 
	 * @param point the {@code Point} to multiply the copy of this {@code Point} instance with
	 * @return a copy of this {@code Point} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Point copyAndMultiply(final Point point) {
		return copy().multiply(point);
	}
	
	/**
	 * Copies and sets a new position along the X-axis for the new {@code Point} instance.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code setX(x)} instead.
	 * 
	 * @param x the new position along the X-axis of a copy of this {@code Point} instance
	 * @return a copy of this {@code Point} instance
	 */
	public Point copyAndSetX(final double x) {
		return copy().setX(x);
	}
	
	/**
	 * Copies and sets a new position along the Y-axis for the new {@code Point} instance.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code setY(y)} instead.
	 * 
	 * @param y the new position along the Y-axis of a copy of this {@code Point} instance
	 * @return a copy of this {@code Point} instance
	 */
	public Point copyAndSetY(final double y) {
		return copy().setY(y);
	}
	
	/**
	 * Copies and sets a new position along the Z-axis for the new {@code Point} instance.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code setZ(z)} instead.
	 * 
	 * @param z the new position along the Z-axis of a copy of this {@code Point} instance
	 * @return a copy of this {@code Point} instance
	 */
	public Point copyAndSetZ(final double z) {
		return copy().setZ(z);
	}
	
	/**
	 * Copies and subtracts the scalar values {@code x}, {@code y} and {@code z} from the new {@code Point} instance.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code subtract(x, y, z)} instead.
	 * 
	 * @param x a scalar value to be subtracted from the X-axis of a copy of this {@code Point} instance
	 * @param y a scalar value to be subtracted from the Y-axis of a copy of this {@code Point} instance
	 * @param z a scalar value to be subtracted from the Z-axis of a copy of this {@code Point} instance
	 * @return a copy of this {@code Point} instance
	 */
	public Point copyAndSubtract(final double x, final double y, final double z) {
		return copy().subtract(x, y, z);
	}
	
	/**
	 * Copies and subtracts {@code point} from the new {@code Point} instance.
	 * <p>
	 * A copy of this {@code Point} instance will be returned.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code subtract(point)} instead.
	 * 
	 * @param point the {@code Point} to subtract from the new {@code Point} instance
	 * @return a copy of this {@code Point} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Point copyAndSubtract(final Point point) {
		return copy().subtract(point);
	}
	
	/**
	 * Divides this {@code Point} instance with the scalar value {@code scalar}.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndDivide(scalar)} instead.
	 * 
	 * @param scalar a scalar value to divide the X-, Y- and Z-axes with
	 * @return itself, such that you can chain multiple calls
	 */
	public Point divide(final double scalar) {
		return multiply(1.0D / scalar);
	}
	
	/**
	 * Divides this {@code Point} instance with the scalar values {@code x}, {@code y} and {@code z}.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndDivide(x, y, z)} instead.
	 * 
	 * @param x a scalar value to divide the X-axis with
	 * @param y a scalar value to divide the Y-axis with
	 * @param z a scalar value to divide the Z-axis with
	 * @return itself, such that you can chain multiple calls
	 */
	public Point divide(final double x, final double y, final double z) {
		return set(this.x / x, this.y / y, this.z / z);
	}
	
	/**
	 * Divides this {@code Point} instance with {@code point}.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndDivide(point)} instead.
	 * 
	 * @param point the {@code Point} to divide this {@code Point} instance with
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Point divide(final Point point) {
		return divide(point.x, point.y, point.z);
	}
	
	/**
	 * Multiplies this {@code Point} instance with the scalar value {@code scalar}.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndMultiply(scalar)} instead.
	 * 
	 * @param scalar a scalar value to multiply the X-, Y- and Z-axes with
	 * @return itself, such that you can chain multiple calls
	 */
	public Point multiply(final double scalar) {
		return multiply(scalar, scalar, scalar);
	}
	
	/**
	 * Multiplies this {@code Point} instance with the scalar values {@code x}, {@code y} and {@code z}.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndMultiply(x, y, z)} instead.
	 * 
	 * @param x a scalar value to multiply the X-axis with
	 * @param y a scalar value to multiply the Y-axis with
	 * @param z a scalar value to multiply the Z-axis with
	 * @return itself, such that you can chain multiple calls
	 */
	public Point multiply(final double x, final double y, final double z) {
		return set(this.x * x, this.y * y, this.z * z);
	}
	
	/**
	 * Multiplies this {@code Point} instance with {@code point}.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndMultiply(vector)} instead.
	 * 
	 * @param point the {@code Point} to multiply this {@code Point} instance with
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Point multiply(final Point point) {
		return multiply(point.x, point.y, point.z);
	}
	
	/**
	 * Sets a new position along the X-, Y- and Z-axes for this {@code Point} instance.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called.
	 * 
	 * @param x the new position along the X-axis
	 * @param y the new position along the Y-axis
	 * @param z the new position along the Z-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Point set(final double x, final double y, final double z) {
		return setX(x).setY(y).setZ(z);
	}
	
	/**
	 * Sets a new position along the X-, Y- and Z-axes for this {@code Point} instance.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called.
	 * 
	 * @param point the {@code Point} to set this {@code Point} instance to
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Point set(final Point point) {
		return set(point.x, point.y, point.z);
	}
	
	/**
	 * Sets a new position along the X-axis for this {@code Point} instance.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSetX(x)} instead.
	 * 
	 * @param x the new position along the X-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Point setX(final double x) {
		this.x = x;
		
		return this;
	}
	
	/**
	 * Sets a new position along the Y-axis for this {@code Point} instance.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSetY(y)} instead.
	 * 
	 * @param y the new position along the Y-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Point setY(final double y) {
		this.y = y;
		
		return this;
	}
	
	/**
	 * Sets a new position along the Z-axis for this {@code Point} instance.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSetZ(z)} instead.
	 * 
	 * @param z the new position along the Z-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Point setZ(final double z) {
		this.z = z;
		
		return this;
	}
	
	/**
	 * Subtracts the scalar values {@code x}, {@code y} and {@code z} from this {@code Point} instance.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSubtract(x, y, z)} instead.
	 * 
	 * @param x a scalar value to be subtracted from the X-axis
	 * @param y a scalar value to be subtracted from the Y-axis
	 * @param z a scalar value to be subtracted from the Z-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Point subtract(final double x, final double y, final double z) {
		return set(this.x - x, this.y - y, this.z - z);
	}
	
	/**
	 * Subtracts {@code point} from this {@code Point} instance.
	 * <p>
	 * The {@code Point} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSubtract(point)} instead.
	 * 
	 * @param point the {@code Point} to subtract from this {@code Point} instance
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Point subtract(final Point point) {
		return subtract(point.x, point.y, point.z);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Point} instance.
	 * 
	 * @return a {@code String} representation of this {@code Point} instance
	 */
	@Override
	public String toString() {
		return String.format("Point: [X=%s], [Y=%s], [Z=%s]", Double.toString(this.x), Double.toString(this.y), Double.toString(this.z));
	}
	
	/**
	 * Returns a {@code Vector} representation of this {@code Point} instance.
	 * <p>
	 * A similar method can be found in the {@code Vector} class. One that returns a {@code Point} representation of a {@code Vector} instance.
	 * 
	 * @return a {@code Vector} representation of this {@code Point} instance
	 */
	public Vector toVector() {
		return new Vector(this.x, this.y, this.z);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with the {@code Point}s in {@code points} copied.
	 * <p>
	 * If {@code points} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param points the array of {@code Point}s to copy
	 * @return a {@code List} with the {@code Point}s in {@code points} copied
	 * @throws NullPointerException thrown if, and only if, {@code points} is {@code null}
	 */
	public static List<Point> copyToList(final Point... points) {
		return Arrays.stream(points).map(point -> point.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns a new maximum {@code Point} instance.
	 * 
	 * @return a new maximum {@code Point} instance
	 */
	public static Point maximum() {
		return new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}
	
	/**
	 * Returns a new minimum {@code Point} instance.
	 * 
	 * @return a new minimum {@code Point} instance
	 */
	public static Point minimum() {
		return new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	}
	
	/**
	 * Returns a new {@code Point} instance with its X-, Y- and Z-axes set to {@code x}, {@code y} and {@code z}, respectively.
	 * 
	 * @param x the position on the X-axis
	 * @param y the position on the Y-axis
	 * @param z the position on the Z-axis
	 * @return a new {@code Point} instance with its X-, Y- and Z-axes set to {@code x}, {@code y} and {@code z}, respectively
	 */
	public static Point valueOf(final double x, final double y, final double z) {
		return new Point(x, y, z);
	}
	
	/**
	 * Returns a new {@code Point} instance with its X-, Y- and Z-axes set to {@code 1.0D}, {@code 0.0D} and {@code 0.0D}, respectively.
	 * 
	 * @return a new {@code Point} instance with its X-, Y- and Z-axes set to {@code 1.0D}, {@code 0.0D} and {@code 0.0D}, respectively
	 */
	public static Point x() {
		return new Point(1.0D, 0.0D, 0.0D);
	}
	
	/**
	 * Returns a new {@code Point} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code 1.0D} and {@code 0.0D}, respectively.
	 * 
	 * @return a new {@code Point} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code 1.0D} and {@code 0.0D}, respectively
	 */
	public static Point y() {
		return new Point(0.0D, 1.0D, 0.0D);
	}
	
	/**
	 * Returns a new {@code Point} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code 0.0D} and {@code 1.0D}, respectively.
	 * 
	 * @return a new {@code Point} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code 0.0D} and {@code 1.0D}, respectively
	 */
	public static Point z() {
		return new Point(0.0D, 0.0D, 1.0D);
	}
	
	/**
	 * Returns a new {@code Point} instance with its X-, Y- and Z-axes all set to {@code 0.0D}.
	 * 
	 * @return a new {@code Point} instance with its X-, Y- and Z-axes all set to {@code 0.0D}
	 */
	public static Point zero() {
		return new Point(0.0D, 0.0D, 0.0D);
	}
}