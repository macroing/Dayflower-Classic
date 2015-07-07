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

public final class Sphere extends Shape {
	private final double radius;
	private final Point position;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Sphere(final Material material, final Texture texture, final double radius, final Point position) {
		super(material, texture);
		
		this.radius = radius;
		this.position = Objects.requireNonNull(position, "position == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean isIntersecting(final Intersection intersection) {
		final Ray ray = intersection.getRay();
		
		final Vector delta = this.position.toVector().subtract(ray.getOrigin().toVector());
		
		final double epsilon = 1.e-4D;
		final double b = delta.dotProduct(ray.getDirection());
		
		double discriminant = b * b - delta.lengthSquared() + this.radius * this.radius;
		double distance = 0.0D;
		
		if(discriminant >= 0.0D) {
			discriminant = Math.sqrt(discriminant);
			
			distance = b - discriminant;
			
			if(distance <= epsilon) {
				distance = b + discriminant;
				
				if(distance <= epsilon) {
					distance = 0.0D;
				}
			}
		}
		
		if(distance > epsilon && distance < intersection.getDistance()) {
			intersection.setDistance(distance);
			intersection.setShape(this);
		}
		
		return distance > epsilon;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public Point getPosition() {
		return this.position.copy();
	}
	
	@Override
	public Point getUV(final Point surfaceIntersectionPoint) {
		final Vector distance = this.position.copyAndSubtract(surfaceIntersectionPoint).toVector().normalize();
		
		final double u = 0.5D + Math.atan2(distance.getX(), distance.getZ()) / (2.0D * Math.PI);
		final double v = 0.5D + Math.asin(distance.getY()) / Math.PI;
		
		return Point.valueOf(u, v, 0.0D);
		
//		return surfaceIntersectionPoint.toVector().subtract(this.position.toVector()).normalize().toPoint();
	}
	
	@Override
	public Vector getSurfaceNormal(final Point surfaceIntersectionPoint) {
		return surfaceIntersectionPoint.toVector().subtract(this.position.toVector()).normalize();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Sphere newInstance(final Material material, final double radius, final Point position) {
		return new Sphere(material, SolidTexture.newInstance(1, 1, new RGBSpectrum(0.0D, 0.0D, 0.0D)), radius, position);
	}
	
	public static Sphere newInstance(final Material material, final Texture texture, final double radius, final Point position) {
		return new Sphere(material, texture, radius, position);
	}
}