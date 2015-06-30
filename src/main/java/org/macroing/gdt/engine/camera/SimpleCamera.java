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

//TODO: This SimpleCamera class will be replaced with the Camera class and its subclasses in the future.
public final class SimpleCamera implements ConfigurationObserver {
	private Configuration configuration = Configuration.getDefaultInstance();
	private Point eye = Point.zero();
//	private Point lookAt = Point.zero();//New
	private Vector lookAt = Vector.z().negate();//Old
	private Vector right = Vector.x();//Old
//	private Vector u = Vector.zero();//New
	private Vector up = Vector.y();
//	private Vector v = Vector.zero();//New
//	private Vector w = Vector.zero();//New
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private SimpleCamera() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	public Point getEye() {
		return this.eye.copy();
	}
	
	public Vector getLookAt() {//Old
//	public Point getLookAt() {//New
		return this.lookAt;
	}
	
	public Ray newRay(final double u, final double v) {
//		Old:
		
		final Vector direction = this.right.copyAndMultiply(u).add(this.up.copyAndMultiply(v)).add(this.lookAt);
		
		final Point origin = this.eye.copyAndAdd(direction.toPoint().multiply(140.0D));
		
		return new Ray(0, origin, direction.copyAndNormalize());
		
//		New:
		
//		final Vector direction = this.u.copyAndMultiply(u).add(this.v.copyAndMultiply(v)).subtract(w.copyAndMultiply(1.0D)).normalize();
		
//		final Point origin = eye.copy();
		
//		return new Ray(0, origin, direction);
	}
	
	public Vector getRight() {//Old
		return this.right;
	}
	
	public Vector getUp() {
		return this.up;
	}
	
	public void calculateOrthonormalBasis() {
		calculateOrthonormalBasisFor(this.configuration.getWidthScaled(), this.configuration.getHeightScaled());
	}
	
	public void calculateOrthonormalBasisFor(final double width, final double height) {
//		Old:
		
		this.right = new Vector(width * 0.5D / height, 0.0D, 0.0D);
		this.up = this.right.copyAndCrossProduct(this.lookAt).normalize().multiply(0.5D);
		
//		New:
		
//		this.w.set(this.eye.copyAndSubtract(this.lookAt).toVector());
//		this.w.normalize();
		
//		this.u.set(this.up.copyAndCrossProduct(this.w));
//		this.u.normalize();
		
//		this.v.set(this.w.copyAndCrossProduct(this.u));
		
//		System.out.println("Right: " + new Vector(this.width * 0.5D / this.height, 0.0D, 0.0D));
//		System.out.println("Up: " + new Vector(this.width * 0.5D / this.height, 0.0D, 0.0D).copyAndCrossProduct(Vector.z().negate()).normalize().multiply(140.0D));
//		System.out.println("U: " + this.u);
//		System.out.println("V: " + this.v);
//		System.out.println("W: " + this.w);
	}
	
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
		this.eye = Objects.requireNonNull(eye, "eye == null");
	}
	
	public void setLookAt(final Vector lookAt) {//Old
//	public void setLookAt(final Point lookAt) {//New
		this.lookAt = Objects.requireNonNull(lookAt, "lookAt == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code SimpleCamera} instance.
	 * 
	 * @return a new {@code SimpleCamera} instance
	 */
	public static SimpleCamera newInstance() {
		return new SimpleCamera();
	}
}