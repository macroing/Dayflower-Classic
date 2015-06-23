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

public final class Triangle {
	private final Point a;
	private final Point b;
	private final Point c;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Triangle(final Point a, final Point b, final Point c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Point getA() {
		return this.a;
	}
	
	public Point getB() {
		return this.b;
	}
	
	public Point getC() {
		return this.c;
	}
	
	@Override
	public String toString() {
		return String.format("Triangle: [A=%s], [B=%s], [C=%s]", this.a, this.b, this.c);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Triangle newInstance(final Point a, final Point b, final Point c) {
		return new Triangle(a, b, c);
	}
}