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

import java.util.Objects;

/**
 * A model of a mouse event.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class MouseEvent {
	private final int count;
	private final MouseState mouseState;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private MouseEvent(final int count, final MouseState mouseState) {
		this.count = count;
		this.mouseState = mouseState;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the click or scroll count.
	 * 
	 * @return the click or scroll count
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * Returns the {@link MouseState} assigned to this {@code MouseEvent}.
	 * 
	 * @return the {@code MouseState} assigned to this {@code MouseEvent}
	 */
	public MouseState getMouseState() {
		return this.mouseState;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code MouseEvent} for the click or scroll count and a {@link MouseState}.
	 * <p>
	 * Any negative count value will be converted into its absolute counterpart.
	 * <p>
	 * If {@code mouseState} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param count the click or scroll count
	 * @param mouseState the {@code MouseState} to use
	 * @return a new {@code MouseEvent}
	 * @throws NullPointerException thrown if, and only if, {@code mouseState} is {@code null}
	 */
	public static MouseEvent newInstance(final int count, final MouseState mouseState) {
		return new MouseEvent(count, Objects.requireNonNull(mouseState, "mouseState == null"));
	}
}