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

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Objects;

import javax.swing.JFrame;

import org.macroing.gdt.engine.input.Mouse;
import org.macroing.gdt.engine.input.MouseEvent;
import org.macroing.gdt.engine.input.MouseState;

final class MouseWheelListenerImpl implements MouseWheelListener {
	private final JFrame jFrame;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private MouseWheelListenerImpl(final JFrame jFrame) {
		this.jFrame = jFrame;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		if(this.jFrame.isActive()) {
			final MouseState mouseState;
			
			if(e.getWheelRotation() < 0) {
				mouseState = MouseState.SCROLLED_UP;
			} else {
				mouseState = MouseState.SCROLLED_DOWN;
			}
			
			final MouseEvent mouseEvent = MouseEvent.newInstance(0, mouseState);
			
			Mouse.getInstance().fireMouseEvent(mouseEvent);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static MouseWheelListener newInstance(final JFrame jFrame) {
		return new MouseWheelListenerImpl(Objects.requireNonNull(jFrame, "jFrame == null"));
	}
}