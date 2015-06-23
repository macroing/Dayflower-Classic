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
package org.macroing.gdt.engine.sampler;

import java.lang.reflect.Field;//TODO: Fix this class and remove this comment once done. Add Javadocs etc.
import java.util.ArrayList;
import java.util.List;

/**
 * A {@code Sample} with information such as time and U-, V-, X- and Y-coordinates.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Sample {
	private double time;
	private double u;
	private double v;
	private double weight = 1.0D;
	private double x;
	private double y;
	private final List<double[]> listOf1Ds = new ArrayList<>();
	private final List<double[]> listOf2Ds = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Sample() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the time set for this {@code Sample} instance.
	 * 
	 * @return the time set for this {@code Sample} instance
	 */
	public double getTime() {
		return this.time;
	}
	
	/**
	 * Returns the U-coordinate set for this {@code Sample} instance.
	 * 
	 * @return the U-coordinate set for this {@code Sample} instance
	 */
	public double getU() {
		return this.u;
	}
	
	/**
	 * Returns the V-coordinate set for this {@code Sample} instance.
	 * 
	 * @return the V-coordinate set for this {@code Sample} instance
	 */
	public double getV() {
		return this.v;
	}
	
	/**
	 * Returns the weight set for this {@code Sample} instance.
	 * <p>
	 * By default {@code 1.0D} will be returned.
	 * 
	 * @return the weight set for this {@code Sample} instance
	 */
	public double getWeight() {
		return this.weight;
	}
	
	/**
	 * Returns the X-coordinate set for this {@code Sample} instance.
	 * 
	 * @return the X-coordinate set for this {@code Sample} instance
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Returns the Y-coordinate set for this {@code Sample} instance.
	 * 
	 * @return the Y-coordinate set for this {@code Sample} instance
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Returns the {@code double} array of the 1D samples given an index.
	 * <p>
	 * If {@code index} is out of range, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the array to get
	 * @return the {@code double} array of the 1D samples given an index
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is out of range
	 */
	public double[] get1D(final int index) {
		return this.listOf1Ds.get(index);
	}
	
	/**
	 * Returns the {@code double} array of the 2D samples given an index.
	 * <p>
	 * If {@code index} is out of range, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the array to get
	 * @return the {@code double} array of the 2D samples given an index
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is out of range
	 */
	public double[] get2D(final int index) {
		return this.listOf2Ds.get(index);
	}
	
	/**
	 * Adds an array of {@code double}s that can later store sample information.
	 * <p>
	 * Returns the index for the added array, such that it's possible to retrieve it at a later time.
	 * <p>
	 * The length of the array with the sample data will be {@code size * 1}.
	 * 
	 * @param size the sample size
	 * @return the index for the added array, such that it's possible to retrieve it at a later time
	 */
	public int add1D(final int size) {
		this.listOf1Ds.add(new double[size]);
		
		return this.listOf1Ds.size() - 1;
	}
	
	/**
	 * Adds an array of {@code double}s that can later store sample information.
	 * <p>
	 * Returns the index for the added array, such that it's possible to retrieve it at a later time.
	 * <p>
	 * The length of the array with the sample data will be {@code size * 2}.
	 * 
	 * @param size the sample size
	 * @return the index for the added array, such that it's possible to retrieve it at a later time
	 */
	public int add2D(final int size) {
		this.listOf2Ds.add(new double[size * 2]);
		
		return this.listOf2Ds.size() - 1;
	}
	
	/**
	 * Returns the size of the 1D sample list.
	 * 
	 * @return the size of the 1D sample list
	 */
	public int size1D() {
		return this.listOf1Ds.size();
	}
	
	/**
	 * Returns the size of the 2D sample list.
	 * 
	 * @return the size of the 2D sample list
	 */
	public int size2D() {
		return this.listOf2Ds.size();
	}
	
	/**
	 * Sets the time for this {@code Sample} instance.
	 * <p>
	 * Returns this {@code Sample} instance, such that method chaining is possible.
	 * 
	 * @param time the new time
	 * @return this {@code Sample} instance, such that method chaining is possible
	 */
	public Sample setTime(final double time) {
		this.time = time;
		
		return this;
	}
	
	/**
	 * Sets the U-coordinate for this {@code Sample} instance.
	 * <p>
	 * Returns this {@code Sample} instance, such that method chaining is possible.
	 * 
	 * @param u the new U-coordinate
	 * @return this {@code Sample} instance, such that method chaining is possible
	 */
	public Sample setU(final double u) {
		this.u = u;
		
		return this;
	}
	
	/**
	 * Sets the V-coordinate for this {@code Sample} instance.
	 * <p>
	 * Returns this {@code Sample} instance, such that method chaining is possible.
	 * 
	 * @param v the new V-coordinate
	 * @return this {@code Sample} instance, such that method chaining is possible
	 */
	public Sample setV(final double v) {
		this.v = v;
		
		return this;
	}
	
	/**
	 * Sets the weight for this {@code Sample} instance.
	 * <p>
	 * Returns this {@code Sample} instance, such that method chaining is possible.
	 * <p>
	 * By default, the weight will be set to {@code 1.0D}.
	 * 
	 * @param weight the new weight
	 * @return this {@code Sample} instance, such that method chaining is possible
	 */
	public Sample setWeight(final double weight) {
		this.weight = weight;
		
		return this;
	}
	
	/**
	 * Sets the X-coordinate for this {@code Sample} instance.
	 * <p>
	 * Returns this {@code Sample} instance, such that method chaining is possible.
	 * 
	 * @param x the new X-coordinate
	 * @return this {@code Sample} instance, such that method chaining is possible
	 */
	public Sample setX(final double x) {
		this.x = x;
		
		return this;
	}
	
	/**
	 * Sets the Y-coordinate for this {@code Sample} instance.
	 * <p>
	 * Returns this {@code Sample} instance, such that method chaining is possible.
	 * 
	 * @param y the new Y-coordinate
	 * @return this {@code Sample} instance, such that method chaining is possible
	 */
	public Sample setY(final double y) {
		this.y = y;
		
		return this;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Sample} instance.
	 * 
	 * @return a {@code String} representation of this {@code Sample} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getClass().getSimpleName());
		stringBuilder.append(" ");
		stringBuilder.append("X=" + getX());
		stringBuilder.append(" ");
		stringBuilder.append("Y=" + getY());
		stringBuilder.append(" ");
		stringBuilder.append("U=" + getU());
		stringBuilder.append(" ");
		stringBuilder.append("V=" + getV());
		stringBuilder.append(" ");
		stringBuilder.append("Time=" + getTime());
		
		final String string = stringBuilder.toString();
		
		return string;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code Sample} instance.
	 * 
	 * @return a new {@code Sample} instance
	 */
	public static Sample newInstance() {
		return new Sample();
	}
}