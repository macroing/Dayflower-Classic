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
 * A {@code MouseState} provides information about the fired mouse event. That is, whether the mouse pointer was moved, a button was pressed or released, or the scroll wheel was scrolled up or down.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum MouseState {
	/**
	 * A {@code MouseState} denoting that the mouse pointer was moved down.
	 */
	MOVED_DOWN("Moved down"),
	
	/**
	 * A {@code MouseState} denoting that the mouse pointer was moved left.
	 */
	MOVED_LEFT("Moved left"),
	
	/**
	 * A {@code MouseState} denoting that the mouse pointer was moved right.
	 */
	MOVED_RIGHT("Moved right"),
	
	/**
	 * A {@code MouseState} denoting that the mouse pointer was moved up.
	 */
	MOVED_UP("Moved up"),
	
	/**
	 * A {@code MouseState} denoting that the first mouse button was pressed.
	 */
	PRESSED_BUTTON_1("Pressed button 1"),
	
	/**
	 * A {@code MouseState} denoting that the second mouse button was pressed.
	 */
	PRESSED_BUTTON_2("Pressed button 2"),
	
	/**
	 * A {@code MouseState} denoting that the third mouse button was pressed.
	 */
	PRESSED_BUTTON_3("Pressed button 3"),
	
	/**
	 * A {@code MouseState} denoting that the first mouse button was released.
	 */
	RELEASED_BUTTON_1("Released button 1"),
	
	/**
	 * A {@code MouseState} denoting that the second mouse button was released.
	 */
	RELEASED_BUTTON_2("Released button 2"),
	
	/**
	 * A {@code MouseState} denoting that the third mouse button was released.
	 */
	RELEASED_BUTTON_3("Released button 3"),
	
	/**
	 * A {@code MouseState} denoting that the scroll wheel was scrolled down.
	 */
	SCROLLED_DOWN("Scrolled down"),
	
	/**
	 * A {@code MouseState} denoting that the scroll wheel was scrolled up.
	 */
	SCROLLED_UP("Scrolled up");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String toString;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private MouseState(final String toString) {
		this.toString = toString;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code MouseState}.
	 * 
	 * @return a {@code String} representation of this {@code MouseState}
	 */
	@Override
	public String toString() {
		return this.toString;
	}
}