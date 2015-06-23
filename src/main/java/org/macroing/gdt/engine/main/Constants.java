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
package org.macroing.gdt.engine.main;

final class Constants {
	public static final int DEFAULT_HEIGHT = 768;
	public static final int DEFAULT_HEIGHT_SCALE_FOR_QUALITY = 1;
	public static final int DEFAULT_HEIGHT_SCALE_FOR_SPEED = 4;
	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_WIDTH_SCALE_FOR_QUALITY = 1;
	public static final int DEFAULT_WIDTH_SCALE_FOR_SPEED = 4;
	public static final String ORGANIZATION_NAME = "Macroing.org";
	public static final String PROJECT_CATEGORY_NAME = "GDT";
	public static final String PROJECT_NAME_EXTERNAL = "Dayflower";
	public static final String PROJECT_NAME_INTERNAL = "Engine";
	public static final String VERSION = "0.1-beta";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static boolean isRenderingInRealtime = false;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Constants() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static boolean isRenderingInRealtime() {
		return isRenderingInRealtime;
	}
	
	public static boolean isSkippingProbabilisticallyTerminatingRay() {
		return isRenderingInRealtime();
	}
	
	public static int getDefaultHeight() {
		return DEFAULT_HEIGHT;
	}
	
	public static int getDefaultHeightScale() {
		return isRenderingInRealtime() ? DEFAULT_HEIGHT_SCALE_FOR_SPEED : DEFAULT_HEIGHT_SCALE_FOR_QUALITY;
	}
	
	public static int getDefaultHeightScaled() {
		return isRenderingInRealtime() ? DEFAULT_HEIGHT / DEFAULT_HEIGHT_SCALE_FOR_SPEED : DEFAULT_HEIGHT / DEFAULT_HEIGHT_SCALE_FOR_QUALITY;
	}
	
	public static int getDefaultWidth() {
		return DEFAULT_WIDTH;
	}
	
	public static int getDefaultWidthScale() {
		return isRenderingInRealtime() ? DEFAULT_WIDTH_SCALE_FOR_SPEED : DEFAULT_WIDTH_SCALE_FOR_QUALITY;
	}
	
	public static int getDefaultWidthScaled() {
		return isRenderingInRealtime() ? DEFAULT_WIDTH / DEFAULT_WIDTH_SCALE_FOR_SPEED : DEFAULT_WIDTH / DEFAULT_WIDTH_SCALE_FOR_QUALITY;
	}
	
	public static int getDepthUntilProbabilisticallyTerminatingRay() {
		return isRenderingInRealtime() ? 2 : 5;
	}
	
	public static String getTitle() {
		return String.format("%s %s %s v.%s - %s", ORGANIZATION_NAME, PROJECT_CATEGORY_NAME, PROJECT_NAME_INTERNAL, VERSION, PROJECT_NAME_EXTERNAL);
	}
	
	public static void setRenderingInRealtime(final boolean isRenderingInRealtime) {
		Constants.isRenderingInRealtime = isRenderingInRealtime;
	}
}