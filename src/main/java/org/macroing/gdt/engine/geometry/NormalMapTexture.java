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

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;//TODO: Fix this class and remove this comment once done. Add Javadocs etc.
import java.util.Objects;

public final class NormalMapTexture extends Texture {
	private final int[] data;
	private final Texture texture;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private NormalMapTexture(final int width, final int height, final int[] data, final Texture texture) {
		super(width, height);
		
		this.data = data;
		this.texture = Objects.requireNonNull(texture, "texture == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Spectrum getColorAt(final double u, final double v) {
		return this.texture.getColorAt(u, v);
	}
	
	@Override
	public Vector getSurfaceNormalAt(final double u, final double v, final Vector surfaceNormal) {
		final int width = getWidth();
		final int height = getHeight();
		final int x = (int)(width * ((u + 1.0D) * 0.5D));
		final int y = (int)(height * ((v + 1.0D) * 0.5D));
		final int index = y * width + x;
		final int rGB = this.data[index];
		
		final double red = RGBSpectrum.toRed(rGB);// / 256.0D;
		final double green = RGBSpectrum.toGreen(rGB);// / 256.0D;
		final double blue = RGBSpectrum.toBlue(rGB);// / 256.0D;
		
//		return surfaceNormal.copyAndMultiply(Vector.valueOf(red, green, blue).normalize());
//		return surfaceNormal;
		return surfaceNormal.copyAndCrossProduct(Vector.valueOf(red, green, blue)).normalize();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static NormalMapTexture newInstance(final Texture texture, final File file) {
		final BufferedImage bufferedImage = BufferedImages.createBufferedImageFrom(file);
		
		final int width = bufferedImage.getWidth();
		final int height = bufferedImage.getHeight();
		
		final int[] data = BufferedImages.getDataFrom(bufferedImage);
		
		return new NormalMapTexture(width, height, data, texture);
	}
	
	public static NormalMapTexture newInstance(final Texture texture, final String fileName) {
		return newInstance(texture, new File(fileName));
	}
}