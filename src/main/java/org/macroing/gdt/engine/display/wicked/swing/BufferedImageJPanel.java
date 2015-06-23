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

import javax.swing.JPanel;

final class BufferedImageJPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final boolean isRenderingInRealtime;
	private final BufferedImage bufferedImage;
	private final int height;
	private final int width;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BufferedImageJPanel(final BufferedImage bufferedImage, final boolean isRenderingInRealtime, final int width, final int height) {
		this.bufferedImage = bufferedImage;
		this.isRenderingInRealtime = isRenderingInRealtime;
		this.width = width;
		this.height = height;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void paintComponent(final Graphics graphics) {
		final Graphics2D graphics2D = Graphics2D.class.cast(graphics);
		
		if(this.isRenderingInRealtime) {
			graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		} else {
			graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		}
		
		graphics2D.drawImage(this.bufferedImage, 0, 0, this.width, this.height, null);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static BufferedImageJPanel newInstance(final BufferedImage bufferedImage, final boolean isRenderingInRealtime, final int width, final int height) {
		final
		BufferedImageJPanel bufferedImageJPanel = new BufferedImageJPanel(bufferedImage, isRenderingInRealtime, width, height);
		bufferedImageJPanel.setLayout(new LayoutManagerImpl());
		bufferedImageJPanel.setPreferredSize(new Dimension(width, height));
		
		return bufferedImageJPanel;
	}
}