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
package org.macroing.gdt.engine.display;

import java.util.function.Consumer;

import org.macroing.gdt.engine.renderer.Renderer;

/**
 * An entity observing the rendering progress by a concrete {@link Display} implementation.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface DisplayObserver {
	/**
	 * Called by a concrete {@link Display} implementation when it's time to render to a given {@link PixelIterable}.
	 * <p>
	 * The {@code Consumer} provided can be notified for each {@link Pixel} being rendered to by a concrete {@link Renderer}, so that the {@code Display} can update itself on a timely basis.
	 * <p>
	 * If either {@code pixelIterable} or {@code consumer} are {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made. Although, if any of them are {@code null}, that's a bug, either in our or some third-party code.
	 * 
	 * @param pixelIterable an {@code Iterable} that iterates over {@code Pixel}s
	 * @param consumer a {@code Consumer} that accepts {@code Pixel}s that have been rendered to
	 */
	void render(final PixelIterable pixelIterable, final Consumer<Pixel> consumer);
}