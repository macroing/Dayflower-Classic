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
 * An abstract base-class for all text labels.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Label<T extends Label<T>> extends TextComponent<T> {
	/**
	 * Constructs a new {@code Label} instance.
	 * <p>
	 * If either {@code id} or {@code wickedDisplay} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param id the ID of this {@code Label} instance
	 * @param wickedDisplay the {@link WickedDisplay} that created this {@code Label} instance
	 * @throws NullPointerException thrown if, and only if, either {@code id} or {@code wickedDisplay} are {@code null}
	 */
	protected Label(final String id, final WickedDisplay wickedDisplay) {
		super(id, wickedDisplay);
	}
}