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
 * A {@link Filter} implementation that implements a Box Filter.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class BoxFilter extends Filter {
	private BoxFilter(final double width, final double height) {
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
		return 1.0D;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code BoxFilter} instance with a width and a height of {@code 0.5D} and {@code 0.5D}, respectively.
	 * 
	 * @return a new {@code BoxFilter} instance with a width and a height of {@code 0.5D} and {@code 0.5D}, respectively
	 */
	public static BoxFilter newInstance() {
		return newInstance(0.5D, 0.5D);
	}
	
	/**
	 * Returns a new {@code BoxFilter} instance given a width and a height.
	 * 
	 * @param width the width to use
	 * @param height the height to use
	 * @return a new {@code BoxFilter} instance given a width and a height
	 */
	public static BoxFilter newInstance(final double width, final double height) {
		return new BoxFilter(width, height);
	}
}