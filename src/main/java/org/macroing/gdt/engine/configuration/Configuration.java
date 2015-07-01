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
package org.macroing.gdt.engine.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.macroing.gdt.engine.util.Lock;
import org.macroing.gdt.engine.util.Ranges;

/**
 * This {@code Configuration} class contains all the configuration parameters for the entire engine.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Configuration {
	private static final Configuration DEFAULT_INSTANCE = new Configuration();
	private static final int DEFAULT_DEPTH_UNTIL_PROBABILISTICALLY_TERMINATING_RAY = 5;
	private static final int DEFAULT_HEIGHT = 768;
	private static final int DEFAULT_HEIGHT_SCALE_FOR_QUALITY = 1;
	private static final int DEFAULT_HEIGHT_SCALE_FOR_QUALITY_AND_SUPERSAMPLING_WITH_DOWNSCALING = 2;
	private static final int DEFAULT_HEIGHT_SCALE_FOR_SPEED = 4;
	private static final int DEFAULT_WIDTH = 1024;
	private static final int DEFAULT_WIDTH_SCALE_FOR_QUALITY = 1;
	private static final int DEFAULT_WIDTH_SCALE_FOR_QUALITY_AND_SUPERSAMPLING_WITH_DOWNSCALING = 2;
	private static final int DEFAULT_WIDTH_SCALE_FOR_SPEED = 4;
	private static final String ORGANIZATION_NAME = "Macroing.org";
	private static final String PROJECT_CATEGORY_NAME = "GDT";
	private static final String PROJECT_NAME_EXTERNAL = "Dayflower";
	private static final String PROJECT_NAME_INTERNAL = "Engine";
	private static final String VERSION = "0.1-beta";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicBoolean isRenderingInRealtime = new AtomicBoolean();
	private final AtomicBoolean isSkippingProbabilisticallyTerminatingRay = new AtomicBoolean();
	private final AtomicBoolean isSupersamplingWithDownscaling = new AtomicBoolean();
	private final AtomicInteger depthUntilProbabilisticallyTerminatingRay = new AtomicInteger(DEFAULT_DEPTH_UNTIL_PROBABILISTICALLY_TERMINATING_RAY);
	private final AtomicInteger height = new AtomicInteger(DEFAULT_HEIGHT);
	private final AtomicInteger heightScaleForQuality = new AtomicInteger(DEFAULT_HEIGHT_SCALE_FOR_QUALITY);
	private final AtomicInteger heightScaleForQualityAndSupersamplingWithDownscaling = new AtomicInteger(DEFAULT_HEIGHT_SCALE_FOR_QUALITY_AND_SUPERSAMPLING_WITH_DOWNSCALING);
	private final AtomicInteger heightScaleForSpeed = new AtomicInteger(DEFAULT_HEIGHT_SCALE_FOR_SPEED);
	private final AtomicInteger width = new AtomicInteger(DEFAULT_WIDTH);
	private final AtomicInteger widthScaleForQuality = new AtomicInteger(DEFAULT_WIDTH_SCALE_FOR_QUALITY);
	private final AtomicInteger widthScaleForQualityAndSupersamplingWithDownscaling = new AtomicInteger(DEFAULT_WIDTH_SCALE_FOR_QUALITY_AND_SUPERSAMPLING_WITH_DOWNSCALING);
	private final AtomicInteger widthScaleForSpeed = new AtomicInteger(DEFAULT_WIDTH_SCALE_FOR_SPEED);
	private final AtomicReference<String> title = new AtomicReference<>(String.format("%s %s %s v.%s - %s", ORGANIZATION_NAME, PROJECT_CATEGORY_NAME, PROJECT_NAME_INTERNAL, VERSION, PROJECT_NAME_EXTERNAL));
	private final List<ConfigurationObserver> configurationObservers = new CopyOnWriteArrayList<>();
	private final Lock lock = new Lock();//TODO: Finish locking for all parts in this class.
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Configuration() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isConfiguring() {
		return this.lock.isLocked();
	}
	
	public boolean isRenderingInRealtime() {
		return this.lock.getIfUnlocked(() -> Boolean.valueOf(this.isRenderingInRealtime.get())).booleanValue();
	}
	
	public boolean isSkippingProbabilisticallyTerminatingRay() {
		return this.lock.getIfUnlocked(() -> Boolean.valueOf(this.isSkippingProbabilisticallyTerminatingRay.get())).booleanValue();
	}
	
	public boolean isSupersamplingWithDownscaling() {
		return this.lock.getIfUnlocked(() -> Boolean.valueOf(this.isSupersamplingWithDownscaling.get())).booleanValue();
	}
	
	public int getDepthUntilProbabilisticallyTerminatingRay() {
		return this.lock.getIfUnlocked(() -> Integer.valueOf(this.depthUntilProbabilisticallyTerminatingRay.get())).intValue();
	}
	
	public int getHeight() {
		return this.lock.getIfUnlocked(() -> Integer.valueOf(this.height.get())).intValue();
	}
	
	public int getHeightScale() {
		return isRenderingInRealtime() ? getHeightScaleForSpeed() : isSupersamplingWithDownscaling() ? getHeightScaleForQualityAndSupersamplingWithDownscaling() : getHeightScaleForQuality();
	}
	
	public int getHeightScaled() {
		return isRenderingInRealtime() ? getHeight() / getHeightScale() : isSupersamplingWithDownscaling() ? getHeight() * getHeightScale() : getHeight() / getHeightScale();
	}
	
	public int getHeightScaleForQuality() {
		return this.lock.getIfUnlocked(() -> Integer.valueOf(this.heightScaleForQuality.get())).intValue();
	}
	
	public int getHeightScaleForQualityAndSupersamplingWithDownscaling() {
		return this.lock.getIfUnlocked(() -> Integer.valueOf(this.heightScaleForQualityAndSupersamplingWithDownscaling.get())).intValue();
	}
	
	public int getHeightScaleForSpeed() {
		return this.lock.getIfUnlocked(() -> Integer.valueOf(this.heightScaleForSpeed.get())).intValue();
	}
	
	public int getWidth() {
		return this.lock.getIfUnlocked(() -> Integer.valueOf(this.width.get())).intValue();
	}
	
	public int getWidthScale() {
		return isRenderingInRealtime() ? getWidthScaleForSpeed() : isSupersamplingWithDownscaling() ? getWidthScaleForQualityAndSupersamplingWithDownscaling() : getWidthScaleForQuality();
	}
	
	public int getWidthScaled() {
		return isRenderingInRealtime() ? getWidth() / getWidthScale() : isSupersamplingWithDownscaling() ? getWidth() * getWidthScale() : getWidth() / getWidthScale();
	}
	
	public int getWidthScaleForQuality() {
		return this.lock.getIfUnlocked(() -> Integer.valueOf(this.widthScaleForQuality.get())).intValue();
	}
	
	public int getWidthScaleForQualityAndSupersamplingWithDownscaling() {
		return this.lock.getIfUnlocked(() -> Integer.valueOf(this.widthScaleForQualityAndSupersamplingWithDownscaling.get())).intValue();
	}
	
	public int getWidthScaleForSpeed() {
		return this.lock.getIfUnlocked(() -> Integer.valueOf(this.widthScaleForSpeed.get())).intValue();
	}
	
	public List<ConfigurationObserver> getConfigurationObservers() {
		return this.lock.getIfUnlocked(() -> new ArrayList<>(this.configurationObservers));
	}
	
	public String getTitle() {
		return this.lock.getIfUnlocked(() -> this.title.get());
	}
	
	public void addConfigurationObserver(final ConfigurationObserver configurationObserver) {
		if(!this.configurationObservers.contains(configurationObserver)) {
			this.configurationObservers.add(Objects.requireNonNull(configurationObserver, "configurationObserver == null"));
		}
	}
	
	public void removeConfigurationObserver(final ConfigurationObserver configurationObserver) {
		this.configurationObservers.remove(Objects.requireNonNull(configurationObserver, "configurationObserver == null"));
	}
	
	public void setDepthUntilProbabilisticallyTerminatingRay(final int depthUntilProbabilisticallyTerminatingRay) {
		this.depthUntilProbabilisticallyTerminatingRay.set(Ranges.requireRange(depthUntilProbabilisticallyTerminatingRay, 0, Integer.MAX_VALUE));
		
		doNotifyOfOnUpdate();
	}
	
	public void setHeight(final int height) {
		this.height.set(Ranges.requireRange(height, 0, Integer.MAX_VALUE));
		
		doNotifyOfOnUpdate();
	}
	
	public void setHeightScaleForQuality(final int heightScaleForQuality) {
		this.heightScaleForQuality.set(Ranges.requireRange(heightScaleForQuality, 0, Integer.MAX_VALUE));
		
		doNotifyOfOnUpdate();
	}
	
	public void setHeightScaleForQualityAndSupersamplingWithDownscaling(final int heightScaleForQualityAndSupersamplingWithDownscaling) {
		this.heightScaleForQualityAndSupersamplingWithDownscaling.set(Ranges.requireRange(heightScaleForQualityAndSupersamplingWithDownscaling, 0, Integer.MAX_VALUE));
		
		doNotifyOfOnUpdate();
	}
	
	public void setHeightScaleForSpeed(final int heightScaleForSpeed) {
		this.heightScaleForSpeed.set(Ranges.requireRange(heightScaleForSpeed, 0, Integer.MAX_VALUE));
		
		doNotifyOfOnUpdate();
	}
	
	public void setRenderingInRealtime(final boolean isRenderingInRealtime) {
		this.isRenderingInRealtime.set(isRenderingInRealtime);
		
		doNotifyOfOnUpdate();
	}
	
	public void setSkippingProbabilisticallyTerminatingRay(final boolean isSkippingProbabilisticallyTerminatingRay) {
		this.isSkippingProbabilisticallyTerminatingRay.set(isSkippingProbabilisticallyTerminatingRay);
		
		doNotifyOfOnUpdate();
	}
	
	public void setSupersamplingWithDownscaling(final boolean isSupersamplingWithDownscaling) {
		this.isSupersamplingWithDownscaling.set(isSupersamplingWithDownscaling);
		
		doNotifyOfOnUpdate();
	}
	
	public void setTitle(final String title) {
		this.title.set(Objects.requireNonNull(title, "title == null"));
		
		doNotifyOfOnUpdate();
	}
	
	public void setWidth(final int width) {
		this.width.set(Ranges.requireRange(width, 0, Integer.MAX_VALUE));
		
		doNotifyOfOnUpdate();
	}
	
	public void setWidthScaleForQuality(final int widthScaleForQuality) {
		this.widthScaleForQuality.set(Ranges.requireRange(widthScaleForQuality, 0, Integer.MAX_VALUE));
		
		doNotifyOfOnUpdate();
	}
	
	public void setWidthScaleForQualityAndSupersamplingWithDownscaling(final int widthScaleForQualityAndSupersamplingWithDownscaling) {
		this.widthScaleForQualityAndSupersamplingWithDownscaling.set(Ranges.requireRange(widthScaleForQualityAndSupersamplingWithDownscaling, 0, Integer.MAX_VALUE));
		
		doNotifyOfOnUpdate();
	}
	
	public void setWidthScaleForSpeed(final int widthScaleForSpeed) {
		this.widthScaleForSpeed.set(Ranges.requireRange(widthScaleForSpeed, 0, Integer.MAX_VALUE));
		
		doNotifyOfOnUpdate();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the default {@code Configuration} instance.
	 * 
	 * @return the default {@code Configuration} instance
	 */
	public static Configuration getDefaultInstance() {
		return DEFAULT_INSTANCE;
	}
	
	/**
	 * Returns a new {@code Configuration} instance.
	 * 
	 * @return a new {@code Configuration} instance
	 */
	public static Configuration newInstance() {
		return new Configuration();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doNotifyOfOnUpdate() {
		this.configurationObservers.forEach(configurationObserver -> configurationObserver.onUpdate(this));
	}
}