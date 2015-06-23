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

/**
 * A class that consists exclusively of static methods that solves polynomial equations, such as linear systems and quadratic equations.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PolynomialEquations {
	private PolynomialEquations() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Attempts to solve the linear system given the variables in {@code a} and {@code b}.
	 * <p>
	 * Returns a {@code double} array with length {@code 2}.
	 * <p>
	 * If the linear system cannot be solved, the returned {@code double} array will contain values of {@code Double.NaN}.
	 * <p>
	 * If either {@code a}, {@code a[0]}, {@code a[1]} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@code a.length}, {@code a[0].length}, {@code a[1].length} or {@code b.length} are less than {@code 2}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * 
	 * @param a a two-dimensional {@code double} array
	 * @param b a one-dimensional {@code double} array
	 * @return a {@code double} array with length {@code 2}
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, either {@code a.length}, {@code a[0].length}, {@code a[1].length} or {@code b.length} are less than {@code 2}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code a[0]}, {@code a[1]} or {@code b} are {@code null}
	 */
	public static double[] solveLinearSystem(final double[][] a, final double[] b) {
		final double[] result = new double[] {Double.NaN, Double.NaN};
		
		final double determinant = a[0][0] * a[1][1] - a[0][1] * a[1][0];
		final double determinantReciprocal = 1.0D / determinant;
		
		if(Math.abs(determinant) >= 1e-10D) {
			final double value0 = (a[1][1] * b[0] - a[0][1] * b[1]) * determinantReciprocal;
			final double value1 = (a[0][0] * b[1] - a[1][0] * b[0]) * determinantReciprocal;
			
			if(!Double.isNaN(value0) && !Double.isNaN(value1)) {
				result[0] = value0;
				result[1] = value1;
			}
		}
		
		return result;
    }
	
	/**
	 * Attempts to solve the quadratic equation given {@code a}, {@code b} and {@code c}.
	 * <p>
	 * Returns a {@code double} array with length {@code 2}.
	 * <p>
	 * If the quadratic equation cannot be solved, the returned {@code double} array will contain values of {@code Double.NaN}.
	 * 
	 * @param a the quadratic coefficient
	 * @param b the linear coefficient
	 * @param c the constant or free term
	 * @return a {@code double} array with length {@code 2}
	 */
	public static double[] solveQuadraticEquation(final double a, final double b, final double c) {
		final double[] result = new double[] {Double.NaN, Double.NaN};
		
		final double discriminant = b * b - 4.0D * a * c;
		
		if(discriminant >= 0.0D) {
			final double discriminantSqrt = Math.sqrt(discriminant);
			final double quadratic = -0.5D * (b < 0.0D ? b - discriminantSqrt : b + discriminantSqrt);
			final double result0 = quadratic / a;
			final double result1 = c / quadratic;
			
			result[0] = Math.min(result0, result1);
			result[1] = Math.max(result0, result1);
		}
		
		return result;
	}
}