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

import javax.swing.JButton;

import org.macroing.gdt.engine.display.wicked.Button;
import org.macroing.gdt.engine.display.wicked.Color;
import org.macroing.gdt.engine.display.wicked.Font;
import org.macroing.gdt.engine.display.wicked.WickedDisplay;

final class ButtonImpl extends Button<ButtonImpl> {
	private final JButton jButton = ComponentUtilities.newJButton();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ButtonImpl(final String id, final WickedDisplay wickedDisplay) {
		super(id, wickedDisplay);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean isMovable() {
		return Boolean.class.cast(ComponentUtilities.getClientProperty(this.jButton, ComponentUtilities.KEY_MOVABLE, Boolean.FALSE)).booleanValue();
	}
	
	@Override
	public boolean isResizable() {
		return Boolean.class.cast(ComponentUtilities.getClientProperty(this.jButton, ComponentUtilities.KEY_RESIZABLE, Boolean.FALSE)).booleanValue();
	}
	
	@Override
	public boolean isVisible() {
		return ComponentUtilities.isVisible(this.jButton);
	}
	
	@Override
	public ButtonImpl setFont(final Font font, final long key) {
		if(authenticate(key)) {
//			TODO: Add support for fonts.
		}
		
		return this;
	}
	
	@Override
	public ButtonImpl setForeground(final Color foreground, final long key) {
		if(authenticate(key)) {
//			TODO: Add support for foreground colors.
		}
		
		return this;
	}
	
	@Override
	public ButtonImpl setLocation(final int x, final int y, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setLocation(this.jButton, x, y);
		}
		
		return this;
	}
	
	@Override
	public ButtonImpl setMovable(final boolean isMovable, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.putClientProperty(this.jButton, ComponentUtilities.KEY_MOVABLE, Boolean.valueOf(isMovable));
		}
		
		return this;
	}
	
	@Override
	public ButtonImpl setResizable(final boolean isResizable, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.putClientProperty(this.jButton, ComponentUtilities.KEY_RESIZABLE, Boolean.valueOf(isResizable));
		}
		
		return this;
	}
	
	@Override
	public ButtonImpl setSize(final int width, final int height, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setSize(this.jButton, width, height);
		}
		
		return this;
	}
	
	@Override
	public ButtonImpl setText(final String text, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setText(this.jButton, text);
		}
		
		return this;
	}
	
	@Override
	public ButtonImpl setVisible(final boolean isVisible, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setVisible(this.jButton, isVisible);
		}
		
		return this;
	}
	
	@Override
	public Color getForeground() {
		return null;
	}
	
	@Override
	public Font getFont() {
		return null;
	}
	
	@Override
	public int getHeight() {
		return ComponentUtilities.getHeight(this.jButton);
	}
	
	@Override
	public int getWidth() {
		return ComponentUtilities.getWidth(this.jButton);
	}
	
	@Override
	public int getX() {
		return ComponentUtilities.getX(this.jButton);
	}
	
	@Override
	public int getY() {
		return ComponentUtilities.getY(this.jButton);
	}
	
	@Override
	public Object getComponentObject() {
		return this.jButton;
	}
	
	@Override
	public String getText() {
		return ComponentUtilities.getText(this.jButton);
	}
}