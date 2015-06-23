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

import java.util.Objects;

import org.macroing.gdt.engine.util.PRNG;

/**
 * A {@link Material} implementation that always returns a specific "solid" {@link Spectrum}.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SolidMaterial extends Material {
	private final double refractiveIndex;
	private final Spectrum emission;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private SolidMaterial(final double refractiveIndex, final Spectrum emission) {
		this.refractiveIndex = refractiveIndex;
		this.emission = emission;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the refractive index currently assigned to this {@code SolidMaterial} instance.
	 * 
	 * @return the refractive index currently assigned to this {@code SolidMaterial} instance
	 */
	@Override
	public double getRefractiveIndex() {
		return this.refractiveIndex;
	}
	
	/**
	 * Returns a {@link Spectrum} with the emission currently assigned to this {@code SolidMaterial} instance.
	 * <p>
	 * Modifying the returned {@code Spectrum} instance will not affect this {@code SolidMaterial} instance, as it will be copied.
	 * 
	 * @return a {@code Spectrum} with the emission currently assigned to this {@code SolidMaterial} instance
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
		return intersection.getShape().getTexture().getColorAt(0.0D, 0.0D);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code SolidMaterial} with the given refractive index and emission.
	 * <p>
	 * This method will copy {@code emission}.
	 * <p>
	 * If {@code emission} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param refractiveIndex the refractive index to use
	 * @param emission the {@code Spectrum} to copy and use as emission
	 * @return a new {@code SolidMaterial} with the given refractive index and emission
	 * @throws NullPointerException thrown if, and only if, {@code emission} is {@code null}
	 */
	public static SolidMaterial newInstance(final double refractiveIndex, final Spectrum emission) {
		return new SolidMaterial(refractiveIndex, Objects.requireNonNull(emission, "emission == null"));
	}
}