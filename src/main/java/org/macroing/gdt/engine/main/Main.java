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
package org.macroing.gdt.engine.main;

import java.util.concurrent.atomic.AtomicLong;

import org.macroing.gdt.engine.application.Application;
import org.macroing.gdt.engine.application.concurrent.ConcurrentApplication;
import org.macroing.gdt.engine.camera.Camera;
import org.macroing.gdt.engine.camera.SimpleCamera;
import org.macroing.gdt.engine.display.Display;
import org.macroing.gdt.engine.display.PixelIterable;
import org.macroing.gdt.engine.display.wicked.WickedDisplay;
import org.macroing.gdt.engine.geometry.Point;
import org.macroing.gdt.engine.geometry.Scene;
import org.macroing.gdt.engine.geometry.Transform;
import org.macroing.gdt.engine.geometry.Vector;
import org.macroing.gdt.engine.input.KeyboardEvent;
import org.macroing.gdt.engine.input.KeyboardObserver;
import org.macroing.gdt.engine.renderer.PathTracingRenderer;
import org.macroing.gdt.engine.renderer.RayTracingRenderer;
import org.macroing.gdt.engine.renderer.Renderer;

/**
 * A simple implementation of the {@link ConcurrentApplication} that runs the default configurations of the engine.
 * <p>
 * As of this writing, the default configurations consists of the following:
 * <ul>
 * <li>{@link Display} - A Java Swing-based {@link WickedDisplay} implementation. It's based on the Wicked toolkit that was merged with this framework.</li>
 * <li>{@link PathTracingRenderer} - A simple Path Tracer implementation.</li>
 * <li>{@link Scene} - Where the current configuration would be a modified version of the Cornell Box scene.</li>
 * </ul>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Main extends ConcurrentApplication implements KeyboardObserver {
	private static final String ID_CHECK_BOX_REALTIME_RENDERING = "CheckBox.RealtimeRendering";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicLong lastUpdate = new AtomicLong(0L);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Main() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Overridden to handle configuration.
	 */
	@Override
	protected void configure() {
		doConfigureCamera();
		doConfigureScene();
		doConfigureSimpleCamera();
		doConfigureDisplay();
	}
	
	@Override
	@SuppressWarnings("incomplete-switch")
	public void onKeyboardEvent(final KeyboardEvent keyboardEvent) {
		switch(keyboardEvent.getKeyState()) {
			case PRESSED:
				break;
			case RELEASED:
				switch(keyboardEvent.getKey()) {
					case KEY_A:
						doAttemptToMoveSimpleCamera();
						
						break;
					default:
						break;
				}
				
				break;
			default:
				break;
		}
	}
	
	@Override
	public void update() {
		if(System.currentTimeMillis() - this.lastUpdate.get() > 10000L) {
//			doAttemptToMoveSimpleCamera();
			
			this.lastUpdate.set(System.currentTimeMillis());
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The main entry-point for this class.
	 * <p>
	 * Currently the parameter arguments provided by the {@code args} variable aren't used. But that may change in the future.
	 * 
	 * @param args the parameter arguments that are not currently used
	 */
	public static void main(final String[] args) {
		final
		Application application = new Main();
		application.start();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doAttemptToMoveSimpleCamera() {
		final Renderer renderer = getRenderer();
		
		if(renderer instanceof RayTracingRenderer) {
			final RayTracingRenderer rayTracingRenderer = RayTracingRenderer.class.cast(renderer);
			
			final
			SimpleCamera simpleCamera = rayTracingRenderer.getSimpleCamera();
			simpleCamera.setEye(new Point(50.0D + (Math.random() * 5.0D) - (Math.random() * 5.0D), 42.0D + (Math.random() * 5.0D) - (Math.random() * 5.0D), 295.6D + (Math.random() * 5.0D) - (Math.random() * 5.0D)));
			simpleCamera.calculateOrthonormalBasisFor(Constants.getDefaultWidthScaled(), Constants.getDefaultHeightScaled());
			
			final Display display = getDisplay();
			
			for(final PixelIterable pixelIterable : display.getPixelIterables()) {
				pixelIterable.forEach(pixel -> pixel.clear());
			}
		}
	}
	
	private void doConfigureCamera() {
		final Renderer renderer = getRenderer();
		
		if(renderer instanceof RayTracingRenderer) {
			final RayTracingRenderer rayTracingRenderer = RayTracingRenderer.class.cast(renderer);
			
			final Camera camera = rayTracingRenderer.getCamera();
			
			final Point source = Point.zero();
			final Point target = new Point(0.0D, 0.0D, -2.0D);
			
			final Vector up = Vector.y();
			
			final
			Transform transform = camera.getCameraToWorld();
			transform.set(Transform.lookAt(source, target, up));
		}
	}
	
	private void doConfigureDisplay() {
		final
		WickedDisplay wickedDisplay = WickedDisplay.class.cast(getDisplay());
		wickedDisplay.setSuperSamplingWithDownScaling(Constants.isSuperSamplingWithDownScaling());
		wickedDisplay.setRenderingInRealtime(Constants.isRenderingInRealtime());
		wickedDisplay.setHeightScale(Constants.getDefaultHeightScale());
		wickedDisplay.setWidthScale(Constants.getDefaultWidthScale());
		wickedDisplay.setResolution(Constants.getDefaultWidth(), Constants.getDefaultHeight());
		wickedDisplay.setTitle(Constants.getTitle());
		wickedDisplay.configure();
		wickedDisplay.addCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).getCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).setLocation(10, 10).setText("Realtime rendering").setVisible(true);
		wickedDisplay.getCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).setOnSelectionChange(checkBox -> {});
	}
	
	private void doConfigureScene() {
		final Renderer renderer = getRenderer();
		
		if(renderer instanceof RayTracingRenderer) {
			final RayTracingRenderer rayTracingRenderer = RayTracingRenderer.class.cast(renderer);
			
			final
			Scene scene = rayTracingRenderer.getScene();
			scene.setDepthUntilProbabilisticallyTerminatingRay(Constants.getDepthUntilProbabilisticallyTerminatingRay());
			scene.setSkippingProbabilisticallyTerminatingRay(Constants.isSkippingProbabilisticallyTerminatingRay());
		}
	}
	
	private void doConfigureSimpleCamera() {
		final Renderer renderer = getRenderer();
		
		if(renderer instanceof RayTracingRenderer) {
			final RayTracingRenderer rayTracingRenderer = RayTracingRenderer.class.cast(renderer);
			
			final
			SimpleCamera simpleCamera = rayTracingRenderer.getSimpleCamera();
			simpleCamera.setEye(new Point(50.0D, 42.0D, 295.6D));
//			simpleCamera.setLookAt(new Point(50.0D, 42.0D, 295.5D));
			simpleCamera.calculateOrthonormalBasisFor(Constants.getDefaultWidthScaled(), Constants.getDefaultHeightScaled());
		}
	}
}