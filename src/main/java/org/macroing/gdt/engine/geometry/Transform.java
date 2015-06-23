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
 * A class for performing transformations on geometric primitives.
 * <p>
 * This class is mutable and therefore not suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Transform {
	private final Matrix matrix;
	private final Matrix matrixInversed;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Transform(final Matrix matrix, final Matrix matrixInversed) {
		this.matrix = matrix;
		this.matrixInversed = matrixInversed;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, this {@code Transform} instance has a scale.
	 * 
	 * @return {@code true} if, and only if, this {@code Transform} instance has a scale
	 */
	public boolean hasScale() {
		final double length0 = transform(new Vector(1.0D, 0.0D, 0.0D)).lengthSquared();
		final double length1 = transform(new Vector(0.0D, 1.0D, 0.0D)).lengthSquared();
		final double length2 = transform(new Vector(0.0D, 0.0D, 1.0D)).lengthSquared();
		
		return length0 < 0.999D || length0 > 1.001D || length1 < 0.999D || length1 > 1.001D || length2 < 0.999D || length2 > 1.001D;
	}
	
	/**
	 * Transforms {@code boundingVolume} according to this {@code Transform} instance.
	 * <p>
	 * Returns the transformed {@link BoundingVolume}, which may or may not be the same instance as {@code boundingVolume}.
	 * <p>
	 * If {@code boundingVolume} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param boundingVolume the {@code BoundingVolume} to transform according to this {@code Transform} instance
	 * @return the transformed {@code BoundingVolume}, which may or may not be the same instance as {@code boundingVolume}
	 * @throws NullPointerException thrown if, and only if, {@code boundingVolume} is {@code null}
	 */
	public BoundingVolume transform(final BoundingVolume boundingVolume) {
		if(boundingVolume instanceof AxisAlignedBoundingBox) {
			AxisAlignedBoundingBox axisAlignedBoundingBox = AxisAlignedBoundingBox.class.cast(boundingVolume);
			
			final Point maximum = axisAlignedBoundingBox.getMaximum();
			final Point minimum = axisAlignedBoundingBox.getMinimum();
			
			final double maximumX = maximum.getX();
			final double maximumY = maximum.getY();
			final double maximumZ = maximum.getZ();
			final double minimumX = minimum.getX();
			final double minimumY = minimum.getY();
			final double minimumZ = minimum.getZ();
			
			axisAlignedBoundingBox = AxisAlignedBoundingBox.newInstance(transform(new Point(minimumX, minimumY, minimumZ)));
			axisAlignedBoundingBox = AxisAlignedBoundingBox.union(axisAlignedBoundingBox, transform(new Point(maximumX, minimumY, minimumZ)));
			axisAlignedBoundingBox = AxisAlignedBoundingBox.union(axisAlignedBoundingBox, transform(new Point(minimumX, maximumY, minimumZ)));
			axisAlignedBoundingBox = AxisAlignedBoundingBox.union(axisAlignedBoundingBox, transform(new Point(minimumX, minimumY, maximumZ)));
			axisAlignedBoundingBox = AxisAlignedBoundingBox.union(axisAlignedBoundingBox, transform(new Point(minimumX, maximumY, maximumZ)));
			axisAlignedBoundingBox = AxisAlignedBoundingBox.union(axisAlignedBoundingBox, transform(new Point(maximumX, maximumY, minimumZ)));
			axisAlignedBoundingBox = AxisAlignedBoundingBox.union(axisAlignedBoundingBox, transform(new Point(maximumX, minimumY, maximumZ)));
			axisAlignedBoundingBox = AxisAlignedBoundingBox.union(axisAlignedBoundingBox, transform(new Point(maximumX, maximumY, maximumZ)));
			
			return axisAlignedBoundingBox;
		}
		
		return boundingVolume;
	}
	
	/**
	 * Transforms {@code point} according to this {@code Transform} instance.
	 * <p>
	 * Returns a new {@link Point} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point the {@code Point} to transform according to this {@code Transform} instance
	 * @return a new {@code Point} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public Point transform(Point point) {
		final double[][] matrix = this.matrix.getMatrix();
		
		double x = point.getX();
		double y = point.getY();
		double z = point.getZ();
		double w = matrix[3][0] * x + matrix[3][1] * y + matrix[3][2] * z + matrix[3][3];
		
		x = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2] * z + matrix[0][3];
		y = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2] * z + matrix[1][3];
		z = matrix[2][0] * x + matrix[2][1] * y + matrix[2][2] * z + matrix[2][3];
		
		point = new Point(x, y, z);
		
		if(w != 1.0D) {
			point.divide(w);
		}
		
		return point;
	}
	
	/**
	 * Returns a copy of the underlying {@link Matrix} instance.
	 * 
	 * @return a copy of the underlying {@code Matrix} instance
	 */
	public Matrix getMatrix() {
		return this.matrix.copy();
	}
	
	/**
	 * Returns a copy of the underlying inversed {@link Matrix} instance.
	 * 
	 * @return a copy of the underlying inversed {@code Matrix} instance
	 */
	public Matrix getMatrixInversed() {
		return this.matrixInversed.copy();
	}
	
	/**
	 * Transforms {@code ray} according to this {@code Transform} instance.
	 * <p>
	 * Returns a new {@link Ray} instance.
	 * <p>
	 * If {@code ray} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param ray the {@code Ray} to transform according to this {@code Transform} instance
	 * @return a new {@code Ray} instance
	 * @throws NullPointerException thrown if, and only if, {@code ray} is {@code null}
	 */
	public Ray transform(final Ray ray) {
		final Point origin = transform(ray.getOrigin());
		
		final Vector direction = transform(ray.getDirection());
		
		return new Ray(0, origin, direction);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Transform} instance.
	 * 
	 * @return a {@code String} representation of this {@code Transform} instance
	 */
	@Override
	public String toString() {
		return String.format("%s %s", this.matrix, this.matrixInversed);
	}
	
	/**
	 * Returns a copy of this {@code Transform} instance.
	 * 
	 * @return a copy of this {@code Transform} instance
	 */
	public Transform copy() {
		return new Transform(this.matrix.copy(), this.matrixInversed.copy());
	}
	
	/**
	 * Returns a new inverse version of this {@code Transform} instance.
	 * 
	 * @return a new inverse version of this {@code Transform} instance
	 */
	public Transform inverse() {
		return newInstance(this.matrixInversed, this.matrix);
	}
	
	/**
	 * Sets this {@code Transform} instance to {@code transform}.
	 * <p>
	 * Returns this {@code Transform} instance.
	 * <p>
	 * If {@code transform} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param transform the {@code Transform} to set this {@code Transform} instance to
	 * @return this {@code Transform} instance
	 * @throws NullPointerException thrown if, and only if, {@code transform} is {@code null}
	 */
	public Transform set(final Transform transform) {
		this.matrix.set(transform.matrix);
		this.matrixInversed.set(transform.matrixInversed);
		
		return this;
	}
	
	/**
	 * Multiplies this {@code Transform} instance with {@code transform}.
	 * <p>
	 * Returns a new {@code Transform} instance.
	 * <p>
	 * If {@code transform} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param transform the {@code Transform} to multiply this {@code Transform} instance with
	 * @return a new {@code Transform} instance
	 * @throws NullPointerException thrown if, and only if, {@code transform} is {@code null}
	 */
	public Transform multiply(final Transform transform) {
		final Matrix matrix = this.matrix.multiply(transform.matrix);
		final Matrix matrixInversed = this.matrixInversed.multiply(transform.matrixInversed);
		
		return newInstance(matrix, matrixInversed);
	}
	
	/**
	 * Returns a new transpose version of this {@code Transform} instance.
	 * 
	 * @return a new transpose version of this {@code Transform} instance
	 */
	public Transform transpose() {
		return newInstance(this.matrix.transpose(), this.matrixInversed.transpose());
	}
	
	/**
	 * Transforms {@code vector} according to this {@code Transform} instance.
	 * <p>
	 * Returns a new {@link Vector} instance.
	 * <p>
	 * If {@code vector} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vector the {@code Vector} to transform according to this {@code Transform} instance
	 * @return a new {@code Vector} instance
	 * @throws NullPointerException thrown if, and only if, {@code vector} is {@code null}
	 */
	public Vector transform(final Vector vector) {
		final double[][] matrix = this.matrix.getMatrix();
		
		double x = vector.getX();
		double y = vector.getY();
		double z = vector.getZ();
		
		x = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2] * z;
		y = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2] * z;
		z = matrix[2][0] * x + matrix[2][1] * y + matrix[2][2] * z;
		
		return new Vector(x, y, z);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code Transform} that is the inverse of {@code transform}.
	 * <p>
	 * If {@code transform} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param transform the {@code Transform} from which to create an inverse {@code Transform}
	 * @return a new {@code Transform} that is the inverse of {@code transform}
	 * @throws NullPointerException thrown if, and only if, {@code transform} is {@code null}
	 */
	public static Transform inverse(final Transform transform) {
		return newInstance(transform.matrixInversed, transform.matrix);
	}
	
	/**
	 * Returns a look-at {@code Transform} based on the {@code source}, {@code target} and {@code up}.
	 * <p>
	 * If either {@code source}, {@code target} or {@code up} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param source the source {@link Point}
	 * @param target the target {@code Point}
	 * @param up the up {@link Vector}
	 * @return a look-at {@code Transform} based on the {@code source}, {@code target} and {@code up}
	 */
	public static Transform lookAt(final Point source, final Point target, final Vector up) {
		final double[][] m = new double[4][4];
		
		m[0][3] = source.getX();
		m[1][3] = source.getY();
		m[2][3] = source.getZ();
		m[3][3] = 1.0D;
		
		final Vector direction = target.toVector().subtract(source.toVector()).normalize();
		final Vector vector0 = up.copyAndNormalize().crossProduct(direction);
		
		if(vector0.length() == 0.0D) {
			return newInstance();
		}
		
		vector0.normalize();
		
		final Vector vector1 = direction.copyAndCrossProduct(vector0);
		
		m[0][0] = vector0.getX();
		m[1][0] = vector0.getY();
		m[2][0] = vector0.getZ();
		m[3][0] = 0.0D;
		m[0][1] = vector1.getX();
		m[1][1] = vector1.getY();
		m[2][1] = vector1.getZ();
		m[3][1] = 0.0D;
		m[0][2] = direction.getX();
		m[1][2] = direction.getY();
		m[2][2] = direction.getZ();
		m[3][2] = 0.0D;
		
		final Matrix matrix = Matrix.newInstance(m);
		
		return newInstance(matrix.inverse(), matrix);
	}
	
	//TODO
	public static Transform newInstance() {
		return newInstance(Matrix.newInstance());
	}
	
	//TODO
	public static Transform newInstance(final Matrix matrix) {
		return newInstance(matrix, matrix.inverse());
	}
	
	//TODO
	public static Transform newInstance(final Matrix matrix, final Matrix matrixInverse) {
		return new Transform(matrix.copy(), matrixInverse.copy());
	}
	
	//TODO
	public static Transform orthographic(final double zNear, final double zFar) {
		return scale(1.0D, 1.0D, 1.0D / (zFar - zNear)).multiply(translate(0.0D, 0.0D, -zNear));
	}
	
	//TODO
	public static Transform perspective(final double fieldOfView, final double nearPlane, final double farPlane) {
		final Matrix matrix = Matrix.newInstance(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, farPlane / (farPlane - nearPlane), -farPlane * nearPlane / (farPlane - nearPlane), 0.0D, 0.0D, 1.0D, 0.0D);
		
		final double angle = 1.0D / Math.tan(Math.toRadians(fieldOfView) * 0.5D);
		
		return scale(angle, angle, 1.0D).multiply(newInstance(matrix));
	}
	
	//TODO
	public static Transform rotate(final double angle, Vector vector) {
		vector = vector.copyAndNormalize();
		
		final double sin = Math.sin(Math.toRadians(angle));
		final double cos = Math.cos(Math.toRadians(angle));
		
		final double[][] m = new double[4][4];
		
		m[0][0] = vector.getX() * vector.getX() + (1.0D - vector.getX() * vector.getX()) * cos;
		m[0][1] = vector.getX() * vector.getY() * (1.0D - cos) - vector.getZ() * sin;
		m[0][2] = vector.getX() * vector.getZ() * (1.0D - cos) + vector.getY() * sin;
		m[0][3] = 0.0D;
		
		m[1][0] = vector.getX() * vector.getY() * (1.0D - cos) + vector.getZ() * sin;
		m[1][1] = vector.getY() * vector.getY() + (1.0D - vector.getY() * vector.getY()) * cos;
		m[1][2] = vector.getY() * vector.getZ() * (1.0D - cos) - vector.getX() * sin;
		m[1][3] = 0.0D;
		
		m[2][0] = vector.getX() * vector.getZ() * (1.0D - cos) - vector.getY() * sin;
		m[2][1] = vector.getY() * vector.getZ() * (1.0D - cos) + vector.getX() * sin;
		m[2][2] = vector.getZ() * vector.getZ() + (1.0D - vector.getZ() * vector.getZ()) * cos;
		m[2][3] = 0.0D;
		
		m[3][0] = 0.0D;
		m[3][1] = 0.0D;
		m[3][2] = 0.0D;
		m[3][3] = 1.0D;
		
		final Matrix matrix = Matrix.newInstance(m);
		final Matrix matrixInversed = matrix.transpose();
		
		return newInstance(matrix, matrixInversed);
	}
	
	//TODO
	public static Transform rotateX(final double angle) {
		final double sin = Math.sin(Math.toRadians(angle));
		final double cos = Math.cos(Math.toRadians(angle));
		
		final Matrix matrix = Matrix.newInstance(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, cos, -sin, 0.0D, 0.0D, sin, cos, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
		final Matrix matrixInversed = matrix.transpose();
		
		return newInstance(matrix, matrixInversed);
	}
	
	//TODO
	public static Transform rotateY(final double angle) {
		final double sin = Math.sin(Math.toRadians(angle));
		final double cos = Math.cos(Math.toRadians(angle));
		
		final Matrix matrix = Matrix.newInstance(cos, 0.0D, sin, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, -sin, 0.0D, cos, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
		final Matrix matrixInversed = matrix.transpose();
		
		return newInstance(matrix, matrixInversed);
	}
	
	//TODO
	public static Transform rotateZ(final double angle) {
		final double sin = Math.sin(Math.toRadians(angle));
		final double cos = Math.cos(Math.toRadians(angle));
		
		final Matrix matrix = Matrix.newInstance(cos, sin, 0.0D, 0.0D, -sin, cos, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
		final Matrix matrixInversed = matrix.transpose();
		
		return newInstance(matrix, matrixInversed);
	}
	
	//TODO
	public static Transform scale(final double x, final double y, final double z) {
		final Matrix matrix = Matrix.newInstance(x, 0.0D, 0.0D, 0.0D, 0.0D, y, 0.0D, 0.0D, 0.0D, 0.0D, z, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
		final Matrix matrixInversed = Matrix.newInstance(1.0D / x, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D / y, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D / z, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
		
		return newInstance(matrix, matrixInversed);
	}
	
	//TODO
	public static Transform scale(final Vector vector) {
		return scale(vector.getX(), vector.getY(), vector.getZ());
	}
	
	//TODO
	public static Transform translate(final double x, final double y, final double z) {
		final Matrix matrix = Matrix.newInstance(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, x, y, z, 1.0D);
		final Matrix matrixInversed = Matrix.newInstance(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -x, -y, -z, 1.0D);
		
		return newInstance(matrix, matrixInversed);
	}
	
	//TODO
	public static Transform translate(final Vector vector) {
		return translate(vector.getX(), vector.getY(), vector.getZ());
	}
}