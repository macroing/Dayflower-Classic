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
 * An abstract base-class that models a material.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization. This may, however, not be the case for all subclasses.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Material {
	/**
	 * A constant with the refractive index of air. The value used is {@code 1.0D}.
	 */
	public static final double REFRACTIVE_INDEX_AIR = 1.0D;
	
	/**
	 * A constant with one of many refractive indices of glass. The value used here is {@code 1.5D}.
	 */
	public static final double REFRACTIVE_INDEX_GLASS = 1.5D;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Material} instance.
	 */
	protected Material() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the refractive index currently assigned to this {@code Material} instance.
	 * 
	 * @return the refractive index currently assigned to this {@code Material} instance
	 */
	public abstract double getRefractiveIndex();
	
	/**
	 * Returns a {@link Spectrum} with the emission currently assigned to this {@code Material} instance.
	 * <p>
	 * Modifying the returned {@code Spectrum} instance should not affect this {@code Material} instance.
	 * 
	 * @return a {@code Spectrum} with the emission currently assigned to this {@code Material} instance
	 */
	public abstract Spectrum getEmission();
	
	/**
	 * Calculates the radiance given the information provided by {@code intersection}.
	 * <p>
	 * Returns a {@link Spectrum} instance with the calculated radiance.
	 * <p>
	 * If either {@code intersection} or {@code pRNG} are {@code null}, {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param pass the current rendering pass
	 * @param intersection the {@link Intersection} providing the information for the radiance calculation
	 * @param pRNG the {@link PRNG} that may or may not be used in the radiance calculation process
	 * @return a {@code Spectrum} instance with the calculated radiance
	 * @throws NullPointerException thrown if, and only if, either {@code intersection} or {@code pRNG} are {@code null}
	 */
	public abstract Spectrum radiance(final int pass, final Intersection intersection, final PRNG pRNG);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the evaluated Fresnel reflection for a given angle and two refractive indices.
	 * 
	 * @param angle the angle to use in the evaluation process
	 * @param refractiveIndex0 one of two refractive indices to use
	 * @param refractiveIndex1 one of two refractive indices to use
	 * @return the evaluated Fresnel reflection for a given angle and two refractive indices
	 */
	public static double evaluateFresnelReflectionFor(final double angle, final double refractiveIndex0, final double refractiveIndex1) {
		final double reflectance = evaluateReflectanceFor(refractiveIndex0, refractiveIndex1);
		final double reflection = reflectance + (1.0D - reflectance) * Math.pow(1.0D - Math.cos(angle), 5.0D);
		
		return reflection;
	}
	
	/**
	 * Returns the evaluated reflectance given two refractive indices.
	 * 
	 * @param refractiveIndex0 one of two refractive indices to use
	 * @param refractiveIndex1 one of two refractive indices to use
	 * @return the evaluated reflectance given two refractive indices
	 */
	public static double evaluateReflectanceFor(final double refractiveIndex0, final double refractiveIndex1) {
		return Math.pow((refractiveIndex1 - refractiveIndex0) / (refractiveIndex0 + refractiveIndex1), 2.0D);
	}
	
	/**
	 * Returns the evaluated transmittance given two refractive indices.
	 * 
	 * @param refractiveIndex0 one of two refractive indices to use
	 * @param refractiveIndex1 one of two refractive indices to use
	 * @return the evaluated transmittance given two refractive indices
	 */
	public static double evaluateTransmittanceFor(final double refractiveIndex0, final double refractiveIndex1) {
		return 4.0D * refractiveIndex0 * refractiveIndex1 / Math.pow(refractiveIndex0 + refractiveIndex1, 2.0D);
	}
	
	/**
	 * A convenience method used by most {@code Material} implementations when they reflect a {@link Ray}.
	 * <p>
	 * Returns the {@link Spectrum} for {@code reflectedRay}.
	 * <p>
	 * If either {@code intersection}, {@code pRNG} or {@code reflectedRay} are {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made for {@code pRNG} and {@code reflectedRay}.
	 * 
	 * @param pass the current rendering pass
	 * @param intersection an {@link Intersection} containing intersection information
	 * @param pRNG a {@link PRNG} that may or may not be used in the calculation process
	 * @param reflectedRay the reflected {@link Ray} for which to calculate some radiance
	 * @return the {@link Spectrum} for {@code reflectedRay}
	 * @throws NullPointerException thrown if, and only if, either {@code intersection}, {@code pRNG} or {@code reflectedRay} are {@code null}
	 */
	public static Spectrum radiance(final int pass, final Intersection intersection, final PRNG pRNG, final Ray reflectedRay) {
		intersection.setDistance(Constants.INFINITY);
		intersection.setRay(reflectedRay);
		
		return intersection.getScene().radiance(pass, intersection, pRNG);
	}
}