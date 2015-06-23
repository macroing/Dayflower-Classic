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
package org.macroing.gdt.engine.geometry;

import java.util.Objects;

import org.macroing.gdt.engine.util.Functions;

/**
 * An abstract base-class that models the spectrum of light.
 * <p>
 * This class is mutable and therefore not suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Spectrum {
	private final double[] coefficients;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Spectrum} instance given the coefficients to use.
	 * <p>
	 * If {@code coefficients} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param coefficients the {@code double} array with the coefficients to use
	 * @throws NullPointerException thrown if, and only if, {@code coefficients} is {@code null}
	 */
	protected Spectrum(final double... coefficients) {
		this.coefficients = Objects.requireNonNull(coefficients);
	}
	
	/**
	 * Constructs a new {@code Spectrum} instance given the number of coefficients to use.
	 * <p>
	 * If {@code coefficients} is less than {@code 0}, a {@code NegativeArraySizeException} will be thrown.
	 * 
	 * @param coefficients the number of coefficients to use for this {@code Spectrum} instance
	 * @throws NegativeArraySizeException thrown if, and only if, {@code coefficients} is less than {@code 0}
	 */
	protected Spectrum(final int coefficients) {
		this.coefficients = new double[coefficients];
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, at least one of the provided coefficients contains a value equal to {@code Double.NaN}, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, at least one of the provided coefficients contains a value equal to {@code Double.NaN}, {@code false} otherwise
	 */
	public final boolean hasNaNs() {
		boolean hasNaNs = false;
		
		for(final double coefficient : this.coefficients) {
			if(Double.isNaN(coefficient)) {
				hasNaNs = true;
				
				break;
			}
		}
		
		return hasNaNs;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Spectrum} is considered to be entirely black, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Spectrum} is considered to be entirely black, {@code false} otherwise
	 */
	public abstract boolean isBlack();
	
	/**
	 * Returns {@code true} if, and only if, this {@code Spectrum} is brighter than {@code spectrum}, {@code false} otherwise.
	 * <p>
	 * The {@code Spectrum} with the highest luminance value (as per the {@code getLuminance()} method), is said to be the brightest.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param spectrum the {@code Spectrum} to compare this {@code Spectrum} to for brightness
	 * @return {@code true} if, and only if, this {@code Spectrum} is brighter than {@code spectrum}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final boolean isBrighterThan(final Spectrum spectrum) {
		return getLuminance() > spectrum.getLuminance();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Spectrum} is darker than {@code spectrum}, {@code false} otherwise.
	 * <p>
	 * The {@code Spectrum} with the lowest luminance value (as per the {@code getLuminance()} method), is said to be the darkest.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param spectrum the {@code Spectrum} to compare this {@code Spectrum} to for darkness
	 * @return {@code true} if, and only if, this {@code Spectrum} is darker than {@code spectrum}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final boolean isDarkerThan(final Spectrum spectrum) {
		return getLuminance() < spectrum.getLuminance();
	}
	
	/**
	 * Returns the {@code double} value that represents the coefficient at index {@code index}.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@code getCoefficientCount()}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the coefficient value to get
	 * @return the {@code double} value that represents the coefficient at index {@code index}
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getCoefficientCount()}
	 */
	public final double getCoefficient(final int index) {
		return this.coefficients[index];
	}
	
	/**
	 * Returns the luminance of this {@code Spectrum} instance.
	 * <p>
	 * The luminance of a {@code Spectrum} determines how bright or dark it is.
	 * 
	 * @return the luminance of this {@code Spectrum} instance
	 */
	public abstract double getLuminance();
	
	/**
	 * Returns a {@code double} array with the coefficients of this {@code Spectrum} instance in XYZ-form.
	 * <p>
	 * This method does not alter the {@code Spectrum} instance on which it is called.
	 * 
	 * @return a {@code double} array with the coefficients of this {@code Spectrum} instance in XYZ-form
	 */
	public abstract double[] toXYZ();
	
	/**
	 * Returns the coefficient count of this {@code Spectrum} instance.
	 * 
	 * @return the coefficient count of this {@code Spectrum} instance
	 */
	public final int getCoefficientCount() {
		return this.coefficients.length;
	}
	
	/**
	 * Adds {@code scalar} to all coefficients of this {@code Spectrum} instance.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndAdd(scalar)} instead.
	 * 
	 * @param scalar the scalar to add to all coefficients of this {@code Spectrum} instance
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 */
	public final Spectrum add(final double scalar) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] += scalar;
		}
		
		return this;
	}
	
	/**
	 * Adds the coefficients provided by {@code spectrum} to this {@code Spectrum} instance.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndAdd(spectrum)} instead.
	 * 
	 * @param spectrum the {@code Spectrum} with the coefficients to add to this {@code Spectrum} instance
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum add(final Spectrum spectrum) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] += spectrum.coefficients[i];
		}
		
		return this;
	}
	
	/**
	 * Clamps this {@code Spectrum} instance to the range {@code 0.0D} (inclusive) and {@code Double.POSITIVE_INFINITY} (inclusive).
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndClamp()} instead.
	 * 
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 */
	public final Spectrum clamp() {
		return clamp(0.0D, Double.POSITIVE_INFINITY);
	}
	
	/**
	 * Clamps this {@code Spectrum} instance to the range {@code low} (inclusive) and {@code high} (inclusive).
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndClamp(low, high)} instead.
	 * 
	 * @param low the lowest value of the range to clamp this {@code Spectrum} instance to
	 * @param high the highest value of the range to clamp this {@code Spectrum} instance to
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 */
	public final Spectrum clamp(final double low, final double high) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] = Functions.clamp(this.coefficients[i], low, high);
		}
		
		return this;
	}
	
	/**
	 * Returns a copy of this {@code Spectrum} instance.
	 * 
	 * @return a copy of this {@code Spectrum} instance
	 */
	public abstract Spectrum copy();
	
	/**
	 * Copies and adds {@code scalar} to all coefficients of the new {@code Spectrum} instance.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code add(scalar)} instead.
	 * 
	 * @param scalar the scalar to add to all coefficients of the new {@code Spectrum} instance
	 * @return a copy of this {@code Spectrum} instance
	 */
	public final Spectrum copyAndAdd(final double scalar) {
		return copy().add(scalar);
	}
	
	/**
	 * Copies and adds the coefficients provided by {@code spectrum} to the new {@code Spectrum} instance.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of the new {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code add(spectrum)} instead.
	 * 
	 * @param spectrum the {@code Spectrum} with the coefficients to add to the new {@code Spectrum} instance
	 * @return a copy of this {@code Spectrum} instance
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of the new {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum copyAndAdd(final Spectrum spectrum) {
		return copy().add(spectrum);
	}
	
	/**
	 * Copies and clamps the new {@code Spectrum} instance to the range {@code 0.0D} (inclusive) and {@code Double.POSITIVE_INFINITY} (inclusive).
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code clamp()} instead.
	 * 
	 * @return a copy of this {@code Spectrum} instance
	 */
	public final Spectrum copyAndClamp() {
		return copy().clamp();
	}
	
	/**
	 * Copies and clamps the new {@code Spectrum} instance to the range {@code low} (inclusive) and {@code high} (inclusive).
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code clamp(low, high)} instead.
	 * 
	 * @param low the lowest value of the range to clamp the new {@code Spectrum} instance to
	 * @param high the highest value of the range to clamp the new {@code Spectrum} instance to
	 * @return a copy of this {@code Spectrum} instance
	 */
	public final Spectrum copyAndClamp(final double low, final double high) {
		return copy().clamp(low, high);
	}
	
	/**
	 * Copies and divides all coefficients of the new {@code Spectrum} instance with {@code scalar}.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code divide(scalar)} instead.
	 * 
	 * @param scalar the scalar to divide all coefficients of the new {@code Spectrum} instance with
	 * @return a copy of this {@code Spectrum} instance
	 */
	public final Spectrum copyAndDivide(final double scalar) {
		return copy().divide(scalar);
	}
	
	/**
	 * Copies and divides the coefficients provided by the new {@code Spectrum} instance with {@code spectrum}.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of the new {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code divide(spectrum)} instead.
	 * 
	 * @param spectrum the {@code Spectrum} with the coefficients to divide the new {@code Spectrum} instance with
	 * @return a copy of this {@code Spectrum} instance
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of the new {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum copyAndDivide(final Spectrum spectrum) {
		return copy().divide(spectrum);
	}
	
	/**
	 * Copies and performs {@code Math.exp(coefficient)} on all coefficients of the new {@code Spectrum} instance.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code exp()} instead.
	 * 
	 * @return a copy of this {@code Spectrum} instance
	 */
	public final Spectrum copyAndExp() {
		return copy().exp();
	}
	
	/**
	 * Copies and multiplies all coefficients of the new {@code Spectrum} instance with {@code scalar}.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code multiply(scalar)} instead.
	 * 
	 * @param scalar the scalar to multiply all coefficients of the new {@code Spectrum} instance with
	 * @return a copy of this {@code Spectrum} instance
	 */
	public final Spectrum copyAndMultiply(final double scalar) {
		return copy().multiply(scalar);
	}
	
	/**
	 * Copies and multiplies the coefficients provided by the new {@code Spectrum} instance with {@code spectrum}.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of the new {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code multiply(spectrum)} instead.
	 * 
	 * @param spectrum the {@code Spectrum} with the coefficients to multiply the new {@code Spectrum} instance with
	 * @return a copy of this {@code Spectrum} instance
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of the new {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum copyAndMultiply(final Spectrum spectrum) {
		return copy().multiply(spectrum);
	}
	
	/**
	 * Copies and performs negation on all coefficients of the new {@code Spectrum} instance.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code negate()} instead.
	 * 
	 * @return a copy of this {@code Spectrum} instance
	 */
	public final Spectrum copyAndNegate() {
		return copy().negate();
	}
	
	/**
	 * Copies and performs {@code Math.pow(coefficient, power)} on all coefficients of the new {@code Spectrum} instance.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code pow(power)} instead.
	 * 
	 * @param power the second parameter argument to {@code Math.pow(double, double)}
	 * @return a copy of this {@code Spectrum} instance
	 */
	public final Spectrum copyAndPow(final double power) {
		return copy().pow(power);
	}
	
	/**
	 * Copies and performs {@code Math.pow(coefficient, spectrum.getCoefficient(index))} on all coefficients provided by the new {@code Spectrum} instance.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of the new {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code pow(spectrum)} instead.
	 * 
	 * @param spectrum the second parameter argument to {@code Math.pow(double, double)} for each coefficient
	 * @return a copy of this {@code Spectrum} instance
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of the new {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum copyAndPow(final Spectrum spectrum) {
		return copy().pow(spectrum);
	}
	
	/**
	 * Copies and performs {@code Math.sqrt(coefficient)} on all coefficients of the new {@code Spectrum} instance.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code sqrt()} instead.
	 * 
	 * @return a copy of this {@code Spectrum} instance
	 */
	public final Spectrum copyAndSqrt() {
		return copy().sqrt();
	}
	
	/**
	 * Copies and subtracts {@code scalar} from all coefficients of the new {@code Spectrum} instance.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code subtract(scalar)} instead.
	 * 
	 * @param scalar the scalar to subtract from all coefficients of the new {@code Spectrum} instance
	 * @return a copy of this {@code Spectrum} instance
	 */
	public final Spectrum copyAndSubtract(final double scalar) {
		return copy().subtract(scalar);
	}
	
	/**
	 * Copies and subtracts the coefficients provided by {@code spectrum} from the new {@code Spectrum} instance.
	 * <p>
	 * Returns a copy of this {@code Spectrum} instance.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of the new {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of a copy of the instance on which it is called. If you don't want this behavior, consider calling {@code subtract(spectrum)} instead.
	 * 
	 * @param spectrum the {@code Spectrum} with the coefficients to subtract from the new {@code Spectrum} instance
	 * @return a copy of this {@code Spectrum} instance
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of the new {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum copyAndSubtract(final Spectrum spectrum) {
		return copy().subtract(spectrum);
	}
	
	/**
	 * Divides all coefficients of this {@code Spectrum} instance with {@code scalar}.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndDivide(scalar)} instead.
	 * 
	 * @param scalar the scalar to divide all coefficients of this {@code Spectrum} instance with
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 */
	public final Spectrum divide(final double scalar) {
		return multiply(1.0D / scalar);
	}
	
	/**
	 * Divides the coefficients provided by this {@code Spectrum} instance with {@code spectrum}.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndDivide(spectrum)} instead.
	 * 
	 * @param spectrum the {@code Spectrum} with the coefficients to divide this {@code Spectrum} instance with
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum divide(final Spectrum spectrum) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] /= spectrum.coefficients[i];
		}
		
		return this;
	}
	
	/**
	 * Performs {@code Math.exp(coefficient)} on all coefficients of this {@code Spectrum} instance.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndExp()} instead.
	 * 
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 */
	public final Spectrum exp() {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] = Math.exp(this.coefficients[i]);
		}
		
		return this;
	}
	
	/**
	 * Multiplies all coefficients of this {@code Spectrum} instance with {@code scalar}.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndMultiply(scalar)} instead.
	 * 
	 * @param scalar the scalar to multiply all coefficients of this {@code Spectrum} instance with
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 */
	public final Spectrum multiply(final double scalar) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] *= scalar;
		}
		
		return this;
	}
	
	/**
	 * Multiplies the coefficients provided by this {@code Spectrum} instance with {@code spectrum}.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndMultiply(spectrum)} instead.
	 * 
	 * @param spectrum the {@code Spectrum} with the coefficients to multiply this {@code Spectrum} instance with
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum multiply(final Spectrum spectrum) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] *= spectrum.coefficients[i];
		}
		
		return this;
	}
	
	/**
	 * Performs negation on all coefficients of this {@code Spectrum} instance.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndNegate()} instead.
	 * 
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 */
	public final Spectrum negate() {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] = -this.coefficients[i];
		}
		
		return this;
	}
	
	/**
	 * Performs {@code Math.pow(coefficient, power)} on all coefficients of this {@code Spectrum} instance.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndPow(power)} instead.
	 * 
	 * @param power the second parameter argument to {@code Math.pow(double, double)}
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 */
	public final Spectrum pow(final double power) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] = Math.pow(this.coefficients[i], power);
		}
		
		return this;
	}
	
	/**
	 * Performs {@code Math.pow(coefficient, spectrum.getCoefficient(index))} on all coefficients provided by this {@code Spectrum} instance.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndPow(spectrum)} instead.
	 * 
	 * @param spectrum the second parameter argument to {@code Math.pow(double, double)} for each coefficient
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum pow(final Spectrum spectrum) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] = Math.pow(this.coefficients[i], spectrum.coefficients[i]);
		}
		
		return this;
	}
	
	/**
	 * Sets the coefficients of this {@code Spectrum} instance to the coefficients of {@code spectrum}.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called.
	 * 
	 * @param spectrum the {@code Spectrum} with the coefficients to set this {@code Spectrum} instance to
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum set(final Spectrum spectrum) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] = spectrum.coefficients[i];
		}
		
		return this;
	}
	
	/**
	 * Sets the coefficient at index {@code index} in this {@code Spectrum} instance.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@code getCoefficientCount()}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called.
	 * 
	 * @param index the index of the coefficient to set
	 * @param coefficient the new coefficient to set on the given index
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getCoefficientCount()}
	 */
	public final Spectrum setCoefficient(final int index, final double coefficient) {
		this.coefficients[index] = coefficient;
		
		return this;
	}
	
	/**
	 * Performs {@code Math.sqrt(coefficient)} on all coefficients of this {@code Spectrum} instance.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSqrt()} instead.
	 * 
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 */
	public final Spectrum sqrt() {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] = Math.sqrt(this.coefficients[i]);
		}
		
		return this;
	}
	
	/**
	 * Subtracts {@code scalar} from all coefficients of this {@code Spectrum} instance.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSubtract(scalar)} instead.
	 * 
	 * @param scalar the scalar to subtract from all coefficients of this {@code Spectrum} instance
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 */
	public final Spectrum subtract(final double scalar) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] -= scalar;
		}
		
		return this;
	}
	
	/**
	 * Subtracts the coefficients provided by {@code spectrum} from this {@code Spectrum} instance.
	 * <p>
	 * Returns this {@code Spectrum} instance, such that method chaining is possible.
	 * <p>
	 * If {@code spectrum} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method modifies the state of the instance on which it is called. If you don't want this behavior, consider calling {@code copyAndSubtract(spectrum)} instead.
	 * 
	 * @param spectrum the {@code Spectrum} with the coefficients to subtract from this {@code Spectrum} instance
	 * @return this {@code Spectrum} instance, such that method chaining is possible
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the coefficient count of this {@code Spectrum} instance is higher than that of {@code spectrum}
	 * @throws NullPointerException thrown if, and only if, {@code spectrum} is {@code null}
	 */
	public final Spectrum subtract(final Spectrum spectrum) {
		for(int i = 0; i < this.coefficients.length; i++) {
			this.coefficients[i] -= spectrum.coefficients[i];
		}
		
		return this;
	}
}