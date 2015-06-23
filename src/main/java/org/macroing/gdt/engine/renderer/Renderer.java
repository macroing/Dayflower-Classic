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

import java.util.Objects;

import org.macroing.gdt.engine.display.Pixel;
import org.macroing.gdt.engine.display.PixelIterable;
import org.macroing.gdt.engine.util.PRNG;
import org.macroing.gdt.engine.util.ThreadLocalRandomPRNG;

/**
 * An abstract base-class defining the general contract for all rendering algorithms.
 * <p>
 * This class makes no assumptions about what rendering algorithm to use, and whether it be in 2D or 3D. That's all up to the implementation at hand.
 * <p>
 * For instance, the pipelines of a Rasterizer and a Ray Tracer differ substantially. Although a lot of the math and geometry used are (essentially) the same.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Renderer {
	private PRNG pRNG = ThreadLocalRandomPRNG.newInstance();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Renderer} instance.
	 */
	protected Renderer() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link PRNG} currently assigned to this {@code Renderer} instance.
	 * <p>
	 * By default a {@link ThreadLocalRandomPRNG} is assigned.
	 * <p>
	 * A {@code PRNG} is a pseudo-random number generator that may be used in many different algorithms.
	 * 
	 * @return the {@link PRNG} currently assigned to this {@code Renderer} instance
	 */
	public final PRNG getPRNG() {
		return this.pRNG;
	}
	
	/**
	 * Called when it's time to render.
	 * <p>
	 * This is mainly where the Rasterizer- and Ray Tracer-pipelines differ. Therefore, different subclasses of this {@code Renderer} class should be used, depending on the rendering algorithm.
	 * <p>
	 * If either {@code pixelIterable} or {@code rendererObserver} are {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * A {@link PixelIterable} is a data structure that can be iterated. It iterates over {@link Pixel} instances, each one referring to an individual pixel on the screen. They contain methods to manipulate their data.
	 * <p>
	 * When more than one {@code Thread} is used in the rendering process, the {@code PixelIterable} only contains a subset of all {@code Pixel} instances.
	 * <p>
	 * A {@link RendererObserver} is an entity observing per-pixel updates by a concrete {@code Renderer} implementation.
	 * 
	 * @param pixelIterable a {@link PixelIterable} can iterate over {@link Pixel} instances, each one referring to an individual pixel on the screen
	 * @param rendererObserver an entity observing per-pixel updates by a concrete {@code Renderer} implementation
	 * @throws NullPointerException thrown if, and only if, either {@code pixelIterable} or {@code rendererObserver} are {@code null}
	 */
	public abstract void render(final PixelIterable pixelIterable, final RendererObserver rendererObserver);
	
	/**
	 * Sets the {@link PRNG} instance to use.
	 * <p>
	 * If {@code pRNG} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * By default a {@link ThreadLocalRandomPRNG} is assigned.
	 * <p>
	 * A {@code PRNG} is a pseudo-random number generator that may be used in many different algorithms.
	 * 
	 * @param pRNG the {@code PRNG} to use
	 * @throws NullPointerException thrown if, and only if, {@code pRNG} is {@code null}
	 */
	public final void setPRNG(final PRNG pRNG) {
		this.pRNG = Objects.requireNonNull(pRNG, "pRNG == null");
	}
}