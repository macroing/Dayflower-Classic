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
 * A {@link Filter} implementation that implements a Triangle Filter.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TriangleFilter extends Filter {
	private TriangleFilter(final double width, final double height) {
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
		return Math.max(0.0D, getWidth() - Math.abs(x)) * Math.max(0.0D, getHeight() - Math.abs(y));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code TriangleFilter} instance with a width and a height of {@code 2.0D} and {@code 2.0D}, respectively.
	 * 
	 * @return a new {@code TriangleFilter} instance with a width and a height of {@code 2.0D} and {@code 2.0D}, respectively
	 */
	public static TriangleFilter newInstance() {
		return newInstance(2.0D, 2.0D);
	}
	
	/**
	 * Returns a new {@code TriangleFilter} instance given a width and a height.
	 * 
	 * @param width the width to use
	 * @param height the height to use
	 * @return a new {@code TriangleFilter} instance given a width and a height
	 */
	public static TriangleFilter newInstance(final double width, final double height) {
		return new TriangleFilter(width, height);
	}
}