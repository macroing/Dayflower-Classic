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
 * An abstract base-class for filtering.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Filter {
	private final double height;
	private final double heightReciprocal;
	private final double width;
	private final double widthReciprocal;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Filter} instance given a width and a height.
	 * 
	 * @param width the width of the {@code Filter} instance
	 * @param height the height of the {@code Filter} instance
	 */
	protected Filter(final double width, final double height) {
		this.height = height;
		this.heightReciprocal = 1.0D / height;
		this.width = width;
		this.widthReciprocal = 1.0D / width;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an evaluated value based on {@code x} and {@code y}.
	 * 
	 * @param x a {@code double} value denoting X
	 * @param y a {@code double} value denoting Y
	 * @return an evaluated value based on {@code x} and {@code y}
	 */
	public abstract double evaluate(final double x, final double y);
	
	/**
	 * Returns the height of this {@code Filter} instance.
	 * 
	 * @return the height of this {@code Filter} instance
	 */
	public final double getHeight() {
		return this.height;
	}
	
	/**
	 * Returns the reciprocal of the height of this {@code Filter} instance.
	 * 
	 * @return the reciprocal of the height of this {@code Filter} instance
	 */
	public final double getHeightReciprocal() {
		return this.heightReciprocal;
	}
	
	/**
	 * Returns the width of this {@code Filter} instance.
	 * 
	 * @return the width of this {@code Filter} instance
	 */
	public final double getWidth() {
		return this.width;
	}
	
	/**
	 * Returns the reciprocal of the width of this {@code Filter} instance.
	 * 
	 * @return the reciprocal of the width of this {@code Filter} instance
	 */
	public final double getWidthReciprocal() {
		return this.widthReciprocal;
	}
}