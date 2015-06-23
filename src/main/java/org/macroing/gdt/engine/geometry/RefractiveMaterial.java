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
 * A {@link Material} implementation that reflects light in a refractive way.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class RefractiveMaterial extends Material {
	private final double refractiveIndex;
	private final Spectrum emission;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private RefractiveMaterial(final double refractiveIndex, final Spectrum emission) {
		this.refractiveIndex = refractiveIndex;
		this.emission = emission;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the refractive index currently assigned to this {@code RefractiveMaterial} instance.
	 * 
	 * @return the refractive index currently assigned to this {@code RefractiveMaterial} instance
	 */
	@Override
	public double getRefractiveIndex() {
		return this.refractiveIndex;
	}
	
	/**
	 * Returns a {@link Spectrum} with the emission currently assigned to this {@code RefractiveMaterial} instance.
	 * <p>
	 * Modifying the returned {@code Spectrum} instance will not affect this {@code RefractiveMaterial} instance, as it will be copied.
	 * 
	 * @return a {@code Spectrum} with the emission currently assigned to this {@code RefractiveMaterial} instance
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
	 * If either {@code intersection} or {@code pRNG} are {@code null}, {@code NullPointerException} may be thrown. But no guarantees can be made for {@code pRNG}.
	 * 
	 * @param pass the current rendering pass
	 * @param intersection the {@link Intersection} providing the information for the radiance calculation
	 * @param pRNG the {@link PRNG} that may or may not be used in the radiance calculation process
	 * @return a {@code Spectrum} instance with the calculated radiance
	 * @throws NullPointerException thrown if, and only if, either {@code intersection} or {@code pRNG} are {@code null}
	 */
	@Override
	public Spectrum radiance(final int pass, final Intersection intersection, final PRNG pRNG) {
//		TODO: Change the names of all variables that looks like _XYZ_, to something that is easier to understand. First I need to understand what they mean...
		
//		Get the point on the surface of the shape that was intersected by the incident ray. That point will be the origin of the reflected ray.
		final Point origin = intersection.getSurfaceIntersectionPoint();
		
//		Get the incident ray.
		final Ray incidentRay = intersection.getRay();
		
//		Get the direction of the incident ray.
		final Vector incidentDirection = incidentRay.getDirection();
		
//		Get the surface normal on the surface of the shape at the surface intersection point.
		final Vector surfaceNormal = intersection.getSurfaceNormal();
		
//		Get the surface normal that is properly oriented for refraction.
		final Vector surfaceNormalProperlyOriented = intersection.getSurfaceNormalProperlyOriented();
		
//		Calculate whether the refracted direction is pointing from the outside in, rather than from the inside out of the shape.
		final boolean isDirectedInwards = surfaceNormal.dotProduct(surfaceNormalProperlyOriented) > 0.0D;
		
		final double refractiveIndex0 = intersection.getRefractiveIndex0();
		final double refractiveIndex1 = intersection.getRefractiveIndex1();
		final double _DDN_ = incidentDirection.dotProduct(surfaceNormalProperlyOriented);
		final double _NNT_ = isDirectedInwards ? refractiveIndex0 / refractiveIndex1 : refractiveIndex1 / refractiveIndex0;
		final double _COS_2_T_ = 1.0D - _NNT_ * _NNT_ * (1.0D - _DDN_ * _DDN_);
		
		final Vector reflectionDirection = incidentDirection.copyAndSubtract(surfaceNormal.copyAndMultiply(2.0D).multiply(surfaceNormal.dotProduct(incidentDirection)));
		
		if(_COS_2_T_ < 0.0D) {
			return RGBSpectrum.black();//TODO: Look into why it doesn't terminate at times, causing a StackOverflowError to be thrown. The below code is the original code that caused this. This is a temporary fix. But it looks the same to me.
//			return reflect(intersection, pRNG, incidentRay.reflect(origin, reflectionDirection));
		}
		
		final Vector transmissionDirection = incidentDirection.copyAndMultiply(_NNT_).subtract(surfaceNormal.copyAndMultiply((isDirectedInwards ? 1.0D : -1.0D) * (_DDN_ * _NNT_ + Math.sqrt(_COS_2_T_)))).normalize();
		
		final double angle = 1.0D - (isDirectedInwards ? -_DDN_ : transmissionDirection.dotProduct(surfaceNormal));
		final double reflection = evaluateFresnelReflectionFor(angle, refractiveIndex0, refractiveIndex1);
		
		if(incidentRay.getDepth() > 2) {
			final double probability = 0.25D + 0.5D * reflection;
			
			if(pRNG.nextDouble() < probability) {
				final double reflectionProbability = reflection / probability;
				
				return radiance(pass, intersection, pRNG, incidentRay.reflect(origin, reflectionDirection)).multiply(reflectionProbability);
			}
			
			final double transmission = 1.0D - reflection;
			final double transmissionProbability = transmission / (1.0D - probability);
			
			return radiance(pass, intersection, pRNG, incidentRay.reflect(origin, transmissionDirection)).multiply(transmissionProbability);
		}
		
		final double transmission = 1.0D - reflection;
		
		return radiance(pass, intersection, pRNG, incidentRay.reflect(origin, reflectionDirection)).multiply(reflection).add(radiance(pass, intersection, pRNG, incidentRay.reflect(origin, transmissionDirection)).multiply(transmission));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code RefractiveMaterial} with the given refractive index and emission.
	 * <p>
	 * This method will copy {@code emission}.
	 * <p>
	 * If {@code emission} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param refractiveIndex the refractive index to use
	 * @param emission the {@code Spectrum} to copy and use as emission
	 * @return a new {@code RefractiveMaterial} with the given refractive index and emission
	 * @throws NullPointerException thrown if, and only if, {@code emission} is {@code null}
	 */
	public static RefractiveMaterial newInstance(final double refractiveIndex, final Spectrum emission) {
		return new RefractiveMaterial(refractiveIndex, emission.copy());
	}
}