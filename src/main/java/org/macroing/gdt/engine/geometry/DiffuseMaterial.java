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

import org.macroing.gdt.engine.util.PRNG;

/**
 * A {@link Material} implementation that reflects light in a perfect diffusive way, using Lambertian reflectance.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class DiffuseMaterial extends Material {
	private final double refractiveIndex;
	private final Spectrum emission;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private DiffuseMaterial(final double refractiveIndex, final Spectrum emission) {
		this.refractiveIndex = refractiveIndex;
		this.emission = emission;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the refractive index currently assigned to this {@code DiffuseMaterial} instance.
	 * 
	 * @return the refractive index currently assigned to this {@code DiffuseMaterial} instance
	 */
	@Override
	public double getRefractiveIndex() {
		return this.refractiveIndex;
	}
	
	/**
	 * Returns a {@link Spectrum} with the emission currently assigned to this {@code DiffuseMaterial} instance.
	 * <p>
	 * Modifying the returned {@code Spectrum} instance will not affect this {@code DiffuseMaterial} instance, as it will be copied.
	 * 
	 * @return a {@code Spectrum} with the emission currently assigned to this {@code DiffuseMaterial} instance
	 */
	@Override
	public Spectrum getEmission() {
		return this.emission.copy();
	}
	
	/**
	 * Calculates the radiance given the information provided by {@code intersection}.
	 * <p>
	 * Returns a {@link Spectrum} instance with the calculated radiance.
	 * <p>
	 * If either {@code intersection} or {@code pRNG} are {@code null}, {@code NullPointerException} may be thrown.
	 * 
	 * @param pass the current rendering pass
	 * @param intersection the {@link Intersection} providing the information for the radiance calculation
	 * @param pRNG the {@link PRNG} that may or may not be used in the radiance calculation process
	 * @return a {@code Spectrum} instance with the calculated radiance
	 * @throws NullPointerException thrown if, and only if, either {@code intersection} or {@code pRNG} are {@code null}
	 */
	@Override
	public Spectrum radiance(final int pass, final Intersection intersection, final PRNG pRNG) {
		final double r1 = 2.0D * Math.PI * pRNG.nextDouble();
		final double r2Squared = pRNG.nextDouble();
		final double r2 = Math.sqrt(r2Squared);
		
//		Are these the orthonormal basis vectors?
		final Vector w = intersection.getSurfaceNormalProperlyOriented();
		final Vector u = (Math.abs(w.getX()) > 0.1D ? Vector.y() : Vector.x()).crossProduct(w).normalize();
		final Vector v = w.copyAndCrossProduct(u);
		
//		Calculate the direction of the reflected ray, based on TODO.
		final Vector reflectedDirection = u.multiply(Math.cos(r1)).multiply(r2).add(v.multiply(Math.sin(r1)).multiply(r2)).add(w.multiply(Math.sqrt(1.0D - r2Squared))).normalize();
		
//		Get the point on the surface of the shape that was intersected by the incident ray. That point will be the origin of the reflected ray.
		final Point origin = intersection.getSurfaceIntersectionPoint();
		
//		Get the incident ray.
		final Ray incidentRay = intersection.getRay();
		
//		Create a new reflected ray based on the incident ray, the surface intersection point as its origin and the new reflected direction.
		final Ray reflectedRay = incidentRay.reflect(origin, reflectedDirection);
		
//		Calculate the radiance given the reflected ray.
		return radiance(pass, intersection, pRNG, reflectedRay);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code DiffuseMaterial} with the given refractive index and emission.
	 * <p>
	 * This method will copy {@code emission}.
	 * <p>
	 * If {@code emission} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param refractiveIndex the refractive index to use
	 * @param emission the {@code Spectrum} to copy and use as emission
	 * @return a new {@code DiffuseMaterial} with the given refractive index and emission
	 * @throws NullPointerException thrown if, and only if, {@code emission} is {@code null}
	 */
	public static DiffuseMaterial newInstance(final double refractiveIndex, final Spectrum emission) {
		return new DiffuseMaterial(refractiveIndex, emission.copy());
	}
}