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

import javax.swing.JLabel;

import org.macroing.gdt.engine.display.wicked.Color;
import org.macroing.gdt.engine.display.wicked.Font;
import org.macroing.gdt.engine.display.wicked.Label;
import org.macroing.gdt.engine.display.wicked.WickedDisplay;

final class LabelImpl extends Label<LabelImpl> {
	private final JLabel jLabel = ComponentUtilities.newJLabel();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public LabelImpl(final String id, final WickedDisplay wickedDisplay) {
		super(id, wickedDisplay);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean isMovable() {
		return Boolean.class.cast(ComponentUtilities.getClientProperty(this.jLabel, ComponentUtilities.KEY_MOVABLE, Boolean.FALSE)).booleanValue();
	}
	
	@Override
	public boolean isResizable() {
		return Boolean.class.cast(ComponentUtilities.getClientProperty(this.jLabel, ComponentUtilities.KEY_RESIZABLE, Boolean.FALSE)).booleanValue();
	}
	
	@Override
	public boolean isVisible() {
		return ComponentUtilities.isVisible(this.jLabel);
	}
	
	@Override
	public LabelImpl setFont(final Font font, final long key) {
		if(authenticate(key)) {
//			TODO: Add support for fonts.
		}
		
		return this;
	}
	
	@Override
	public LabelImpl setForeground(final Color foreground, final long key) {
		if(authenticate(key)) {
//			TODO: Add support for foreground colors.
		}
		
		return this;
	}
	
	@Override
	public LabelImpl setLocation(final int x, final int y, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setLocation(this.jLabel, x, y);
		}
		
		return this;
	}
	
	@Override
	public LabelImpl setMovable(final boolean isMovable, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.putClientProperty(this.jLabel, ComponentUtilities.KEY_MOVABLE, Boolean.valueOf(isMovable));
		}
		
		return this;
	}
	
	@Override
	public LabelImpl setResizable(final boolean isResizable, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.putClientProperty(this.jLabel, ComponentUtilities.KEY_RESIZABLE, Boolean.valueOf(isResizable));
		}
		
		return this;
	}
	
	@Override
	public LabelImpl setSize(final int width, final int height, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setSize(this.jLabel, width, height);
		}
		
		return this;
	}
	
	@Override
	public LabelImpl setText(final String text, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setText(this.jLabel, text);
		}
		
		return this;
	}
	
	@Override
	public LabelImpl setVisible(final boolean isVisible, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setVisible(this.jLabel, isVisible);
		}
		
		return this;
	}
	
	@Override
	public Color getForeground() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Font getFont() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int getHeight() {
		return ComponentUtilities.getHeight(this.jLabel);
	}
	
	@Override
	public int getWidth() {
		return ComponentUtilities.getWidth(this.jLabel);
	}
	
	@Override
	public int getX() {
		return ComponentUtilities.getX(this.jLabel);
	}
	
	@Override
	public int getY() {
		return ComponentUtilities.getY(this.jLabel);
	}
	
	@Override
	public Object getComponentObject() {
		return this.jLabel;
	}
	
	@Override
	public String getText() {
		return ComponentUtilities.getText(this.jLabel);
	}
}