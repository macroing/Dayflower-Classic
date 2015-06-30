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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JPanel;

import org.macroing.gdt.engine.configuration.Configuration;

final class BufferedImageJPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicReference<BufferedImage> bufferedImage = new AtomicReference<>();
	private final AtomicReference<Configuration> configuration = new AtomicReference<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BufferedImageJPanel() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void configure() {
		setPreferredSize(new Dimension(this.configuration.get().getWidth(), this.configuration.get().getHeight()));
		setLayout(new LayoutManagerImpl());
	}
	
	@Override
	public void paintComponent(final Graphics graphics) {
		final Configuration configuration = this.configuration.get();
		
		if(configuration == null) {
			return;
		}
		
		final BufferedImage bufferedImage = this.bufferedImage.get();
		
		if(bufferedImage == null) {
			return;
		}
		
		final Graphics2D graphics2D = Graphics2D.class.cast(graphics);
		
		if(configuration.isRenderingInRealtime()) {
			graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		} else {
			graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		}
		
		graphics2D.drawImage(bufferedImage, 0, 0, configuration.getWidth(), configuration.getHeight(), null);
	}
	
	public void setBufferedImage(final BufferedImage bufferedImage) {
		this.bufferedImage.set(Objects.requireNonNull(bufferedImage, "bufferedImage == null"));
	}
	
	public void setConfiguration(final Configuration configuration) {
		this.configuration.set(Objects.requireNonNull(configuration, "configuration == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static BufferedImageJPanel newInstance() {
		return new BufferedImageJPanel();
	}
}