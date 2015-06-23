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
 * A class that consists exclusively of static methods that performs interpolations.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Interpolation {
	private Interpolation() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a linearly interpolated version of {@code a} and {@code b}, based on {@code fraction} (which should be between {@code 0.0D} (inclusive) and {@code 1.0D} (inclusive)).
	 * 
	 * @param a one of the two {@code double} values used in the linear interpolation process
	 * @param b one of the two {@code double} values used in the linear interpolation process
	 * @param fraction a {@code double} between {@code 0.0D} (inclusive) and {@code 1.0D} (inclusive)
	 * @return a linearly interpolated version of {@code a} and {@code b}, based on {@code fraction}
	 */
	public static double lerp(final double a, final double b, final double fraction) {
		return (1.0D - fraction) * a + fraction * b;
	}
	
	/**
	 * Returns a linearly interpolated version of {@code a} and {@code b}, in {@code int} form, based on {@code fraction} (which should be between {@code 0.0D} (inclusive) and {@code 1.0D} (inclusive)).
	 * 
	 * @param a one of the two {@code double} values used in the linear interpolation process
	 * @param b one of the two {@code double} values used in the linear interpolation process
	 * @param fraction a {@code double} between {@code 0.0D} (inclusive) and {@code 1.0D} (inclusive)
	 * @return a linearly interpolated version of {@code a} and {@code b}, in {@code int} form, based on {@code fraction}
	 */
	public static int lerpToInt(final double a, final double b, final double fraction) {
		return (int)(lerp(a, b, fraction));
	}
}