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
package org.macroing.gdt.engine.sampler;

import java.lang.reflect.Field;//TODO: Fix this class and remove this comment once done. Add Javadocs etc.

import org.macroing.gdt.engine.util.Interpolation;
import org.macroing.gdt.engine.util.PRNG;

/**
 * An abstract base-class for sampling.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Sampler {
	/**
	 * A constant with the default per-pixel sample count.
	 * <p>
	 * The per-pixel sample count is oftentimes known as Samples Per Pixel (SPP).
	 */
	public static final int DEFAULT_PER_PIXEL_SAMPLE_COUNT = 32;
	
	/**
	 * A constant that tells the {@code Sampler} there is no maximum sample count.
	 */
	public static final int NO_MAXIMUM_SAMPLE_COUNT = -1;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private double shutterClose;
	private double shutterOpen;
	private int maximumX;
	private int maximumY;
	private int minimumX;
	private int minimumY;
	private int perPixelSampleCount = DEFAULT_PER_PIXEL_SAMPLE_COUNT;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Sampler} instance.
	 */
	protected Sampler() {
		
	}
	
	/**
	 * Constructs a new {@code Sampler} instance given a reference {@code Sampler}.
	 * <p>
	 * If {@code sampler} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param sampler the reference {@code Sampler}
	 * @throws NullPointerException thrown if, and only if, {@code sampler} is {@code null}
	 */
	protected Sampler(final Sampler sampler) {
		this.shutterClose = sampler.shutterClose;
		this.shutterOpen = sampler.shutterOpen;
		this.maximumX = sampler.maximumX;
		this.maximumY = sampler.maximumY;
		this.minimumX = sampler.minimumX;
		this.minimumY = sampler.minimumY;
		this.perPixelSampleCount = sampler.perPixelSampleCount;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Creates a new sample and stores it into {@code sample}.
	 * <p>
	 * Returns {@code true} if, and only if, {@code sample} contains a new sample, {@code false} otherwise.
	 * <p>
	 * If either {@code sample} or {@code pRNG} are {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param sample the {@link Sample} to store the sample in
	 * @param pRNG the {@code PRNG} that can produce pseudorandom numbers
	 * @return {@code true} if, and only if, {@code sample} contains a new sample, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code sample} or {@code pRNG} are {@code null}
	 */
	public abstract boolean sample(final Sample sample, final PRNG pRNG);
	
	/**
	 * Returns the shutter close property.
	 * 
	 * @return the shutter close property
	 */
	public final double getShutterClose() {
		return this.shutterClose;
	}
	
	/**
	 * Returns the shutter open property.
	 * 
	 * @return the shutter open property
	 */
	public final double getShutterOpen() {
		return this.shutterOpen;
	}
	
	/**
	 * Returns the current sample count.
	 * 
	 * @return the current sample count
	 */
	public abstract int getCurrentSampleCount();
	
	/**
	 * Returns the maximum sample count.
	 * <p>
	 * Some {@code Sampler}s have no maximum sample count. Those will return {@code Sampler.NO_MAXIMUM_SAMPLE_COUNT}.
	 * 
	 * @return the maximum sample count
	 */
	public abstract int getMaximumSampleCount();
	
	/**
	 * Returns the maximum X for this {@code Sampler} instance.
	 * 
	 * @return the maximum X for this {@code Sampler} instance
	 */
	public final int getMaximumX() {
		return this.maximumX;
	}
	
	/**
	 * Returns the maximum Y for this {@code Sampler} instance.
	 * 
	 * @return the maximum Y for this {@code Sampler} instance
	 */
	public final int getMaximumY() {
		return this.maximumY;
	}
	
	/**
	 * Returns the minimum X for this {@code Sampler} instance.
	 * 
	 * @return the minimum X for this {@code Sampler} instance
	 */
	public final int getMinimumX() {
		return this.minimumX;
	}
	
	/**
	 * Returns the minimum Y for this {@code Sampler} instance.
	 * 
	 * @return the minimum Y for this {@code Sampler} instance
	 */
	public final int getMinimumY() {
		return this.minimumY;
	}
	
	/**
	 * Returns the per-pixel sample count.
	 * <p>
	 * The per-pixel sample count is oftentimes known as Samples Per Pixel (SPP).
	 * 
	 * @return the per-pixel sample count
	 */
	public final int getPerPixelSampleCount() {
		return this.perPixelSampleCount;
	}
	
	/**
	 * Returns a copy of this {@code Sampler} instance.
	 * <p>
	 * A copied {@code Sampler} instance may or may not copy the value provided by the method {@code getCurrentSampleCount()}.
	 * 
	 * @return a copy of this {@code Sampler} instance
	 */
	public abstract Sampler copy();
	
	/**
	 * Returns a new {@code Sampler} for sub-sampling.
	 * 
	 * @param index the index for the new {@code Sampler}
	 * @param count the count of {@code Sampler}s being used
	 * @return a new {@code Sampler} for sub-sampling
	 */
	public final Sampler newSubSampler(final int index, final int count) {
		final Sampler sampler = copy();
		
		final int deltaX = this.maximumX - this.minimumX;
		final int deltaY = this.maximumY - this.minimumY;
		
		int numberX = index;
		int numberY = 1;
		
		while((numberX & 0x1) == 0 && 2 * deltaX * numberY < deltaY * numberX) {
			numberX >>= 1;
			numberY <<= 1;
		}
		
		final double originX = index % numberX;
		final double originY = index / numberX;
		final double fractionX0 = (originX + 0.0D) / numberX;
		final double fractionX1 = (originX + 1.0D) / numberX;
		final double fractionY0 = (originY + 0.0D) / numberY;
		final double fractionY1 = (originY + 1.0D) / numberY;
		
		final int maximumX = Interpolation.lerpToInt(sampler.minimumX, sampler.maximumX, fractionX1);
		final int maximumY = Interpolation.lerpToInt(sampler.minimumY, sampler.maximumY, fractionY1);
		final int minimumX = Interpolation.lerpToInt(sampler.minimumX, sampler.maximumX, fractionX0);
		final int minimumY = Interpolation.lerpToInt(sampler.minimumY, sampler.maximumY, fractionY0);
		
		sampler.setMaximumX(maximumX);
		sampler.setMaximumY(maximumY);
		sampler.setMinimumX(minimumX);
		sampler.setMinimumY(minimumY);
		
		return sampler;
	}
	
	/**
	 * Sets a new maximum X for this {@code Sampler} instance.
	 * <p>
	 * Returns this {@code Sampler} instance, such that method chaining is possible.
	 * <p>
	 * This method will call {@code reset()}.
	 * 
	 * @param maximumX the new maximum X
	 * @return this {@code Sampler} instance, such that method chaining is possible
	 */
	public final Sampler setMaximumX(final int maximumX) {
		this.maximumX = maximumX;
		
		reset();
		
		return this;
	}
	
	/**
	 * Sets a new maximum Y for this {@code Sampler} instance.
	 * <p>
	 * Returns this {@code Sampler} instance, such that method chaining is possible.
	 * <p>
	 * This method will call {@code reset()}.
	 * 
	 * @param maximumY the new maximum Y
	 * @return this {@code Sampler} instance, such that method chaining is possible
	 */
	public final Sampler setMaximumY(final int maximumY) {
		this.maximumY = maximumY;
		
		reset();
		
		return this;
	}
	
	/**
	 * Sets a new minimum X for this {@code Sampler} instance.
	 * <p>
	 * Returns this {@code Sampler} instance, such that method chaining is possible.
	 * <p>
	 * This method will call {@code reset()}.
	 * 
	 * @param minimumX the new minimum X
	 * @return this {@code Sampler} instance, such that method chaining is possible
	 */
	public final Sampler setMinimumX(final int minimumX) {
		this.minimumX = minimumX;
		
		reset();
		
		return this;
	}
	
	/**
	 * Sets a new minimum Y for this {@code Sampler} instance.
	 * <p>
	 * Returns this {@code Sampler} instance, such that method chaining is possible.
	 * <p>
	 * This method will call {@code reset()}.
	 * 
	 * @param minimumY the new minimum Y
	 * @return this {@code Sampler} instance, such that method chaining is possible
	 */
	public final Sampler setMinimumY(final int minimumY) {
		this.minimumY = minimumY;
		
		reset();
		
		return this;
	}
	
	/**
	 * Sets a new per-pixel sample count for this {@code Sampler} instance.
	 * <p>
	 * Returns this {@code Sampler} instance, such that method chaining is possible.
	 * <p>
	 * This method will call {@code reset()}.
	 * <p>
	 * The per-pixel sample count is oftentimes known as Samples Per Pixel (SPP).
	 * 
	 * @param perPixelSampleCount the new per-pixel sample count
	 * @return this {@code Sampler} instance, such that method chaining is possible
	 */
	public final Sampler setPerPixelSampleCount(final int perPixelSampleCount) {
		this.perPixelSampleCount = perPixelSampleCount;
		
		reset();
		
		return this;
	}
	
	/**
	 * Sets a new shutter close property for this {@code Sampler} instance.
	 * <p>
	 * Returns this {@code Sampler} instance, such that method chaining is possible.
	 * <p>
	 * This method will call {@code reset()}.
	 * 
	 * @param shutterClose the new shutter close property
	 * @return this {@code Sampler} instance, such that method chaining is possible
	 */
	public final Sampler setShutterClose(final double shutterClose) {
		this.shutterClose = shutterClose;
		
		reset();
		
		return this;
	}
	
	/**
	 * Sets a new shutter open property for this {@code Sampler} instance.
	 * <p>
	 * Returns this {@code Sampler} instance, such that method chaining is possible.
	 * <p>
	 * This method will call {@code reset()}.
	 * 
	 * @param shutterOpen the new shutter open property
	 * @return this {@code Sampler} instance, such that method chaining is possible
	 */
	public final Sampler setShutterOpen(final double shutterOpen) {
		this.shutterOpen = shutterOpen;
		
		reset();
		
		return this;
	}
	
	/**
	 * Resets this {@code Sampler} instance.
	 */
	protected abstract void reset();
}