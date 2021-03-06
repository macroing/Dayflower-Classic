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
 * A class that consists exclusively of static methods that operates on or returns {@code String}s.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Strings {
	private Strings() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} that consists of {@code string} repeated {@code repetition} times.
	 * <p>
	 * If {@code string} is {@code null}, the {@code String} literal {@code "null"} will be repeated {@code repetition} times.
	 * <p>
	 * If {@code repetition} is less than or equal to {@code 0}, an empty {@code String} will be returned.
	 * 
	 * @param string the {@code String} to repeat
	 * @param repetition how many times {@code string} should be repeated
	 * @return a {@code String} that consists of {@code string} repeated {@code repetition} times
	 */
	public static String repeat(final String string, final int repetition) {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0; i < repetition; i++) {
			stringBuilder.append(string);
		}
		
		return stringBuilder.toString();
	}
}