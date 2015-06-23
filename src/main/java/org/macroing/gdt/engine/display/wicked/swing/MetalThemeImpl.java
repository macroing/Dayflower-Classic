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

import java.awt.Color;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.OceanTheme;

final class MetalThemeImpl extends OceanTheme {
	public MetalThemeImpl() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public ColorUIResource getBlack() {
		return new ColorUIResource(0, 0, 0);
	}
	
	@Override
	public ColorUIResource getHighlightedTextColor() {
		return new ColorUIResource(255, 255, 255);
	}
	
	@Override
	public ColorUIResource getMenuBackground() {
		return new ColorUIResource(255, 255, 255);
	}
	
	@Override
	public ColorUIResource getMenuForeground() {
		return new ColorUIResource(0, 0, 0);
	}
	
	@Override
	public ColorUIResource getMenuSelectedBackground() {
		return new ColorUIResource(0, 0, 0);
	}
	
	@Override
	public ColorUIResource getMenuSelectedForeground() {
		return new ColorUIResource(255, 255, 255);
	}
	
	@Override
	protected ColorUIResource getPrimary1() {
		return new ColorUIResource(255, 255, 255);
	}
	
	@Override
	protected ColorUIResource getPrimary2() {
		return new ColorUIResource(new Color(0, 0, 0, 0));
	}
	
	@Override
	protected ColorUIResource getPrimary3() {
		return new ColorUIResource(0x41CFE5);
	}
	
	@Override
	public ColorUIResource getTextHighlightColor() {
		return new ColorUIResource(0, 0, 0);
	}
	
	@Override
	protected ColorUIResource getSecondary1() {
		return new ColorUIResource(190, 200, 200);
	}
	
	@Override
	protected ColorUIResource getSecondary2() {
		return new ColorUIResource(0x66E8F3);
	}
	
	@Override
	protected ColorUIResource getSecondary3() {
		return new ColorUIResource(190, 200, 200);
	}
	
	@Override
	public ColorUIResource getSystemTextColor() {
		return new ColorUIResource(0, 0, 0);
	}
	
	@Override
	public ColorUIResource getWhite() {
		return new ColorUIResource(255, 255, 255);
	}
	
	@Override
	public void addCustomEntriesToTable(final UIDefaults table) {
		super.addCustomEntriesToTable(table);
		
		final List<Object> gradient = Arrays.asList(new Object[] {new Float(0.3F), new Float(0.0F), new ColorUIResource(0x66E8F3), getWhite(), getSecondary2()});
		
		table.put("activeCaption", new ColorUIResource(255, 0, 0));
		
		table.put("Button.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		table.put("Button.gradient", gradient);
		table.put("Button.margin", new Insets(10, 10, 10, 10));
		table.put("Button.rollover", Boolean.TRUE);
		
		table.put("CheckBox.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		table.put("CheckBox.gradient", gradient);
		table.put("CheckBox.margin", new Insets(10, 10, 10, 10));
		table.put("CheckBox.rollover", Boolean.TRUE);
	}
}