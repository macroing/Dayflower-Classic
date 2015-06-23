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
package org.macroing.gdt.engine.input;

/**
 * A model of a mouse pointer.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface MousePointer {
	/**
	 * Returns the location of the mouse pointer on the X-axis.
	 * <p>
	 * If this method is not supported by the current configuration, an {@code UnsupportedOperationException} should be thrown.
	 * 
	 * @return the location of the mouse pointer on the X-axis
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the current configuration
	 */
	int getX();
	
	/**
	 * Returns the location of the mouse pointer on the Y-axis.
	 * <p>
	 * If this method is not supported by the current configuration, an {@code UnsupportedOperationException} should be thrown.
	 * 
	 * @return the location of the mouse pointer on the Y-axis
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the current configuration
	 */
	int getY();
	
	/**
	 * Sets the location of the mouse pointer on the X- and Y-axes.
	 * <p>
	 * If this method is not supported by the current configuration, an {@code UnsupportedOperationException} should be thrown.
	 * 
	 * @param x the location of the mouse pointer on the X-axis
	 * @param y the location of the mouse pointer on the Y-axis
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the current configuration
	 */
	void setLocation(final int x, final int y);
	
	/**
	 * Sets the location of the mouse pointer on the X-axis.
	 * <p>
	 * If this method is not supported by the current configuration, an {@code UnsupportedOperationException} should be thrown.
	 * 
	 * @param x the location of the mouse pointer on the X-axis
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the current configuration
	 */
	void setX(final int x);
	
	/**
	 * Sets the location of the mouse pointer on the Y-axis.
	 * <p>
	 * If this method is not supported by the current configuration, an {@code UnsupportedOperationException} should be thrown.
	 * 
	 * @param y the location of the mouse pointer on the Y-axis
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the current configuration
	 */
	void setY(final int y);
}