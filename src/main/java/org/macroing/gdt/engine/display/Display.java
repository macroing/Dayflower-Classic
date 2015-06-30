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

import org.macroing.gdt.engine.configuration.Configuration;
import org.macroing.gdt.engine.configuration.ConfigurationObserver;

/**
 * An abstract base-class that defines the general contract for a display.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Display implements ConfigurationObserver {
	private Configuration configuration;
	private DisplayObserver displayObserver;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Display} instance.
	 */
	protected Display() {
		setConfiguration(Configuration.getDefaultInstance());
		setDisplayObserver((pixelIterable, consumer, booleanSupplier) -> {});
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Configuration} currently assigned to this {@code Display} instance.
	 * <p>
	 * By default {@code Configuration.getDefaultInstance()} is assigned.
	 * 
	 * @return the {@code Configuration} currently assigned to this {@code Display} instance
	 */
	public final Configuration getConfiguration() {
		return this.configuration;
	}
	
	/**
	 * Returns the {@link DisplayObserver} currently assigned to this {@code Display} instance.
	 * <p>
	 * By default an empty {@code DisplayObserver} is assigned.
	 * 
	 * @return the {@code DisplayObserver} currently assigned to this {@code Display} instance
	 */
	public final DisplayObserver getDisplayObserver() {
		return this.displayObserver;
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
	 * Called by a {@link Configuration} instance when it's been updated.
	 * <p>
	 * If {@code configuration} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param configuration the {@code Configuration} that was updated prior to this method call
	 * @throws NullPointerException thrown if, and only if, {@code configuration} is {@code null}
	 */
	@Override
	public final void onUpdate(final Configuration configuration) {
		configure();
	}
	
	/**
	 * Renders this {@code Display} instance.
	 */
	public abstract void render();
	
	/**
	 * Sets a new {@link Configuration} to this {@code Display} instance.
	 * <p>
	 * If {@code configuration} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * By default an empty {@code Configuration.getDefaultInstance()} is assigned.
	 * 
	 * @param configuration the new {@code Configuration}
	 * @throws NullPointerException thrown if, and only if, {@code configuration} is {@code null}
	 */
	public final void setConfiguration(final Configuration configuration) {
		if(this.configuration != null) {
			this.configuration.removeConfigurationObserver(this);
		}
		
		this.configuration = Objects.requireNonNull(configuration, "configuration == null");
		this.configuration.addConfigurationObserver(this);
	}
	
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
		this.displayObserver = Objects.requireNonNull(displayObserver, "displayObserver == null");
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