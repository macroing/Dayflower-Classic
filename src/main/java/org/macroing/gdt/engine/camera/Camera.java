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
import org.macroing.gdt.engine.sampler.Sample;

/**
 * An abstract base-class that defines the general contract for a camera.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Camera {
	private final Point position = Point.zero();
	private final Transform cameraToWorld = Transform.newInstance();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Camera} instance.
	 */
	protected Camera() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the position of this {@code Camera} instance.
	 * <p>
	 * This method will return a copy of the underlying {@code Point}.
	 * 
	 * @return the position of this {@code Camera} instance
	 */
	public final Point getPosition() {
		return this.position.copy();
	}
	
	/**
	 * Returns a newly generated {@code Ray} given a {@code Sample}.
	 * <p>
	 * If {@code sample} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param sample the {@code Sample} that guides the generation of the {@code Ray}
	 * @return a newly generated {@code Ray} given a {@code Sample}
	 * @throws NullPointerException thrown if, and only if, {@code sample} is {@code null}
	 */
	public abstract Ray newRay(final Sample sample);
	
	/**
	 * Returns a newly generated {@code RayDifferential} given a {@code Sample}.
	 * <p>
	 * If {@code sample} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param sample the {@code Sample} that guides the generation of the {@code RayDifferential}
	 * @return a newly generated {@code RayDifferential} given a {@code Sample}
	 * @throws NullPointerException thrown if, and only if, {@code sample} is {@code null}
	 */
	public abstract RayDifferential newRayDifferential(final Sample sample);
	
	/**
	 * Returns the {@code Transform} that transforms from camera-space to world-space.
	 * 
	 * @return the {@code Transform} that transforms from camera-space to world-space
	 */
	public final Transform getCameraToWorld() {
		return this.cameraToWorld;
	}
	
	/**
	 * Call this method whenever you have changed any aspects of this class or its subclasses.
	 */
	public abstract void configure();
	
	/**
	 * Call this method to make this {@code Camera} instance move backward given a Z-value.
	 * <p>
	 * The Z-value is assumed to be positive. But the value used will be {@code -z}.
	 * 
	 * @param z the Z-value with the distance to move
	 */
	public final void moveBackward(final double z) {
		final
		Transform transform = getCameraToWorld();
		transform.set(transform.multiply(Transform.translate(0.0D, 0.0D, -z)));
	}
	
	/**
	 * Call this method to make this {@code Camera} instance move down given a Y-value.
	 * <p>
	 * The Y-value is assumed to be positive. But the value used will be {@code -y}.
	 * 
	 * @param y the Y-value with the distance to move
	 */
	public final void moveDown(final double y) {
		final
		Transform transform = getCameraToWorld();
		transform.set(transform.multiply(Transform.translate(0.0D, -y, 0.0D)));
	}
	
	/**
	 * Call this method to make this {@code Camera} instance move forward given a Z-value.
	 * <p>
	 * The Z-value is assumed to be positive.
	 * 
	 * @param z the Z-value with the distance to move
	 */
	public final void moveForward(final double z) {
		final
		Transform transform = getCameraToWorld();
		transform.set(transform.multiply(Transform.translate(0.0D, 0.0D, z)));
	}
	
	/**
	 * Call this method to make this {@code Camera} instance move left given an X-value.
	 * <p>
	 * The X-value is assumed to be positive. But the value used will be {@code -x}.
	 * 
	 * @param x the X-value with the distance to move
	 */
	public final void moveLeft(final double x) {
		final
		Transform transform = getCameraToWorld();
		transform.set(transform.multiply(Transform.translate(-x, 0.0D, 0.0D)));
	}
	
	/**
	 * Call this method to make this {@code Camera} instance move right given an X-value.
	 * <p>
	 * The X-value is assumed to be positive.
	 * 
	 * @param x the X-value with the distance to move
	 */
	public final void moveRight(final double x) {
		final
		Transform transform = getCameraToWorld();
		transform.set(transform.multiply(Transform.translate(x, 0.0D, 0.0D)));
	}
	
	/**
	 * Call this method to make this {@code Camera} instance move up given a Y-value.
	 * <p>
	 * The Y-value is assumed to be positive.
	 * 
	 * @param y the Y-value with the distance to move
	 */
	public final void moveUp(final double y) {
		final
		Transform transform = getCameraToWorld();
		transform.set(transform.multiply(Transform.translate(0.0D, y, 0.0D)));
	}
	
	/**
	 * Sets a new position for this {@code Camera} instance.
	 * <p>
	 * If {@code position} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param position the new position
	 * @throws NullPointerException thrown if, and only if, {@code position} is {@code null}
	 */
	public final void setPosition(final Point position) {
		this.position.set(position);
	}
	
	/**
	 * Call this method to make this {@code Camera} instance turn down given an angle.
	 * <p>
	 * The angle is assumed to be positive. But the value used will be {@code -angle}.
	 * 
	 * @param angle the angle to turn down
	 */
	public final void turnDown(final double angle) {
		final
		Transform transform = getCameraToWorld();
		transform.set(transform.multiply(Transform.rotateX(-angle)));
	}
	
	/**
	 * Call this method to make this {@code Camera} instance turn left given an angle.
	 * <p>
	 * The angle is assumed to be positive. But the value used will be {@code -angle}.
	 * 
	 * @param angle the angle to turn left
	 */
	public final void turnLeft(final double angle) {
		final
		Transform transform = getCameraToWorld();
		transform.set(transform.multiply(Transform.rotateY(-angle)));
	}
	
	/**
	 * Call this method to make this {@code Camera} instance turn right given an angle.
	 * <p>
	 * The angle is assumed to be positive.
	 * 
	 * @param angle the angle to turn right
	 */
	public final void turnRight(final double angle) {
		final
		Transform transform = getCameraToWorld();
		transform.set(transform.multiply(Transform.rotateY(angle)));
	}
	
	/**
	 * Call this method to make this {@code Camera} instance turn up given an angle.
	 * <p>
	 * The angle is assumed to be positive.
	 * 
	 * @param angle the angle to turn up
	 */
	public final void turnUp(final double angle) {
		final
		Transform transform = getCameraToWorld();
		transform.set(transform.multiply(Transform.rotateX(angle)));
	}
}