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
 * This {@code BoundingVolume} interface defines the standard API for intersection tests of groups of primitives, shapes or other geometric constructs.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface BoundingVolume {
	/**
	 * Returns {@code true} if, and only if, this {@code BoundingVolume} contains {@code point}, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, this method may throw a {@code NullPointerException}. But no guarantees can be made.
	 * 
	 * @param point the {@link Point} to check for containment
	 * @return {@code true} if, and only if, this {@code BoundingVolume} contains {@code point}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	boolean contains(final Point point);
	
	/**
	 * Returns {@code true} if, and only if, this {@code BoundingVolume} intersects with {@code boundingVolume}, {@code false} otherwise.
	 * <p>
	 * If {@code boundingVolume} is {@code null}, this method may throw a {@code NullPointerException}. But no guarantees can be made.
	 * <p>
	 * If an unsupported {@code BoundingVolume} is supplied, an {@code UnsupportedOperationException} may be thrown. But no guarantees can be made.
	 * 
	 * @param boundingVolume the {@code BoundingVolume} to check for intersection with this {@code BoundingVolume} instance
	 * @return {@code true} if, and only if, this {@code BoundingVolume} intersects with {@code boundingVolume}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code boundingVolume} is {@code null}
	 * @throws UnsupportedOperationException thrown if, and only if, an unsupported {@code BoundingVolume} is supplied
	 */
	boolean intersects(final BoundingVolume boundingVolume);
	
	/**
	 * Returns the surface area of this {@code BoundingVolume}.
	 * 
	 * @return the surface area of this {@code BoundingVolume}
	 */
	double getSurfaceArea();
	
	/**
	 * Returns the volume of this {@code BoundingVolume}.
	 * 
	 * @return the volume of this {@code BoundingVolume}
	 */
	double getVolume();
	
	/**
	 * Returns the center {@link Point} in this {@code BoundingVolume}.
	 * 
	 * @return the center {@code Point} in this {@code BoundingVolume}
	 */
	Point getCenter();
	
	/**
	 * Returns the {@link Point} inside this {@code BoundingVolume} that is the closest to {@code point}.
	 * <p>
	 * If {@code point} is {@code null}, this method may throw a {@code NullPointerException}. But no guarantees can be made.
	 * 
	 * @param point a {@code Point}
	 * @return the {@code Point} inside this {@code BoundingVolume} that is the closest to {@code point}
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	Point getClosestPointTo(final Point point);
}