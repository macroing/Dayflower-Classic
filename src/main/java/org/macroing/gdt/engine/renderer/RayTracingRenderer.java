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
package org.macroing.gdt.engine.renderer;

import java.util.Objects;

import org.macroing.gdt.engine.camera.Camera;
import org.macroing.gdt.engine.camera.PerspectiveCamera;
import org.macroing.gdt.engine.camera.SimpleCamera;
import org.macroing.gdt.engine.geometry.Scene;

/**
 * An abstract base-class defining the general contract for all Ray Tracing-based rendering algorithms.
 * <p>
 * This class makes no assumptions about what rendering algorithm to use, whether it be for instance Whitted Ray Tracing or Path Tracing. That's all up to the implementation at hand.
 * <p>
 * This class does, however, assume that the rendering algorithm is based on the Ray Tracing approach, as opposed to Rasterizers like Scanline renderers.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class RayTracingRenderer extends Renderer {
	private Camera camera = PerspectiveCamera.newInstance();
	private SimpleCamera simpleCamera = SimpleCamera.newInstance();
	private Scene scene = Scene.newCornellBox();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code RayTracingRenderer} instance.
	 */
	protected RayTracingRenderer() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Camera} currently assigned to this {@code RayTracingRenderer} instance.
	 * 
	 * @return the {@code Camera} currently assigned to this {@code RayTracingRenderer} instance
	 */
	public final Camera getCamera() {
		return this.camera;
	}
	
	/**
	 * Returns the {@link Scene} currently assigned to this {@code RayTracingRenderer} instance.
	 * <p>
	 * By default a Cornell Box {@code Scene} is used.
	 * 
	 * @return the {@code Scene} currently assigned to this {@code RayTracingRenderer} instance
	 */
	public final Scene getScene() {
		return this.scene;
	}
	
	/**
	 * Returns the {@link SimpleCamera} currently assigned to this {@code RayTracingRenderer} instance.
	 * <p>
	 * This method will be removed in the future. At that time consider using {@code getCamera()} instead.
	 * 
	 * @return the {@code SimpleCamera} currently assigned to this {@code RayTracingRenderer} instance
	 */
	public final SimpleCamera getSimpleCamera() {
		return this.simpleCamera;
	}
	
	/**
	 * Sets a new {@link Camera} for this {@code RayTracingRenderer} instance.
	 * <p>
	 * If {@code camera} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param camera the new {@code Camera}
	 * @throws NullPointerException thrown if, and only if, {@code camera} is {@code null}
	 */
	public final void setCamera(final Camera camera) {
		this.camera = Objects.requireNonNull(camera, "camera == null");
	}
	
	/**
	 * Sets a new {@link Scene} for this {@code RayTracingRenderer} instance.
	 * <p>
	 * If {@code scene} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param scene the new {@code Scene}
	 * @throws NullPointerException thrown if, and only if, {@code scene} is {@code null}
	 */
	public final void setScene(final Scene scene) {
		this.scene = Objects.requireNonNull(scene, "scene == null");
	}
	
	/**
	 * Sets a new {@link SimpleCamera} for this {@code RayTracingRenderer} instance.
	 * <p>
	 * If {@code simpleCamera} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will be removed in the future. At that time consider using {@code setCamera(Camera)} instead.
	 * 
	 * @param simpleCamera the new {@code SimpleCamera}
	 * @throws NullPointerException thrown if, and only if, {@code simpleCamera} is {@code null}
	 */
	public final void setSimpleCamera(final SimpleCamera simpleCamera) {
		this.simpleCamera = Objects.requireNonNull(simpleCamera, "simpleCamera == null");
	}
}