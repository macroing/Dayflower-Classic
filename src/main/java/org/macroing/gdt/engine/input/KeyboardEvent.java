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
 * A model of a keyboard event.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class KeyboardEvent {
	private final Key key;
	private final KeyState keyState;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private KeyboardEvent(final Key key, final KeyState keyState) {
		this.key = key;
		this.keyState = keyState;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Key} assigned to this {@code KeyboardEvent}.
	 * 
	 * @return the {@code Key} assigned to this {@code KeyboardEvent}
	 */
	public Key getKey() {
		return this.key;
	}
	
	/**
	 * Returns the {@link KeyState} assigned to this {@code KeyboardEvent}.
	 * 
	 * @return the {@code KeyState} assigned to this {@code KeyboardEvent}
	 */
	public KeyState getKeyState() {
		return this.keyState;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code KeyboardEvent} for a {@link Key} and {@link KeyState}.
	 * <p>
	 * If either {@code key} or {@code keyState} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param key the {@code Key} to use
	 * @param keyState the {@code KeyState} to use
	 * @return a new {@code KeyboardEvent}
	 * @throws NullPointerException thrown if, and only if, either {@code key} or {@code keyState} are {@code null}
	 */
	public static KeyboardEvent newInstance(Key key, KeyState keyState) {
		return new KeyboardEvent(Objects.requireNonNull(key, "key == null"), Objects.requireNonNull(keyState, "keyState == null"));
	}
}