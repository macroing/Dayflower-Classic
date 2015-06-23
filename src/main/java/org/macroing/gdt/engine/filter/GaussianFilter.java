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
 * A {@link Filter} implementation that implements a Gaussian Filter.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class GaussianFilter extends Filter {
	private final double falloff;
	private final double x;
	private final double y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private GaussianFilter(final double width, final double height, final double falloff) {
		super(width, height);
		
		this.falloff = falloff;
		this.x = Math.exp(-falloff * width * width);
		this.y = Math.exp(-falloff * height * height);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an evaluated value based on {@code x} and {@code y}.
	 * 
	 * @param x a {@code double} value denoting X
	 * @param y a {@code double} value denoting Y
	 * @return an evaluated value based on {@code x} and {@code y}
	 */
	@Override
	public double evaluate(final double x, final double y) {
		return gaussian(x, this.x, this.falloff) * gaussian(y, this.y, this.falloff);
	}
	
	/**
	 * Returns a {@code double} value denoting the falloff.
	 * 
	 * @return a {@code double} value denoting the falloff
	 */
	public double getFalloff() {
		return this.falloff;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the Gaussian given the three constants {@code a}, {@code b} and {@code c}.
	 * 
	 * @param a one of the three constants
	 * @param b one of the three constants
	 * @param c one of the three constants
	 * @return the Gaussian given the three constants {@code a}, {@code b} and {@code c}
	 */
	public static double gaussian(final double a, final double b, final double c) {
		return Math.max(0.0D, Math.exp(-c * a * a) - b);
	}
	
	/**
	 * Returns a new {@code GaussianFilter} instance with a width, a height and a falloff of {@code 2.0D}, {@code 2.0D} and {@code 2.0D}, respectively.
	 * 
	 * @return a new {@code GaussianFilter} instance with a width, a height and a falloff of {@code 2.0D}, {@code 2.0D} and {@code 2.0D}, respectively
	 */
	public static GaussianFilter newInstance() {
		return newInstance(2.0D, 2.0D, 2.0D);
	}
	
	/**
	 * Returns a new {@code GaussianFilter} instance given a width, a height and a falloff.
	 * 
	 * @param width the width to use
	 * @param height the height to use
	 * @param falloff the falloff to use
	 * @return a new {@code GaussianFilter} instance given a width, a height and a falloff
	 */
	public static GaussianFilter newInstance(final double width, final double height, final double falloff) {
		return new GaussianFilter(width, height, falloff);
	}
}