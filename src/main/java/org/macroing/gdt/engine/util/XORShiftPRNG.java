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

/**
 * A {@link PRNG} implementation using the XOR Shift algorithm.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class XORShiftPRNG extends PRNG {
	private final Random random = XORShiftRandom.newInstance();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private XORShiftPRNG() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the next uniformly distributed {@code double} from this {@code XORShiftPRNG} instance.
	 * <p>
	 * The value returned will be between {@code 0.0D} (inclusive) and {@code 1.0D} (exclusive).
	 * 
	 * @return the next uniformly distributed {@code double} from this {@code XORShiftPRNG} instance
	 */
	@Override
	public double nextDouble() {
		return this.random.nextDouble();
	}
	
	/**
	 * Returns the next uniformly distributed {@code int} from this {@code XORShiftPRNG} instance.
	 * <p>
	 * The value returned will be between {@code 0} (inclusive) and {@code bound} (exclusive).
	 * <p>
	 * If {@code bound} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param bound the positive upper bound (exclusive)
	 * @return the next uniformly distributed {@code int} from this {@code XORShiftPRNG} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code bound} is less than {@code 0}
	 */
	@Override
	public int nextInt(final int bound) {
		return this.random.nextInt(bound);
	}
	
	/**
	 * Sets a new seed for this {@code XORShiftPRNG} instance.
	 * <p>
	 * Returns the {@code XORShiftPRNG} instance itself, such that it's possible to chain multiple calls.
	 * 
	 * @param seed the new seed
	 * @return the instance itself
	 */
	@Override
	public XORShiftPRNG setSeed(final long seed) {
		this.random.setSeed(seed);
		
		return this;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code XORShiftPRNG} instance.
	 * 
	 * @return a new {@code XORShiftPRNG} instance
	 */
	public static XORShiftPRNG newInstance() {
		return new XORShiftPRNG();
	}
}