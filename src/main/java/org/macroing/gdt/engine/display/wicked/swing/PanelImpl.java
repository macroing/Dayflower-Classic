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

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.macroing.gdt.engine.display.wicked.Component;
import org.macroing.gdt.engine.display.wicked.Panel;
import org.macroing.gdt.engine.display.wicked.WickedDisplay;

final class PanelImpl extends Panel<PanelImpl> {
	private final ComponentMovingMouseAdapter componentMovingMouseAdapter = doNewComponentMovingMouseAdapter();
	private final JPanel jPanel = ComponentUtilities.newJPanel();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public PanelImpl(final String id, final WickedDisplay wickedDisplay) {
		super(id, wickedDisplay);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean isMovable() {
		return Boolean.class.cast(ComponentUtilities.getClientProperty(this.jPanel, ComponentUtilities.KEY_MOVABLE, Boolean.FALSE)).booleanValue();
	}
	
	@Override
	public boolean isResizable() {
		return Boolean.class.cast(ComponentUtilities.getClientProperty(this.jPanel, ComponentUtilities.KEY_RESIZABLE, Boolean.FALSE)).booleanValue();
	}
	
	@Override
	public boolean isVisible() {
		return ComponentUtilities.isVisible(this.jPanel);
	}
	
	@Override
	public int getHeight() {
		return ComponentUtilities.getHeight(this.jPanel);
	}
	
	@Override
	public int getWidth() {
		return ComponentUtilities.getWidth(this.jPanel);
	}
	
	@Override
	public int getX() {
		return ComponentUtilities.getX(this.jPanel);
	}
	
	@Override
	public int getY() {
		return ComponentUtilities.getY(this.jPanel);
	}
	
	@Override
	public Object getComponentObject() {
		return this.jPanel;
	}
	
	@Override
	public PanelImpl addComponent(final Component<?> component, final long key) {
		if(component != null && authenticate(key)) {
			final Object componentObject = component.getComponentObject();
			
			final JComponent jComponent = JComponent.class.cast(componentObject);
			
			ComponentUtilities.add(jComponent, this.jPanel);
			
			this.componentMovingMouseAdapter.addComponents(jComponent);
		}
		
		return this;
	}
	
	@Override
	public PanelImpl setLocation(final int x, final int y, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setLocation(this.jPanel, x, y);
			
			final
			WickedDisplay wickedDisplay = getWickedDisplay();
			wickedDisplay.updateComponentPin(this);
		}
		
		return this;
	}
	
	@Override
	public PanelImpl setMovable(final boolean isMovable, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.putClientProperty(this.jPanel, ComponentUtilities.KEY_MOVABLE, Boolean.valueOf(isMovable));
		}
		
		return this;
	}
	
	@Override
	public PanelImpl setResizable(final boolean isResizable, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.putClientProperty(this.jPanel, ComponentUtilities.KEY_RESIZABLE, Boolean.valueOf(isResizable));
		}
		
		return this;
	}
	
	@Override
	public PanelImpl setSize(final int width, final int height, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setSize(this.jPanel, width, height);
			
			final
			WickedDisplay wickedDisplay = getWickedDisplay();
			wickedDisplay.updateComponentPin(this);
		}
		
		return this;
	}
	
	@Override
	public PanelImpl setVisible(final boolean isVisible, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setVisible(this.jPanel, isVisible);
		}
		
		return this;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static ComponentMovingMouseAdapter doNewComponentMovingMouseAdapter() {
		final
		ComponentMovingMouseAdapter componentMovingMouseAdapter = new ComponentMovingMouseAdapter();
		componentMovingMouseAdapter.setEdgeInsets(new Insets(0, 0, 0, 0));
		componentMovingMouseAdapter.setSnapSize(new Dimension(10, 10));
		
		return componentMovingMouseAdapter;
	}
}