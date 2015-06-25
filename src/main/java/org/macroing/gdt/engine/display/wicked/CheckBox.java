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

import java.util.function.Consumer;

/**
 * An abstract base-class for all check boxes.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class CheckBox<T extends CheckBox<T>> extends Component<T> {
	/**
	 * Constructs a new {@code CheckBox} instance.
	 * <p>
	 * If either {@code id} or {@code wickedDisplay} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param id the ID of this {@code CheckBox} instance
	 * @param wickedDisplay the {@link WickedDisplay} that created this {@code CheckBox} instance
	 * @throws NullPointerException thrown if, and only if, either {@code id} or {@code wickedDisplay} are {@code null}
	 */
	protected CheckBox(final String id, final WickedDisplay wickedDisplay) {
		super(id, wickedDisplay);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, this {@code CheckBox} is selected, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code CheckBox} is selected, {@code false} otherwise
	 */
	public abstract boolean isSelected();
	
	/**
	 * Returns the text currently set for this {@code CheckBox} instance.
	 * 
	 * @return the text currently set for this {@code CheckBox} instance
	 */
	public abstract String getText();
	
	/**
	 * Attempts to set the {@code Consumer} that will be notified of selection changes.
	 * <p>
	 * Calling this method is equivalent to calling {@code checkBox.setOnSelectionChange(consumer, 0L)}.
	 * <p>
	 * If this {@code CheckBox} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code checkBox.setOnSelectionChange(consumer, checkBox.getKey())}.
	 * <p>
	 * Returned is the {@code CheckBox} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code consumer} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param consumer the {@code Consumer} that will be notified of selection changes
	 * @return the {@code CheckBox} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code consumer} is {@code null}
	 */
	public final T setOnSelectionChange(final Consumer<CheckBox<T>> consumer) {
		return setOnSelectionChange(consumer, 0L);
	}
	
	/**
	 * Attempts to set the {@code Consumer} that will be notified of selection changes.
	 * <p>
	 * If this {@code CheckBox} is currently locked, no state change will be performed unless {@code key == checkBox.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code checkBox.setOnSelectionChange(isSelected, checkBox.getKey())}.
	 * <p>
	 * Returned is the {@code CheckBox} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code consumer} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param consumer the {@code Consumer} that will be notified of selection changes
	 * @param key the key to temporarily unlock and lock the {@code CheckBox}
	 * @return the {@code CheckBox} instance itself, such that chaining can be performed
	 * @throws NullPointerException thrown if, and only if, {@code consumer} is {@code null}
	 */
	public abstract T setOnSelectionChange(final Consumer<CheckBox<T>> consumer, final long key);
	
	/**
	 * Attempts to set the selected state of this {@code CheckBox} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code checkBox.setSelected(isSelected, 0L)}.
	 * <p>
	 * If this {@code CheckBox} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code checkBox.setSelected(isSelected, checkBox.getKey())}.
	 * <p>
	 * Returned is the {@code CheckBox} instance itself, such that chaining can be performed.
	 * 
	 * @param isSelected the new selected state
	 * @return the {@code CheckBox} instance itself
	 */
	public final T setSelected(boolean isSelected) {
		return setSelected(isSelected, 0L);
	}
	
	/**
	 * Attempts to set the selected state of this {@code CheckBox} instance.
	 * <p>
	 * If this {@code CheckBox} is currently locked, no state change will be performed unless {@code key == checkBox.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code checkBox.setSelected(isSelected, checkBox.getKey())}.
	 * <p>
	 * Returned is the {@code CheckBox} instance itself, such that chaining can be performed.
	 * 
	 * @param isSelected the new selected state
	 * @param key the key to temporarily unlock and lock the {@code CheckBox}
	 * @return the {@code CheckBox} instance itself
	 */
	public abstract T setSelected(final boolean isSelected, final long key);
	
	/**
	 * Attempts to set the text for this {@code CheckBox} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code checkBox.setText(text, 0L)}.
	 * <p>
	 * If this {@code CheckBox} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code checkBox.setText(text, checkBox.getKey())}.
	 * <p>
	 * Returned is the {@code CheckBox} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code text} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param text the new text
	 * @return the {@code CheckBox} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code text} is {@code null}
	 */
	public final T setText(final String text) {
		return setText(text, 0L);
	}
	
	/**
	 * Attempts to set the text for this {@code CheckBox} instance.
	 * <p>
	 * If this {@code CheckBox} is currently locked, no state change will be performed unless {@code key == checkBox.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code checkBox.setText(text, checkBox.getKey())}.
	 * <p>
	 * Returned is the {@code CheckBox} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code text} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param text the new text
	 * @param key the key to temporarily unlock and lock the {@code CheckBox}
	 * @return the {@code CheckBox} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code text} is {@code null}
	 */
	public abstract T setText(final String text, final long key);
}