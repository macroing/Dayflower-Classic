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

import java.lang.reflect.Field;//TODO: Fix this class and remove this comment once done. Add Javadocs etc.

import org.macroing.gdt.engine.util.Interpolation;

/**
 * A {@link Spectrum} implementation that is implemented in terms of an RGB-model.
 * <p>
 * It has three coefficients; R, G and B. They denote Red, Green and Blue, respectively.
 * <p>
 * This class is mutable and therefore not suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class RGBSpectrum extends Spectrum {
	/**
	 * The coefficient of the B (Blue) component of the {@code RGBSpectrum}.
	 */
	public static final int INDEX_OF_BLUE_COEFFICIENT = 2;
	
	/**
	 * The coefficient of the G (Green) component of the {@code RGBSpectrum}.
	 */
	public static final int INDEX_OF_GREEN_COEFFICIENT = 1;
	
	/**
	 * The coefficient of the R (Red) component of the {@code RGBSpectrum}.
	 */
	public static final int INDEX_OF_RED_COEFFICIENT = 0;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public RGBSpectrum(final double red, final double green, final double blue) {
		super(red, green, blue);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private RGBSpectrum() {
		super(3);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, this {@code RGBSpectrum} is considered to be entirely black, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code RGBSpectrum} is considered to be entirely black, {@code false} otherwise
	 */
	@Override
	public boolean isBlack() {
		return getRed() == 0.0D && getGreen() == 0.0D && getBlue() == 0.0D;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code RGBSpectrum} is considered to be blue, {@code false} otherwise.
	 * <p>
	 * It is considered to be blue if, and only if, it is more blue than green and more blue than red.
	 * 
	 * @return {@code true} if, and only if, this {@code RGBSpectrum} is considered to be blue, {@code false} otherwise
	 */
	public boolean isBlue() {
		return getBlue() > getRed() && getBlue() > getGreen();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code RGBSpectrum} is considered to be green, {@code false} otherwise.
	 * <p>
	 * It is considered to be green if, and only if, it is more green than blue and more green than red.
	 * 
	 * @return {@code true} if, and only if, this {@code RGBSpectrum} is considered to be green, {@code false} otherwise
	 */
	public boolean isGreen() {
		return getGreen() > getRed() && getGreen() > getBlue();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code RGBSpectrum} is considered to be red, {@code false} otherwise.
	 * <p>
	 * It is considered to be red if, and only if, it is more red than blue and more red than green.
	 * 
	 * @return {@code true} if, and only if, this {@code RGBSpectrum} is considered to be red, {@code false} otherwise
	 */
	public boolean isRed() {
		return getRed() > getGreen() && getRed() > getBlue();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code RGBSpectrum} is considered to be entirely white, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code RGBSpectrum} is considered to be entirely white, {@code false} otherwise
	 */
	public boolean isWhite() {
		return getRed() == 255.0D && getGreen() == 255.0D && getBlue() == 255.0D;
	}
	
	/**
	 * Returns the value of the B-coefficient (Blue).
	 * 
	 * @return the value of the B-coefficient (Blue)
	 */
	public double getBlue() {
		return getCoefficient(INDEX_OF_BLUE_COEFFICIENT);
	}
	
	/**
	 * Returns the value of the G-coefficient (Green).
	 * 
	 * @return the value of the G-coefficient (Green)
	 */
	public double getGreen() {
		return getCoefficient(INDEX_OF_GREEN_COEFFICIENT);
	}
	
	/**
	 * Returns the luminance of this {@code RGBSpectrum} instance.
	 * <p>
	 * The luminance of a {@code RGBSpectrum} determines how bright or dark it is.
	 * 
	 * @return the luminance of this {@code RGBSpectrum} instance
	 */
	@Override
	public double getLuminance() {
		return 0.212671D * getRed() + 0.715160D * getGreen() + 0.072169D * getBlue();
	}
	
	/**
	 * Returns the value of the R-coefficient (Red).
	 * 
	 * @return the value of the R-coefficient (Red)
	 */
	public double getRed() {
		return getCoefficient(INDEX_OF_RED_COEFFICIENT);
	}
	
	/**
	 * Returns a {@code double} array with the coefficients of this {@code RGBSpectrum} instance in XYZ-form.
	 * <p>
	 * This method does not alter the {@code RGBSpectrum} instance on which it is called.
	 * 
	 * @return a {@code double} array with the coefficients of this {@code RGBSpectrum} instance in XYZ-form
	 */
	@Override
	public double[] toXYZ() {
		return fromRGBToXYZ(getRed(), getGreen(), getBlue());
	}
	
	/**
	 * Returns a copy of this {@code RGBSpectrum} instance.
	 * 
	 * @return a copy of this {@code RGBSpectrum} instance
	 */
	@Override
	public RGBSpectrum copy() {
		return new RGBSpectrum(getRed(), getGreen(), getBlue());
	}
	
	/**
	 * Sets a new value for the B-coefficient (Blue).
	 * <p>
	 * Returns this {@code RGBSpectrum}, such that method chaining is possible.
	 * 
	 * @param blue the new value for the B-coefficient (Blue)
	 * @return this {@code RGBSpectrum}, such that method chaining is possible
	 */
	public RGBSpectrum setBlue(final double blue) {
		setCoefficient(INDEX_OF_BLUE_COEFFICIENT, blue);
		
		return this;
	}
	
	/**
	 * Sets a new value for the G-coefficient (Green).
	 * <p>
	 * Returns this {@code RGBSpectrum}, such that method chaining is possible.
	 * 
	 * @param green the new value for the G-coefficient (Green)
	 * @return this {@code RGBSpectrum}, such that method chaining is possible
	 */
	public RGBSpectrum setGreen(final double green) {
		setCoefficient(INDEX_OF_GREEN_COEFFICIENT, green);
		
		return this;
	}
	
	/**
	 * Sets a new value for the R-coefficient (Red).
	 * <p>
	 * Returns this {@code RGBSpectrum}, such that method chaining is possible.
	 * 
	 * @param red the new value for the R-coefficient (Red)
	 * @return this {@code RGBSpectrum}, such that method chaining is possible
	 */
	public RGBSpectrum setRed(final double red) {
		setCoefficient(INDEX_OF_RED_COEFFICIENT, red);
		
		return this;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code RGBSpectrum} instance.
	 * 
	 * @return a {@code String} representation of this {@code RGBSpectrum} instance
	 */
	@Override
	public String toString() {
		return "RGBSpectrum = {" + getRed() + ", " + getGreen() + ", " + getBlue() + "}";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param aRGB
	 * @return
	 */
	public static double toAlpha(final double aRGB) {
		return (((int)(aRGB) >> 24) & 0xFF);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param rGB
	 * @return
	 */
	public static double toBlue(final double rGB) {
		return (((int)(rGB) >> 0) & 0xFF);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param rGB
	 * @return
	 */
	public static double toGreen(final double rGB) {
		return (((int)(rGB) >> 8) & 0xFF);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param rGB
	 * @return
	 */
	public static double toRed(final double rGB) {
		return (((int)(rGB) >> 16) & 0xFF);
	}
	
	/**
	 * Returns a {@code double} array of length {@code 3} with {@code red}, {@code green} and {@code blue} converted to X, Y and Z, respectively.
	 * 
	 * @param red the R-coefficient (Red)
	 * @param green the G-coefficient (Green)
	 * @param blue the B-coefficient (Blue)
	 * @return a {@code double} array of length {@code 3} with {@code red}, {@code green} and {@code blue} converted to X, Y and Z, respectively
	 */
	public static double[] fromRGBToXYZ(final double red, final double green, final double blue) {
		final double[] xYZ = new double[3];
		
		xYZ[0] = 0.412453D * red + 0.357580D * green + 0.180423D * blue;
		xYZ[1] = 0.212671D * red + 0.715160D * green + 0.072169D * blue;
		xYZ[2] = 0.019334D * red + 0.119193D * green + 0.950227D * blue;
		
		return xYZ;
	}
	
	/**
	 * Returns a {@code double} array of length {@code 3} with {@code rGB[0]}, {@code rGB[1]} and {@code rGB[2]} converted to X, Y and Z, respectively.
	 * <p>
	 * If {@code rGB} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code rGB.length} is less than {@code 3}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method will not modify the content in the {@code rGB} array.
	 * 
	 * @param rGB a {@code double} array with a length of at least {@code 3}
	 * @return a {@code double} array of length {@code 3} with {@code rGB[0]}, {@code rGB[1]} and {@code rGB[2]} converted to X, Y and Z, respectively
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, {@code rGB.length} is less than {@code 3}
	 * @throws NullPointerException thrown if, and only if, {@code rGB} is {@code null}
	 */
	public static double[] fromRGBToXYZ(final double[] rGB) {
		return fromRGBToXYZ(rGB[0], rGB[1], rGB[2]);
	}
	
	/**
	 * Returns a {@code double} array of length {@code 3} with {@code x}, {@code y} and {@code z} converted to R, G and B, respectively.
	 * 
	 * @param x the X-coefficient
	 * @param y the Y-coefficient
	 * @param z the Z-coefficient
	 * @return a {@code double} array of length {@code 3} with {@code x}, {@code y} and {@code z} converted to R, G and B, respectively
	 */
	public static double[] fromXYZToRGB(final double x, final double y, final double z) {
		return fromXYZToRGB(x, y, z, new double[3], 0);
	}
	
	/**
	 * Returns {@code rGB} when it has converted {@code x}, {@code y} and {@code z} to R, G and B, respectively, and stored them inside it.
	 * <p>
	 * If {@code rGB} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code rGB.length} is less than {@code Math.max(offset, 0) + 3}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * The R-, G- and B-coefficients will be stored in {@code rGB[Math.max(offset, 0) + 0]}, {@code rGB[Math.max(offset, 0) + 1]} and {@code rGB[Math.max(offset, 0) + 2]}, respectively.
	 * <p>
	 * This method will modify the content in the {@code rGB} array.
	 * 
	 * @param x the X-coefficient to convert to the R-coefficient
	 * @param y the Y-coefficient to convert to the G-coefficient
	 * @param z the Z-coefficient to convert to the B-coefficient
	 * @param rGB a {@code double} array with a length of at least {@code offset + 3}
	 * @param offset the offset in {@code rGB} from which to start storing the R-, G- and B-coefficients
	 * @return {@code rGB} when it has converted {@code x}, {@code y} and {@code z} to R, G and B, respectively, and stored them inside it
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, {@code rGB.length} is less than {@code Math.max(offset, 0) + 3}
	 * @throws NullPointerException thrown if, and only if, {@code rGB} is {@code null}
	 */
	public static double[] fromXYZToRGB(final double x, final double y, final double z, final double[] rGB, final int offset) {
		rGB[Math.max(offset, 0) + 0] = 3.240479D * x - 1.537150D * y - 0.498535D * z;
		rGB[Math.max(offset, 0) + 1] = -0.969256D * x + 1.875991D * y + 0.041556D * z;
		rGB[Math.max(offset, 0) + 2] = 0.055648D * x - 0.204043D * y + 1.057311D * z;
		
		return rGB;
	}
	
	/**
	 * Returns a {@code double} array of length {@code 3} with {@code xYZ[0]}, {@code xYZ[1]} and {@code xYZ[2]} converted to R, G and B, respectively.
	 * <p>
	 * If {@code xYZ} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the length of {@code xYZ} is less than {@code 3}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method will not modify the content in the {@code xYZ} array.
	 * 
	 * @param xYZ a {@code double} array with a length of at least {@code 3}
	 * @return a {@code double} array of length {@code 3} with {@code xYZ[0]}, {@code xYZ[1]} and {@code xYZ[2]} converted to R, G and B, respectively
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the length of {@code xYZ} is less than {@code 3}
	 * @throws NullPointerException thrown if, and only if, {@code xYZ} is {@code null}
	 */
	public static double[] fromXYZToRGB(final double[] xYZ) {
		return fromXYZToRGB(xYZ, new double[3]);
	}
	
	/**
	 * Returns {@code rGB} when it has converted {@code xYZ[0]}, {@code xYZ[1]} and {@code xYZ[2]} to R, G and B, respectively, and stored them inside it.
	 * <p>
	 * If either {@code xYZ} or {@code rGB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the length of either {@code xYZ} or {@code rGB} are less than {@code 3}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method will not modify the content in the {@code xYZ} array, but it will modify the content in the {@code rGB} array.
	 * 
	 * @param xYZ a {@code double} array with a length of at least {@code 3}
	 * @param rGB a {@code double} array with a length of at least {@code 3}
	 * @return {@code rGB} when it has converted {@code xYZ[0]}, {@code xYZ[1]} and {@code xYZ[2]} to R, G and B, respectively, and stored them inside it
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the length of either {@code xYZ} or {@code rGB} are less than {@code 3}
	 * @throws NullPointerException thrown if, and only if, either {@code xYZ} or {@code rGB} are {@code null}
	 */
	public static double[] fromXYZToRGB(final double[] xYZ, final double[] rGB) {
		return fromXYZToRGB(xYZ, rGB, 0);
	}
	
	/**
	 * Returns {@code rGB} when it has converted {@code xYZ[0]}, {@code xYZ[1]} and {@code xYZ[2]} to R, G and B, respectively, and stored them inside it.
	 * <p>
	 * If either {@code xYZ} or {@code rGB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the length of {@code xYZ} is less than {@code 3} or the length of {@code rGB} is less than {@code Math.max(offset, 0) + 3}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * This method will not modify the content in the {@code xYZ} array, but it will modify the content in the {@code rGB} array.
	 * 
	 * @param xYZ a {@code double} array with a length of at least {@code 3}
	 * @param rGB a {@code double} array with a length of at least {@code Math.max(offset, 0) + 3}
	 * @param offset the offset in {@code rGB} from which to start storing the R-, G- and B-coefficients
	 * @return {@code rGB} when it has converted {@code xYZ[0]}, {@code xYZ[1]} and {@code xYZ[2]} to R, G and B, respectively, and stored them inside it
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, the length of {@code xYZ} is less than {@code 3} or the length of {@code rGB} is less than {@code Math.max(offset, 0) + 3}
	 * @throws NullPointerException thrown if, and only if, either {@code xYZ} or {@code rGB} are {@code null}
	 */
	public static double[] fromXYZToRGB(final double[] xYZ, final double[] rGB, final int offset) {
		return fromXYZToRGB(xYZ[0], xYZ[1], xYZ[2], rGB, offset);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param alpha
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static int toARGB(final double alpha, final double red, final double green, final double blue) {
		return (((int)(alpha) & 0xFF) << 24) | (((int)(red) & 0xFF) << 16) | (((int)(green) & 0xFF) << 8) | (((int)(blue) & 0xFF) << 0);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static int toRGB(final double red, final double green, final double blue) {
		return (((int)(red) & 0xFF) << 16) | (((int)(green) & 0xFF) << 8) | (((int)(blue) & 0xFF) << 0);
	}
	
	/**
	 * Returns a black {@code RGBSpectrum} instance.
	 * <p>
	 * This method is equivalent to {@code valueOf(0.0D, 0.0D, 0.0D)}.
	 * 
	 * @return a black {@code RGBSpectrum} instance
	 */
	public static RGBSpectrum black() {
		return new RGBSpectrum(0.0D, 0.0D, 0.0D);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param rGBSpectrum00
	 * @param rGBSpectrum10
	 * @param rGBSpectrum01
	 * @param rGBSpectrum11
	 * @param fractionX
	 * @param fractionY
	 * @return
	 */
	public static RGBSpectrum blerp(final RGBSpectrum rGBSpectrum00, final RGBSpectrum rGBSpectrum10, final RGBSpectrum rGBSpectrum01, final RGBSpectrum rGBSpectrum11, final double fractionX, final double fractionY) {
		return lerp(lerp(rGBSpectrum00, rGBSpectrum10, fractionX), lerp(rGBSpectrum01, rGBSpectrum11, fractionX), fractionY);
	}
	
	/**
	 * Returns a blue {@code RGBSpectrum} instance.
	 * <p>
	 * This method is equivalent to {@code valueOf(0.0D, 0.0D, 255.0D)}.
	 * 
	 * @return a blue {@code RGBSpectrum} instance
	 */
	public static RGBSpectrum blue() {
		return new RGBSpectrum(0.0D, 0.0D, 255.0D);
	}
	
	/**
	 * TODO: Add Javadocs.
	 * 
	 * @param rGBSpectrum0
	 * @param rGBSpectrum1
	 * @param fraction
	 * @return
	 */
	public static RGBSpectrum lerp(final RGBSpectrum rGBSpectrum0, final RGBSpectrum rGBSpectrum1, double fraction) {
		final double red0 = rGBSpectrum0.getRed();
		final double red1 = rGBSpectrum1.getRed();
		final double red2 = Interpolation.lerp(red0, red1, fraction);
		
		final double green0 = rGBSpectrum0.getGreen();
		final double green1 = rGBSpectrum1.getGreen();
		final double green2 = Interpolation.lerp(green0, green1, fraction);
		
		final double blue0 = rGBSpectrum0.getBlue();
		final double blue1 = rGBSpectrum1.getBlue();
		final double blue2 = Interpolation.lerp(blue0, blue1, fraction);
		
		return new RGBSpectrum(red2, green2, blue2);
	}
	
	/**
	 * Returns a green {@code RGBSpectrum} instance.
	 * <p>
	 * This method is equivalent to {@code valueOf(0.0D, 255.0D, 0.0D)}.
	 * 
	 * @return a green {@code RGBSpectrum} instance
	 */
	public static RGBSpectrum green() {
		return new RGBSpectrum(0.0D, 255.0D, 0.0D);
	}
	
	/**
	 * Returns a red {@code RGBSpectrum} instance.
	 * <p>
	 * This method is equivalent to {@code valueOf(255.0D, 0.0D, 0.0D)}.
	 * 
	 * @return a red {@code RGBSpectrum} instance
	 */
	public static RGBSpectrum red() {
		return new RGBSpectrum(255.0D, 0.0D, 0.0D);
	}
	
	/**
	 * Returns an {@code RGBSpectrum} instance with its R-, G- and B-coefficients set to {@code value}.
	 * <p>
	 * This method is equivalent to {@code valueOf(value, value, value)}.
	 * 
	 * @param value the value of the R-, G- and B-coefficients
	 * @return an {@code RGBSpectrum} instance with its R-, G- and B-coefficients set to {@code value}
	 */
	public static RGBSpectrum valueOf(final double value) {
		return new RGBSpectrum(value, value, value);
	}
	
	/**
	 * Returns an {@code RGBSpectrum} instance with its R-, G- and B-coefficients set to {@code red}, {@code green} and {@code blue}, respectively.
	 * 
	 * @param red the value of the R-coefficient (Red)
	 * @param green the value of the G-coefficient (Green)
	 * @param blue the value of the B-coefficient (Blue)
	 * @return an {@code RGBSpectrum} instance with its R-, G- and B-coefficients set to {@code red}, {@code green} and {@code blue}, respectively
	 */
	public static RGBSpectrum valueOf(final double red, final double green, final double blue) {
		return new RGBSpectrum(red, green, blue);
	}
	
	/**
	 * Returns a white {@code RGBSpectrum} instance.
	 * <p>
	 * This method is equivalent to {@code valueOf(255.0D, 255.0D, 255.0D)}.
	 * 
	 * @return a white {@code RGBSpectrum} instance
	 */
	public static RGBSpectrum white() {
		return new RGBSpectrum(255.0D, 255.0D, 255.0D);
	}
}