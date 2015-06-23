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
package org.macroing.gdt.engine.display.wicked.swing;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import org.macroing.gdt.engine.display.wicked.Component;
import org.macroing.gdt.engine.display.wicked.WickedDisplay;

final class ComponentListenerImpl implements ComponentListener {
	private final Component<?> component;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ComponentListenerImpl(final Component<?> component) {
		this.component = component;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void componentHidden(final ComponentEvent e) {
		
	}
	
	@Override
	public void componentMoved(final ComponentEvent e) {
		doUpdateComponentPin();
	}
	
	@Override
	public void componentResized(final ComponentEvent e) {
		doUpdateComponentPin();
	}
	
	@Override
	public void componentShown(final ComponentEvent e) {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doUpdateComponentPin() {
		final
		WickedDisplay wickedDisplay = this.component.getWickedDisplay();
		wickedDisplay.updateComponentPin(this.component);
	}
}