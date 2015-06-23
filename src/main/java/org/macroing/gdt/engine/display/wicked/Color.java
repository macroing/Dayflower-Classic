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
package org.macroing.gdt.engine.display.wicked;

import org.macroing.gdt.engine.util.Functions;

/**
 * The {@code Color} class models a color by encapsulating its RGBA components.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Color {
	private final int alpha;
	private final int blue;
	private final int green;
	private final int red;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Color(final int red, final int green, final int blue, final int alpha) {
		this.alpha = alpha;
		this.blue = blue;
		this.green = green;
		this.red = red;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the value of the alpha (A) component.
	 * 
	 * @return the value of the alpha (A) component
	 */
	public int getAlpha() {
		return this.alpha;
	}
	
	/**
	 * Returns the value of the blue (B) component.
	 * 
	 * @return the value of the blue (B) component
	 */
	public int getBlue() {
		return this.blue;
	}
	
	/**
	 * Returns the value of the green (G) component.
	 * 
	 * @return the value of the green (G) component
	 */
	public int getGreen() {
		return this.green;
	}
	
	/**
	 * Returns the value of the red (R) component.
	 * 
	 * @return the value of the red (R) component
	 */
	public int getRed() {
		return this.red;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Color} instance given a set of RGBA components.
	 * <p>
	 * If either {@code red}, {@code green}, {@code blue} or {@code alpha} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param red the red (R) component
	 * @param green the green (G) component
	 * @param blue the blue (B) component
	 * @param alpha the alpha (A) component
	 * @return a {@code Color} instance given a set of RGBA components
	 * @throws IllegalArgumentException thrown if, and only if, either {@code red}, {@code green}, {@code blue} or {@code alpha} are less than {@code 0} or greater than {@code 255}
	 */
	public static Color valueOf(final int red, final int green, final int blue, final int alpha) {
		return new Color(Functions.requireRange(red, 0, 255, "red= " + red), Functions.requireRange(green, 0, 255, "green= " + green), Functions.requireRange(blue, 0, 255, "blue= " + blue), Functions.requireRange(alpha, 0, 255, "alpha= " + alpha));
	}
}