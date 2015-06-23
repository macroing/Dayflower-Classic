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
package org.macroing.gdt.engine.input;

/**
 * A model of a key on a keyboard.
 * <p>
 * Currently, {@code Key} does not provide representations for all standard keys. But, more keys may be added in the future.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum Key {
	/**
	 * A {@code Key} denoting the key 0.
	 */
	KEY_0("0"),
	
	/**
	 * A {@code Key} denoting the key 1.
	 */
	KEY_1("1"),
	
	/**
	 * A {@code Key} denoting the key 2.
	 */
	KEY_2("2"),
	
	/**
	 * A {@code Key} denoting the key 3.
	 */
	KEY_3("3"),
	
	/**
	 * A {@code Key} denoting the key 4.
	 */
	KEY_4("4"),
	
	/**
	 * A {@code Key} denoting the key 5.
	 */
	KEY_5("5"),
	
	/**
	 * A {@code Key} denoting the key 6.
	 */
	KEY_6("6"),
	
	/**
	 * A {@code Key} denoting the key 7.
	 */
	KEY_7("7"),
	
	/**
	 * A {@code Key} denoting the key 8.
	 */
	KEY_8("8"),
	
	/**
	 * A {@code Key} denoting the key 9.
	 */
	KEY_9("9"),
	
	/**
	 * A {@code Key} denoting the key A.
	 */
	KEY_A("A"),
	
	/**
	 * A {@code Key} denoting the key ALT.
	 */
	KEY_ALT("ALT"),
	
	/**
	 * A {@code Key} denoting the key ALT GR.
	 */
	KEY_ALT_GR("ALT GR"),
	
	/**
	 * A {@code Key} denoting the key B.
	 */
	KEY_B("B"),
	
	/**
	 * A {@code Key} denoting the key BACK SPACE.
	 */
	KEY_BACK_SPACE("BACK SPACE"),
	
	/**
	 * A {@code Key} denoting the key C.
	 */
	KEY_C("C"),
	
	/**
	 * A {@code Key} denoting the key CAPS LOCK.
	 */
	KEY_CAPS_LOCK("CAPS LOCK"),
	
	/**
	 * A {@code Key} denoting the key D.
	 */
	KEY_D("D"),
	
	/**
	 * A {@code Key} denoting the key E.
	 */
	KEY_E("E"),
	
	/**
	 * A {@code Key} denoting the key ENTER.
	 */
	KEY_ENTER("ENTER"),
	
	/**
	 * A {@code Key} denoting the key ESC.
	 */
	KEY_ESC("ESC"),
	
	/**
	 * A {@code Key} denoting the key F.
	 */
	KEY_F("F"),
	
	/**
	 * A {@code Key} denoting the key F1.
	 */
	KEY_F1("F1"),
	
	/**
	 * A {@code Key} denoting the key F10.
	 */
	KEY_F10("F10"),
	
	/**
	 * A {@code Key} denoting the key F11.
	 */
	KEY_F11("F11"),
	
	/**
	 * A {@code Key} denoting the key F12.
	 */
	KEY_F12("F12"),
	
	/**
	 * A {@code Key} denoting the key F2.
	 */
	KEY_F2("F2"),
	
	/**
	 * A {@code Key} denoting the key F3.
	 */
	KEY_F3("F3"),
	
	/**
	 * A {@code Key} denoting the key F4.
	 */
	KEY_F4("F4"),
	
	/**
	 * A {@code Key} denoting the key F5.
	 */
	KEY_F5("F5"),
	
	/**
	 * A {@code Key} denoting the key F6.
	 */
	KEY_F6("F6"),
	
	/**
	 * A {@code Key} denoting the key F7.
	 */
	KEY_F7("F7"),
	
	/**
	 * A {@code Key} denoting the key F8.
	 */
	KEY_F8("F8"),
	
	/**
	 * A {@code Key} denoting the key F9.
	 */
	KEY_F9("F9"),
	
	/**
	 * A {@code Key} denoting the key G.
	 */
	KEY_G("G"),
	
	/**
	 * A {@code Key} denoting the key H.
	 */
	KEY_H("H"),
	
	/**
	 * A {@code Key} denoting the key I.
	 */
	KEY_I("I"),
	
	/**
	 * A {@code Key} denoting the key J.
	 */
	KEY_J("J"),
	
	/**
	 * A {@code Key} denoting the key K.
	 */
	KEY_K("K"),
	
	/**
	 * A {@code Key} denoting the key L.
	 */
	KEY_L("L"),
	
	/**
	 * A {@code Key} denoting the key LEFT CTRL.
	 */
	KEY_LEFT_CTRL("LEFT CTRL"),
	
	/**
	 * A {@code Key} denoting the key LEFT SHIFT.
	 */
	KEY_LEFT_SHIFT("LEFT SHIFT"),
	
	/**
	 * A {@code Key} denoting the key M.
	 */
	KEY_M("M"),
	
	/**
	 * A {@code Key} denoting the key N.
	 */
	KEY_N("N"),
	
	/**
	 * A {@code Key} denoting the key O.
	 */
	KEY_O("O"),
	
	/**
	 * A {@code Key} denoting the key P.
	 */
	KEY_P("P"),
	
	/**
	 * A {@code Key} denoting the key Q.
	 */
	KEY_Q("Q"),
	
	/**
	 * A {@code Key} denoting the key R.
	 */
	KEY_R("R"),
	
	/**
	 * A {@code Key} denoting the key RIGHT CTRL.
	 */
	KEY_RIGHT_CTRL("RIGHT CTRL"),
	
	/**
	 * A {@code Key} denoting the key RIGHT SHIFT.
	 */
	KEY_RIGHT_SHIFT("RIGHT SHIFT"),
	
	/**
	 * A {@code Key} denoting the key S.
	 */
	KEY_S("S"),
	
	/**
	 * A {@code Key} denoting the key SPACE.
	 */
	KEY_SPACE("SPACE"),
	
	/**
	 * A {@code Key} denoting the key T.
	 */
	KEY_T("T"),
	
	/**
	 * A {@code Key} denoting the key U.
	 */
	KEY_U("U"),
	
	/**
	 * A {@code Key} denoting an unknown key.
	 */
	KEY_UNKNOWN("UNKNOWN"),
	
	/**
	 * A {@code Key} denoting the key V.
	 */
	KEY_V("V"),
	
	/**
	 * A {@code Key} denoting the key W.
	 */
	KEY_W("W"),
	
	/**
	 * A {@code Key} denoting the key X.
	 */
	KEY_X("X"),
	
	/**
	 * A {@code Key} denoting the key Y.
	 */
	KEY_Y("Y"),
	
	/**
	 * A {@code Key} denoting the key Z.
	 */
	KEY_Z("Z");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String toString;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Key(final String toString) {
		this.toString = toString;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Key}.
	 * 
	 * @return a {@code String} representation of this {@code Key}
	 */
	@Override
	public String toString() {
		return this.toString;
	}
}