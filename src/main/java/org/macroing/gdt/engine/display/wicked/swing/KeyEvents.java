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

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.macroing.gdt.engine.input.Key;
import org.macroing.gdt.engine.input.KeyState;
import org.macroing.gdt.engine.input.Keyboard;
import org.macroing.gdt.engine.input.KeyboardEvent;

final class KeyEvents {
	private static final Map<Integer, Key> KEY_CODES_MULTIPLIED_BY_KEY_LOCATIONS_TO_KEYS = new HashMap<>();
	private static final Map<Integer, Key> KEY_CODES_TO_KEYS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private KeyEvents() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		doMapKeyCodesMultipliedByKeyLocationsToKeys();
		doMapKeyCodesToKeys();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void notifyOfKeyboardEvent(final int keyCode, final int keyLocation, final KeyState keyState) {
		final Key key = doGetKeyFromKeyCodeAndKeyLocation(keyCode, keyLocation);
		
		final KeyboardEvent keyboardEvent = KeyboardEvent.newInstance(key, Objects.requireNonNull(keyState, "keyState == null"));
		
		final
		Keyboard keyboard = Keyboard.getInstance();
		keyboard.fireKeyboardEvent(keyboardEvent);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Key doGetKeyFromKeyCodeAndKeyLocation(final int keyCode, final int keyLocation) {
		Key key = null;
		
		synchronized(KEY_CODES_TO_KEYS) {
			key = KEY_CODES_TO_KEYS.get(Integer.valueOf(keyCode));
		}
		
		if(key == null) {
			synchronized(KEY_CODES_MULTIPLIED_BY_KEY_LOCATIONS_TO_KEYS) {
				key = KEY_CODES_MULTIPLIED_BY_KEY_LOCATIONS_TO_KEYS.get(Integer.valueOf(keyCode * keyLocation));
			}
			
			if(key == null) {
				key = Key.KEY_UNKNOWN;
			}
		}
		
		return key;
	}
	
	private static void doMapKeyCodesMultipliedByKeyLocationsToKeys() {
		synchronized(KEY_CODES_MULTIPLIED_BY_KEY_LOCATIONS_TO_KEYS) {
			KEY_CODES_MULTIPLIED_BY_KEY_LOCATIONS_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_CONTROL * KeyEvent.VK_LEFT), Key.KEY_LEFT_CTRL);
			KEY_CODES_MULTIPLIED_BY_KEY_LOCATIONS_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_CONTROL * KeyEvent.VK_RIGHT), Key.KEY_RIGHT_CTRL);
			KEY_CODES_MULTIPLIED_BY_KEY_LOCATIONS_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_SHIFT * KeyEvent.VK_LEFT), Key.KEY_LEFT_SHIFT);
			KEY_CODES_MULTIPLIED_BY_KEY_LOCATIONS_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_SHIFT * KeyEvent.VK_RIGHT), Key.KEY_RIGHT_SHIFT);
		}
	}
	
	private static void doMapKeyCodesToKeys() {
		synchronized(KEY_CODES_TO_KEYS) {
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_0), Key.KEY_0);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_1), Key.KEY_1);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_2), Key.KEY_2);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_3), Key.KEY_3);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_4), Key.KEY_4);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_5), Key.KEY_5);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_6), Key.KEY_6);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_7), Key.KEY_7);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_8), Key.KEY_8);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_9), Key.KEY_9);
			
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_A), Key.KEY_A);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_B), Key.KEY_B);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_C), Key.KEY_C);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_D), Key.KEY_D);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_E), Key.KEY_E);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F), Key.KEY_F);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_G), Key.KEY_G);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_I), Key.KEY_I);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_J), Key.KEY_J);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_K), Key.KEY_K);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_L), Key.KEY_L);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_M), Key.KEY_M);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_N), Key.KEY_N);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_O), Key.KEY_O);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_P), Key.KEY_P);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_Q), Key.KEY_Q);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_R), Key.KEY_R);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_S), Key.KEY_S);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_T), Key.KEY_T);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_U), Key.KEY_U);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_V), Key.KEY_V);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_W), Key.KEY_W);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_X), Key.KEY_X);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_Y), Key.KEY_Y);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_Z), Key.KEY_Z);
			
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F1), Key.KEY_F1);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F2), Key.KEY_F2);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F3), Key.KEY_F3);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F4), Key.KEY_F4);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F5), Key.KEY_F5);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F6), Key.KEY_F6);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F7), Key.KEY_F7);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F8), Key.KEY_F8);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F9), Key.KEY_F9);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F10), Key.KEY_F10);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F11), Key.KEY_F11);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_F12), Key.KEY_F12);
			
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_ALT), Key.KEY_ALT);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_ALT_GRAPH), Key.KEY_ALT_GR);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_BACK_SPACE), Key.KEY_BACK_SPACE);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_CAPS_LOCK), Key.KEY_CAPS_LOCK);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_ENTER), Key.KEY_ENTER);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_ESCAPE), Key.KEY_ESC);
			KEY_CODES_TO_KEYS.put(Integer.valueOf(KeyEvent.VK_SPACE), Key.KEY_SPACE);
		}
	}
}