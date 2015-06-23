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
package org.macroing.gdt.engine.renderer;

import org.macroing.gdt.engine.display.Display;
import org.macroing.gdt.engine.display.Pixel;

/**
 * An entity observing per-pixel updates by a concrete {@link Renderer} implementation.
 * <p>
 * It usually refers to some source, such as a {@link Display} that, when updates arrive, updates said source.
 * <p>
 * But you're free to implement it however you may want.
 * <p>
 * If you want more than one source to be notified, you can implement one that delegates to a {@code List} of {@code RendererObserver}s.
 * <p>
 * The current design-decision was made, because of the overhead added by iterating a {@code List}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface RendererObserver {
	/**
	 * Called by a concrete {@link Renderer} implementation when a given {@link Pixel} was updated.
	 * <p>
	 * This makes it possible for some arbitrary source to update its data at appropriate times.
	 * <p>
	 * A source can be, and usually is, a {@link Display}. But it does not have to be. It could also be an instance of a class that writes to an image on your computer, or anything else for that matter.
	 * <p>
	 * If {@code pixel} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made. Although, if {@code pixel} is {@code null}, that's a bug, either in our or some third-party code.
	 * 
	 * @param pixel the {@code Pixel} that was updated by the {@code Renderer}
	 * @throws NullPointerException thrown if, and only if, {@code pixel} is {@code null}
	 */
	void update(final Pixel pixel);
}