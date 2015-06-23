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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.Objects;

import javax.swing.JFrame;

import org.macroing.gdt.engine.input.Mouse;
import org.macroing.gdt.engine.input.MouseEvent;
import org.macroing.gdt.engine.input.MouseState;

final class MouseListenerImpl extends MouseAdapter {
	private final JFrame jFrame;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private MouseListenerImpl(final JFrame jFrame) {
		this.jFrame = jFrame;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void mousePressed(final java.awt.event.MouseEvent e) {
		if(this.jFrame.isActive()) {
			switch(e.getButton()) {
				case java.awt.event.MouseEvent.BUTTON1:
					Mouse.getInstance().fireMouseEvent(MouseEvent.newInstance(e.getClickCount(), MouseState.PRESSED_BUTTON_1));
					
					break;
				case java.awt.event.MouseEvent.BUTTON2:
					Mouse.getInstance().fireMouseEvent(MouseEvent.newInstance(e.getClickCount(), MouseState.PRESSED_BUTTON_2));
					
					break;
				case java.awt.event.MouseEvent.BUTTON3:
					Mouse.getInstance().fireMouseEvent(MouseEvent.newInstance(e.getClickCount(), MouseState.PRESSED_BUTTON_3));
					
					break;
				default:
					break;
			}
		}
	}
	
	@Override
	public void mouseReleased(final java.awt.event.MouseEvent e) {
		if(this.jFrame.isActive()) {
			switch(e.getButton()) {
				case java.awt.event.MouseEvent.BUTTON1:
					Mouse.getInstance().fireMouseEvent(MouseEvent.newInstance(e.getClickCount(), MouseState.RELEASED_BUTTON_1));
					
					break;
				case java.awt.event.MouseEvent.BUTTON2:
					Mouse.getInstance().fireMouseEvent(MouseEvent.newInstance(e.getClickCount(), MouseState.RELEASED_BUTTON_2));
					
					break;
				case java.awt.event.MouseEvent.BUTTON3:
					Mouse.getInstance().fireMouseEvent(MouseEvent.newInstance(e.getClickCount(), MouseState.RELEASED_BUTTON_3));
					
					break;
				default:
					break;
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static MouseListener newInstance(final JFrame jFrame) {
		return new MouseListenerImpl(Objects.requireNonNull(jFrame, "jFrame == null"));
	}
}