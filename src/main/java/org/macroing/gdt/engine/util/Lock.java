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

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * A {@code Lock} is a key-based locking-mechanism.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Lock {
	/**
	 * The default key that represents an unlocked lock.
	 */
	public static final long DEFAULT_KEY = 0L;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicLong key = new AtomicLong();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new unlocked {@code Lock} instance.
	 */
	public Lock() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Checks if this {@code Lock} is currently locked.
	 * <p>
	 * Returns {@code true} if, and only if, this {@code Lock} is currently locked, {@code false} otherwise.
	 * <p>
	 * Calling this method is equivalent to calling {@code lock.isLockedBy(Lock.DEFAULT_KEY) == false}.
	 * 
	 * @return {@code true} if, and only if, this {@code Lock} is currently locked, {@code false} otherwise
	 */
	public synchronized boolean isLocked() {
		return !isLockedBy(DEFAULT_KEY);
	}
	
	/**
	 * Checks if this {@code Lock} is currently locked by {@code key}.
	 * <p>
	 * Returns {@code true} if, and only if, this {@code Lock} was locked by {@code key}, {@code false} otherwise.
	 * <p>
	 * Calling {@code lock.isLockedBy(Lock.DEFAULT_KEY)} will return {@code true} if, and only if, no lock is currently held.
	 * 
	 * @param key the key to check
	 * @return {@code true} if, and only if, this {@code Lock} was locked by {@code key}, {@code false} otherwise
	 */
	public synchronized boolean isLockedBy(final long key) {
		return this.key.get() == key;
	}
	
	/**
	 * Attempts to unlock this {@code Lock} using {@code key}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code lock.isLockedBy(key)} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * If {@code key == Lock.DEFAULT_KEY}, that can be seen as unlocking an unlocked lock. The result is an unlocked lock.
	 * 
	 * @param key the key to attempt to unlock this {@code Lock} with
	 * @return {@code true} if, and only if, {@code lock.isLockedBy(key)} returns {@code true}, {@code false} otherwise
	 */
	public synchronized boolean unlock(final long key) {
		return this.key.compareAndSet(key, DEFAULT_KEY);
	}
	
	/**
	 * Attempts to lock this {@code Lock}.
	 * <p>
	 * Returns a key that can be used to unlock this {@code Lock} with.
	 * <p>
	 * If the returned key is {@code Lock.DEFAULT_KEY}, this {@code Lock} was already locked by someone else. This also means that calling {@code lock.unlock(key)} will most likely return {@code false}.
	 * 
	 * @return a key that can be used to unlock this {@code Lock} with
	 */
	public synchronized long lock() {
		long key = DEFAULT_KEY;
		
		if(this.key.get() == DEFAULT_KEY) {
			do {
				key = ThreadLocalRandom.current().nextLong();
			} while(key != DEFAULT_KEY);
			
			this.key.set(key);
		}
		
		return key;
	}
	
	public <T> T getIfUnlocked(final Supplier<T> supplier) {
		if(isLocked()) {
			throw new IllegalStateException("Lock.isLocked()");
		}
		
		return supplier.get();
	}
}