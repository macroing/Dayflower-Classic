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
 * A {@link Filter} implementation that implements a Catmull-Rom Filter.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class CatmullRomFilter extends Filter {
	private CatmullRomFilter(final double width, final double height) {
		super(width, height);
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
		final double value0 = x * x + y * y;
		final double value1 = Math.sqrt(value0);
		
		double value2 = 0.0D;
		
		if(value1 < 2.0D) {
			if(value1 < 1.0D) {
				value2 = 3.0D * value1 * value0 - 5.0D * value0 + 2.0D;
			} else {
				value2 = -value1 * value0 + 5.0D * value0 - 8.0D * value1 + 4.0D;
			}
		}
		
		return value2;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code CatmullRomFilter} instance given a width and a height.
	 * 
	 * @param width the width to use
	 * @param height the height to use
	 * @return a new {@code CatmullRomFilter} instance given a width and a height
	 */
	public static CatmullRomFilter newInstance(final double width, final double height) {
		return new CatmullRomFilter(width, height);
	}
}