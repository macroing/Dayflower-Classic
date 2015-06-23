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
package org.macroing.gdt.engine.util;

import java.lang.reflect.Field;//TODO: Fix this class and remove this comment once done. Add Javadocs etc.

/**
 * A class that consists exclusively of static methods that performs various Monte Carlo algorithms.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class MonteCarlo {
	/**
	 * TODO: Add Javadocs...
	 */
	public static final double ONE_MINUS_EPSILON = 0x1.FFFFFEp-1D;
	
	/**
	 * TODO: Add Javadocs...
	 */
	public static final double PI_MULTIPLIED_BY_FOUR = Math.PI * 4.0D;
	
	/**
	 * TODO: Add Javadocs...
	 */
	public static final double PI_MULTIPLIED_BY_FOUR_RECIPROCAL = 1.0D / PI_MULTIPLIED_BY_FOUR;
	
	/**
	 * TODO: Add Javadocs...
	 */
	public static final double PI_MULTIPLIED_BY_TWO = Math.PI * 2.0D;
	
	/**
	 * TODO: Add Javadocs...
	 */
	public static final double PI_MULTIPLIED_BY_TWO_RECIPROCAL = 1.0D / PI_MULTIPLIED_BY_TWO;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private MonteCarlo() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a balanced heuristic given {@code numberA}, {@code probabilityDensityFunctionA}, {@code numberB} and {@code probabilityDensityFunctionB}.
	 * 
	 * @param numberA some {@code int}
	 * @param probabilityDensityFunctionA some {@code double}
	 * @param numberB some {@code int}
	 * @param probabilityDensityFunctionB some {@code double}
	 * @return a balanced heuristic given {@code numberA}, {@code probabilityDensityFunctionA}, {@code numberB} and {@code probabilityDensityFunctionB}
	 */
	public static double evaluateBalancedHeuristicFor(final int numberA, final double probabilityDensityFunctionA, final int numberB, final double probabilityDensityFunctionB) {
		final double a = numberA * probabilityDensityFunctionA;
		final double b = numberB * probabilityDensityFunctionB;
		final double balancedHeuristic = a / (a + b);
		
		return balancedHeuristic;
	}
	
	/**
	 * Returns a power heuristic given {@code numberA}, {@code probabilityDensityFunctionA}, {@code numberB} and {@code probabilityDensityFunctionB}.
	 * 
	 * @param numberA some {@code int}
	 * @param probabilityDensityFunctionA some {@code double}
	 * @param numberB some {@code int}
	 * @param probabilityDensityFunctionB some {@code double}
	 * @return a power heuristic given {@code numberA}, {@code probabilityDensityFunctionA}, {@code numberB} and {@code probabilityDensityFunctionB}
	 */
	public static double evaluatePowerHeuristicFor(final int numberA, final double probabilityDensityFunctionA, final int numberB, final double probabilityDensityFunctionB) {
		final double a = numberA * probabilityDensityFunctionA;
		final double b = numberB * probabilityDensityFunctionB;
		final double powerOfA = a * a;
		final double powerOfB = b * b;
		final double powerHeuristic = powerOfA / (powerOfA + powerOfB);
		
		return powerHeuristic;
	}
	
	/**
	 * Returns the radical inverse given {@code index} and {@code base}.
	 * 
	 * @param index some {@code int}
	 * @param base some {@code int}
	 * @return the radical inverse given {@code index} and {@code base}
	 */
	public static double evaluateRadicalInverseFor(int index, final int base) {
		double result = 0.0D;
		double baseInversed0 = 1.0D / base;
		double baseInversed1 = baseInversed0;
		
		while(index > 0) {
			result += baseInversed1 * (index % base);
			
			index *= baseInversed0;
			
			baseInversed1 *= baseInversed0;
		}
		
		return result;
	}
	
	/**
	 * Returns the Sobol sequence with base two given {@code number} and {@code scramble}.
	 * 
	 * @param number some {@code long}
	 * @param scramble some {@code long}
	 * @return the Sobol sequence with base two given {@code number} and {@code scramble}
	 */
	public static double evaluateSobolSequenceWithBaseTwoFor(long number, long scramble) {
		for(long value = 1L << 31L; number != 0L; number >>= 1L, value ^= value >> 1L) {
			if((number & 0x1L) != 0L) {
				scramble ^= value;
			}
		}
		
		return Math.min(((scramble >> 8L) & 0xFFFFFFL) / (1L << 24L), ONE_MINUS_EPSILON);
	}
	
	/**
	 * Returns the Van Der Corput sequence given {@code number} and {@code scramble}.
	 * 
	 * @param number some {@code long}
	 * @param scramble some {@code long}
	 * @return the Van Der Corput sequence given {@code number} and {@code scramble}
	 */
	public static double evaluateVanDerCorputSequenceFor(long number, final long scramble) {
		number = (number << 16L) | (number >> 16L);
		number = ((number & 0x00FF00FFL) << 8) | ((number & 0xFF00FF00L) >> 8);
		number = ((number & 0x0F0F0F0FL) << 4) | ((number & 0xF0F0F0F0L) >> 4);
		number = ((number & 0x33333333L) << 2) | ((number & 0xCCCCCCCCL) >> 2);
		number = ((number & 0x55555555L) << 1) | ((number & 0xAAAAAAAAL) >> 1);
		number ^= scramble;
		
		return Math.min(((number >> 8L) & 0xFFFFFFL) / (1L << 24L), ONE_MINUS_EPSILON);
	}
	
	/**
	 * Returns the uniform cone probability density function given {@code cosThetaMaximum}.
	 * 
	 * @param cosThetaMaximum the maximum cos theta
	 * @return the uniform cone probability density function given {@code cosThetaMaximum}
	 */
	public static double getUniformConeProbabilityDensityFunctionFor(final double cosThetaMaximum) {
		return 1.0D / (PI_MULTIPLIED_BY_TWO * (1.0D - cosThetaMaximum));
	}
	
	/**
	 * Returns the uniform hemisphere probability density function.
	 * 
	 * @return the uniform hemisphere probability density function
	 */
	public static double getUniformHemisphereProbabilityDensityFunction() {
		return PI_MULTIPLIED_BY_TWO_RECIPROCAL;
	}
	
	/**
	 * Returns the uniform sphere probability density function.
	 * 
	 * @return the uniform sphere probability density function
	 */
	public static double getUniformSphereProbabilityDensityFunction() {
		return PI_MULTIPLIED_BY_FOUR_RECIPROCAL;
	}
	
	/**
	 * Returns a {@code double} array of length {@code 2}, corresponding to the X- and Y-coordinates of the concentric sample disk.
	 * 
	 * @param rx the random value to use for the X-axis
	 * @param ry the random value to use for the Y-axis
	 * @return a {@code double} array of length {@code 2}, corresponding to the X- and Y-coordinates of the concentric sample disk
	 */
	public static double[] toConcentricSampleDisk(final double rx, final double ry) {
		double dx = 0.0D;
		double dy = 0.0D;
		double r = 0.0D;
		double theta = 0.0D;
		
		final double sx = 2.0D * rx - 1.0D;
		final double sy = 2.0D * ry - 1.0D;
		
		if(sx != 0.0D || sy != 0.0D) {
			if(sx >= -sy) {
				if(sx > sy) {
					r = sx;
					
					if(sy > 0.0D) {
						theta = sy / r;
					} else {
						theta = 8.0D + sy / r;
					}
				} else {
					r = sy;
					
					theta = 2.0D - sx / r;
				}
			} else if(sx <= sy) {
				r = -sx;
				
				theta = 4.0D - sy / r;
			} else {
				r = -sy;
				
				theta = 6.0D + sx / r;
			}
			
			theta *= Math.PI * 0.25D;
			
			dx = r * Math.cos(theta);
			dy = r * Math.sin(theta);
		}
		
		return new double[] {dx, dy};
	}
	
	/**
	 * Performs the Latin Hypercube algorithm on {@code samples}, given its size and dimensions.
	 * <p>
	 * If either {@code samples} or {@code pRNG} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param samples the {@code double} array with sample values
	 * @param size the sample size in the {@code double} array (not necessarily the same as the length of it)
	 * @param dimensions the sample dimensions
	 * @param pRNG the {@code PRNG} to produce pseudorandom numbers
	 * @throws NullPointerException thrown if, and only if, either {@code samples} or {@code pRNG} are {@code null}
	 */
	public static void performLatinHypercube(final double[] samples, final int size, final int dimensions, final PRNG pRNG) {
		final double delta = 1.0D / size;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < dimensions; j++) {
				samples[dimensions * i + j] = Math.min((i + pRNG.nextDouble()) * delta, ONE_MINUS_EPSILON);
			}
		}
		
		for(int i = 0; i < dimensions; i++) {
			for(int j = 0; j < size; j++) {
				Arrays.swap(samples, dimensions * j + i, dimensions * pRNG.nextInt(size) + i);
			}
		}
	}
}