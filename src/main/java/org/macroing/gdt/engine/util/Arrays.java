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
package org.macroing.gdt.engine.util;

/**
 * A class that consists exclusively of static methods that operates on or returns arrays.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Arrays {
	private Arrays() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new deeply cloned version of {@code oldArray}.
	 * <p>
	 * If {@code oldArray} or any element within it are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param oldArray the array to clone deeply
	 * @return a new deeply cloned version of {@code oldArray}
	 * @throws NullPointerException thrown if, and only if, either {@code oldArray} or any element within it are {@code null}
	 */
	public static double[][] deepClone(final double[][] oldArray) {
		final double[][] newArray = oldArray.clone();
		
		for(int i = 0; i < newArray.length; i++) {
			newArray[i] = newArray[i].clone();
		}
		
		return newArray;
	}
	
	/**
	 * Swaps the two elements given by {@code array[index0]} and {@code array[index1]}.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@code index0} or {@code index1} are less than {@code 0}, or greater than or equal to {@code array.length}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * 
	 * @param array the array in which to swap two elements
	 * @param index0 one of the two indices to swap elements
	 * @param index1 one of the two indices to swap elements
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, either {@code index0} or {@code index1} are less than {@code 0}, or greater than or equal to {@code array.length}
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public static void swap(final double[] array, final int index0, final int index1) {
		final double element0 = array[index0];
		final double element1 = array[index1];
		
		array[index0] = element1;
		array[index1] = element0;
	}
	
	/**
	 * Swaps the two elements given by {@code array0[array0Index0][array0Index1]} and {@code array1[array1Index0][array1Index1]}.
	 * <p>
	 * If either {@code array0} or {@code array1} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code array0Index0} is less than {@code 0}, or greater than or equal to {@code array0.length}, or {@code array0Index1} is less than {@code 0}, or greater than or equal to {@code array0[array0Index0].length}, an
	 * {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * <p>
	 * If {@code array1Index0} is less than {@code 0}, or greater than or equal to {@code array1.length}, or {@code array1Index1} is less than {@code 0}, or greater than or equal to {@code array1[array1Index0].length}, an
	 * {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * 
	 * @param array0 one of the two arrays to swap elements in
	 * @param array0Index0 the index of the first dimension in {@code array0}
	 * @param array0Index1 the index of the second dimension in {@code array0}
	 * @param array1 one of the two arrays to swap elements in
	 * @param array1Index0 the index of the first dimension in {@code array1}
	 * @param array1Index1 the index of the second dimension in {@code array1}
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, either {@code array0Index0}, {@code array0Index1}, {@code array1Index0} or {@code array1Index1} are out of bounds
	 * @throws NullPointerException thrown if, and only if, either {@code array0} or {@code array1} are {@code null}
	 */
	public static void swap(final double[][] array0, final int array0Index0, final int array0Index1, final double[][] array1, final int array1Index0, final int array1Index1) {
		final double element0 = array0[array0Index0][array0Index1];
		final double element1 = array1[array1Index0][array1Index1];
		
		array0[array0Index0][array0Index1] = element1;
		array1[array1Index0][array1Index1] = element0;
	}
}