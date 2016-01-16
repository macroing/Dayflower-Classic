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

import org.macroing.gdt.engine.application.Application;
import org.macroing.gdt.engine.application.concurrent.ConcurrentApplication;
import org.macroing.gdt.engine.camera.Camera;
import org.macroing.gdt.engine.camera.NewSimpleCamera;
import org.macroing.gdt.engine.camera.OldSimpleCamera;
import org.macroing.gdt.engine.camera.PerspectiveCamera;
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
import org.macroing.gdt.engine.input.Mouse;
import org.macroing.gdt.engine.input.MouseEvent;
import org.macroing.gdt.engine.input.MouseObserver;
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
public final class Main extends ConcurrentApplication implements KeyboardObserver, MouseObserver {
	private static final CameraType CAMERA_TYPE = CameraType.SIMPLE_CAMERA;
	private static final String ID_CHECK_BOX_REALTIME_RENDERING = "CheckBox.RealtimeRendering";
	private static final String ID_LABEL_SAMPLES = "Label.Samples";
	private static final String ID_LABEL_SAMPLES_PER_SECOND = "Label.SamplesPerSecond";
	
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
		doConfigurePathTracingRenderer();
		doConfigureScene();
		doConfigureSimpleCamera();
		doConfigureDisplay();
	}
	
	@Override
	@SuppressWarnings("incomplete-switch")
	public void onKeyboardEvent(final KeyboardEvent keyboardEvent) {
		switch(keyboardEvent.getKeyState()) {
			case PRESSED:
				switch(keyboardEvent.getKey()) {
					case KEY_A:
						doAdjustCamera(-0.2D, 0.0D, 0.0D);
						doAdjustSimpleCamera(-5.0D, 0.0D, 0.0D);
						
						break;
					case KEY_C:
						doClear();
						
						break;
					case KEY_D:
						doAdjustCamera(0.2D, 0.0D, 0.0D);
						doAdjustSimpleCamera(5.0D, 0.0D, 0.0D);
						
						break;
					case KEY_ESC:
						stop();
						
						break;
					case KEY_M:
						final
						Mouse mouse = Mouse.getInstance();
						mouse.setRecentering(!mouse.isRecentering());
						
						break;
					case KEY_R:
						final
						WickedDisplay wickedDisplay = WickedDisplay.class.cast(getDisplay());
						wickedDisplay.getCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).setSelected(!wickedDisplay.getCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).isSelected());
						
						break;
					case KEY_S:
						doAdjustCamera(0.0D, 0.0D, -0.2D);
						doAdjustSimpleCamera(0.0D, 0.0D, 5.0D);
						
						break;
					case KEY_W:
						doAdjustCamera(0.0D, 0.0D, 0.2D);
						doAdjustSimpleCamera(0.0D, 0.0D, -5.0D);
						
						break;
					default:
						break;
				}
				
				break;
			case RELEASED:
				break;
			default:
				break;
		}
	}
	
	@Override
	@SuppressWarnings("incomplete-switch")
	public void onMouseEvent(final MouseEvent mouseEvent) {
		switch(mouseEvent.getMouseState()) {
			case MOVED_DOWN:
				doRotateCameraAlongX(0.5D);
				
				break;
			case MOVED_LEFT:
				doRotateCameraAlongY(-0.5D);
				
				break;
			case MOVED_RIGHT:
				doRotateCameraAlongY(0.5D);
				
				break;
			case MOVED_UP:
				doRotateCameraAlongX(-0.5D);
				
				break;
			default:
				break;
		}
	}
	
	@Override
	public void update() {
		
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
	
	private void doAdjustCamera(final double x, final double y, final double z) {
		if(CAMERA_TYPE == CameraType.CAMERA) {
			final Renderer renderer = getRenderer();
			
			if(renderer instanceof RayTracingRenderer) {
				final RayTracingRenderer rayTracingRenderer = RayTracingRenderer.class.cast(renderer);
				
				final Camera camera = rayTracingRenderer.getCamera();
				
				if(camera instanceof PerspectiveCamera) {
					final PerspectiveCamera perspectiveCamera = PerspectiveCamera.class.cast(camera);
					
					final
					Point point = perspectiveCamera.getOrigin();
					point.add(x, y, z);
				} else {
					final Transform transform = camera.getCameraToWorld();
					
					transform.set(transform.multiply(Transform.translate(x, y, z)));
				}
				
				doClear();
			}
		}
	}
	
	private void doAdjustSimpleCamera(final double x, final double y, final double z) {
		if(CAMERA_TYPE == CameraType.SIMPLE_CAMERA) {
			final Renderer renderer = getRenderer();
			
			if(renderer instanceof RayTracingRenderer) {
				final RayTracingRenderer rayTracingRenderer = RayTracingRenderer.class.cast(renderer);
				
				final SimpleCamera simpleCamera = rayTracingRenderer.getSimpleCamera();
				
				final Point eye = simpleCamera.getEye();
				
				simpleCamera.setEye(new Point(eye.getX() + x, eye.getY() + y, eye.getZ() + z));
				simpleCamera.calculateOrthonormalBasis();
				
				doClear();
			}
		}
	}
	
	private void doClear() {
		final Display display = getDisplay();
		
		for(final PixelIterable pixelIterable : display.getPixelIterables()) {
			pixelIterable.forEach(pixel -> pixel.clear());
		}
		
		final
		Renderer renderer = getRenderer();
		renderer.resetPass();
	}
	
	private void doConfigureCamera() {
		if(CAMERA_TYPE == CameraType.CAMERA) {
			final Renderer renderer = getRenderer();
			
			if(renderer instanceof RayTracingRenderer) {
				final RayTracingRenderer rayTracingRenderer = RayTracingRenderer.class.cast(renderer);
				
				final Camera camera = rayTracingRenderer.getCamera();
				
				final Point source = Point.valueOf(50.0D, 42.0D, 295.6D);//Point.zero();
				final Point target = new Point(0.0D, 0.0D, -2.0D);
				
				final Vector up = Vector.y();
				
				final
				Transform transform = camera.getCameraToWorld();
				transform.set(Transform.lookAt(source, target, up));
			}
		}
	}
	
	private void doConfigureDisplay() {
		final
		WickedDisplay wickedDisplay = WickedDisplay.class.cast(getDisplay());
		wickedDisplay.configure();
		wickedDisplay.addCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).getCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).setLocation(10, 10).setSelected(wickedDisplay.getConfiguration().isRenderingInRealtime()).setText("Realtime rendering").setVisible(true);
		wickedDisplay.getCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).setOnSelectionChange(checkBox -> {
			wickedDisplay.getConfiguration().setRenderingInRealtime(wickedDisplay.getCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).isSelected());
			wickedDisplay.getConfiguration().setDepthUntilProbabilisticallyTerminatingRay(wickedDisplay.getCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).isSelected() ? 2 : 5);
			wickedDisplay.getConfiguration().setSkippingProbabilisticallyTerminatingRay(wickedDisplay.getCheckBox(ID_CHECK_BOX_REALTIME_RENDERING).isSelected());
			
			doConfigureSimpleCamera();
		});
