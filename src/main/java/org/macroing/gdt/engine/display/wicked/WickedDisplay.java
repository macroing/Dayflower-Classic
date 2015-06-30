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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.macroing.gdt.engine.display.Display;

public abstract class WickedDisplay extends Display {
	private final AtomicInteger id = new AtomicInteger();
	private final Map<String, Pin> pins = new LinkedHashMap<>();
	private final Map<String, State> states = new LinkedHashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code WickedDisplay} instance.
	 */
	protected WickedDisplay() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link Button} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} should be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code Button}, an {@code IllegalArgumentException} should be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} should be thrown.
	 * 
	 * @param id the ID of the {@code Button} to return
	 * @return a {@code Button} instance given an ID
	 * @throws IllegalArgumentException should be thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code Button}
	 * @throws NullPointerException should be thrown if, and only if, {@code id} is {@code null}
	 */
	public abstract Button<?> getButton(final String id);
	
	/**
	 * Returns a {@link CheckBox} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} should be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code CheckBox}, an {@code IllegalArgumentException} should be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} should be thrown.
	 * 
	 * @param id the ID of the {@code CheckBox} to return
	 * @return a {@code CheckBox} instance given an ID
	 * @throws IllegalArgumentException should be thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code CheckBox}
	 * @throws NullPointerException should be thrown if, and only if, {@code id} is {@code null}
	 */
	public abstract CheckBox<?> getCheckBox(final String id);
	
	/**
	 * Returns a {@link Component} instance given an {@code Object}.
	 * <p>
	 * If {@code componentObject} is {@code null}, a {@code NullPointerException} should be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that {@code Object}, an {@code IllegalArgumentException} should be thrown.
	 * <p>
	 * To clarify, {@code componentObject} should be equal to the {@code Object} returned by the {@code getComponentObject()} method of a {@code Component} instance, in order for that {@code Component} instance to be returned by this method.
	 * <p>
	 * There should be at most one {@code Component} for which this criterion is satisfied, otherwise there may be a bug in the implementation.
	 * 
	 * @param componentObject the {@code Object} assigned to the {@code Component} to return
	 * @return a {@code Component} instance given an {@code Object}
	 * @throws IllegalArgumentException should be thrown if, and only if, no {@code Component} has been assigned that {@code Object}
	 * @throws NullPointerException should be thrown if, and only if, {@code componentObject} is {@code null}
	 */
	public abstract Component<?> getComponentByComponentObject(final Object componentObject);
	
	/**
	 * Returns a {@link Container} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} should be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code Container}, an {@code IllegalArgumentException} should be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} should be thrown.
	 * 
	 * @param id the ID of the {@code Container} to return
	 * @return a {@code Container} instance given an ID
	 * @throws IllegalArgumentException should be thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code Container}
	 * @throws NullPointerException should be thrown if, and only if, {@code id} is {@code null}
	 */
	public abstract Container<?> getContainer(final String id);
	
	/**
	 * Returns a {@link Label} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} should be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code Label}, an {@code IllegalArgumentException} should be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} should be thrown.
	 * 
	 * @param id the ID of the {@code Label} to return
	 * @return a {@code Label} instance given an ID
	 * @throws IllegalArgumentException should be thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code Label}
	 * @throws NullPointerException should be thrown if, and only if, {@code id} is {@code null}
	 */
	public abstract Label<?> getLabel(final String id);
	
	/**
	 * Returns a {@link Panel} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} should be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code Panel}, an {@code IllegalArgumentException} should be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} should be thrown.
	 * 
	 * @param id the ID of the {@code Panel} to return
	 * @return a {@code Panel} instance given an ID
	 * @throws IllegalArgumentException should be thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code Panel}
	 * @throws NullPointerException should be thrown if, and only if, {@code id} is {@code null}
	 */
	public abstract Panel<?> getPanel(final String id);
	
	/**
	 * Returns a {@link Pin} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no {@code Pin} given the ID exists, it should be created.
	 * 
	 * @param id the ID of the {@code Pin} to return
	 * @return a {@code Pin} instance given an ID
	 * @throws NullPointerException thrown if, and only if, {@code id} is {@code null}
	 */
	public final Pin getPin(final String id) {
		return this.pins.computeIfAbsent(id.toLowerCase(), key -> Pin.newInstance(key));
	}
	
	/**
	 * Returns a {@link State} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no {@code State} given the ID exists, it should be created.
	 * 
	 * @param id the ID of the {@code State} to return
	 * @return a {@code State} instance given an ID
	 * @throws NullPointerException thrown if, and only if, {@code id} is {@code null}
	 */
	public final State getState(final String id) {
		return this.states.computeIfAbsent(id.toLowerCase(), key -> State.newInstance(key));
	}
	
	/**
	 * Returns the root {@link Window}.
	 * <p>
	 * The ID of the root {@code Window} should always be equal to {@code Integer.toString(0)}.
	 * 
	 * @return the root {@code Window}
	 */
	public final Window<?> getWindow() {
		return getWindow(Integer.toString(0));
	}
	
	/**
	 * Returns a {@link Window} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} should be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code Window}, an {@code IllegalArgumentException} should be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} should be thrown.
	 * 
	 * @param id the ID of the {@code Window} to return
	 * @return a {@code Window} instance given an ID
	 * @throws IllegalArgumentException should be thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code Window}
	 * @throws NullPointerException should be thrown if, and only if, {@code id} is {@code null}
	 */
	public abstract Window<?> getWindow(final String id);
	
	//TODO: Comment...
	public final WickedDisplay addButton() {
		return addButton(doGetNextId());
	}
	
	//TODO: Comment...
	public final WickedDisplay addButton(final String id) {
		return addButton(id, "0");
	}
	
	//TODO: Comment...
	public abstract WickedDisplay addButton(final String id, final String parentId);
	
	//TODO: Comment...
	public final WickedDisplay addCheckBox() {
		return addCheckBox(doGetNextId());
	}
	
	//TODO: Comment...
	public final WickedDisplay addCheckBox(final String id) {
		return addCheckBox(id, "0");
	}
	
	//TODO: Comment...
	public abstract WickedDisplay addCheckBox(final String id, final String parentId);
	
	//TODO: Comment...
	public final WickedDisplay addLabel() {
		return addLabel(doGetNextId());
	}
	
	//TODO: Comment...
	public final WickedDisplay addLabel(final String id) {
		return addLabel(id, "0");
	}
	
	//TODO: Comment...
	public abstract WickedDisplay addLabel(final String id, final String parentId);
	
	//TODO: Comment...
	public final WickedDisplay addPanel() {
		return addPanel(doGetNextId());
	}
	
	//TODO: Comment...
	public final WickedDisplay addPanel(final String id) {
		return addPanel(id, "0");
	}
	
	//TODO: Comment...
	public abstract WickedDisplay addPanel(final String id, final String parentId);
	
	//TODO: Comment...
	public final WickedDisplay addWindow() {
		return addWindow(doGetNextId());
	}
	
	//TODO: Comment...
	public abstract WickedDisplay addWindow(final String id);
	
	//TODO: Comment...
	public abstract WickedDisplay updateComponentPin(final Component<?> component);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String doGetNextId() {
		return Integer.toString(this.id.getAndIncrement());
	}
}