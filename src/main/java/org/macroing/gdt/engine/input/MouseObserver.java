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
 * A means to observe {@link MouseEvent}s fired from a {@link Mouse} instance.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface MouseObserver {
	/**
	 * Called by the {@link Mouse} instance when it fires a {@link MouseEvent}.
	 * 
	 * @param mouseEvent the {@code MouseEvent} that was fired
	 */
	void onMouseEvent(final MouseEvent mouseEvent);
}