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
 * A class that consists exclusively of static methods that performs various operations.
 * <p>
 * The operations contained in this class could not be categorized and placed in their own class for whatever reason.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Functions {
	private Functions() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a clamped version of {@code value}.
	 * <p>
	 * The clamped version will be at least as low as {@code low}, or at most as high as {@code high}.
	 * 
	 * @param value the {@code double} value to clamp
	 * @param low the lowest {@code double} value to be returned
	 * @param high the highest {@code double} value to be returned
	 * @return a clamped version of {@code value}
	 */
	public static double clamp(final double value, final double low, final double high) {
		if(value < low) {
			return low;
		} else if(value > high) {
			return high;
		} else {
			return value;
		}
	}
	
	/**
	 * Returns a gamma corrected version of {@code value}.
	 * 
	 * @param value the value to perform gamma correction on
	 * @return a gamma corrected version of {@code value}
	 */
	public static double performGammaCorrectionFor(final double value) {
		return Math.pow(clamp(value, 0.0D, 1.0D), 1.0D / 2.2D) * 255.0D + 0.5D;
	}
	
	/**
	 * Returns a clamped version of {@code value}.
	 * <p>
	 * The clamped version will be at least as low as {@code low}, or at most as high as {@code high}.
	 * 
	 * @param value the {@code int} value to clamp
	 * @param low the lowest {@code int} value to be returned
	 * @param high the highest {@code int} value to be returned
	 * @return a clamped version of {@code value}
	 */
	public static int clamp(final int value, final int low, final int high) {
		if(value < low) {
			return low;
		} else if(value > high) {
			return high;
		} else {
			return value;
		}
	}
	
	/**
	 * Returns {@code value}, but only if it is within the range of {@code low} (inclusive) and {@code high} (inclusive).
	 * <p>
	 * If it is not within said range, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The {@code message} parameter will be the detail message of the thrown {@code IllegalArgumentException}. It may be {@code null}.
	 * 
	 * @param value the value to verify
	 * @param low the lowest value allowed (inclusive)
	 * @param high the highest value allowed (inclusive)
	 * @param message the detail message to the {@code IllegalArgumentException}
	 * @return {@code value}, but only if it is within the range of {@code low} (inclusive) and {@code high} (inclusive)
	 * @throws IllegalArgumentException thrown if, and only if, {@code value} is less than {@code low} or greater than {@code high}
	 */
	public static int requireRange(final int value, final int low, final int high, final String message) {
		if(value >= low && value <= high) {
			return value;
		}
		
		throw new IllegalArgumentException(message);
	}
	
	/**
	 * Returns an {@code int} representation of a {@code double} value.
	 * 
	 * @param value the value to convert from {@code double} to {@code int}
	 * @return an {@code int} representation of a {@code double} value
	 */
	public static int toInt(final double value) {
		return (int)(value);
	}
}