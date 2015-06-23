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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Objects;

import org.macroing.gdt.engine.input.KeyState;

final class ActionListenerImpl implements ActionListener {
	private final List<KeyEvent> keyEvents;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ActionListenerImpl(final List<KeyEvent> keyEvents) {
		this.keyEvents = keyEvents;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		if(this.keyEvents.size() > 0) {
			final KeyEvent keyEvent = this.keyEvents.get(this.keyEvents.size() - 1);
			
			KeyEvents.notifyOfKeyboardEvent(keyEvent.getKeyCode(), keyEvent.getKeyLocation(), KeyState.PRESSED);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static ActionListener newInstance(final List<KeyEvent> keyEvents) {
		return new ActionListenerImpl(Objects.requireNonNull(keyEvents, "keyEvents == null"));
	}
}