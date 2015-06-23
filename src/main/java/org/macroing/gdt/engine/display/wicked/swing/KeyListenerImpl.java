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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.macroing.gdt.engine.input.KeyState;

final class KeyListenerImpl extends KeyAdapter {
	private final JFrame jFrame;
	private final List<KeyEvent> keyEvents = new ArrayList<>();
	private final Timer timer = new Timer(10, ActionListenerImpl.newInstance(this.keyEvents));
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private KeyListenerImpl(final JFrame jFrame) {
		this.jFrame = jFrame;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void keyPressed(final KeyEvent e) {
		if(this.jFrame.isVisible()) {
			if(this.jFrame.isActive()) {
				if(!this.timer.isRunning()) {
					this.timer.start();
				}
				
				boolean isAddable = true;
				
				for(int i = 0; i < this.keyEvents.size(); i++) {
					if(this.keyEvents.get(i).getKeyCode() == e.getKeyCode()) {
						isAddable = false;
						
						break;
					}
				}
				
				if(isAddable) {
					this.keyEvents.add(e);
				}
			}
		} else if(this.timer.isRunning()) {
			this.timer.stop();
		}
		
		e.consume();
	}
	
	@Override
	public void keyReleased(final KeyEvent e) {
		if(this.jFrame.isActive()) {
			for(int i = 0; i < this.keyEvents.size(); i++) {
				if(this.keyEvents.get(i).getKeyCode() == e.getKeyCode()) {
					this.keyEvents.remove(i);
					
					break;
				}
			}
			
			KeyEvents.notifyOfKeyboardEvent(e.getKeyCode(), e.getKeyLocation(), KeyState.RELEASED);
		}
		
		e.consume();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static KeyListener newInstance(final JFrame jFrame) {
		return new KeyListenerImpl(Objects.requireNonNull(jFrame, "jFrame == null"));
	}
}