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
 * An abstract base-class for all {@link Component}s containing text-state.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class TextComponent<T extends TextComponent<T>> extends Component<T> {
	/**
	 * Constructs a new {@code TextComponent} instance.
	 * <p>
	 * If either {@code id} or {@code wickedDisplay} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param id the ID of this {@code TextComponent} instance
	 * @param wickedDisplay the {@link WickedDisplay} that created this {@code TextComponent} instance
	 * @throws NullPointerException thrown if, and only if, either {@code id} or {@code wickedDisplay} are {@code null}
	 */
	protected TextComponent(final String id, final WickedDisplay wickedDisplay) {
		super(id, wickedDisplay);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the foreground color of this {@code TextComponent} instance.
	 * 
	 * @return the foreground color of this {@code TextComponent} instance
	 */
	public abstract Color getForeground();
	
	/**
	 * Returns the font of this {@code TextComponent} instance.
	 * 
	 * @return the font of this {@code TextComponent} instance
	 */
	public abstract Font getFont();
	
	/**
	 * Returns the text of this {@code TextComponent} instance.
	 * 
	 * @return the text of this {@code TextComponent} instance
	 */
	public abstract String getText();
	
	/**
	 * Attempts to set the font of this {@code TextComponent} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code textComponent.setFont(font, 0L)}.
	 * <p>
	 * If this {@code TextComponent} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code textComponent.setFont(font, textComponent.getKey())}.
	 * <p>
	 * Returned is the {@code TextComponent} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code font} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param font the new font
	 * @return the {@code TextComponent} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code font} is {@code null}
	 */
	public final T setFont(final Font font) {
		return setFont(font, 0L);
	}
	
	/**
	 * Attempts to set the font of this {@code TextComponent} instance.
	 * <p>
	 * If this {@code TextComponent} is currently locked, no state change will be performed unless {@code key == textComponent.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code textComponent.setFont(font, component.getKey())}.
	 * <p>
	 * Returned is the {@code TextComponent} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code font} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param font the new font
	 * @param key the key to temporarily unlock and lock the {@code TextComponent}
	 * @return the {@code TextComponent} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code font} is {@code null}
	 */
	public abstract T setFont(final Font font, final long key);
	
	/**
	 * Attempts to set the foreground color of this {@code TextComponent} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code textComponent.setForeground(foreground, 0L)}.
	 * <p>
	 * If this {@code TextComponent} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code textComponent.setForeground(foreground, textComponent.getKey())}.
	 * <p>
	 * Returned is the {@code TextComponent} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code foreground} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param foreground the new foreground color
	 * @return the {@code TextComponent} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code foreground} is {@code null}
	 */
	public final T setForeground(final Color foreground) {
		return setForeground(foreground, 0L);
	}
	
	/**
	 * Attempts to set the foreground color of this {@code TextComponent} instance.
	 * <p>
	 * If this {@code TextComponent} is currently locked, no state change will be performed unless {@code key == textComponent.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code textComponent.setForeground(foreground, component.getKey())}.
	 * <p>
	 * Returned is the {@code TextComponent} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code foreground} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param foreground the new foreground color
	 * @param key the key to temporarily unlock and lock the {@code TextComponent}
	 * @return the {@code TextComponent} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code foreground} is {@code null}
	 */
	public abstract T setForeground(final Color foreground, final long key);
	
	/**
	 * Attempts to set the text of this {@code TextComponent} instance.
	 * <p>
	 * Calling this method is equivalent to calling {@code textComponent.setText(text, 0L)}.
	 * <p>
	 * If this {@code TextComponent} is currently locked, no state change will be performed.
	 * <p>
	 * If you need to force a state change, simply call {@code textComponent.setText(text, textComponent.getKey())}.
	 * <p>
	 * Returned is the {@code TextComponent} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code text} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param text the new text
	 * @return the {@code TextComponent} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code text} is {@code null}
	 */
	public final T setText(final String text) {
		return setText(text, 0L);
	}
	
	/**
	 * Attempts to set the text of this {@code TextComponent} instance.
	 * <p>
	 * If this {@code TextComponent} is currently locked, no state change will be performed unless {@code key == textComponent.getKey()}.
	 * <p>
	 * If you need to force a state change, simply call {@code textComponent.setText(text, component.getKey())}.
	 * <p>
	 * Returned is the {@code TextComponent} instance itself, such that chaining can be performed.
	 * <p>
	 * If {@code text} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param text the new text
	 * @param key the key to temporarily unlock and lock the {@code TextComponent}
	 * @return the {@code TextComponent} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code text} is {@code null}
	 */
	public abstract T setText(final String text, final long key);
}