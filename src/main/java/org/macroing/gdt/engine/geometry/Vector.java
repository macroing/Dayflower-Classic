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
import java.util.Objects;

import org.macroing.gdt.engine.util.Functions;
import org.macroing.gdt.engine.util.MonteCarlo;
import org.macroing.gdt.engine.util.PRNG;

/**
 * A class that models an Euclidean vector with double-precision floating-point format and three axes (X, Y and Z).
 * <p>
 * This class is mutable and therefore not suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Vector {
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
	 * Constructs a new {@code Vector} instance with its X-, Y- and Z-axes set to {@code x}, {@code y} and {@code z}, respectively.
	 * 
	 * @param x the position on the X-axis
	 * @param y the position on the Y-axis
	 * @param z the position on the Z-axis
	 */
	public Vector(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Vector}, and that instance is equal to this instance, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare against for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Vector}, and that instance is equal to this instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Vector)) {
			return false;
		} else if(Double.compare(this.x, Vector.class.cast(object).x) != 0) {
			return false;
		} else if(Double.compare(this.y, Vector.class.cast(object).y) != 0) {
			return false;
		} else if(Double.compare(this.z, Vector.class.cast(object).z) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * TODO: Add Javadocs...
	 * 
	 * @param vector
	 * @return
	 */
	public boolean hasSameHemisphereAs(final Vector vector) {
		return this.z * vector.z > 0.0D;
	}
	
	/**
	 * TODO: Add Javadocs...
	 * 
	 * @param vector
	 * @return
	 * @throws NullPointerException
	 */
	public double angle(final Vector vector) {
		return Math.acos(dotProduct(vector) / Math.sqrt(lengthSquared() * vector.lengthSquared())) * 180.0D / Math.PI;
	}
	
	/**
	 * TODO: Add Javadocs...
	 * 
	 * @return
	 */
	public double cosPhi() {
		final double sinTheta = sinTheta();
		
		if(sinTheta == 0.0D) {
			return 1.0D;
		}
		
		return Functions.clamp(this.x / sinTheta, -1.0D, 1.0D);
	}
	
	/**
	 * TODO: Add Javadocs...
	 * 
	 * @return
	 */
	public double cosTheta() {
		return this.z;
	}
	
	/**
	 * TODO: Add Javadocs...
	 * 
	 * @return
	 */
	public double cosThetaAbsolute() {
		return Math.abs(this.z);
	}
	
	/**
	 * Returns the distance between this {@code Vector} instance and {@code vector}.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vector the {@code Vector} to check the distance to
	 * @return the distance between this {@code Vector} instance and {@code vector}
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public double distance(final Vector vector) {
		return Math.sqrt(distanceSquared(vector));
	}
	
	/**
	 * Returns the squared distance between this {@code Vector} instance and {@code vector}.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vector the {@code Vector} to check the squared distance to
	 * @return the squared distance between this {@code Vector} instance and {@code vector}
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public double distanceSquared(final Vector vector) {
		final double deltaX = vector.getX() - this.x;
		final double deltaY = vector.getY() - this.y;
		final double deltaZ = vector.getZ() - this.z;
		final double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
		
		return distanceSquared;
	}
	
	/**
	 * Returns the dot product between this {@code Vector} instance and {@code vector}.
	 * <p>
	 * The dot product can also be referred to as the inner product or the scalar product.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vector the {@code Vector} against which to calculate the dot product
	 * @return the dot product between this {@code Vector} instance and {@code vector}
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public double dotProduct(final Vector vector) {
		return this.x * vector.x + this.y * vector.y + this.z * vector.z;
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param vector
	 * @return
	 * @throws NullPointerException
	 */
	public double dotProductAbsolute(final Vector vector) {
		return Math.abs(dotProduct(vector));
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
	 * Returns the length of this {@code Vector} instance.
	 * <p>
	 * The length can also be referred to as the magnitude or the norm.
	 * <p>
	 * This method produces a result equivalent to {@code Math.sqrt(vector.lengthSquared())} and {@code Math.sqrt(vector.dotProduct(vector))}.
	 * 
	 * @return the length of this {@code Vector} instance
	 */
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	/**
	 * Returns the length of this {@code Vector} instance, but without performing a square root.
	 * <p>
	 * The length can also be referred to as the magnitude or the norm.
	 * <p>
	 * This method produces a result equivalent to {@code vector.dotProduct(vector)}.
	 * 
	 * @return the length of this {@code Vector} instance, but without performing a square root
	 */
	public double lengthSquared() {
		return dotProduct(this);
	}
	
	/**
	 * TODO: Add Javadocs...
	 * 
	 * @param vector
	 * @return
	 * @throws NullPointerException
	 */
	public double perpendicular(final Vector vector) {
		return this.x * vector.y - this.y * vector.x;
	}
	
	/**
	 * TODO: Add Javadocs...
	 * 
	 * @return
	 */
	public double sinTheta() {
		return Math.sqrt(sinThetaSquared());
	}
	
	/**
	 * TODO: Add Javadocs...
	 * 
	 * @return
	 */
	public double sinThetaSquared() {
		return Math.max(0.0D, 1.0D - cosTheta() * cosTheta());
	}
	
	/**
	 * Returns the spherical phi angle from this {@code Vector} instance.
	 * 
	 * @return the spherical phi angle from this {@code Vector} instance
	 */
	public double sphericalPhi() {
		double theta = Math.atan2(this.y, this.x);
		
		if(theta < 0.0D) {
			theta += MonteCarlo.PI_MULTIPLIED_BY_TWO;
		}
		
		return theta;
	}
	
	/**
	 * Returns the spherical theta angle from this {@code Vector} instance.
	 * 
	 * @return the spherical theta angle from this {@code Vector} instance
	 */
	public double sphericalTheta() {
		return Math.acos(Functions.clamp(this.z, -1.0D, 1.0D));
	}
	
	/**
	 * Returns a hash-code for this {@code Vector} instance.
	 * 
	 * @return a hash-code for this {@code Vector} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z));
	}
	
	/**
	 * Returns a {@code Point} representation of this {@code Vector} instance.
	 * <p>
	 * A similar method can be found in the {@code Point} class. One that returns a {@code Vector} representation of a {@code Point} instance.
	 * 
	 * @return a {@code Point} representation of this {@code Vector} instance
	 */
	public Point toPoint() {
		return new Point(this.x, this.y, this.z);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Vector} instance.
	 * 
	 * @return a {@code String} representation of this {@code Vector} instance
	 */
	@Override
	public String toString() {
		return String.format("Vector: [X=%s], [Y=%s], [Z=%s]", Double.toString(this.x), Double.toString(this.y), Double.toString(this.z));
	}
	
	/**
	 * Adds the scalar values {@code x}, {@code y} and {@code z} to this {@code Vector} instance.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndAdd(x, y, z)} instead.
	 * 
	 * @param x a scalar value to be added to the X-axis
	 * @param y a scalar value to be added to the Y-axis
	 * @param z a scalar value to be added to the Z-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector add(final double x, final double y, final double z) {
		return set(this.x + x, this.y + y, this.z + z);
	}
	
	/**
	 * Adds {@code vector} to this {@code Vector} instance.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndAdd(vector)} instead.
	 * 
	 * @param vector the {@code Vector} to add to this {@code Vector} instance
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector add(final Vector vector) {
		return add(vector.x, vector.y, vector.z);
	}
	
	/**
	 * Returns a new copy of this {@code Vector} instance.
	 * 
	 * @return a new copy of this {@code Vector} instance
	 */
	public Vector copy() {
		return valueOf(this.x, this.y, this.z);
	}
	
	/**
	 * Copies and adds the scalar values {@code x}, {@code y} and {@code z} to the new {@code Vector} instance.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code add(x, y, z)} instead.
	 * 
	 * @param x a scalar value to be added to the X-axis of a copy of this {@code Vector} instance
	 * @param y a scalar value to be added to the Y-axis of a copy of this {@code Vector} instance
	 * @param z a scalar value to be added to the Z-axis of a copy of this {@code Vector} instance
	 * @return a copy of this {@code Vector} instance
	 */
	public Vector copyAndAdd(final double x, final double y, final double z) {
		return copy().add(x, y, z);
	}
	
	/**
	 * Copies and adds {@code vector} to the new {@code Vector} instance.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code add(vector)} instead.
	 * 
	 * @param vector the {@code Vector} to add to the new {@code Vector} instance
	 * @return a copy of this {@code Vector} instance
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector copyAndAdd(final Vector vector) {
		return copy().add(vector);
	}
	
	/**
	 * Copies and sets the new {@code Vector} instance to the cross product between itself and {@code vector}.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code crossProduct(vector)} instead.
	 * 
	 * @param vector one of the two {@code Vector}s in the cross product
	 * @return a copy of this {@code Vector} instance
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector copyAndCrossProduct(final Vector vector) {
		return copy().crossProduct(vector);
	}
	
	/**
	 * Copies and divides the new {@code Vector} instance with the scalar value {@code scalar}.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code divide(scalar)} instead.
	 * 
	 * @param scalar a scalar value to divide the X-, Y- and Z-axes with
	 * @return a copy of this {@code Vector} instance
	 */
	public Vector copyAndDivide(final double scalar) {
		return copy().divide(scalar);
	}
	
	/**
	 * Copies and divides the new {@code Vector} instance with the scalar values {@code x}, {@code y} and {@code z}.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code divide(x, y, z)} instead.
	 * 
	 * @param x a scalar value to divide the X-axis with
	 * @param y a scalar value to divide the Y-axis with
	 * @param z a scalar value to divide the Z-axis with
	 * @return a copy of this {@code Vector} instance
	 */
	public Vector copyAndDivide(final double x, final double y, final double z) {
		return copy().divide(x, y, z);
	}
	
	/**
	 * Copies and divides the new {@code Vector} instance with {@code vector}.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code divide(vector)} instead.
	 * 
	 * @param vector the {@code Vector} to divide the copy of this {@code Vector} instance with
	 * @return a copy of this {@code Vector} instance
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector copyAndDivide(final Vector vector) {
		return copy().divide(vector);
	}
	
	/**
	 * Copies and multiplies the new {@code Vector} instance with the scalar value {@code scalar}.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code multiply(scalar)} instead.
	 * 
	 * @param scalar a scalar value to multiply the X-, Y- and Z-axes with
	 * @return a copy of this {@code Vector} instance
	 */
	public Vector copyAndMultiply(final double scalar) {
		return copy().multiply(scalar);
	}
	
	/**
	 * Copies and multiplies the new {@code Vector} instance with the scalar values {@code x}, {@code y} and {@code z}.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code multiply(x, y, z)} instead.
	 * 
	 * @param x a scalar value to multiply the X-axis with
	 * @param y a scalar value to multiply the Y-axis with
	 * @param z a scalar value to multiply the Z-axis with
	 * @return a copy of this {@code Vector} instance
	 */
	public Vector copyAndMultiply(final double x, final double y, final double z) {
		return copy().multiply(x, y, z);
	}
	
	/**
	 * Copies and multiplies the new {@code Vector} instance with {@code vector}.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code multiply(vector)} instead.
	 * 
	 * @param vector the {@code Vector} to multiply the copy of this {@code Vector} instance with
	 * @return a copy of this {@code Vector} instance
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector copyAndMultiply(final Vector vector) {
		return copy().multiply(vector);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @return
	 */
	public Vector copyAndNegate() {
		return copy().negate();
	}
	
	/**
	 * Copies and sets the new {@code Vector} instance to a normalized version of itself.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code normalize()} instead.
	 * 
	 * @return a copy of this {@code Vector} instance
	 */
	public Vector copyAndNormalize() {
		return copy().normalize();
	}
	
	/**
	 * Copies and sets a new position along the X-axis for the new {@code Vector} instance.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code setX(x)} instead.
	 * 
	 * @param x the new position along the X-axis of a copy of this {@code Vector} instance
	 * @return a copy of this {@code Vector} instance
	 */
	public Vector copyAndSetX(final double x) {
		return copy().setX(x);
	}
	
	/**
	 * Copies and sets a new position along the Y-axis for the new {@code Vector} instance.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code setY(y)} instead.
	 * 
	 * @param y the new position along the Y-axis of a copy of this {@code Vector} instance
	 * @return a copy of this {@code Vector} instance
	 */
	public Vector copyAndSetY(final double y) {
		return copy().setY(y);
	}
	
	/**
	 * Copies and sets a new position along the Z-axis for the new {@code Vector} instance.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code setZ(z)} instead.
	 * 
	 * @param z the new position along the Z-axis of a copy of this {@code Vector} instance
	 * @return a copy of this {@code Vector} instance
	 */
	public Vector copyAndSetZ(final double z) {
		return copy().setZ(z);
	}
	
	/**
	 * Copies and subtracts the scalar values {@code x}, {@code y} and {@code z} from the new {@code Vector} instance.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code subtract(x, y, z)} instead.
	 * 
	 * @param x a scalar value to be subtracted from the X-axis of a copy of this {@code Vector} instance
	 * @param y a scalar value to be subtracted from the Y-axis of a copy of this {@code Vector} instance
	 * @param z a scalar value to be subtracted from the Z-axis of a copy of this {@code Vector} instance
	 * @return a copy of this {@code Vector} instance
	 */
	public Vector copyAndSubtract(final double x, final double y, final double z) {
		return copy().subtract(x, y, z);
	}
	
	/**
	 * Copies and subtracts {@code vector} from the new {@code Vector} instance.
	 * <p>
	 * A copy of this {@code Vector} instance will be returned.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code subtract(vector)} instead.
	 * 
	 * @param vector the {@code Vector} to subtract from the new {@code Vector} instance
	 * @return a copy of this {@code Vector} instance
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector copyAndSubtract(final Vector vector) {
		return copy().subtract(vector);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param vector
	 * @return
	 * @throws NullPointerException
	 */
	public Vector copyAndSurfaceNormal(final Vector vector) {
		return copy().surfaceNormal(vector);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param vector0
	 * @param vector1
	 * @return
	 * @throws NullPointerException
	 */
	public Vector copyAndSurfaceNormal(final Vector vector0, final Vector vector1) {
		return copy().surfaceNormal(vector0, vector1);
	}
	
	/**
	 * Sets this {@code Vector} instance to the cross product between itself and {@code vector}.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndCrossProduct(vector)} instead.
	 * 
	 * @param vector one of the two {@code Vector}s in the cross product
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector crossProduct(final Vector vector) {
		return set(this.y * vector.z - this.z * vector.y, this.z * vector.x - this.x * vector.z, this.x * vector.y - this.y * vector.x);
	}
	
	/**
	 * Sets this {@code Vector} instance to the cross product between {@code vector0} and {@code vector1}.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If either {@code vector0} or {@code vector1} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called.
	 * 
	 * @param vector0 one of the two {@code Vector}s in the cross product
	 * @param vector1 one of the two {@code Vector}s in the cross product
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, either {@code vector0} or {@code vector1} are {@code null}
	 */
	public Vector crossProduct(final Vector vector0, final Vector vector1) {
		return set(vector0.y * vector1.z - vector0.z * vector1.y, vector0.z * vector1.x - vector0.x * vector1.z, vector0.x * vector1.y - vector0.y * vector1.x);
	}
	
	/**
	 * Divides this {@code Vector} instance with the scalar value {@code scalar}.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndDivide(scalar)} instead.
	 * 
	 * @param scalar a scalar value to divide the X-, Y- and Z-axes with
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector divide(final double scalar) {
		return multiply(1.0D / scalar);
	}
	
	/**
	 * Divides this {@code Vector} instance with the scalar values {@code x}, {@code y} and {@code z}.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndDivide(x, y, z)} instead.
	 * 
	 * @param x a scalar value to divide the X-axis with
	 * @param y a scalar value to divide the Y-axis with
	 * @param z a scalar value to divide the Z-axis with
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector divide(final double x, final double y, final double z) {
		return set(this.x / x, this.y / y, this.z / z);
	}
	
	/**
	 * Divides this {@code Vector} instance with {@code vector}.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndDivide(vector)} instead.
	 * 
	 * @param vector the {@code Vector} to divide this {@code Vector} instance with
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector divide(final Vector vector) {
		return divide(vector.x, vector.y, vector.z);
	}
	
	/**
	 * Multiplies this {@code Vector} instance with the scalar value {@code scalar}.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndMultiply(scalar)} instead.
	 * 
	 * @param scalar a scalar value to multiply the X-, Y- and Z-axes with
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector multiply(final double scalar) {
		return multiply(scalar, scalar, scalar);
	}
	
	/**
	 * Multiplies this {@code Vector} instance with the scalar values {@code x}, {@code y} and {@code z}.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndMultiply(x, y, z)} instead.
	 * 
	 * @param x a scalar value to multiply the X-axis with
	 * @param y a scalar value to multiply the Y-axis with
	 * @param z a scalar value to multiply the Z-axis with
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector multiply(final double x, final double y, final double z) {
		return set(this.x * x, this.y * y, this.z * z);
	}
	
	/**
	 * Multiplies this {@code Vector} instance with {@code vector}.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndMultiply(vector)} instead.
	 * 
	 * @param vector the {@code Vector} to multiply this {@code Vector} instance with
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector multiply(final Vector vector) {
		return multiply(vector.x, vector.y, vector.z);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @return
	 */
	public Vector negate() {
		return set(-this.x, -this.y, -this.z);
	}
	
	/**
	 * Sets this {@code Vector} instance to a normalized version of itself.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndNormalize()} instead.
	 * 
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector normalize() {
		return multiply(1.0D / length());
	}
	
	/**
	 * Sets a new position along the X-, Y- and Z-axes for this {@code Vector} instance.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called.
	 * 
	 * @param x the new position along the X-axis
	 * @param y the new position along the Y-axis
	 * @param z the new position along the Z-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector set(final double x, final double y, final double z) {
		return setX(x).setY(y).setZ(z);
	}
	
	/**
	 * Sets a new position along the X-, Y- and Z-axes for this {@code Vector} instance.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called.
	 * 
	 * @param vector the {@code Vector} to set this {@code Vector} instance to
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector set(final Vector vector) {
		return set(vector.x, vector.y, vector.z);
	}
	
	/**
	 * Sets a new position along the X-axis for this {@code Vector} instance.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSetX(x)} instead.
	 * 
	 * @param x the new position along the X-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector setX(final double x) {
		this.x = x;
		
		return this;
	}
	
	/**
	 * Sets a new position along the Y-axis for this {@code Vector} instance.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSetY(y)} instead.
	 * 
	 * @param y the new position along the Y-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector setY(final double y) {
		this.y = y;
		
		return this;
	}
	
	/**
	 * Sets a new position along the Z-axis for this {@code Vector} instance.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSetZ(z)} instead.
	 * 
	 * @param z the new position along the Z-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector setZ(final double z) {
		this.z = z;
		
		return this;
	}
	
	/**
	 * Subtracts the scalar values {@code x}, {@code y} and {@code z} from this {@code Vector} instance.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSubtract(x, y, z)} instead.
	 * 
	 * @param x a scalar value to be subtracted from the X-axis
	 * @param y a scalar value to be subtracted from the Y-axis
	 * @param z a scalar value to be subtracted from the Z-axis
	 * @return itself, such that you can chain multiple calls
	 */
	public Vector subtract(final double x, final double y, final double z) {
		return set(this.x - x, this.y - y, this.z - z);
	}
	
	/**
	 * Subtracts {@code vector} from this {@code Vector} instance.
	 * <p>
	 * The {@code Vector} instance itself will be returned, such that you can chain multiple calls.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSubtract(vector)} instead.
	 * 
	 * @param vector the {@code Vector} to subtract from this {@code Vector} instance
	 * @return itself, such that you can chain multiple calls
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector subtract(final Vector vector) {
		return subtract(vector.x, vector.y, vector.z);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param vector
	 * @return
	 * @throws NullPointerException
	 */
	public Vector surfaceNormal(final Vector vector) {
		crossProduct(vector);
		
		if(length() > 0.0D) {
			normalize();
		}
		
		return this;
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param vector0
	 * @param vector1
	 * @return
	 * @throws NullPointerException
	 */
	public Vector surfaceNormal(final Vector vector0, final Vector vector1) {
		final Vector vector2 = vector0.copyAndSubtract(this);
		final Vector vector3 = vector1.copyAndSubtract(this);
		
		return set(vector2).surfaceNormal(vector3);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the probability that {@code vector} was returned by the {@code newCosineWeightedHemisphereSample(PRNG)} method, rather than any other {@code Vector} in the same hemisphere.
	 * <p>
	 * Only the Z-axis is taken into account by this method.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vector the {@code Vector} to find the probability for
	 * @return the probability that {@code vector} was returned by the {@code newCosineWeightedHemisphereSample(PRNG)} method, rather than any other {@code Vector} in the same hemisphere
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public static double getCosineWeightedHemisphereSampleProbabilityFor(final Vector vector) {
		return vector.z / Math.PI;
	}
	
	/**
	 * Returns a new {@code Vector} instance that forms a random cosine-weighted hemisphere sample.
	 * <p>
	 * If {@code pRNG} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pRNG the {@code PRNG} that will be used to create the random cosine-weighted hemisphere sample
	 * @return a new {@code Vector} instance that forms a random cosine-weighted hemisphere sample
	 * @throws NullPointerException thrown if, and only if, {@code pRNG} is {@code null}
	 */
	public static Vector newCosineWeightedHemisphereSample(final PRNG pRNG) {
		final double cosTheta = Math.sqrt(1.0D - pRNG.nextDouble());
		final double sinTheta = Math.sqrt(1.0D - cosTheta * cosTheta);
		final double phi = 2.0D * Math.PI * pRNG.nextDouble();
		final double x = sinTheta * Math.cos(phi);
		final double y = sinTheta * Math.sin(phi);
		
		return new Vector(x, y, cosTheta);
	}
	
	/**
	 * TODO: Add Javadocs...
	 * 
	 * @param sinTheta
	 * @param cosTheta
	 * @param phi
	 * @return
	 */
	public static Vector toSphericalDirection(final double sinTheta, final double cosTheta, final double phi) {
		final double x = sinTheta * Math.cos(phi);
		final double y = sinTheta * Math.sin(phi);
		final double z = cosTheta;
		
		return new Vector(x, y, z);
	}
	
	/**
	 * TODO: Add Javadocs...
	 * 
	 * @param sinTheta
	 * @param cosTheta
	 * @param phi
	 * @param vector0
	 * @param vector1
	 * @param vector2
	 * @return
	 */
	public static Vector toSphericalDirection(final double sinTheta, final double cosTheta, final double phi, final Vector vector0, final Vector vector1, final Vector vector2) {
		final double x = sinTheta * Math.cos(phi);
		final double y = sinTheta * Math.sin(phi);
		final double z = cosTheta;
		
		return vector0.copyAndMultiply(x).add(vector1.copyAndMultiply(y)).add(vector2.copyAndMultiply(z));
	}
	
	/**
	 * Returns a {@code Vector} instance with its X-, Y- and Z-axes all set to {@code 0.0D}.
	 * 
	 * @return a {@code Vector} instance with its X-, Y- and Z-axes all set to {@code 0.0D}
	 */
	public static Vector zero() {
		return new Vector(0.0D, 0.0D, 0.0D);
	}
	
	/**
	 * Returns a {@code Vector} instance with its X-, Y- and Z-axes set to {@code x}, {@code y} and {@code z}, respectively.
	 * 
	 * @param x the position on the X-axis
	 * @param y the position on the Y-axis
	 * @param z the position on the Z-axis
	 * @return a {@code Vector} instance with its X-, Y- and Z-axes set to {@code x}, {@code y} and {@code z}, respectively
	 */
	public static Vector valueOf(final double x, final double y, final double z) {
		return new Vector(x, y, z);
	}
	
	/**
	 * Returns a {@code Vector} instance with its X-, Y- and Z-axes set to {@code 1.0D}, {@code 0.0D} and {@code 0.0D}, respectively.
	 * 
	 * @return a {@code Vector} instance with its X-, Y- and Z-axes set to {@code 1.0D}, {@code 0.0D} and {@code 0.0D}, respectively
	 */
	public static Vector x() {
		return x(1.0D);
	}
	
	/**
	 * Returns a {@code Vector} instance with its X-, Y- and Z-axes set to {@code x}, {@code 0.0D} and {@code 0.0D}, respectively.
	 * 
	 * @return a {@code Vector} instance with its X-, Y- and Z-axes set to {@code x}, {@code 0.0D} and {@code 0.0D}, respectively
	 */
	public static Vector x(final double x) {
		return new Vector(x, 0.0D, 0.0D);
	}
	
	/**
	 * Returns a {@code Vector} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code 1.0D} and {@code 0.0D}, respectively.
	 * 
	 * @return a {@code Vector} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code 1.0D} and {@code 0.0D}, respectively
	 */
	public static Vector y() {
		return y(1.0D);
	}
	
	/**
	 * Returns a {@code Vector} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code y} and {@code 0.0D}, respectively.
	 * 
	 * @return a {@code Vector} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code y} and {@code 0.0D}, respectively
	 */
	public static Vector y(final double y) {
		return new Vector(0.0D, y, 0.0D);
	}
	
	/**
	 * Returns a {@code Vector} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code 0.0D} and {@code 1.0D}, respectively.
	 * 
	 * @return a {@code Vector} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code 0.0D} and {@code 1.0D}, respectively
	 */
	public static Vector z() {
		return z(1.0D);
	}
	
	/**
	 * Returns a {@code Vector} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code 0.0D} and {@code z}, respectively.
	 * 
	 * @return a {@code Vector} instance with its X-, Y- and Z-axes set to {@code 0.0D}, {@code 0.0D} and {@code z}, respectively
	 */
	public static Vector z(final double z) {
		return new Vector(0.0D, 0.0D, z);
	}
}