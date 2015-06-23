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

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A model of a mouse.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Mouse {
	private static final Mouse INSTANCE = new Mouse();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean isRecentering;
	private final List<MouseObserver> mouseObservers = new CopyOnWriteArrayList<>();
	private MousePointer mousePointer;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Mouse() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the re-centering property.
	 * <p>
	 * If the re-centering property is set to {@code true}, any class that mediates between this {@code Mouse} instance and the Operating System, should make sure the mouse pointer is re-centered, such that no matter how much you drag it, it will never
	 * reach any side of the window.
	 * 
	 * @return the re-centering property
	 */
	public boolean isRecentering() {
		return this.isRecentering;
	}
	
	/**
	 * Returns the location of the mouse pointer on the X-axis.
	 * <p>
	 * If this method is not supported by the current configuration, an {@code UnsupportedOperationException} will be thrown.
	 * 
	 * @return the location of the mouse pointer on the X-axis
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the current configuration
	 */
	public int getX() {
		try {
			return this.mousePointer.getX();
		} catch(final NullPointerException e) {
			throw new UnsupportedOperationException("This method is not supported by the current configuration.");
		}
	}
	
	/**
	 * Returns the location of the mouse pointer on the Y-axis.
	 * <p>
	 * If this method is not supported by the current configuration, an {@code UnsupportedOperationException} will be thrown.
	 * 
	 * @return the location of the mouse pointer on the Y-axis
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the current configuration
	 */
	public int getY() {
		try {
			return this.mousePointer.getY();
		} catch(final NullPointerException e) {
			throw new UnsupportedOperationException("This method is not supported by the current configuration.");
		}
	}
	
	/**
	 * Adds {@code mouseObserver}, if not already present.
	 * <p>
	 * If {@code mouseObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param mouseObserver the {@link MouseObserver} to add
	 * @throws NullPointerException thrown if, and only if, {@code mouseObserver} is {@code null}
	 */
	public void addMouseObserver(final MouseObserver mouseObserver) {
		Objects.requireNonNull(mouseObserver, "mouseObserver == null");
		
		if(!this.mouseObservers.contains(mouseObserver)) {
			this.mouseObservers.add(mouseObserver);
		}
	}
	
	/**
	 * Fires a {@link MouseEvent} by notifying all currently added {@link MouseObserver}s.
	 * <p>
	 * If {@code mouseEvent} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will not make the Operating System aware of any fired mouse events.
	 * 
	 * @param mouseEvent the {@code MouseEvent} that was fired
	 * @throws NullPointerException thrown if, and only if, {@code mouseEvent} is {@code null}
	 */
	public void fireMouseEvent(final MouseEvent mouseEvent) {
		Objects.requireNonNull(mouseEvent, "mouseEvent == null");
		
		for(final MouseObserver mouseObserver : this.mouseObservers) {
			mouseObserver.onMouseEvent(mouseEvent);
		}
	}
	
	/**
	 * Removes {@code mouseObserver}, if present.
	 * <p>
	 * If {@code mouseObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param mouseObserver the {@link MouseObserver} to remove
	 * @throws NullPointerException thrown if, and only if, {@code mouseObserver} is {@code null}
	 */
	public void removeMouseObserver(final MouseObserver mouseObserver) {
		this.mouseObservers.remove(Objects.requireNonNull(mouseObserver, "mouseObserver == null"));
	}
	
	/**
	 * Sets the location of the mouse pointer on the X- and Y-axes.
	 * <p>
	 * If this method is not supported by the current configuration, an {@code UnsupportedOperationException} will be thrown.
	 * <p>
	 * This method does not by itself update the mouse pointer. That's up to any class that mediates between this {@code Mouse} instance and the Operating System. It can be done by providing this {@code Mouse} instance with a {@link MousePointer}
	 * implementation.
	 * <p>
	 * Any negative coordinate value will be converted into its absolute counterpart.
	 * 
	 * @param x the location of the mouse pointer on the X-axis
	 * @param y the location of the mouse pointer on the Y-axis
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the current configuration
	 */
	public void setLocation(final int x, final int y) {
		try {
			this.mousePointer.setLocation(Math.abs(x), Math.abs(y));
		} catch(final NullPointerException e) {
			throw new UnsupportedOperationException("This method is not supported by the current configuration.");
		}
	}
	
	/**
	 * Sets the new {@link MousePointer} to use.
	 * <p>
	 * If {@code mousePointer} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param mousePointer the new {@code MousePointer} to use
	 * @throws NullPointerException thrown if, and only if, {@code mousePointer} is {@code null}
	 */
	public void setMousePointer(final MousePointer mousePointer) {
		this.mousePointer = Objects.requireNonNull(mousePointer, "mousePointer == null");
	}
	
	/**
	 * Sets the re-centering property for this {@code Mouse} instance.
	 * <p>
	 * If the re-centering property is set to {@code true}, any class that mediates between this {@code Mouse} instance and the Operating System, should make sure the mouse pointer is re-centered, such that no matter how much you drag it, it will never
	 * reach any side of the window.
	 * 
	 * @param isRecentering the re-centering property for this {@code Mouse} instance
	 */
	public void setRecentering(final boolean isRecentering) {
		this.isRecentering = isRecentering;
	}
	
	/**
	 * Sets the location of the mouse pointer on the X-axis.
	 * <p>
	 * If this method is not supported by the current configuration, an {@code UnsupportedOperationException} will be thrown.
	 * <p>
	 * This method does not by itself update the mouse pointer. That's up to any class that mediates between this {@code Mouse} instance and the Operating System. It can be done by providing this {@code Mouse} instance with a {@link MousePointer}
	 * implementation.
	 * <p>
	 * Any negative coordinate value will be converted into its absolute counterpart.
	 * 
	 * @param x the location of the mouse pointer on the X-axis
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the current configuration
	 */
	public void setX(final int x) {
		try {
			this.mousePointer.setX(Math.abs(x));
		} catch(final NullPointerException e) {
			throw new UnsupportedOperationException("This method is not supported by the current configuration.");
		}
	}
	
	/**
	 * Sets the location of the mouse pointer on the Y-axis.
	 * <p>
	 * If this method is not supported by the current configuration, an {@code UnsupportedOperationException} will be thrown.
	 * <p>
	 * This method does not by itself update the mouse pointer. That's up to any class that mediates between this {@code Mouse} instance and the Operating System. It can be done by providing this {@code Mouse} instance with a {@link MousePointer}
	 * implementation.
	 * <p>
	 * Any negative coordinate value will be converted into its absolute counterpart.
	 * 
	 * @param y the location of the mouse pointer on the Y-axis
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the current configuration
	 */
	public void setY(final int y) {
		try {
			this.mousePointer.setY(Math.abs(y));
		} catch(final NullPointerException e) {
			throw new UnsupportedOperationException("This method is not supported by the current configuration.");
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the one and only {@code Mouse} instance.
	 * 
	 * @return the one and only {@code Mouse} instance
	 */
	public static Mouse getInstance() {
		return INSTANCE;
	}
}