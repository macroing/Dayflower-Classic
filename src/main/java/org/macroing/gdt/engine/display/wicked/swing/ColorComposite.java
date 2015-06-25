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

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

final class ColorComposite implements Composite {
	private final float blue;
	private final float green;
	private final float red;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ColorComposite(final float red, final float green, final float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public CompositeContext createContext(final ColorModel sourceColorModel, final ColorModel destinationColorModel, final RenderingHints renderingHints) {
		return new CompositeContextImpl(this.red, this.green, this.blue);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class CompositeContextImpl implements CompositeContext {
		private final float red;
		private final float green;
		private final float blue;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		CompositeContextImpl(final float red, final float green, final float blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public void compose(final Raster sourceRaster, final Raster destinationRaster, final WritableRaster destinationWritableRaster) {
			final int width = destinationWritableRaster.getWidth();
			final int height = destinationWritableRaster.getHeight();
			final int numBands = sourceRaster.getNumBands();
			
			final float[] sourceRasterPixel = new float[numBands];
			final float[] destinationRasterPixel = new float[numBands];
			final float[] destinationWritableRasterPixel = new float[numBands];
			
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					sourceRaster.getPixel(x, y, sourceRasterPixel);
					destinationRaster.getPixel(x, y, destinationRasterPixel);
					destinationWritableRaster.getPixel(x, y, destinationWritableRasterPixel);
					
					System.arraycopy(sourceRasterPixel, 0, destinationWritableRasterPixel, 0, destinationWritableRasterPixel.length);
					
					destinationWritableRasterPixel[0] = this.red;
					destinationWritableRasterPixel[1] = this.green;
					destinationWritableRasterPixel[2] = this.blue;
					
					destinationWritableRaster.setPixel(x, y, destinationWritableRasterPixel);
				}
			}
		}
		
		@Override
		public void dispose() {
			
		}
	}
}