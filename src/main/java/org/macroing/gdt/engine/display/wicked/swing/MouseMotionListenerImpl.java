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

import java.awt.Point;
import java.awt.event.MouseMotionListener;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.macroing.gdt.engine.input.Mouse;
import org.macroing.gdt.engine.input.MouseEvent;
import org.macroing.gdt.engine.input.MouseState;

final class MouseMotionListenerImpl implements MouseMotionListener {
	private boolean isRecenteringMouse;
	private final JFrame jFrame;
	private final Mouse mouse = Mouse.getInstance();
	private final Point centerPoint = new Point();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private MouseMotionListenerImpl(final JFrame jFrame) {
		this.jFrame = jFrame;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void mouseDragged(final java.awt.event.MouseEvent e) {
		doMouseMoved(e);
	}
	
	@Override
	public void mouseMoved(final java.awt.event.MouseEvent e) {
		doMouseMoved(e);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static MouseMotionListener newInstance(final JFrame jFrame) {
		return new MouseMotionListenerImpl(Objects.requireNonNull(jFrame, "jFrame == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doMouseMoved(final java.awt.event.MouseEvent e) {
		if(this.jFrame.isActive()) {
			if(this.isRecenteringMouse && this.centerPoint.x == e.getXOnScreen() && this.centerPoint.y == e.getYOnScreen()) {
				this.isRecenteringMouse = false;
			} else {
				final int x = e.getXOnScreen();
				final int y = e.getYOnScreen();
				final int deltaX = x - this.centerPoint.x;
				final int deltaY = y - this.centerPoint.y;
				final int amountX = Math.abs(deltaX);
				final int amountY = Math.abs(deltaY);
				
				doRecenterMouse(e);
				
				if(deltaX < 0) {
					Mouse.getInstance().fireMouseEvent(MouseEvent.newInstance(amountX, MouseState.MOVED_LEFT));
				} else if(deltaX > 0) {
					Mouse.getInstance().fireMouseEvent(MouseEvent.newInstance(amountX, MouseState.MOVED_RIGHT));
				}
				
				if(deltaY < 0) {
					Mouse.getInstance().fireMouseEvent(MouseEvent.newInstance(amountY, MouseState.MOVED_UP));
				} else if(deltaY > 0) {
					Mouse.getInstance().fireMouseEvent(MouseEvent.newInstance(amountY, MouseState.MOVED_DOWN));
				}
			}
		}
	}
	
	private synchronized void doRecenterMouse(final java.awt.event.MouseEvent e) {
		if(this.jFrame.isActive() && this.mouse.isRecentering()) {
			this.centerPoint.x = this.jFrame.getWidth() / 2;
			this.centerPoint.y = this.jFrame.getHeight() / 2;
			
			SwingUtilities.convertPointToScreen(this.centerPoint, this.jFrame);
			
			this.isRecenteringMouse = true;
			
			this.mouse.setLocation(this.centerPoint.x, this.centerPoint.y);
		}
	}
}