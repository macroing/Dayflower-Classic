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

/**
 * An abstract base-class for generating pseudorandom numbers.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class PRNG {
	/**
	 * Constructs a new {@code PRNG} instance.
	 */
	protected PRNG() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the next uniformly distributed {@code double} from this {@code PRNG} instance.
	 * <p>
	 * The value returned will be between {@code 0.0D} (inclusive) and {@code 1.0D} (exclusive).
	 * 
	 * @return the next uniformly distributed {@code double} from this {@code PRNG} instance
	 */
	public abstract double nextDouble();
	
	/**
	 * Returns the next uniformly distributed {@code int} from this {@code PRNG} instance.
	 * <p>
	 * The value returned will be between {@code 0} (inclusive) and {@code Integer.MAX_VALUE} (exclusive).
	 * <p>
	 * A call to this method is equivalent to {@code nextInt(Integer.MAX_VALUE)}.
	 * 
	 * @return the next uniformly distributed {@code int} from this {@code PRNG} instance
	 */
	public final int nextInt() {
		return nextInt(Integer.MAX_VALUE);
	}
	
	/**
	 * Returns the next uniformly distributed {@code int} from this {@code PRNG} instance.
	 * <p>
	 * The value returned will be between {@code 0} (inclusive) and {@code bound} (exclusive).
	 * <p>
	 * If {@code bound} is less than {@code 0}, an {@code IllegalArgumentException} may be thrown. But no guarantees can be made.
	 * 
	 * @param bound the positive upper bound (exclusive)
	 * @return the next uniformly distributed {@code int} from this {@code PRNG} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code bound} is less than {@code 0}
	 */
	public abstract int nextInt(final int bound);
	
	/**
	 * Sets a new seed for this {@code PRNG} instance.
	 * <p>
	 * Returns the {@code PRNG} instance itself, such that it's possible to chain multiple calls.
	 * 
	 * @param seed the new seed
	 * @return the instance itself
	 */
	public abstract PRNG setSeed(final long seed);
	
	/**
	 * Shuffles {@code array} using the current sequence provided by this {@code PRNG} instance.
	 * <p>
	 * Returns the {@code PRNG} instance itself, such that it's possible to chain multiple calls.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param array the array to shuffle
	 * @return the {@code PRNG} instance itself
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public final PRNG shuffle(final double[] array) {
		final int length = array.length;
		
		for(int i = length; i > 1; i--) {
			Arrays.swap(array, i - 1, nextInt(i));
		}
		
		return this;
	}
	
	/**
	 * Shuffles {@code array} using the current sequence provided by this {@code PRNG} instance.
	 * <p>
	 * In this method, {@code array} is represented as a flattened multidimensional array. The length of each dimension is provided by {@code length} and the dimension count is provided by {@code dimensions}. Therefore, the length of {@code array} must
	 * be at least {@code length * dimensions}.
	 * <p>
	 * If either {@code length} or {@code dimensions} are negative, nothing will happen.
	 * <p>
	 * Returns the {@code PRNG} instance itself, such that it's possible to chain multiple calls.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code length * dimensions} is greater than or equal to {@code array.length}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * 
	 * @param array the multidimensional array to shuffle
	 * @param length the length of one dimension
	 * @param dimensions the dimensions of the array
	 * @return the {@code PRNG} instance itself
	 * @throws IllegalArgumentException thrown if, and only if, {@code length * dimensions} is greater than or equal to {@code array.length}
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public final PRNG shuffle(final double[] array, final int length, final int dimensions) {
		for(int i = 0; i < length; i++) {
			final int j = i + nextInt() % (length - i);
			
			for(int k = 0; k < dimensions; k++) {
				Arrays.swap(array, dimensions * i + k, dimensions * j + k);
			}
		}
		
		return this;
	}
}