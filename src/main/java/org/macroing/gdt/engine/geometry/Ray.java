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

public final class Ray {
	private double time;
	private int depth;
	private final Point origin;
	private final Vector direction;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Ray(final int depth, final Point origin, final Vector direction) {
		this.depth = depth;
		this.origin = Objects.requireNonNull(origin, "origin == null").copy();
		this.direction = Objects.requireNonNull(direction, "direction == null").copy();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public double getTime() {
		return this.time;
	}
	
	public int getDepth() {
		return this.depth;
	}
	
	public Point getOrigin() {
		return this.origin;
	}
	
	public Point getPointAt(final double distance) {
		return this.origin.copyAndAdd(this.direction.copyAndMultiply(distance));
	}
	
	public Ray copy() {
		return new Ray(this.depth, this.origin, this.direction);
	}
	
	public Ray reflect(final Point origin, final Vector direction) {
		return new Ray(this.depth + 1, origin, direction);
	}
	
	@Override
	public String toString() {
		return String.format("Ray: [Origin=%s], [Direction=%s], [Depth=%s], [Time=%s]", this.origin, this.direction, Integer.toString(this.depth), Double.toString(this.time));
	}
	
	public Vector getDirection() {
		return this.direction;
	}
	
	public void set(final Ray ray) {
		this.depth = ray.depth;
		this.origin.set(ray.origin);
		this.direction.set(ray.direction);
		this.time = ray.time;
	}
	
	public void setDirection(final Vector direction) {
		this.direction.set(direction);
	}
	
	public void setOrigin(final Point origin) {
		this.origin.set(origin);
	}
	
	public void setTime(final double time) {
		this.time = time;
	}
}