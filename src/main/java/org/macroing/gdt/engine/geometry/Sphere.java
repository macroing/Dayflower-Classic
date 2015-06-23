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

public final class Sphere extends Shape {
	private double radius;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Sphere(final double radius, final Material material, final Point position, final Texture texture) {
		super(material, position, texture);
		
		this.radius = radius;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean isIntersecting(final Intersection intersection) {
		final Ray ray = intersection.getRay();
		
		final Vector delta = getPosition().toVector().subtract(ray.getOrigin().toVector());
		
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
		
		if(distance != 0.0D && distance < intersection.getDistance()) {
			intersection.setDistance(distance);
			intersection.setShape(this);
		}
		
		return distance != 0.0D;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	@Override
	public Point getUV(final Point surfaceIntersectionPoint) {
		return surfaceIntersectionPoint.toVector().subtract(getPosition().toVector()).normalize().toPoint();
	}
	
	public void setRadius(final double radius) {
		this.radius = radius;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Sphere newInstance(final double radius, final Material material, final Point position) {
		return new Sphere(radius, material, position, SolidTexture.newInstance(1, 1, new RGBSpectrum(0.0D, 0.0D, 0.0D)));
	}
	
	public static Sphere newInstance(final double radius, final Material material, final Point position, final Texture texture) {
		return new Sphere(radius, material, position, texture);
	}
}