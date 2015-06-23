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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * The abstract base-class for all components.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Component<T extends Component<T>> {
	private long key;
	private final Map<String, Object> properties = new LinkedHashMap<>();
	private final Random random = new Random();
	private final String id;
	private final WickedDisplay wickedDisplay;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Component} instance.
	 * <p>
	 * If either {@code id} or {@code wickedDisplay} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param id the ID of this {@code Component} instance
	 * @param wickedDisplay the {@link WickedDisplay} that created this {@code Component} instance
	 * @throws NullPointerException thrown if, and only if, either {@code id} or {@code wickedDisplay} are {@code null}
	 */
	protected Component(final String id, final WickedDisplay wickedDisplay) {
		this.id = Objects.requireNonNull(id, "id == null");
		this.wickedDisplay = Objects.requireNonNull(wickedDisplay, "wickedDisplay == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Attempts to authenticate with this {@code Component} instance.
	 * <p>
	 * Calling {@code component.authenticate(0L)} will return {@code true} if, and only if, no lock is currently held.
	 * 
	 * @param key the key to authenticate with
	 * @return {@code true} if, and only if, authentication was successful, {@code false} otherwise
	 */
	protected final synchronized boolean authenticate(final long key) {
		return this.key == key;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} refers to this {@code Component} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code Component} instance for equality
	 * @return {@code true} if, and only if, {@code object} refers to this {@code Component} instance, {@code false} otherwise
	 */
	@Override
	public final boolean equals(final Object object) {
		return super.equals(object);
	}
	
	/**
	 * Returns {@code true} if, and only if, a property with a key of {@code key} exists, {@code false} otherwise.
	 * <p>
	 * If {@code key} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param key the key to find a property for
	 * @return {@code true} if, and only if, a property with a key of {@code key} exists, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code key} is {@code null}
	 */
	public final synchronized boolean hasProperty(final String key) {
		return this.properties.containsKey(Objects.requireNonNull(key, "key == null"));
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Component} is movable, {@code false} otherwise.
	 * <p>
	 * A {@code Component} that is movable, can be moved around with your mouse.
	 * 
	 * @return {@code true} if, and only if, this {@code Component} is movable, {@code false} otherwise
	 */
	public abstract boolean isMovable();
	
	/**
	 * Returns {@code true} if, and only if, this {@code Component} is resizable, {@code false} otherwise.
	 * <p>
	 * A {@code Component} that is resizable, can be resized with your mouse.
	 * 
	 * @return {@code true} if, and only if, this {@code Component} is resizable, {@code false} otherwise
	 */
	public abstract boolean isResizable();
	
	/**
	 * Returns {@code true} if, and only if, this {@code Component} is visible, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Component} is visible, {@code false} otherwise
	 */
	public abstract boolean isVisible();
	
	/**
	 * Attempts to unlock this {@code Component} given {@code key} as key.
	 * <p>
	 * Returns {@code true} if, and only if, this {@code Component} was unlocked, {@code false} otherwise.
	 * 
	 * @param key the key to unlock this {@code Component} with
	 * @return {@code true} if, and only if, this {@code Component} was unlocked, {@code false} otherwise
	 */
	public final synchronized boolean unlock(final long key) {
		boolean hasUnlocked = false;
		
		if(this.key == key) {
			this.key = 0L;
			
			hasUnlocked = true;
		}
		
		return hasUnlocked;
	}
	
	/**
	 * Returns the height of this {@code Component}.
	 * 
	 * @return the height of this {@code Component}
	 */
	public abstract int getHeight();
	
	/**
	 * Returns the width of this {@code Component}.
	 * 
	 * @return the width of this {@code Component}
	 */
	public abstract int getWidth();
	
	/**
	 * Returns the position along the X-axis for this {@code Component}.
	 * 
	 * @return the position along the X-axis for this {@code Component}
	 */
	public abstract int getX();
	
	/**
	 * Returns the position along the Y-axis for this {@code Component}.
	 * 
	 * @return the position along the Y-axis for this {@code Component}
	 */
	public abstract int getY();
	
	/**
	 * Returns a hash-code for this {@code Component} instance.
	 * 
	 * @return a hash-code for this {@code Component} instance
	 */
	@Override
	public final int hashCode() {
		return super.hashCode();
	}
	
	/**
	 * Returns the key currently assigned to this {@code Component} instance.
	 * <p>
	 * If no key has been assigned, {@code 0L} will be returned.
	 * <p>
	 * This method has been provided so you can force state changes. Obviously it's not recommended, as that would make the entire locking mechanism obsolete. But at least you'll have the option at your disposal, should you ever need it.
	 * 
	 * @return the key currently assigned to this {@code Component} instance
	 */
	public final synchronized long getKey() {
		return this.key;
	}
	
	/**
	 * Attempts to lock this {@code Component}, such that you need a key to modify the state of it.
	 * <p>
	 * The {@code long} returned by this method is the key to unlock it with.
	 * <p>
	 * If the key is {@code 0L}, then this operation did not succeed, because it was already locked by someone else.
	 * 
	 * @return a {@code long} denoting the key or {@code 0L} if the locking operation did not succeed
	 */
	public final synchronized long lock() {
		if(this.key == 0L) {
			do {
				this.key = this.random.nextLong();
			} while(this.key != 0L);
			
			return this.key;
		}
		
		return 0L;
	}
	
	/**
	 * Returns the implementation-specific {@code Object} corresponding to this {@code Component} instance.
	 * <p>
	 * For instance, if the {@link Platform} that created this {@code Component} instance uses Swing, then this method should return the {@code JComponent}, {@code JWindow} or whatever type this {@code Component} instance is represented by.
	 * <p>
	 * This method should not return {@code null}.
	 * 
	 * @return the implementation-specific {@code Object} corresponding to this {@code Component} instance
	 */
	public abstract Object getComponentObject();
	
	/**
	 * Returns the ID associated with this {@code Component} instance.
	 * <p>
	 * The ID cannot be {@code null}.
	 * 
	 * @return the ID associated with this {@code Component} instance
	 */
	public final String getId() {
		return this.id;
	}
	
	/**
	 * Attempts to set the location of this {@code Component} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code component.setLocation(x, y, 0L)}.
	 * <p>
	 * If this {@code Component} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code component.setLocation(x, y, component.getKey())}.
	 * <p>
	 * Returned is the {@code Component} instance itself, such that chaining can be performed.
	 * <p>
	 * The behavior of specifying a negative value for {@code x} or {@code y} is not currently well defined.
	 * 
	 * @param x the new position along the X-axis
	 * @param y the new position along the Y-axis
	 * @return the {@code Component} instance itself
	 */
	public final T setLocation(final int x, final int y) {
		return setLocation(x, y, 0L);
	}
	
	/**
	 * Attempts to set the location of this {@code Component} instance.
	 * <p>
	 * If this {@code Component} is currently locked, no state change will be performed unless {@code key == component.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code component.setLocation(x, y, component.getKey())}.
	 * <p>
	 * Returned is the {@code Component} instance itself, such that chaining can be performed.
	 * <p>
	 * The behavior of specifying a negative value for {@code x} or {@code y} is not currently well defined.
	 * 
	 * @param x the new position along the X-axis
	 * @param y the new position along the Y-axis
	 * @param key the key to temporarily unlock and lock the {@code Component}
	 * @return the {@code Component} instance itself
	 */
	public abstract T setLocation(final int x, final int y, final long key);
	
	/**
	 * Attempts to set the movable state of this {@code Component} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code component.setMovable(isMovable, 0L)}.
	 * <p>
	 * If this {@code Component} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code component.setMovable(isMovable, component.getKey())}.
	 * <p>
	 * Returned is the {@code Component} instance itself, such that chaining can be performed.
	 * 
	 * @param isMovable the new movable state
	 * @return the {@code Component} instance itself
	 */
	public final T setMovable(final boolean isMovable) {
		return setMovable(isMovable, 0L);
	}
	
	/**
	 * Attempts to set the movable state of this {@code Component} instance.
	 * <p>
	 * If this {@code Component} is currently locked, no state change will be performed unless {@code key == component.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code component.setMovable(isMovable, component.getKey())}.
	 * <p>
	 * Returned is the {@code Component} instance itself, such that chaining can be performed.
	 * 
	 * @param isMovable the new movable state
	 * @param key the key to temporarily unlock and lock the {@code Component}
	 * @return the {@code Component} instance itself
	 */
	public abstract T setMovable(final boolean isMovable, final long key);
	
	/**
	 * Attempts to set the resizable state of this {@code Component} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code component.setResizable(isResizable, 0L)}.
	 * <p>
	 * If this {@code Component} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code component.setResizable(isResizable, component.getKey())}.
	 * <p>
	 * Returned is the {@code Component} instance itself, such that chaining can be performed.
	 * 
	 * @param isResizable the new resizable state
	 * @return the {@code Component} instance itself
	 */
	public final T setResizable(final boolean isResizable) {
		return setResizable(isResizable, 0L);
	}
	
	/**
	 * Attempts to set the resizable state of this {@code Component} instance.
	 * <p>
	 * If this {@code Component} is currently locked, no state change will be performed unless {@code key == component.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code component.setResizable(isResizable, component.getKey())}.
	 * <p>
	 * Returned is the {@code Component} instance itself, such that chaining can be performed.
	 * 
	 * @param isResizable the new resizable state
	 * @param key the key to temporarily unlock and lock the {@code Component}
	 * @return the {@code Component} instance itself
	 */
	public abstract T setResizable(final boolean isResizable, final long key);
	
	/**
	 * Attempts to set the size of this {@code Component} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code component.setSize(width, height, 0L)}.
	 * <p>
	 * If this {@code Component} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code component.setSize(width, height, component.getKey())}.
	 * <p>
	 * Returned is the {@code Component} instance itself, such that chaining can be performed.
	 * <p>
	 * The behavior of specifying a negative value for {@code width} or {@code height} is not currently well defined.
	 * 
	 * @param width the new width
	 * @param height the height
	 * @return the {@code Component} instance itself
	 */
	public final T setSize(final int width, final int height) {
		return setSize(width, height, 0L);
	}
	
	/**
	 * Attempts to set the size of this {@code Component} instance.
	 * <p>
	 * If this {@code Component} is currently locked, no state change will be performed unless {@code key == component.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code component.setSize(width, height, component.getKey())}.
	 * <p>
	 * Returned is the {@code Component} instance itself, such that chaining can be performed.
	 * <p>
	 * The behavior of specifying a negative value for {@code width} or {@code height} is not currently well defined.
	 * 
	 * @param width the new width
	 * @param height the new height
	 * @param key the key to temporarily unlock and lock the {@code Component}
	 * @return the {@code Component} instance itself
	 */
	public abstract T setSize(final int width, final int height, final long key);
	
	/**
	 * Attempts to set the visibility state of this {@code Component} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code component.setVisible(isVisible, 0L)}.
	 * <p>
	 * If this {@code Component} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code component.setVisible(isVisible, component.getKey())}.
	 * <p>
	 * Returned is the {@code Component} instance itself, such that chaining can be performed.
	 * 
	 * @param isVisible the new visibility state
	 * @return the {@code Component} instance itself
	 */
	public final T setVisible(final boolean isVisible) {
		return setVisible(isVisible, 0L);
	}
	
	/**
	 * Attempts to set the visibility state of this {@code Component} instance.
	 * <p>
	 * If this {@code Component} is currently locked, no state change will be performed unless {@code key == component.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code component.setVisible(isVisible, component.getKey())}.
	 * <p>
	 * Returned is the {@code Component} instance itself, such that chaining can be performed.
	 * 
	 * @param isVisible the new visibility state
	 * @param key the key to temporarily unlock and lock the {@code Component}
	 * @return the {@code Component} instance itself
	 */
	public abstract T setVisible(final boolean isVisible, final long key);
	
	/**
	 * Returns the {@link WickedDisplay} that was used to create this {@code Component} instance.
	 * <p>
	 * The returned {@code WickedDisplay} will not be {@code null}.
	 * 
	 * @return the {@code WickedDisplay} that was used to create this {@code Component} instance
	 */
	public final WickedDisplay getWickedDisplay() {
		return this.wickedDisplay;
	}
}