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

import java.util.function.Consumer;

import javax.swing.JCheckBox;

import org.macroing.gdt.engine.display.wicked.CheckBox;
import org.macroing.gdt.engine.display.wicked.WickedDisplay;

final class CheckBoxImpl extends CheckBox<CheckBoxImpl> {
	private final JCheckBox jCheckBox = ComponentUtilities.newJCheckBox();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CheckBoxImpl(final String id, final WickedDisplay wickedDisplay) {
		super(id, wickedDisplay);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean isMovable() {
		return Boolean.class.cast(ComponentUtilities.getClientProperty(this.jCheckBox, ComponentUtilities.KEY_MOVABLE, Boolean.FALSE)).booleanValue();
	}
	
	@Override
	public boolean isResizable() {
		return Boolean.class.cast(ComponentUtilities.getClientProperty(this.jCheckBox, ComponentUtilities.KEY_RESIZABLE, Boolean.FALSE)).booleanValue();
	}
	
	@Override
	public boolean isSelected() {
		return ComponentUtilities.isSelected(this.jCheckBox);
	}
	
	@Override
	public boolean isVisible() {
		return ComponentUtilities.isVisible(this.jCheckBox);
	}
	
	@Override
	public CheckBoxImpl setLocation(final int x, final int y, long key) {
		if(authenticate(key)) {
			ComponentUtilities.setLocation(this.jCheckBox, x, y);
			
			final
			WickedDisplay wickedDisplay = getWickedDisplay();
			wickedDisplay.updateComponentPin(this);
		}
		
		return this;
	}
	
	@Override
	public CheckBoxImpl setMovable(final boolean isMovable, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.putClientProperty(this.jCheckBox, ComponentUtilities.KEY_MOVABLE, Boolean.valueOf(isMovable));
		}
		
		return this;
	}
	
	@Override
	public CheckBoxImpl setOnSelectionChange(final Consumer<CheckBox<CheckBoxImpl>> consumer, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.removeAllActionListeners(this.jCheckBox);
			ComponentUtilities.addActionListener(this.jCheckBox, e -> consumer.accept(this));
		}
		
		return this;
	}
	
	@Override
	public CheckBoxImpl setResizable(final boolean isResizable, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.putClientProperty(this.jCheckBox, ComponentUtilities.KEY_RESIZABLE, Boolean.valueOf(isResizable));
		}
		
		return this;
	}
	
	@Override
	public CheckBoxImpl setSelected(final boolean isSelected, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setSelected(this.jCheckBox, isSelected);
		}
		
		return this;
	}
	
	@Override
	public CheckBoxImpl setSize(final int width, final int height, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setSize(this.jCheckBox, width, height);
			
			final
			WickedDisplay wickedDisplay = getWickedDisplay();
			wickedDisplay.updateComponentPin(this);
		}
		
		return this;
	}
	
	@Override
	public CheckBoxImpl setText(final String text, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setText(this.jCheckBox, text);
		}
		
		return this;
	}
	
	@Override
	public CheckBoxImpl setVisible(final boolean isVisible, final long key) {
		if(authenticate(key)) {
			ComponentUtilities.setVisible(this.jCheckBox, isVisible);
		}
		
		return this;
	}
	
	@Override
	public int getHeight() {
		return ComponentUtilities.getHeight(this.jCheckBox);
	}
	
	@Override
	public int getWidth() {
		return ComponentUtilities.getWidth(this.jCheckBox);
	}
	
	@Override
	public int getX() {
		return ComponentUtilities.getX(this.jCheckBox);
	}
	
	@Override
	public int getY() {
		return ComponentUtilities.getY(this.jCheckBox);
	}
	
	@Override
	public Object getComponentObject() {
		return this.jCheckBox;
	}
	
	@Override
	public String getText() {
		return ComponentUtilities.getText(this.jCheckBox);
	}
}