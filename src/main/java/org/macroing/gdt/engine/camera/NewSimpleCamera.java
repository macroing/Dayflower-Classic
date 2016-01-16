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

//TODO: This NewSimpleCamera class will be replaced with the Camera class and its subclasses in the future.
public final class NewSimpleCamera extends SimpleCamera {
	private double viewPlaneDistance = 800.0D;
	private Point lookAt = Point.zero();
	private Vector u = Vector.zero();
	private Vector v = Vector.zero();
	private Vector w = Vector.zero();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private NewSimpleCamera() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public double getViewPlaneDistance() {
		return this.viewPlaneDistance;
	}
	
	public Point getLookAt() {
		return this.lookAt;
	}
	
	@Override
	public Ray newRay(final double u, final double v) {
		final Vector direction = this.u.copyAndMultiply(u).add(this.v.copyAndMultiply(v)).subtract(this.w.copyAndMultiply(this.viewPlaneDistance)).normalize();
		
		final Point origin = getEye();
		
		return new Ray(0, origin, direction);
	}
	
	@Override
	public void calculateOrthonormalBasisFor(final double width, final double height) {
		this.w.set(getEye().subtract(this.lookAt).toVector());
		this.w.normalize();
		
		this.u.set(getUp().crossProduct(this.w));
		this.u.normalize();
		
		this.v.set(this.w.copyAndCrossProduct(this.u));
	}
	
	public void setLookAt(final Point lookAt) {
		this.lookAt = Objects.requireNonNull(lookAt, "lookAt == null");
	}
	
	public void setViewPlaneDistance(final double viewPlaneDistance) {
		this.viewPlaneDistance = viewPlaneDistance;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code NewSimpleCamera} instance.
	 * 
	 * @return a new {@code NewSimpleCamera} instance
	 */
	public static NewSimpleCamera newInstance() {
		return new NewSimpleCamera();
	}
}