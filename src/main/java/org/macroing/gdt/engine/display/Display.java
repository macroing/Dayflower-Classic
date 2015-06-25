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

import java.util.List;
import java.util.Objects;

/**
 * An abstract base-class that defines the general contract for a display.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Display {
	private boolean isRenderingInRealtime;
	private boolean isSuperSamplingWithDownScaling;
	private DisplayObserver displayObserver = (pixelIterable, consumer) -> {};
	private int heightScale = 1;
	private int widthScale = 1;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Display} instance.
	 */
	protected Display() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, this {@code Display} is rendering in realtime, {@code false} otherwise.
	 * <p>
	 * The configuration for a {@code Display} rendering in realtime is usually different from one that is not.
	 * 
	 * @return {@code true} if, and only if, this {@code Display} is rendering in realtime, {@code false} otherwise
	 */
	public final boolean isRenderingInRealtime() {
		return this.isRenderingInRealtime;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Display} is rendering super-sampling with down-scaling, {@code false} otherwise.
	 * <p>
	 * The effect of this property should only be applied if {@code isRenderingInRealtime()} returns {@code false}.
	 * 
	 * @return {@code true} if, and only if, this {@code Display} is rendering super-sampling with down-scaling, {@code false} otherwise
	 */
	public final boolean isSuperSamplingWithDownScaling() {
		return this.isSuperSamplingWithDownScaling;
	}
	
	/**
	 * Returns the {@link DisplayObserver} currently assigned to this {@code Display} instance.
	 * <p>
	 * By default an empty {@code DisplayObserver} is assigned.
	 * 
	 * @return the {@link DisplayObserver} currently assigned to this {@code Display} instance
	 */
	public final DisplayObserver getDisplayObserver() {
		return this.displayObserver;
	}
	
	/**
	 * Returns the height currently set for this {@code Display} instance.
	 * 
	 * @return the height currently set for this {@code Display} instance
	 */
	public abstract int getHeight();
	
	/**
	 * Returns the height scale currently set for this {@code Display} instance.
	 * <p>
	 * The default height scale is {@code 1}. That means no height scaling will be performed.
	 * <p>
	 * Height scales are positive, which means that a height scale of {@code 2} is half the height.
	 * <p>
	 * Given a height of {@code 600} and a height scale of {@code 2}, the first thing that happens is that the height of the image to render to is divided by the height scale. The result in this case is {@code 300}. At the time the image is displayed,
	 * the current height of {@code 300} is multiplied by the height scale to get back to the initial height of {@code 600}. All this essentially means that less processing power will be needed to construct the image. But what's drawn to the screen will
	 * look like big blocks. The higher the height scale, the bigger the blocks.
	 * <p>
	 * What's been mentioned above was for the height only. But the same applies to the width.
	 * 
	 * @return the height scale currently set for this {@code Display} instance
	 */
	public final int getHeightScale() {
		return this.heightScale;
	}
	
	/**
	 * Returns the width currently set for this {@code Display} instance.
	 * 
	 * @return the width currently set for this {@code Display} instance
	 */
	public abstract int getWidth();
	
	/**
	 * Returns the width scale currently set for this {@code Display} instance.
	 * <p>
	 * The default width scale is {@code 1}. That means no width scaling will be performed.
	 * <p>
	 * Width scales are positive, which means that a width scale of {@code 2} is half the width.
	 * <p>
	 * Given a width of {@code 800} and a width scale of {@code 2}, the first thing that happens is that the width of the image to render to is divided by the width scale. The result in this case is {@code 400}. At the time the image is displayed, the
	 * current width of {@code 400} is multiplied by the width scale to get back to the initial width of {@code 800}. All this essentially means that less processing power will be needed to construct the image. But what's drawn to the screen will look
	 * like big blocks. The higher the width scale, the bigger the blocks.
	 * <p>
	 * What's been mentioned above was for the width only. But the same applies to the height.
	 * 
	 * @return the width scale currently set for this {@code Display} instance
	 */
	public final int getWidthScale() {
		return this.widthScale;
	}
	
	/**
	 * Returns a {@code List} with all currently assigned {@link PixelIterable} instances.
	 * <p>
	 * Modifying the returned {@code List} should not affect this {@code Display} instance.
	 * 
	 * @return a {@code List} with all currently assigned {@code PixelIterable} instances
	 */
	public abstract List<PixelIterable> getPixelIterables();
	
	/**
	 * Returns the {@link PixelIterable} with the given index.
	 * <p>
	 * If {@code index} is invalid, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the {@code PixelIterable} to return
	 * @return the {@code PixelIterable} with the given index
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is invalid
	 */
	public abstract PixelIterable getPixelIterableAt(final int index);
	
	/**
	 * Call this method to clear what's been rendered so far.
	 */
	public abstract void clear();
	
	/**
	 * Configures this {@code Display} instance.
	 * <p>
	 * This method needs to be called after you have changed any properties of this {@code Display} instance.
	 */
	public abstract void configure();
	
	/**
	 * Hides this {@code Display} instance.
	 * <p>
	 * In addition to hiding itself, it should also:
	 * <ul>
	 * <li>Stop all internal execution, such as periodic updates.</li>
	 * <li>Dispose of any used resources.</li>
	 * </ul>
	 * <p>
	 * If this method is called while in a hidden state, nothing should happen.
	 */
	public abstract void hide();
	
	/**
	 * Renders this {@code Display} instance.
	 */
	public abstract void render();
	
	/**
	 * Sets a new {@link DisplayObserver} to this {@code Display} instance.
	 * <p>
	 * If {@code displayObserver} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * By default an empty {@code DisplayObserver} is assigned.
	 * 
	 * @param displayObserver the new {@code DisplayObserver}
	 * @throws NullPointerException thrown if, and only if, {@code displayObserver} is {@code null}
	 */
	public final void setDisplayObserver(final DisplayObserver displayObserver) {
		this.displayObserver = Objects.requireNonNull(displayObserver, "displayObserver");
	}
	
	/**
	 * Sets a new height for this {@code Display} instance.
	 * 
	 * @param height the new height
	 */
	public abstract void setHeight(final int height);
	
	/**
	 * Sets a new height scale for this {@code Display} instance.
	 * <p>
	 * The default height scale is {@code 1}. That means no height scaling will be performed.
	 * <p>
	 * Height scales are positive, which means that a height scale of {@code 2} is half the height.
	 * <p>
	 * Given a height of {@code 600} and a height scale of {@code 2}, the first thing that happens is that the height of the image to render to is divided by the height scale. The result in this case is {@code 300}. At the time the image is displayed,
	 * the current height of {@code 300} is multiplied by the height scale to get back to the initial height of {@code 600}. All this essentially means that less processing power will be needed to construct the image. But what's drawn to the screen will
	 * look like big blocks. The higher the height scale, the bigger the blocks.
	 * <p>
	 * What's been mentioned above was for the height only. But the same applies to the width.
	 * 
	 * @param heightScale the new height scale
	 */
	public final void setHeightScale(final int heightScale) {
		this.heightScale = heightScale;
	}
	
	/**
	 * Sets a new realtime rendering property for this {@code Display} instance.
	 * <p>
	 * The configuration for a {@code Display} rendering in realtime is usually different from one that is not.
	 * 
	 * @param isRenderingInRealtime the new realtime rendering property
	 */
	public final void setRenderingInRealtime(final boolean isRenderingInRealtime) {
		this.isRenderingInRealtime = isRenderingInRealtime;
	}
	
	/**
	 * Sets the resolution for this {@code Display} instance.
	 * 
	 * @param width the width part of the resolution
	 * @param height the height part of the resolution
	 */
	public final void setResolution(final int width, final int height) {
		setWidth(width);
		setHeight(height);
	}
	
	/**
	 * Sets a new super-sampling with down-scaling property for this {@code Display} instance.
	 * <p>
	 * The effect of this property should only be applied if {@code isRenderingInRealtime()} returns {@code false}.
	 * 
	 * @param isSuperSamplingWithDownScaling the new super-sampling with down-scaling property
	 */
	public final void setSuperSamplingWithDownScaling(final boolean isSuperSamplingWithDownScaling) {
		this.isSuperSamplingWithDownScaling = isSuperSamplingWithDownScaling;
	}
	
	/**
	 * Sets the title for this {@code Display} instance.
	 * <p>
	 * If {@code title} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * Not all {@code Display}s support a title. In that case, it's up to the implementation at hand to decide what to do. Some implementations may decide to do nothing, whereas others may throw an {@code UnsupportedOperationException}. The action should,
	 * however, be documented.
	 * 
	 * @param title the title for this {@code Display} instance
	 * @throws NullPointerException thrown if, and only if, {@code title} is {@code null}
	 * @throws UnsupportedOperationException thrown if, and only if, this method is not supported by the {@code Display} instance
	 */
	public abstract void setTitle(final String title);
	
	/**
	 * Sets a new width for this {@code Display} instance.
	 * 
	 * @param width the new width
	 */
	public abstract void setWidth(final int width);
	
	/**
	 * Sets a new width scale for this {@code Display} instance.
	 * <p>
	 * The default width scale is {@code 1}. That means no width scaling will be performed.
	 * <p>
	 * Width scales are positive, which means that a width scale of {@code 2} is half the width.
	 * <p>
	 * Given a width of {@code 800} and a width scale of {@code 2}, the first thing that happens is that the width of the image to render to is divided by the width scale. The result in this case is {@code 400}. At the time the image is displayed, the
	 * current width of {@code 400} is multiplied by the width scale to get back to the initial width of {@code 800}. All this essentially means that less processing power will be needed to construct the image. But what's drawn to the screen will look
	 * like big blocks. The higher the width scale, the bigger the blocks.
	 * <p>
	 * What's been mentioned above was for the width only. But the same applies to the height.
	 * 
	 * @param widthScale the new width scale
	 */
	public final void setWidthScale(final int widthScale) {
		this.widthScale = widthScale;
	}
	
	/**
	 * Shows this {@code Display} instance.
	 * <p>
	 * In addition to showing itself, it should also:
	 * <ul>
	 * <li>Start all internal execution, such as periodic updates.</li>
	 * </ul>
	 * <p>
	 * If this method is called while in a shown state, nothing should happen.
	 */
	public abstract void show();
}