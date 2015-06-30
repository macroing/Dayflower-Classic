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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * An {@code Iterable} that iterates over {@link Pixel}s.
 * <p>
 * It's usually done in a seemingly random fashion. Although, the randomness comes from the creation of the {@code PixelIterable}s, and not for each iteration. So it's actually very sequential and deterministic.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PixelIterable implements Iterable<Pixel> {
	private static int currentIndex = 0;
	private final int height;
	private final int width;
	private final Map<Integer, Pixel> pixelMap;
	private final Pixel[] pixels;
	private int index = currentIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PixelIterable(final int width, final int height, final Map<Integer, Pixel> pixelMap, final Pixel[] pixels) {
		this.width = width;
		this.height = height;
		this.pixelMap = pixelMap;
		this.pixels = pixels;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, a {@link Pixel} can be found on the given X- and Y-axes on the screen, {@code false} otherwise.
	 * 
	 * @param x the position along the X-axis on the screen
	 * @param y the position along the Y-axis on the screen
	 * @return {@code true} if, and only if, a {@code Pixel} can be found on the given X- and Y-axes on the screen, {@code false} otherwise
	 */
	public boolean hasPixel(final int x, final int y) {
		return hasPixel(y * this.width + x);
	}
	
	/**
	 * Returns {@code true} if, and only if, a {@link Pixel} can be found given an index, {@code false} otherwise.
	 * 
	 * @param index the index of the {@code Pixel} to check for
	 * @return {@code true} if, and only if, a {@code Pixel} can be found given an index, {@code false} otherwise
	 */
	public boolean hasPixel(final int index) {
		return this.pixelMap.containsKey(Integer.valueOf(index));
	}
	
	/**
	 * Returns the height of the screen that was used in the creation process.
	 * 
	 * @return the height of the screen that was used in the creation process
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Returns the width of the screen that was used in the creation process.
	 * 
	 * @return the width of the screen that was used in the creation process
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Returns the size of this {@code PixelIterable} instance.
	 * 
	 * @return the size of this {@code PixelIterable} instance
	 */
	public int size() {
		return this.pixels.length;
	}
	
	/**
	 * Returns an {@code Iterator} over elements of type {@link Pixel}.
	 * 
	 * @return an {@code Iterator} over elements of type {@code Pixel}
	 */
	@Override
	public Iterator<Pixel> iterator() {
		return PixelIterator.newInstance(this.pixels);
	}
	
	/**
	 * Returns the {@link Pixel} given the index {@code index}, or {@code null} if it does not exist.
	 * 
	 * @param index the index of the {@code Pixel} to get
	 * @return the {@code Pixel} given the index {@code index}, or {@code null} if it does not exist
	 */
	public Pixel getPixel(final int index) {
		return this.pixelMap.get(Integer.valueOf(index));
	}
	
	/**
	 * Returns the {@link Pixel} given its position along the X- and Y-axes, or {@code null} if it does not exist.
	 * 
	 * @param x the position along the X-axis
	 * @param y the position along the Y-axis
	 * @return the {@code Pixel} given its position along the X- and Y-axes, or {@code null} if it does not exist
	 */
	public Pixel getPixel(final int x, final int y) {
		return this.pixelMap.get(Integer.valueOf(y * this.width + x));
	}
	
	public int getIndex() {
		return this.index;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} of {@code PixelIterable}s given a screen width, a screen height, an underlying RGB-data array and the size of the returned {@code List}.
	 * <p>
	 * If {@code rGB} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code rGB.length % size != 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * By specifying the {@code size} variable to some valid value other than {@code 1}, this method will create separate {@code PixelIterable}s. Each one can be used by different {@code Thread}s to iterate over all {@link Pixel}s in a uniformly
	 * distributed and seemingly random fashion. Each iteration is not random, although it will look like it to the eye, because there are a lot of things going on all the time.
	 * 
	 * @param width the width of the screen
	 * @param height the height of the screen
	 * @param rGB the underlying RGB-data array
	 * @param size the size of the returned {@code List}
	 * @return a {@code List} of {@code PixelIterable}s given a screen width, a screen height, an underlying RGB-data array and the size of the returned {@code List}
	 * @throws IllegalArgumentException thrown if, and only if, {@code rGB.length % size != 0}
	 * @throws NullPointerException thrown if, and only if, {@code rGB} is {@code null}
	 */
	public static List<PixelIterable> createPixelIterablesFor(final int width, final int height, final int[] rGB, final int size) {
		if(rGB.length % size == 0) {
			final int sizeOfPixelArray = rGB.length / size;
			
			final List<Pixel> pixelList = Pixel.createPixelsFor(width, height, rGB);
			final List<PixelIterable> pixelIterableList = new ArrayList<>(size);
			
			Collections.shuffle(pixelList);
			
			for(int i = 0, j = 0; i < size; i++) {
				final Map<Integer, Pixel> pixelMap = new LinkedHashMap<>();
				
				final Pixel[] pixelArray = new Pixel[sizeOfPixelArray];
				
				for(int k = 0; k < sizeOfPixelArray; j++, k++) {
					final Pixel pixel = pixelList.get(j);
					
					pixelMap.put(Integer.valueOf(pixel.getIndex()), pixel);
					
					pixelArray[k] = pixel;
				}
				
				final PixelIterable pixelIterable = new PixelIterable(width, height, pixelMap, pixelArray);
				
				pixelIterableList.add(pixelIterable);
			}
			currentIndex++;
			
			return pixelIterableList;
		}
		
		throw new IllegalArgumentException("rGB.length % 8 != 0");
	}
	
	/**
	 * Returns an empty {@code PixelIterable} instance.
	 * <p>
	 * It can be used to create a default value, so that no {@code NullPointerException}s are thrown because you forgot to initialize it correctly.
	 * 
	 * @return an empty {@code PixelIterable} instance
	 */
	public static PixelIterable empty() {
		return new PixelIterable(0, 0, new LinkedHashMap<>(), new Pixel[0]);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class PixelIterator implements Iterator<Pixel> {
		private int index;
		private final Pixel[] pixels;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private PixelIterator(final Pixel[] pixels) {
			this.pixels = pixels;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public boolean hasNext() {
			return this.index < this.pixels.length;
		}
		
		@Override
		public Pixel next() {
			if(this.index < this.pixels.length) {
				return this.pixels[this.index++];
			}
			
			throw new NoSuchElementException();
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public static PixelIterator newInstance(final Pixel[] pixels) {
			return new PixelIterator(pixels);
		}
	}
}