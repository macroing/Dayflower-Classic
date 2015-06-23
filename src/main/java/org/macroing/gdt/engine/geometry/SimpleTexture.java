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
package org.macroing.gdt.engine.geometry;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Fix this class and remove this comment once done. Add Javadocs etc.

import javax.imageio.ImageIO;

public final class SimpleTexture extends Texture {
	private final int[] data;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private SimpleTexture(final int width, final int height, final int[] data) {
		super(width, height);
		
		this.data = data;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Spectrum getColorAt(final double u, final double v) {
//		System.out.printf("U=%f, V=%f, W=%d, H=%d, X=%d, Y=%d%n", u, v, getWidth(), getHeight(), (int)(getWidth() * (u * u * 0.5D)), (int)(getHeight() * (v * v * 0.5D)));
		
		final int width = getWidth();
		final int height = getHeight();
		final int x = (int)(width * ((u + 1.0D) * 0.5D));
		final int y = (int)(height * ((v + 1.0D) * 0.5D));
		final int index = y * width + x;
		final int rGB = index >= 0 && index < this.data.length ? this.data[index] : 0;
		
		final double red = RGBSpectrum.toRed(rGB) / 256.0D;
		final double green = RGBSpectrum.toGreen(rGB) / 256.0D;
		final double blue = RGBSpectrum.toBlue(rGB) / 256.0D;
		
		return new RGBSpectrum(red, green, blue);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static SimpleTexture newInstance(final File file) {
		final BufferedImage bufferedImage = doCreateBufferedImageFrom(file);
		
		final int width = bufferedImage.getWidth();
		final int height = bufferedImage.getHeight();
		
		final int[] data = doGetDataFrom(bufferedImage);
		
		return new SimpleTexture(width, height, data);
	}
	
	public static SimpleTexture newInstance(final String fileName) {
		return newInstance(new File(fileName));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static BufferedImage doCreateBufferedImageFrom(final File file) {
		try {
			BufferedImage bufferedImage0 = ImageIO.read(file);
			
			if(bufferedImage0.getType() != BufferedImage.TYPE_INT_RGB) {
				final BufferedImage bufferedImage1 = new BufferedImage(bufferedImage0.getWidth(), bufferedImage0.getHeight(), BufferedImage.TYPE_INT_RGB);
				
				final
				Graphics2D graphics2D = bufferedImage1.createGraphics();
				graphics2D.drawImage(bufferedImage0, 0, 0, null);
				graphics2D.dispose();
				
				bufferedImage0 = bufferedImage1;
			}
			
			return bufferedImage0;
		} catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private static int[] doGetDataFrom(final BufferedImage bufferedImage) {
		final WritableRaster writableRaster = bufferedImage.getRaster();
		
		final DataBuffer dataBuffer = writableRaster.getDataBuffer();
		
		final DataBufferInt dataBufferInt = DataBufferInt.class.cast(dataBuffer);
		
		final int[] data = dataBufferInt.getData();
		
		return data;
	}
}