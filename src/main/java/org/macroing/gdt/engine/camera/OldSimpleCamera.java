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

//TODO: This OldSimpleCamera class will be replaced with the Camera class and its subclasses in the future.
public final class OldSimpleCamera extends SimpleCamera {
	private Vector lookAt = Vector.z().negate();
	private Vector right = Vector.x();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private OldSimpleCamera() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Ray newRay(final double u, final double v) {
		final Vector direction = this.right.copyAndMultiply(u).add(getUp().multiply(v)).add(this.lookAt);
		
		final Point origin = getEye().add(direction.toPoint().multiply(140.0D));
		
		return new Ray(0, origin, direction.copyAndNormalize());
	}
	
	public Vector getLookAt() {
		return this.lookAt;
	}
	
	public Vector getRight() {
		return this.right;
	}
	
	@Override
	public void calculateOrthonormalBasisFor(final double width, final double height) {
		this.right = new Vector(width * 0.5D / height, 0.0D, 0.0D);
		
		setUp(this.right.copyAndCrossProduct(this.lookAt).normalize().multiply(0.5D));
	}
	
	public void setLookAt(final Vector lookAt) {
		this.lookAt = Objects.requireNonNull(lookAt, "lookAt == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code OldSimpleCamera} instance.
	 * 
	 * @return a new {@code OldSimpleCamera} instance
	 */
	public static OldSimpleCamera newInstance() {
		return new OldSimpleCamera();
	}
}