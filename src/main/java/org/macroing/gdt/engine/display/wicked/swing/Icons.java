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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

final class Icons {
	private Icons() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Icon getTranslucentIcon(final JComponent jComponent, final Icon icon, final float red, final float green, final float blue) {
		if(icon != null) {
			final BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			
			final Composite composite = new ColorComposite(red, green, blue);
			
			final
			Graphics2D graphics2D = bufferedImage.createGraphics();
			graphics2D.setComposite(composite);
			
			icon.paintIcon(jComponent, graphics2D, 0, 0);
			
			graphics2D.dispose();
			
			return new ImageIcon(bufferedImage);
		}
		
		return icon;
	}
}