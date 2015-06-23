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

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A model of a keyboard.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Keyboard {
	private static final Keyboard INSTANCE = new Keyboard();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<KeyboardObserver> keyboardObservers = new CopyOnWriteArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Keyboard() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Adds {@code keyboardObserver}, if not already present.
	 * <p>
	 * If {@code keyboardObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param keyboardObserver the {@link KeyboardObserver} to add
	 * @throws NullPointerException thrown if, and only if, {@code keyboardObserver} is {@code null}
	 */
	public void addKeyboardObserver(final KeyboardObserver keyboardObserver) {
		Objects.requireNonNull(keyboardObserver, "keyboardObserver == null");
		
		if(!this.keyboardObservers.contains(keyboardObserver)) {
			this.keyboardObservers.add(keyboardObserver);
		}
	}
	
	/**
	 * Fires a {@link KeyboardEvent} by notifying all currently added {@link KeyboardObserver}s.
	 * <p>
	 * If {@code keyboardEvent} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will not make the Operating System aware of any fired keyboard events.
	 * 
	 * @param keyboardEvent the {@code KeyboardEvent} that was fired
	 * @throws NullPointerException thrown if, and only if, {@code keyboardEvent} is {@code null}
	 */
	public void fireKeyboardEvent(final KeyboardEvent keyboardEvent) {
		Objects.requireNonNull(keyboardEvent, "keyboardEvent == null");
		
		for(final KeyboardObserver keyboardObserver : this.keyboardObservers) {
			keyboardObserver.onKeyboardEvent(keyboardEvent);
		}
	}
	
	/**
	 * Removes {@code keyboardObserver}, if present.
	 * <p>
	 * If {@code keyboardObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param keyboardObserver the {@link KeyboardObserver} to remove
	 * @throws NullPointerException thrown if, and only if, {@code keyboardObserver} is {@code null}
	 */
	public void removeKeyboardObserver(final KeyboardObserver keyboardObserver) {
		this.keyboardObservers.remove(Objects.requireNonNull(keyboardObserver, "keyboardObserver == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the one and only {@code Keyboard} instance.
	 * 
	 * @return the one and only {@code Keyboard} instance
	 */
	public static Keyboard getInstance() {
		return INSTANCE;
	}
}