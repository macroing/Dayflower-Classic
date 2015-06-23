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
 * An abstract base-class for all containers that can contain other components.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Container<T extends Container<T>> extends Component<T> {
	/**
	 * Constructs a new {@code Container} instance.
	 * <p>
	 * If either {@code id} or {@code wickedDisplay} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param id the ID of this {@code Container} instance
	 * @param wickedDisplay the {@link WickedDisplay} that created this {@code Container} instance
	 * @throws NullPointerException thrown if, and only if, either {@code id} or {@code wickedDisplay} are {@code null}
	 */
	protected Container(final String id, final WickedDisplay wickedDisplay) {
		super(id, wickedDisplay);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Attempts to add {@code component} to this {@code Container} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code container.addComponent(component, 0L)}.
	 * <p>
	 * If this {@code Container} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code container.addComponent(component, container.getKey())}.
	 * <p>
	 * Returned is the {@code Container} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code component} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param component the {@link Component} to add
	 * @return the {@code Container} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code component} is {@code null}
	 */
	public final T addComponent(final Component<?> component) {
		return addComponent(component, 0L);
	}
	
	/**
	 * Attempts to add {@code component} to this {@code Container} instance.
	 * <p>
	 * If this {@code Container} is currently locked, no state change will be performed unless {@code key == container.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code container.addComponent(component, container.getKey())}.
	 * <p>
	 * Returned is the {@code Container} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code component} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param component the {@link Component} to add
	 * @param key the key to temporarily unlock and lock the {@code Container}
	 * @return the {@code Container} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code component} is {@code null}
	 */
	public abstract T addComponent(final Component<?> component, final long key);
}