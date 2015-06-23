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
package org.macroing.gdt.engine.application;

import java.util.Objects;

import org.macroing.gdt.engine.application.concurrent.ConcurrentApplication;
import org.macroing.gdt.engine.display.Display;
import org.macroing.gdt.engine.display.wicked.swing.SwingWickedDisplay;
import org.macroing.gdt.engine.renderer.PathTracingRenderer;
import org.macroing.gdt.engine.renderer.Renderer;

/**
 * An abstract base-class that defines the entry-point for a game application.
 * <p>
 * You need to extend this class or some of the existing abstract subclasses, in order to use this library or framework.
 * <p>
 * You should preferably extend the {@link ConcurrentApplication} class, rather than this class directly. Otherwise you'd have to implement a lot more on your own.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Application {
	private Display display = SwingWickedDisplay.newInstance();
	private Renderer renderer = PathTracingRenderer.newInstance();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Application} instance.
	 */
	protected Application() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Display} assigned to this {@code Application} instance.
	 * <p>
	 * By default a {@link SwingWickedDisplay} is used. However, this may change in the future.
	 * 
	 * @return the {@code Display} assigned to this {@code Application} instance
	 */
	protected final Display getDisplay() {
		return this.display;
	}
	
	/**
	 * Returns the {@link Renderer} assigned to this {@code Application} instance.
	 * <p>
	 * By default a {@link PathTracingRenderer} is used. However, this may change in the future.
	 * 
	 * @return the {@code Renderer} assigned to this {@code Application} instance
	 */
	protected final Renderer getRenderer() {
		return this.renderer;
	}
	
	/**
	 * This method can be overridden by subclasses to allow configuration of this {@code Application} instance.
	 * <p>
	 * This method will be called as soon as the {@code start()} method has been called.
	 * <p>
	 * If not overridden, this method does nothing.
	 */
	protected void configure() {
		
	}
	
	/**
	 * Sets a new {@link Display} for this {@code Application} instance.
	 * <p>
	 * If {@code display} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * By default a {@link SwingWickedDisplay} is used. However, this may change in the future.
	 * 
	 * @param display the new {@code Display}
	 * @throws NullPointerException thrown if, and only if, {@code display} is {@code null}
	 */
	protected final void setDisplay(final Display display) {
		this.display = Objects.requireNonNull(display, "display == null");
	}
	
	/**
	 * Sets a new {@link Renderer} for this {@code Application} instance.
	 * <p>
	 * If {@code renderer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * By default a {@link PathTracingRenderer} is used. However, this may change in the future.
	 * 
	 * @param renderer the new {@code Renderer}
	 * @throws NullPointerException thrown if, and only if, {@code renderer} is {@code null}
	 */
	protected final void setRenderer(final Renderer renderer) {
		this.renderer = Objects.requireNonNull(renderer, "renderer == null");
	}
	
	/**
	 * Call this method to start this {@code Application} instance.
	 * <p>
	 * As soon as this method is called, the {@code configure()} method will be called, allowing subclasses to configure this {@code Application} instance.
	 * <p>
	 * This method will also call the {@code show()} method of the current {@link Display}.
	 */
	public abstract void start();
	
	/**
	 * Call this method to stop this {@code Application} instance.
	 * <p>
	 * After this method has been called, a call to {@code start()} may not work.
	 * <p>
	 * This method will also call the {@code hide()} method of the current {@link Display}.
	 */
	public abstract void stop();
	
	/**
	 * This method can be overridden by subclasses to allow updating on a timely basis.
	 * <p>
	 * If not overridden, this method does nothing.
	 */
	protected void update() {
		
	}
}