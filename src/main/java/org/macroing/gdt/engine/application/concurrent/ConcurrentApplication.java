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
package org.macroing.gdt.engine.application.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.macroing.gdt.engine.application.Application;
import org.macroing.gdt.engine.display.Display;
import org.macroing.gdt.engine.input.Keyboard;
import org.macroing.gdt.engine.input.KeyboardObserver;
import org.macroing.gdt.engine.input.Mouse;
import org.macroing.gdt.engine.input.MouseObserver;
import org.macroing.gdt.engine.renderer.Renderer;

/**
 * An abstract class that extends {@link Application} and adds concurrency.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class ConcurrentApplication extends Application {
	private static final long DELAY = 1000L / 60L;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicBoolean hasStarted = new AtomicBoolean();
	private final AtomicReference<ScheduledFuture<?>> scheduledFuture = new AtomicReference<>();
	private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConcurrentApplication} instance.
	 */
	protected ConcurrentApplication() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Call this method to start this {@code ConcurrentApplication} instance.
	 * <p>
	 * As soon as this method is called, the {@code configure()} method will be called, allowing subclasses to configure this {@code ConcurrentApplication} instance.
	 */
	@Override
	public final void start() {
		if(this.hasStarted.compareAndSet(false, true)) {
			configure();
			
			doConfigureDisplayObserver();
			doAttemptToAddKeyboardObserverToKeyboard();
			doAttemptToAddMouseObserverToMouse();
			doAttemptToScheduleAtFixedRate();
			doShowDisplay();
		}
	}
	
	/**
	 * Call this method to stop this {@code ConcurrentApplication} instance.
	 * <p>
	 * After this method has been called, a call to {@code start()} may not work.
	 */
	@Override
	public final void stop() {
		if(this.hasStarted.compareAndSet(true, false)) {
			doHideDisplay();
			doAttemptToCancelScheduledFuture();
			doAttemptToRemoveKeyboardObserverFromKeyboard();
			doAttemptToRemoveMouseObserverFromMouse();
			doAttemptToShutdownScheduledExecutorService();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doAttemptToAddKeyboardObserverToKeyboard() {
		if(this instanceof KeyboardObserver) {
			final KeyboardObserver keyboardObserver = KeyboardObserver.class.cast(this);
			
			final
			Keyboard keyboard = Keyboard.getInstance();
			keyboard.addKeyboardObserver(keyboardObserver);
		}
	}
	
	private void doAttemptToAddMouseObserverToMouse() {
		if(this instanceof MouseObserver) {
			final MouseObserver mouseObserver = MouseObserver.class.cast(this);
			
			final
			Mouse mouse = Mouse.getInstance();
			mouse.addMouseObserver(mouseObserver);
		}
	}
	
	private void doAttemptToCancelScheduledFuture() {
		this.scheduledFuture.get().cancel(false);
	}
	
	private void doAttemptToRemoveKeyboardObserverFromKeyboard() {
		if(this instanceof KeyboardObserver) {
			final KeyboardObserver keyboardObserver = KeyboardObserver.class.cast(this);
			
			final
			Keyboard keyboard = Keyboard.getInstance();
			keyboard.removeKeyboardObserver(keyboardObserver);
		}
	}
	
	private void doAttemptToRemoveMouseObserverFromMouse() {
		if(this instanceof MouseObserver) {
			final MouseObserver mouseObserver = MouseObserver.class.cast(this);
			
			final
			Mouse mouse = Mouse.getInstance();
			mouse.removeMouseObserver(mouseObserver);
		}
	}
	
	private void doAttemptToScheduleAtFixedRate() {
		this.scheduledFuture.set(this.scheduledExecutorService.scheduleAtFixedRate(() -> update(), DELAY, DELAY, TimeUnit.MILLISECONDS));
	}
	
	private void doAttemptToShutdownScheduledExecutorService() {
		try {
			this.scheduledExecutorService.shutdown();
			this.scheduledExecutorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch(final InterruptedException e) {
			
		}
	}
	
	private void doConfigureDisplayObserver() {
		final Renderer renderer = getRenderer();
		
		final
		Display display = getDisplay();
		display.setDisplayObserver((pixelIterable, consumer) -> {renderer.render(pixelIterable, pixel -> {consumer.accept(pixel);});});
	}
	
	private void doHideDisplay() {
		final
		Display display = getDisplay();
		display.hide();
	}
	
	private void doShowDisplay() {
		final
		Display display = getDisplay();
		display.show();
	}
}