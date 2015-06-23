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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An instance of this {@code Pin} class makes it possible to pin {@link Component}s to a given dynamic location.
 * <p>
 * You can pin {@code Component}s along the X-axis, the Y-axis or the X- and Y-axes.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Pin {
	private final List<Component<?>> componentsPinnedToX = new ArrayList<>();
	private final List<Component<?>> componentsPinnedToY = new ArrayList<>();
	private int x;
	private int y;
	private final String id;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Pin(final String id) {
		this.id = id;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the position along the X-axis.
	 * 
	 * @return the position along the X-axis
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Returns the position along the Y-axis.
	 * 
	 * @return the position along the Y-axis
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Pins {@code component} to the X-axis of this {@code Pin} instance.
	 * <p>
	 * Returns the {@code Pin} instance itself, such that method chanining is possible.
	 * <p>
	 * If {@code component} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param component the {@link Component} to pin to the X-axis of this {@code Pin} instance
	 * @return the {@code Pin} instance itself, such that method chanining is possible
	 * @throws NullPointerException thrown if, and only if, {@code component} is {@code null}
	 */
	public Pin pinComponentToX(final Component<?> component) {
		if(!this.componentsPinnedToX.contains(Objects.requireNonNull(component, "component == null"))) {
			this.componentsPinnedToX.add(component);
			
			doUpdateComponentsPinnedToX();
		}
		
		return this;
	}
	
	/**
	 * Pins {@code component} to the Y-axis of this {@code Pin} instance.
	 * <p>
	 * Returns the {@code Pin} instance itself, such that method chanining is possible.
	 * <p>
	 * If {@code component} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param component the {@link Component} to pin to the Y-axis of this {@code Pin} instance
	 * @return the {@code Pin} instance itself, such that method chanining is possible
	 * @throws NullPointerException thrown if, and only if, {@code component} is {@code null}
	 */
	public Pin pinComponentToY(final Component<?> component) {
		if(!this.componentsPinnedToY.contains(Objects.requireNonNull(component, "component == null"))) {
			this.componentsPinnedToY.add(component);
			
			doUpdateComponentsPinnedToY();
		}
		
		return this;
	}
	
	/**
	 * Unpins all {@link Component}s from this {@code Pin} instance.
	 * <p>
	 * Returns the {@code Pin} instance itself, such that method chanining is possible.
	 * 
	 * @return the {@code Pin} instance itself, such that method chanining is possible
	 */
	public Pin unpinAllComponents() {
		this.componentsPinnedToX.clear();
		this.componentsPinnedToY.clear();
		
		return this;
	}
	
	/**
	 * Sets the position along the X-axis.
	 * <p>
	 * Returns the {@code Pin} instance itself, such that method chanining is possible.
	 * <p>
	 * All {@link Component}s currently pinned to this {@code Pin} instance will be updated to reflect the changes.
	 * 
	 * @param x the position along the X-axis
	 * @return the {@code Pin} instance itself, such that method chanining is possible
	 */
	public Pin setX(final int x) {
		this.x = x;
		
		doUpdateComponentsPinnedToX();
		
		return this;
	}
	
	/**
	 * Sets the position along the Y-axis.
	 * <p>
	 * Returns the {@code Pin} instance itself, such that method chanining is possible.
	 * <p>
	 * All {@link Component}s currently pinned to this {@code Pin} instance will be updated to reflect the changes.
	 * 
	 * @param y the position along the Y-axis
	 * @return the {@code Pin} instance itself, such that method chanining is possible
	 */
	public Pin setY(final int y) {
		this.y = y;
		
		doUpdateComponentsPinnedToY();
		
		return this;
	}
	
	/**
	 * Returns the ID of this {@code Pin} instance.
	 * 
	 * @return the ID of this {@code Pin} instance
	 */
	public String getId() {
		return this.id;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static Pin newInstance(final String id) {
		return new Pin(Objects.requireNonNull(id, "id == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doUpdateComponentsPinnedToX() {
		for(final Component<?> component : this.componentsPinnedToX) {
			final int componentX = component.getX();
			final int pinX = this.x;
			
			if(pinX > componentX) {
				final int width = pinX - componentX;
				final int height = component.getHeight();
				
				component.setSize(width, height);
			}
		}
	}
	
	private void doUpdateComponentsPinnedToY() {
		for(final Component<?> component : this.componentsPinnedToY) {
			final int componentY = component.getY();
			final int pinY = this.y;
			
			if(pinY > componentY) {
				final int width = component.getWidth();
				final int height = pinY - componentY;
				
				component.setSize(width, height);
			}
		}
	}
}