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

public abstract class Shape {
	private final Material material;
	private final Point position;
	private final Texture texture;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected Shape(final Material material, final Point position, final Texture texture) {
		this.material = Objects.requireNonNull(material, "material == null");
		this.position = Objects.requireNonNull(position, "position == null");
		this.texture = Objects.requireNonNull(texture, "texture == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final boolean isEmissive() {
		return !this.material.getEmission().isBlack();
	}
	
	public abstract boolean isIntersecting(final Intersection intersection);
	
	public final Material getMaterial() {
		return this.material;
	}
	
	public final Point getPosition() {
		return this.position.copy();
	}
	
	public abstract Point getUV(final Point surfaceIntersectionPoint);
	
	public final Texture getTexture() {
		return this.texture;
	}
}