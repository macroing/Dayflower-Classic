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

import org.macroing.gdt.engine.util.Arrays;
import org.macroing.gdt.engine.util.Strings;

/**
 * A class that models a 4x4 matrix with double-precision floating-point format.
 * <p>
 * This class is mutable and therefore not suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Matrix {
	private final double[][] matrix;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Matrix(final double m00, final double m01, final double m02, final double m03, final double m10, final double m11, final double m12, final double m13, final double m20, final double m21, final double m22, final double m23, final double m30, final double m31, final double m32, final double m33) {
		this(new double[][] {
			new double[] {m00, m01, m02, m03},
			new double[] {m10, m11, m12, m13},
			new double[] {m20, m21, m22, m23},
			new double[] {m30, m31, m32, m33}
		});
	}
	
	private Matrix(final double[][] matrix) {
		this.matrix = matrix;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, this {@code Matrix} is an identity matrix, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Matrix} is an identity matrix, {@code false} otherwise
	 */
	public boolean isIdentity() {
		final boolean isIdentity0 = this.matrix[0][0] == 1.0D && this.matrix[0][1] == 0.0D && this.matrix[0][2] == 0.0D && this.matrix[0][3] == 0.0D;
		final boolean isIdentity1 = this.matrix[1][0] == 0.0D && this.matrix[1][1] == 1.0D && this.matrix[1][2] == 0.0D && this.matrix[1][3] == 0.0D;
		final boolean isIdentity2 = this.matrix[2][0] == 0.0D && this.matrix[2][1] == 0.0D && this.matrix[2][2] == 1.0D && this.matrix[2][3] == 0.0D;
		final boolean isIdentity3 = this.matrix[3][0] == 0.0D && this.matrix[3][1] == 0.0D && this.matrix[3][2] == 0.0D && this.matrix[3][3] == 1.0D;
		
		return isIdentity0 && isIdentity1 && isIdentity2 && isIdentity3;
	}
	
	/**
	 * Returns the determinant of this {@code Matrix} instance.
	 * 
	 * @return the determinant of this {@code Matrix} instance
	 */
	public double getDeterminant() {
		final double a = (this.matrix[0][0] * (this.matrix[1][1] * this.matrix[2][2] - this.matrix[1][2] * this.matrix[2][1]));
		final double b = (this.matrix[0][1] * (this.matrix[1][0] * this.matrix[2][2] - this.matrix[1][2] * this.matrix[2][0]));
		final double c = (this.matrix[0][2] * (this.matrix[1][0] * this.matrix[2][1] - this.matrix[1][1] * this.matrix[2][0]));
		final double determinant = a - b + c;
		
		return determinant;
	}
	
	/**
	 * Returns a copy of the underlying elements array.
	 * 
	 * @return a copy of the underlying elements array
	 */
	public double[][] getMatrix() {
		return Arrays.deepClone(this.matrix);
	}
	
	/**
	 * Returns a copy of this {@code Matrix} instance.
	 * 
	 * @return a copy of this {@code Matrix} instance
	 */
	public Matrix copy() {
		return newInstance(this.matrix);
	}
	
	/**
	 * Returns an inverse version of this {@code Matrix} instance.
	 * <p>
	 * If this {@code Matrix} instance is a singular matrix, an {@code IllegalStateException} will be thrown.
	 * 
	 * @return an inverse version of this {@code Matrix} instance
	 * @throws IllegalStateException thrown if, and only if, this {@code Matrix} instance is a singular matrix
	 */
	public Matrix inverse() {
		final int[] columnIndices = new int[4];
		final int[] pivotIndices = new int[4];
		final int[] rowIndices = new int[4];
		
		final double[][] matrixInversed = Arrays.deepClone(this.matrix);
		
		for(int i = 0; i < 4; i++) {
			int columnIndex = -1;
			int rowIndex = -1;
			
			double value = 0.0D;
			
			for(int j = 0; j < 4; j++) {
				if(pivotIndices[j] != 1) {
					for(int k = 0; k < 4; k++) {
						if(pivotIndices[k] == 0) {
							if(Math.abs(matrixInversed[j][k]) >= value) {
								value = matrixInversed[j][k];
								columnIndex = k;
								rowIndex = j;
							}
						} else if(pivotIndices[k] > 1) {
							throw new IllegalStateException("A Singular Matrix cannot be inversed!");
						}
					}
				}
			}
			
			pivotIndices[columnIndex]++;
			
			if(columnIndex != rowIndex) {
				for(int j = 0; j < 4; j++) {
					Arrays.swap(matrixInversed, rowIndex, j, matrixInversed, columnIndex, j);
				}
			}
			
			columnIndices[i] = columnIndex;
			rowIndices[i] = rowIndex;
			
			if(matrixInversed[columnIndex][columnIndex] == 0.0D) {
				throw new IllegalStateException("A Singular Matrix cannot be inversed!");
			}
			
			final double inversedPivot = 1.0D / matrixInversed[columnIndex][columnIndex];
			
			matrixInversed[columnIndex][columnIndex] = 1.0D;
			
			for(int j = 0; j < 4; j++) {
				matrixInversed[columnIndex][j] *= inversedPivot;
			}
			
			for(int j = 0; j < 4; j++) {
				if(j != columnIndex) {
					final double save = matrixInversed[j][columnIndex];
					
					matrixInversed[j][columnIndex] = 0.0D;
					
					for(int k = 0; k < 4; k++) {
						matrixInversed[j][k] -= matrixInversed[columnIndex][k] * save;
					}
				}
			}
		}
		
		for(int i = 3; i >= 0; i--) {
			if(columnIndices[i] != rowIndices[i]) {
				for(int j = 0; j < 4; j++) {
					Arrays.swap(matrixInversed, j, rowIndices[i], matrixInversed, j, columnIndices[i]);
				}
			}
		}
		
		return newInstance(matrixInversed);
	}
	
	/**
	 * Multiplies this {@code Matrix} instance with {@code matrix}.
	 * <p>
	 * Returns a new {@code Matrix} instance.
	 * <p>
	 * If {@code matrix} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param matrix the {@code Matrix} to multiply this {@code Matrix} instance with
	 * @return a new {@code Matrix} instance
	 * @throws NullPointerException thrown if, and only if, {@code matrix} is {@code null}
	 */
	public Matrix multiply(final Matrix matrix) {
		final double[][] newMatrix = new double[4][4];
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				newMatrix[i][j] = this.matrix[i][0] * matrix.matrix[0][j] + this.matrix[i][1] * matrix.matrix[1][j] + this.matrix[i][2] * matrix.matrix[2][j] + this.matrix[i][3] * matrix.matrix[3][j];
			}
		}
		
		return newInstance(newMatrix);
	}
	
	/**
	 * Sets this {@code Matrix} instance to {@code matrix}.
	 * <p>
	 * Returns this {@code Matrix} instance.
	 * <p>
	 * If {@code matrix} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code Matrix} instance has more elements than {@code matrix}, an {@code ArrayIndexOutOfBoundsException} will be thrown.
	 * 
	 * @param matrix the {@code Matrix} to set this {@code Matrix} instance to
	 * @return this {@code Matrix} instance
	 * @throws ArrayIndexOutOfBoundsException thrown if, and only if, this {@code Matrix} instance has more elements than {@code matrix}
	 * @throws NullPointerException thrown if, and only if, {@code matrix} is {@code null}
	 */
	public Matrix set(final Matrix matrix) {
		for(int i = 0; i < this.matrix.length; i++) {
			for(int j = 0; j < this.matrix[i].length; j++) {
				this.matrix[i][j] = matrix.matrix[i][j];
			}
		}
		
		return this;
	}
	
	/**
	 * Returns the transpose of this {@code Matrix} instance.
	 * 
	 * @return the transpose of this {@code Matrix} instance
	 */
	public Matrix transpose() {
		final double[][] matrix = new double[][] {
			new double[] {this.matrix[0][0], this.matrix[1][0], this.matrix[2][0], this.matrix[3][0]},
			new double[] {this.matrix[0][1], this.matrix[1][1], this.matrix[2][1], this.matrix[3][1]},
			new double[] {this.matrix[0][2], this.matrix[1][2], this.matrix[2][2], this.matrix[3][2]},
			new double[] {this.matrix[0][3], this.matrix[1][3], this.matrix[2][3], this.matrix[3][3]}
		};
		
		return newInstance(matrix);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Matrix} instance.
	 * 
	 * @return a {@code String} representation of this {@code Matrix} instance
	 */
	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("Matrix");
		stringBuilder.append(" ");
		stringBuilder.append("{");
		
		int length = 0;
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				final String string = Double.toString(this.matrix[i][j]);
				
				length = Math.max(string.length(), length);
			}
		}
		
		for(int i = 0; i < 4; i++) {
			stringBuilder.append("\n");
			stringBuilder.append(" ");
			
			for(int j = 0; j < 4; j++) {
				if(j > 0) {
					stringBuilder.append(",");
					stringBuilder.append(" ");
				}
				
				stringBuilder.append(Strings.repeat(" ", length - Double.toString(this.matrix[i][j]).length()));
				stringBuilder.append(this.matrix[i][j]);
			}
			
			if(i + 1 < 4) {
				stringBuilder.append(",");
			}
		}
		
		stringBuilder.append("\n");
		stringBuilder.append("}");
		
		return stringBuilder.toString();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new identity {@code Matrix} instance.
	 * 
	 * @return a new identity {@code Matrix} instance
	 */
	public static Matrix newInstance() {
		return new Matrix(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
	}
	
	/**
	 * Returns a new {@code Matrix} instance given a set of elements.
	 * 
	 * @param m00 one of the values to use
	 * @param m01 one of the values to use
	 * @param m02 one of the values to use
	 * @param m03 one of the values to use
	 * @param m10 one of the values to use
	 * @param m11 one of the values to use
	 * @param m12 one of the values to use
	 * @param m13 one of the values to use
	 * @param m20 one of the values to use
	 * @param m21 one of the values to use
	 * @param m22 one of the values to use
	 * @param m23 one of the values to use
	 * @param m30 one of the values to use
	 * @param m31 one of the values to use
	 * @param m32 one of the values to use
	 * @param m33 one of the values to use
	 * @return a new {@code Matrix} instance given a set of elements
	 */
	public static Matrix newInstance(final double m00, final double m01, final double m02, final double m03, final double m10, final double m11, final double m12, final double m13, final double m20, final double m21, final double m22, final double m23, final double m30, final double m31, final double m32, final double m33) {
		return new Matrix(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
	}
	
	/**
	 * Returns a new {@code Matrix} instance given the {@code matrix} array.
	 * <p>
	 * The {@code matrix} array will be cloned.
	 * <p>
	 * If {@code matrix} or at least one of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param matrix the array with the elements to use
	 * @return a new {@code Matrix} instance given the {@code matrix} array
	 * @throws NullPointerException thrown if, and only if, {@code matrix} or at least one of its elements are {@code null}
	 */
	public static Matrix newInstance(final double[][] matrix) {
		return new Matrix(Arrays.deepClone(matrix));
	}
}