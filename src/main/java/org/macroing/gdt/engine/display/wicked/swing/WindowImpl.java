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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.macroing.gdt.engine.display.wicked.Component;
import org.macroing.gdt.engine.display.wicked.Window;
import org.macroing.gdt.engine.display.wicked.WickedDisplay;

final class WindowImpl extends Window<WindowImpl> {
	private boolean isMovable = true;
	private final ComponentMovingMouseAdapter componentMovingMouseAdapter = doNewComponentMovingMouseAdapter();
	private final JFrame jFrame = ComponentUtilities.newJFrame();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public WindowImpl(final String id, final WickedDisplay wickedDisplay) {
		super(id, wickedDisplay);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean isMovable() {
		return this.isMovable;
	}
	
	@Override
	public boolean isResizable() {
		return ComponentUtilities.isResizable(this.jFrame);
	}
	
	@Override
	public boolean isVisible() {
		return ComponentUtilities.isVisible(this.jFrame);
	}
	
	@Override
	public int getHeight() {
		return ComponentUtilities.getHeight(this.jFrame);
	}
	
	@Override
	public int getWidth() {
		return ComponentUtilities.getWidth(this.jFrame);
	}
	
	@Override
	public int getX() {
		return ComponentUtilities.getX(this.jFrame);
	}
	
	@Override
	public int getY() {
		return ComponentUtilities.getY(this.jFrame);
	}
	
	@Override
	public Object getComponentObject() {
		return this.jFrame;
	}
	
	@Override
	public WindowImpl addComponent(final Component<?> component, final long key) {
		if(component != null && authenticate(key)) {
			final Object componentObject = component.getComponentObject();
			
			final JComponent jComponent = JComponent.class.cast(componentObject);
			
			Container container = this.jFrame.getContentPane();
			
			synchronized(this.jFrame.getContentPane().getTreeLock()) {
				if(container.getComponentCount() == 1) {
					final Object object = container.getComponent(0);
					
					if(object instanceof Container) {
						container = Container.class.cast(object);
					}
				}
			}
			
			if(!(container instanceof BufferedImageJPanel)) {
				container = this.jFrame.getContentPane();
			}
			
			ComponentUtilities.add(jComponent, container);
			
			this.componentMovingMouseAdapter.addComponents(jComponent);
		}
		
		return this;
	}
	
	@Override
	public WindowImpl setLocation(final int x, final int y, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setLocation(this.jFrame, x, y);
		}
		
		return this;
	}
	
	@Override
	public WindowImpl setMovable(final boolean isMovable, final long key) {
		if(authenticate(key)) {
			this.isMovable = isMovable;
		}
		
		return this;
	}
	
	@Override
	public WindowImpl setResizable(final boolean isResizable, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setResizable(this.jFrame, isResizable);
		}
		
		return this;
	}
	
	@Override
	public WindowImpl setSize(final int width, final int height, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setSize(this.jFrame, width, height);
		}
		
		return this;
	}
	
	@Override
	public WindowImpl setTitle(final String title, final long key) {
		if(title != null && authenticate(key)) {
			this.jFrame.setTitle(title);
		}
		
		return this;
	}
	
	@Override
	public WindowImpl setVisible(final boolean isVisible, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setVisible(this.jFrame, isVisible);
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