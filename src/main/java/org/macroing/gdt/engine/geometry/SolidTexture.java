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

import java.lang.reflect.Field;//TODO: Fix this class and remove this comment once done. Add Javadocs etc.
import java.util.Objects;

public final class SolidTexture extends Texture {
	private final Spectrum spectrum;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private SolidTexture(final int width, final int height, final Spectrum spectrum) {
		super(width, height);
		
		this.spectrum = spectrum;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Spectrum getColorAt(final double u, final double v) {
		return this.spectrum.copy();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static SolidTexture newInstance(final int width, final int height, final Spectrum spectrum) {
		return new SolidTexture(width, height, Objects.requireNonNull(spectrum, "spectrum == null"));
	}
}