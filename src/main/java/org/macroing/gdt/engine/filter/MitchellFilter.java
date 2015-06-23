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
package org.macroing.gdt.engine.filter;

/**
 * A {@link Filter} implementation that implements a Mitchell Filter.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class MitchellFilter extends Filter {
	private final double b;
	private final double c;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private MitchellFilter(final double width, final double height, final double b, final double c) {
		super(width, height);
		
		this.b = b;
		this.c = c;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an evaluated {@code double} value for {@code x} and {@code y}.
	 * 
	 * @param x a {@code double} value denoting X
	 * @param y a {@code double} value denoting Y
	 * @return an evaluated {@code double} value for {@code x} and {@code y}
	 */
	@Override
	public double evaluate(final double x, final double y) {
		return mitchell(x * getWidthReciprocal(), this.b, this.c) * mitchell(y * getHeightReciprocal(), this.b, this.c);
	}
	
	/**
	 * Returns the B-coefficient.
	 * 
	 * @return the B-coefficient
	 */
	public double getB() {
		return this.b;
	}
	
	/**
	 * Returns the C-coefficient.
	 * 
	 * @return the C-coefficient
	 */
	public double getC() {
		return this.c;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the Mitchell given {@code x}, {@code b} and {@code c}.
	 * 
	 * @param x one of the values for Mitchell
	 * @param b one of the values for Mitchell
	 * @param c one of the values for Mitchell
	 * @return the Mitchell given {@code x}, {@code b} and {@code c}
	 */
	public static double mitchell(final double x, final double b, final double c) {
		final double xAbsolute = Math.abs(2.0D * x);
		
		if(xAbsolute > 1.0D) {
			return ((-b - 6.0D * c) * xAbsolute * xAbsolute * xAbsolute + (6.0D * b + 30.0D * c) * xAbsolute * xAbsolute + (-12.0D * b - 48.0D * c) * xAbsolute + (8.0D * b + 24.0D * c)) * (1.0D / 6.0D);
		}
		
		return ((12.0D - 9.0D * b - 6.0D * c) * xAbsolute * xAbsolute * xAbsolute + (-18.0D + 12.0D * b + 6.0D * c) * xAbsolute * xAbsolute + (6.0D - 2.0D * b)) * (1.0D / 6.0D);
	}
	
	/**
	 * Returns a new {@code MitchellFilter} instance with a width, a height and B- and C-coefficients of {@code 2.0D}, {@code 2.0D}, {@code 1.0D / 3.0D} and {@code 1.0D / 3.0D}, respectively.
	 * 
	 * @return a new {@code MitchellFilter} instance with a width, a height and B- and C-coefficients of {@code 2.0D}, {@code 2.0D}, {@code 1.0D / 3.0D} and {@code 1.0D / 3.0D}, respectively
	 */
	public static MitchellFilter newInstance() {
		return newInstance(2.0D, 2.0D, 1.0D / 3.0D, 1.0D / 3.0D);
	}
	
	/**
	 * Returns a new {@code MitchellFilter} instance given a width, a height and B- and C-coefficients.
	 * 
	 * @param width the width to use
	 * @param height the height to use
	 * @param b the B-coefficient to use
	 * @param c the C-coefficient to use
	 * @return a new {@code MitchellFilter} instance given a width, a height and B- and C-coefficients
	 */
	public static MitchellFilter newInstance(final double width, final double height, final double b, final double c) {
		return new MitchellFilter(width, height, b, c);
	}
}