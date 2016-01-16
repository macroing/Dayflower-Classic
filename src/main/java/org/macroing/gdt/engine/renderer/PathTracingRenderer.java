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

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BooleanSupplier;

import org.macroing.gdt.engine.camera.Camera;
import org.macroing.gdt.engine.camera.SimpleCamera;
import org.macroing.gdt.engine.display.Pixel;
import org.macroing.gdt.engine.display.PixelIterable;
import org.macroing.gdt.engine.filter.Filter;
import org.macroing.gdt.engine.filter.MitchellFilter;
import org.macroing.gdt.engine.geometry.Constants;
import org.macroing.gdt.engine.geometry.Intersection;
import org.macroing.gdt.engine.geometry.Ray;
import org.macroing.gdt.engine.geometry.Scene;
import org.macroing.gdt.engine.geometry.Spectrum;
import org.macroing.gdt.engine.sampler.Sample;
import org.macroing.gdt.engine.util.PRNG;

/**
 * A concrete implementation of the {@link RayTracingRenderer} that implements the Path Tracing rendering algorithm.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PathTracingRenderer extends RayTracingRenderer {
	private static final double SAMPLES_PER_THREAD_RECIPROCAL = 1.0D / Runtime.getRuntime().availableProcessors();
	private static final int SAMPLE_FILTER_X = 2;
	private static final int SAMPLE_FILTER_Y = 2;
	private static final int SAMPLES = 1;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicBoolean isUsingSimpleCamera = new AtomicBoolean(true);
	private final AtomicInteger pass = new AtomicInteger();
	private final AtomicLong elapsedTimeMillis = new AtomicLong();
	private final AtomicLong initialTimeMillis = new AtomicLong();
	private final AtomicLong samples = new AtomicLong();
	private final Filter filter = MitchellFilter.newInstance();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PathTracingRenderer() {
		doReset();
	}
	
	public boolean isUsingSimpleCamera() {
		return this.isUsingSimpleCamera.get();
	}
	
	/**
	 * Called when it's time to render.
	 * <p>
	 * If either {@code pixelIterable}, {@code rendererObserver} or {@code booleanSupplier} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * A {@link PixelIterable} is a data structure that can be iterated. It iterates over {@link Pixel} instances, each one referring to an individual pixel on the screen. They contain methods to manipulate their data.
	 * <p>
	 * When more than one {@code Thread} is used in the rendering process, the {@code PixelIterable} only contains a subset of all {@code Pixel} instances.
	 * <p>
	 * A {@link RendererObserver} is an entity observing per-pixel updates by a concrete {@code Renderer} implementation.
	 * 
	 * @param pixelIterable a {@link PixelIterable} can iterate over {@link Pixel} instances, each one referring to an individual pixel on the screen
	 * @param rendererObserver an entity observing per-pixel updates by a concrete {@code Renderer} implementation
	 * @param booleanSupplier a {@code BooleanSupplier} that tells us if we should cancel rendering
	 * @throws NullPointerException thrown if, and only if, either {@code pixelIterable}, {@code rendererObserver} or {@code booleanSupplier} are {@code null}
	 */
	@Override
	public void render(final PixelIterable pixelIterable, final RendererObserver rendererObserver, final BooleanSupplier booleanSupplier) {
		doRenderUsingCamera(pixelIterable, rendererObserver, booleanSupplier);
		doRenderUsingSimpleCamera(pixelIterable, rendererObserver, booleanSupplier);
		doUpdateSamples();
	}
	
	@Override
	public void resetPass() {
		this.pass.set(0);
	}
	
	public void setUsingSimpleCamera(final boolean isUsingSimpleCamera) {
		this.isUsingSimpleCamera.set(isUsingSimpleCamera);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code PathTracingRenderer} instance.
	 * 
	 * @return a new {@code PathTracingRenderer} instance
	 */
	public static PathTracingRenderer newInstance() {
		return new PathTracingRenderer();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doRenderUsingCamera(final PixelIterable pixelIterable, final RendererObserver rendererObserver, final BooleanSupplier booleanSupplier) {
		if(!isUsingSimpleCamera()) {
			final int widthScaled = pixelIterable.getWidthScaled();
			final int heightScaled = pixelIterable.getHeightScaled();
			
			final double widthScaledReciprocal = 1.0D / widthScaled;
			final double heightScaledReciprocal = 1.0D / heightScaled;
			
			final Camera camera = getCamera();
			
			final PRNG pRNG = getPRNG();
			
			final Scene scene = getScene();
			
			final
			Intersection intersection = Intersection.newInstance();
			intersection.setScene(scene);
			
			final int pass = this.pass.getAndIncrement();
			
			for(final Pixel pixel : pixelIterable) {
				if(booleanSupplier.getAsBoolean()) {
					doReset();
					
					return;
				}
				
				final int x = pixel.getX();
				final int y = pixel.getY();
				
				final
				Sample sample = Sample.newInstance();
				sample.setX(x);
				sample.setY(y);
				
				for(int sampleY = 0; sampleY < SAMPLE_FILTER_Y; sampleY++) {
					for(int sampleX = 0; sampleX < SAMPLE_FILTER_X; sampleX++) {
						for(int sample0 = 0; sample0 < SAMPLES; sample0++) {
							final double randomX = 2.0D * pRNG.nextDouble();//[0.0D, 2.0D)
							final double randomY = 2.0D * pRNG.nextDouble();//[0.0D, 2.0D)
							
							final double dx = randomX < 1.0D ? Math.sqrt(randomX) - 1.0D : 1.0D -Math.sqrt(2.0D - randomX);//this.filter.evaluate(randomX, randomX);
							final double dy = randomY < 1.0D ? Math.sqrt(randomY) - 1.0D : 1.0D -Math.sqrt(2.0D - randomY);//this.filter.evaluate(randomY, randomY);
							
							final double u = ((sampleX + 0.5D + dx) * 0.5D + x) * widthScaledReciprocal - 0.5D;
							final double v = ((sampleY + 0.5D + dy) * 0.5D + y) * heightScaledReciprocal - 0.5D;
							
							sample.setU(u);
							sample.setV(v);
							
							final Ray ray = camera.newRay(sample);
							
							intersection.setDistance(Constants.INFINITY);
							intersection.setRay(ray);
							
							final Spectrum spectrum = scene.radiance(pass, intersection, pRNG);
							
							pixel.addSubSamples(1);
							pixel.getRGBSpectrum().add(spectrum);
						}
					}
				}
				
				rendererObserver.update(pixel);
			}
		}
	}
	
	private void doRenderUsingSimpleCamera(final PixelIterable pixelIterable, final RendererObserver rendererObserver, final BooleanSupplier booleanSupplier) {
		if(isUsingSimpleCamera()) {
			final int width = pixelIterable.getWidth();
			final int height = pixelIterable.getHeight();
			final int widthScaled = pixelIterable.getWidthScaled();
			final int heightScaled = pixelIterable.getHeightScaled();
			
			final double widthScaledReciprocal = 1.0D / widthScaled;
			final double heightScaledReciprocal = 1.0D / heightScaled;
			
			final SimpleCamera simpleCamera = getSimpleCamera();
			
			final PRNG pRNG = getPRNG();
			
			final Scene scene = getScene();
			
			final
			Intersection intersection = Intersection.newInstance();
			intersection.setScene(scene);
			
			final int pass = this.pass.getAndIncrement();
			
			for(final Pixel pixel : pixelIterable) {
				if(booleanSupplier.getAsBoolean()) {
					doReset();
					
					return;
				}
				
				final int x = pixel.getX();
				final int y = pixel.getY();
				
				final double randomX = 2.0D * pRNG.nextDouble();//[0.0D, 2.0D)
				final double randomY = 2.0D * pRNG.nextDouble();//[0.0D, 2.0D)
				
				final double dx = randomX < 1.0D ? Math.sqrt(randomX) - 1.0D : 1.0D -Math.sqrt(2.0D - randomX);
				final double dy = randomY < 1.0D ? Math.sqrt(randomY) - 1.0D : 1.0D -Math.sqrt(2.0D - randomY);
				
				final double u = x - width / 2.0D + 0.5D;//((0.5D + dx) * 0.5D + x) * widthScaledReciprocal - 0.5D;
				final double v = y - height / 2.0D + 0.5D;//((0.5D + dy) * 0.5D + y) * heightScaledReciprocal - 0.5D;
				
				final Ray ray = simpleCamera.newRay(u, v);//(-0.5D, 0.5D), (-0.5D, 0.5D)?
				
				intersection.setDistance(Constants.INFINITY);
				intersection.setRay(ray);
				
				final Spectrum spectrum = scene.radiance(pass, intersection, pRNG);
				
				pixel.addSubSamples(1);
				pixel.getRGBSpectrum().add(spectrum);
				
				rendererObserver.update(pixel);
			}
		}
	}
	
	private void doReset() {
		this.pass.set(0);
		this.elapsedTimeMillis.set(0L);
		this.initialTimeMillis.set(System.currentTimeMillis());
		this.samples.set(0L);
	}
	
	private void doUpdateSamples() {
		final long initialTimeMillis = this.initialTimeMillis.get();
		final long elapsedTimeMillis = this.elapsedTimeMillis.updateAndGet(millis -> System.currentTimeMillis() - initialTimeMillis);
		
		final double samples = this.samples.addAndGet(SAMPLE_FILTER_Y * SAMPLE_FILTER_X) * SAMPLES_PER_THREAD_RECIPROCAL;
		final double samplesPerSecond = samples / (elapsedTimeMillis / 1000L);
		
		System.out.printf("Samples: %.2f Samples Per Second: %.2f%n", Double.valueOf(samples), Double.valueOf(samplesPerSecond));
	}
}