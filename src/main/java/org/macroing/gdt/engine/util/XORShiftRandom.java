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
package org.macroing.gdt.engine.util;

import java.util.Random;

final class XORShiftRandom extends Random {
	private static final long serialVersionUID = 1L;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long seed;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private XORShiftRandom() {
		this(System.nanoTime());
	}
	
	private XORShiftRandom(final long seed) {
		this.seed = seed;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected int next(final int bits) {
		long seed = this.seed;
		
		seed ^= seed << 21L;
		seed ^= seed >>> 31L;
		seed ^= seed << 4L;
		
		this.seed = seed;
		
		seed &= (1L << bits) - 1L;
		
		return (int)(seed);
	}
	
	@Override
	public synchronized void setSeed(final long seed) {
		this.seed = seed;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Random newInstance() {
		return new XORShiftRandom();
	}
}