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
 * A {@link Filter} implementation that implements a Lanczos-Sinc Filter.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LanczosSincFilter extends Filter {
	private final double tau;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private LanczosSincFilter(final double width, final double height, final double tau) {
		super(width, height);
		
		this.tau = tau;
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
		return lanczosSinc(x * getWidthReciprocal(), this.tau) * lanczosSinc(y * getHeightReciprocal(), this.tau);
	}
	
	/**
	 * Returns a {@code double} value denoting Tau.
	 * 
	 * @return a {@code double} value denoting Tau
	 */
	public double getTau() {
		return this.tau;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the Lanczos-Sinc given {@code x} and {@code tau}.
	 * 
	 * @param x one of the values for Lanczos-Sinc
	 * @param tau one of the values for Lanczos-Sinc
	 * @return the Lanczos-Sinc given {@code x} and {@code tau}
	 */
	public static double lanczosSinc(final double x, final double tau) {
		double xAbsolute = Math.abs(x);
		
		if(xAbsolute < 1.0e-5D) {
			return 1.0D;
		} else if(xAbsolute > 1.0D) {
			return 0.0D;
		}
		
		xAbsolute *= Math.PI;
		
		final double y = xAbsolute * tau;
		final double sinc = Math.sin(y) / y;
		final double lanczos = Math.sin(xAbsolute) / xAbsolute;
		
		return sinc * lanczos;
	}
	
	/**
	 * Returns a new {@code LanczosSincFilter} instance with a width, a height and Tau of {@code 4.0D}, {@code 4.0D} and {@code 3.0D}, respectively.
	 * 
	 * @return a new {@code LanczosSincFilter} instance with a width, a height and Tau of {@code 4.0D}, {@code 4.0D} and {@code 3.0D}, respectively
	 */
	public static LanczosSincFilter newInstance() {
		return newInstance(4.0D, 4.0D, 3.0D);
	}
	
	/**
	 * Returns a new {@code LanczosSincFilter} instance given a width, a height and Tau.
	 * 
	 * @param width the width to use
	 * @param height the height to use
	 * @param tau the Tau to use
	 * @return a new {@code LanczosSincFilter} instance given a width, a height and Tau
	 */
	public static LanczosSincFilter newInstance(final double width, final double height, final double tau) {
		return new LanczosSincFilter(width, height, tau);
	}
}