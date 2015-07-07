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

public final class Triangle extends Shape {
	private final Point a;
	private final Point b;
	private final Point c;
	private final Vector surfaceNormal;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Triangle(final Material material, final Texture texture, final Point a, final Point b, final Point c) {
		super(material, texture);
		
		this.a = a;
		this.b = b;
		this.c = c;
		this.surfaceNormal = a.toVector().surfaceNormal(b.toVector(), c.toVector());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean isIntersecting(final Intersection intersection) {
		final Ray ray = intersection.getRay();
		
		final Vector direction = ray.getDirection();
		final Vector edge1 = this.b.toVector().subtract(this.a.toVector());
		final Vector edge2 = this.c.toVector().subtract(this.a.toVector());
		final Vector p = direction.copyAndCrossProduct(edge2);
		
		final double epsilon = 1.e-4D;
		final double determinant = edge1.dotProduct(p);
		
		if(determinant == 0.0D) {
			return false;
		}
		
		final double inverseDeterminant = 1.0D / determinant;
		
		final Vector origin = ray.getOrigin().toVector();
		final Vector vector = origin.copyAndSubtract(this.a.toVector());
		
		final double u = vector.dotProduct(p) * inverseDeterminant;
		
		if(u < 0.0D || u > 1.0D) {
			return false;
		}
		
		final Vector q = vector.copyAndCrossProduct(edge1);
		
		final double v = direction.dotProduct(q) * inverseDeterminant;
		
		if(v < 0.0D || u + v > 1.0D) {
			return false;
		}
		
		final double distance = edge2.dotProduct(q) * inverseDeterminant;
		
		if(distance > epsilon && distance < intersection.getDistance()) {
			intersection.setDistance(distance);
			intersection.setShape(this);
		}
		
		return distance > epsilon;
	}
	
	public Point getA() {
		return this.a.copy();
	}
	
	public Point getB() {
		return this.b.copy();
	}
	
	public Point getC() {
		return this.c.copy();
	}
	
	@Override
	public Point getUV(final Point surfaceIntersectionPoint) {
		return surfaceIntersectionPoint.copy();
	}
	
	@Override
	public String toString() {
		return String.format("Triangle: [A=%s], [B=%s], [C=%s]", this.a, this.b, this.c);
	}
	
	@Override
	public Vector getSurfaceNormal(final Point surfaceIntersectionPoint) {
		return this.surfaceNormal.copy();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Triangle newInstance(final Material material, final Texture texture, final Point a, final Point b, final Point c) {
		return new Triangle(material, texture, a, b, c);
	}
}