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
package org.macroing.gdt.engine.display;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.gdt.engine.geometry.RGBSpectrum;
import org.macroing.gdt.engine.util.Functions;

/**
 * A {@code Pixel} contains various useful information about a given pixel on the screen.
 * <p>
 * It's used by an instance of {@link Display} to display a rendered image in one way or another.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Pixel {
	private final int index;
	private int subSamples;
	private final int x;
	private final int y;
	private final int[] rGB;
	private final RGBSpectrum rGBSpectrum = RGBSpectrum.black();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Pixel(final int index, final int x, final int y, final int[] rGB) {
		this.index = index;
		this.x = x;
		this.y = y;
		this.rGB = rGB;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the index of this {@code Pixel}.
	 * 
	 * @return the index of this {@code Pixel}
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Returns the current sub-sample count for this {@code Pixel}.
	 * <p>
	 * Sub-sampling are mainly used when rendering with Ray Tracing.
	 * 
	 * @return the current sub-sample count for this {@code Pixel}
	 */
	public int getSubSamples() {
		return this.subSamples;
	}
	
	/**
	 * Returns the position along the X-axis of the screen.
	 * 
	 * @return the position along the X-axis of the screen
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Returns the position along the Y-axis of the screen.
	 * 
	 * @return the position along the Y-axis of the screen
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Returns the current {@link RGBSpectrum} for this {@code Pixel}.
	 * 
	 * @return the current {@code RGBSpectrum} for this {@code Pixel}
	 */
	public RGBSpectrum getRGBSpectrum() {
		return this.rGBSpectrum;
	}
	
	/**
	 * Adds {@code subSamples} to the sub-sample count of this {@code Pixel}.
	 * <p>
	 * Sub-sampling are mainly used when rendering with Ray Tracing.
	 * 
	 * @param subSamples the sub-sample count to add
	 */
	public void addSubSamples(final int subSamples) {
		this.subSamples += subSamples;
	}
	
	/**
	 * Clears this {@code Pixel} instance.
	 * <p>
	 * The {@link RGBSpectrum} will be set to {@code RGBSpectrum.black()} and the sub-sample count will be set to {@code 0}.
	 */
	public void clear() {
		this.subSamples = 0;
		this.rGBSpectrum.set(RGBSpectrum.black());
	}
	
	/**
	 * Sets a new sub-sample count for this {@code Pixel}.
	 * <p>
	 * Sub-sampling are mainly used when rendering with Ray Tracing.
	 * 
	 * @param subSamples the new sub-sample count
	 */
	public void setSubSamples(final int subSamples) {
		this.subSamples = subSamples;
	}
	
	/**
	 * Updates the underlying array of RGB-data with the data provided by this {@code Pixel} instance.
	 */
	public void update() {
		final double subSamples = this.subSamples;
		final double subSamplesReciprocal = 1.0D / subSamples;
		
		final int r = Functions.toInt(Functions.performGammaCorrectionFor(this.rGBSpectrum.getRed() * subSamplesReciprocal));
		final int g = Functions.toInt(Functions.performGammaCorrectionFor(this.rGBSpectrum.getGreen() * subSamplesReciprocal));
		final int b = Functions.toInt(Functions.performGammaCorrectionFor(this.rGBSpectrum.getBlue() * subSamplesReciprocal));
		
		final int rGB = ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
		
		this.rGB[this.index] = rGB;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} of {@code Pixel}s given a width, a height and the underlying RGB-data array.
	 * <p>
	 * If {@code rGB} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param width the width of the screen
	 * @param height the height of the screen
	 * @param rGB the underlying RGB-data array
	 * @return a {@code List} of {@code Pixel}s given a width, a height and the underlying RGB-data array
	 * @throws NullPointerException thrown if, and only if, {@code rGB} is {@code null}
	 */
	public static List<Pixel> createPixelsFor(final int width, final int height, final int[] rGB) {
		final int length = rGB.length;
		
		final List<Pixel> pixels = new ArrayList<>(length);
		
		for(int i = 0; i < length; i++) {
			final int x = i % width;
			final int y = i / width;
			final int index = (height - y - 1) * width + x;
			
			final Pixel pixel = newInstance(index, x, y, rGB);
			
			pixels.add(pixel);
		}
		
		return pixels;
	}
	
	/**
	 * Returns a new {@code Pixel} instance, given an index, its position along the X- and Y-axes on the screen and the underlying array of RGB-data.
	 * <p>
	 * If either {@code index}, {@code x} or {@code y} are less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code rGB} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param index the index of the {@code Pixel}
	 * @param x the position along the X-axis for the {@code Pixel}
	 * @param y the position along the Y-axis for the {@code Pixel}
	 * @param rGB the underlying array of RGB-data
	 * @return a new {@code Pixel} instance, given an index, its position along the X- and Y-axes on the screen and the underlying array of RGB-data
	 * @throws IllegalArgumentException thrown if, and only if, either {@code index}, {@code x} or {@code y} are less than {@code 0}
	 * @throws NullPointerException thrown if, and only if, {@code rGB} is {@code null}
	 */
	public static Pixel newInstance(final int index, final int x, final int y, final int[] rGB) {
		return new Pixel(Functions.requireRange(index, 0, Integer.MAX_VALUE, "index < 0: " + index), Functions.requireRange(x, 0, Integer.MAX_VALUE, "x < 0: " + x), Functions.requireRange(y, 0, Integer.MAX_VALUE, "y < 0: " + y), Objects.requireNonNull(rGB, "rGB == null"));
	}
}