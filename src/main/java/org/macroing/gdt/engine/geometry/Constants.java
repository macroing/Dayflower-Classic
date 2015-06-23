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
package org.macroing.gdt.engine.geometry;

/**
 * A class that consists exclusively of constant fields that are used by various classes in this API.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Constants {
	/**
	 * The epsilon constant is a very small number.
	 */
	public static final double EPSILON = 1.0E-4D;
	
	/**
	 * The infinity constant is a very large number.
	 */
	public static final double INFINITY = 1.0E20D;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Constants() {
		
	}
}