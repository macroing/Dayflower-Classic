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

/**
 * An abstract base-class for all top-level {@link Container}s.
 * <p>
 * A {@code Window} could, theoretically, be part of an internal windowing system. Take {@code JInternalFrame} in Java Swing as an example.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Window<T extends Window<T>> extends Container<T> {
	/**
	 * Constructs a new {@code Window} instance.
	 * <p>
	 * If either {@code id} or {@code wickedDisplay} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param id the ID of this {@code Window} instance
	 * @param wickedDisplay the {@link WickedDisplay} that created this {@code Window} instance
	 * @throws NullPointerException thrown if, and only if, either {@code id} or {@code wickedDisplay} are {@code null}
	 */
	protected Window(final String id, final WickedDisplay wickedDisplay) {
		super(id, wickedDisplay);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Attempts to set the title of this {@code Window} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code window.setTitle(title, 0L)}.
	 * <p>
	 * If this {@code Window} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code window.setTitle(title, window.getKey())}.
	 * <p>
	 * Returned is the {@code Window} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code title} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * Not all {@code Window}s support a title. In that case, it's up to the implementation at hand to decide what to do. Some implementations may decide to do nothing, whereas others may throw an {@code UnsupportedOperationException}. The action should,
	 * however, be documented.
	 * 
	 * @param title the new title
	 * @return the {@code Window} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code title} is {@code null}
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the {@code Window} instance
	 */
	public final T setTitle(final String title) {
		return setTitle(title, 0L);
	}
	
	/**
	 * Attempts to set the title of this {@code Window} instance.
	 * <p>
	 * If this {@code Window} is currently locked, no state change will be performed unless {@code key == window.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code window.setTitle(title, window.getKey())}.
	 * <p>
	 * Returned is the {@code Window} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code title} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * Not all {@code Window}s support a title. In that case, it's up to the implementation at hand to decide what to do. Some implementations may decide to do nothing, whereas others may throw an {@code UnsupportedOperationException}. The action should,
	 * however, be documented.
	 * 
	 * @param title the new title
	 * @param key the key to temporarily unlock and lock the {@code Window}
	 * @return the {@code Window} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code title} is {@code null}
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the {@code Window} instance
	 */
	public abstract T setTitle(final String title, final long key);
}