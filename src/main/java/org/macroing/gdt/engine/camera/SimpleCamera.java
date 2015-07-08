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
package org.macroing.gdt.engine.camera;

import java.lang.reflect.Field;//TODO: Fix this class and remove this comment once done. Add Javadocs etc.
import java.util.Objects;

import org.macroing.gdt.engine.configuration.Configuration;
import org.macroing.gdt.engine.configuration.ConfigurationObserver;
import org.macroing.gdt.engine.geometry.Point;
import org.macroing.gdt.engine.geometry.Ray;
import org.macroing.gdt.engine.geometry.Vector;

//TODO: This SimpleCamera interface will be replaced with the Camera class and its subclasses in the future.
public abstract class SimpleCamera implements ConfigurationObserver {
	private Configuration configuration;
	private final Point eye = Point.zero();
	private final Vector up = Vector.y();
	
	protected SimpleCamera() {
		setConfiguration(Configuration.getDefaultInstance());
	}
	
	public final Configuration getConfiguration() {
		return this.configuration;
	}
	
	public final Point getEye() {
		return this.eye.copy();
	}
	
	public abstract Ray newRay(final double u, final double v);
	
	public final Vector getUp() {
		return this.up.copy();
	}
	
	public final void calculateOrthonormalBasis() {
		calculateOrthonormalBasisFor(this.configuration.getWidthScaled(), this.configuration.getHeightScaled());
	}
	
	public abstract void calculateOrthonormalBasisFor(final double width, final double height);
	
	@Override
	public void onUpdate(final Configuration configuration) {
		calculateOrthonormalBasis();
	}
	
	public void setConfiguration(final Configuration configuration) {
		if(this.configuration != null) {
			this.configuration.removeConfigurationObserver(this);
		}
		
		this.configuration = Objects.requireNonNull(configuration, "configuration == null");
		this.configuration.addConfigurationObserver(this);
	}
	
	public void setEye(final Point eye) {
		this.eye.set(eye);
	}
	
	public void setUp(final Vector up) {
		this.up.set(up);
	}
}