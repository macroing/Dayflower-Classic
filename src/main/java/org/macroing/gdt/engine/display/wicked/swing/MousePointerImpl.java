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

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Robot;

import org.macroing.gdt.engine.input.MousePointer;

final class MousePointerImpl implements MousePointer {
	private final Robot robot = doCreateRobot();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private MousePointerImpl() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public int getX() {
		try {
			return MouseInfo.getPointerInfo().getLocation().x;
		} catch(HeadlessException | NullPointerException | SecurityException e) {
			throw new UnsupportedOperationException("This method is not supported by the current configuration.");
		}
	}
	
	@Override
	public int getY() {
		try {
			return MouseInfo.getPointerInfo().getLocation().y;
		} catch(HeadlessException | NullPointerException | SecurityException e) {
			throw new UnsupportedOperationException("This method is not supported by the current configuration.");
		}
	}
	
	@Override
	public void setLocation(final int x, final int y) {
		try {
			this.robot.mouseMove(x, y);
		} catch(HeadlessException | NullPointerException | SecurityException e) {
			throw new UnsupportedOperationException("This method is not supported by the current configuration.");
		}
	}
	
	@Override
	public void setX(final int x) {
		try {
			this.robot.mouseMove(x, MouseInfo.getPointerInfo().getLocation().y);
		} catch(HeadlessException | NullPointerException | SecurityException e) {
			throw new UnsupportedOperationException("This method is not supported by the current configuration.");
		}
	}
	
	@Override
	public void setY(final int y) {
		try {
			this.robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x, y);
		} catch(HeadlessException | NullPointerException | SecurityException e) {
			throw new UnsupportedOperationException("This method is not supported by the current configuration.");
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static MousePointer newInstance() {
		return new MousePointerImpl();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Robot doCreateRobot() {
		try {
			return new Robot();
		} catch(AWTException e) {
			return null;
		}
	}
}