//		wickedDisplay.addLabel(ID_LABEL_SAMPLES).getLabel(ID_LABEL_SAMPLES).setLocation(10, 50).setText("Samples: 0");
//		wickedDisplay.addLabel(ID_LABEL_SAMPLES_PER_SECOND).getLabel(ID_LABEL_SAMPLES_PER_SECOND).setLocation(10, 70).setText("Samples per second: 0");
	}
	
	private void doConfigurePathTracingRenderer() {
		final Renderer renderer = getRenderer();
		
		if(renderer instanceof PathTracingRenderer) {
			final
			PathTracingRenderer pathTracingRenderer = PathTracingRenderer.class.cast(renderer);
			pathTracingRenderer.setUsingSimpleCamera(CAMERA_TYPE == CameraType.SIMPLE_CAMERA);
		}
	}
	
	private void doConfigureScene() {
		
	}
	
	private void doConfigureSimpleCamera() {
		if(CAMERA_TYPE == CameraType.SIMPLE_CAMERA) {
			final Renderer renderer = getRenderer();
			
			if(renderer instanceof RayTracingRenderer) {
				final RayTracingRenderer rayTracingRenderer = RayTracingRenderer.class.cast(renderer);
				
				final SimpleCamera simpleCamera = rayTracingRenderer.getSimpleCamera();
				
				if(simpleCamera instanceof NewSimpleCamera) {
					final
					NewSimpleCamera newSimpleCamera = NewSimpleCamera.class.cast(simpleCamera);
					newSimpleCamera.setEye(new Point(50.0D, 42.0D, 155.6D));
					newSimpleCamera.setLookAt(new Point(50.0D, 42.0D, getDisplay().getConfiguration().isRenderingInRealtime() ? -800.0D : -800.0D));
					newSimpleCamera.setViewPlaneDistance(getDisplay().getConfiguration().isRenderingInRealtime() ? 800.0D : 800.0D);
					newSimpleCamera.calculateOrthonormalBasis();
				} else if(simpleCamera instanceof OldSimpleCamera) {
					final
					OldSimpleCamera oldSampleCamera = OldSimpleCamera.class.cast(simpleCamera);
					oldSampleCamera.setEye(new Point(50.0D, 42.0D, 295.6D));
					oldSampleCamera.calculateOrthonormalBasis();
				}
			}
		}
	}
	
	private void doRotateCameraAlongX(final double x) {
		if(CAMERA_TYPE == CameraType.CAMERA) {
			final Renderer renderer = getRenderer();
			
			if(renderer instanceof RayTracingRenderer) {
				final RayTracingRenderer rayTracingRenderer = RayTracingRenderer.class.cast(renderer);
				
				final Camera camera = rayTracingRenderer.getCamera();
				
				final Transform transform = camera.getCameraToWorld();
				
				transform.set(transform.multiply(Transform.rotateX(x)));
				
				doClear();
			}
		}
	}
	
	private void doRotateCameraAlongY(final double y) {
		if(CAMERA_TYPE == CameraType.CAMERA) {
			final Renderer renderer = getRenderer();
			
			if(renderer instanceof RayTracingRenderer) {
				final RayTracingRenderer rayTracingRenderer = RayTracingRenderer.class.cast(renderer);
				
				final Camera camera = rayTracingRenderer.getCamera();
				
				final Transform transform = camera.getCameraToWorld();
				
				transform.set(transform.multiply(Transform.rotateY(y)));
				
				doClear();
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static enum CameraType {
		CAMERA,
		SIMPLE_CAMERA;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private CameraType() {
			
		}
	}
}