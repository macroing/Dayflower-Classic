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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntPredicate;

import org.macroing.gdt.engine.configuration.Configuration;
import org.macroing.gdt.engine.configuration.ConfigurationObserver;
import org.macroing.gdt.engine.util.PRNG;

public final class Scene implements ConfigurationObserver {
	private boolean isRenderingInRealtime;
	private boolean isSkippingProbabilisticallyTerminatingRay;
	private Configuration configuration;
	private int depthUntilProbabilisticallyTerminatingRay;
	private IntPredicate intPredicate = pass -> this.isRenderingInRealtime;
	private final List<Shape> shapes = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Scene() {
		setConfiguration(Configuration.getDefaultInstance());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isIntersecting(final Intersection intersection) {
		boolean isIntersecting = false;
		
		for(final Shape shape : this.shapes) {
			isIntersecting |= shape.isIntersecting(intersection);
		}
		
		return isIntersecting;
	}
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	public Spectrum radiance(final int pass, final Intersection intersection, final PRNG pRNG) {
		if(isIntersecting(intersection)) {
			intersection.calculateSurfaceIntersectionPoint();
			
			final Shape shape = intersection.getShape();
			
			final Point surfaceIntersectionPoint = intersection.getSurfaceIntersectionPoint();
			final Point pointUV = shape.getUV(surfaceIntersectionPoint);
			
			final double u = pointUV.getX();
			final double v = pointUV.getY();
			
			if(this.intPredicate.test(pass)) {
				return shape.getTexture().getColorAt(u, v);
			}
			
			intersection.calculateSurfaceNormal(u, v);
			intersection.calculateSurfaceNormalProperlyOriented();
			
			final Ray ray = intersection.getRay();
			
			final Material material = shape.getMaterial();
			
			final Texture texture = shape.getTexture();
			
			final Spectrum color = texture.getColorAt(u, v);
			final Spectrum emission = material.getEmission();
			
	//		final double weight = material instanceof DiffuseMaterial ? 1.0D : 0.0D;
			
			if(ray.getDepth() > this.depthUntilProbabilisticallyTerminatingRay && (this.isSkippingProbabilisticallyTerminatingRay || doIsProbabilisticallyTerminatingRay(pRNG, color))) {
				return emission;//.multiply(weight);
			}
			
//			if(weight > 0.0D) {
//				return emission.multiply(weight).add(sampleLights(intersection, pRNG)).add(color.multiply(material.radiance(intersection, pRNG)));
//			} else {
				return emission.add(color.multiply(material.radiance(pass, intersection, pRNG)));
//			}
		}
		
		return RGBSpectrum.black();
	}
	
	/*
	public Spectrum sampleLights(final Intersection intersection, final PRNG pRNG) {
		final Spectrum emission = new RGBSpectrum(0.0D, 0.0D, 0.0D);
		
		final Point surfaceIntersectionPoint = intersection.getSurfaceIntersectionPoint();
		final Point pointUV = intersection.getShape().getUV(surfaceIntersectionPoint);
		
		final Vector surfaceNormalProperlyOriented = intersection.getSurfaceNormalProperlyOriented();
		
		final double u = pointUV.getX();
		final double v = pointUV.getY();
		
		final Texture texture = intersection.getShape().getTexture();
		
		final Spectrum color = texture.getColorAt(u, v);
		
		for(final Shape shape : this.shapes) {
			if(shape.isEmissive()) {
				final Vector sw = shape.getPosition().subtract(surfaceIntersectionPoint).toVector();
				final Vector su = ((Math.abs(sw.getX()) > 0.1D ? Vector.y() : Vector.x()).crossProduct(sw)).normalize();
				final Vector sv = sw.copyAndCrossProduct(su);
				
//				TODO: Fix the Sphere cast and radius requirement.
				final double cosAMax = Math.sqrt(1.0D - Sphere.class.cast(shape).getRadius() * Sphere.class.cast(shape).getRadius() / surfaceIntersectionPoint.toVector().subtract(shape.getPosition().toVector()).dotProduct(surfaceIntersectionPoint.copyAndSubtract(shape.getPosition()).toVector()));
				final double eps1 = pRNG.nextDouble();
				final double eps2 = pRNG.nextDouble();
				final double cosA = 1.0D - eps1 + eps1 * cosAMax;
				final double sinA = Math.sqrt(1.0D - cosA * cosA);
				final double phi = 2.0D * Math.PI * eps2;
				
				final Vector direction = su.multiply(Math.cos(phi)).multiply(sinA).add(sv.multiply(Math.sin(phi)).multiply(sinA)).add(sw.multiply(cosA)).normalize();
				
				intersection.setDistance(Constants.INFINITY);
				intersection.setRay(new Ray(0, surfaceIntersectionPoint, direction));
				
				if(isIntersecting(intersection) && intersection.getShape() == shape) {
					final double omega = 2.0D * Math.PI * (1.0D - cosAMax);
					
					emission.add(color.multiply(shape.getMaterial().getEmission().multiply(direction.dotProduct(surfaceNormalProperlyOriented)).multiply(omega)).multiply(1.0D / Math.PI));
				}
			}
		}
		
		return emission;
	}
	*/
	
	public void addShape(final Shape shape) {
		this.shapes.add(Objects.requireNonNull(shape, "shape == null"));
	}
	
	@Override
	public void onUpdate(final Configuration configuration) {
		this.isRenderingInRealtime = configuration.isRenderingInRealtime();
		this.isSkippingProbabilisticallyTerminatingRay = configuration.isSkippingProbabilisticallyTerminatingRay();
		this.depthUntilProbabilisticallyTerminatingRay = configuration.getDepthUntilProbabilisticallyTerminatingRay();
	}
	
	public void removeShape(final Shape shape) {
		this.shapes.remove(Objects.requireNonNull(shape, "shape == null"));
	}
	
	public void setConfiguration(final Configuration configuration) {
		if(this.configuration != null) {
			this.configuration.removeConfigurationObserver(this);
		}
		
		this.configuration = Objects.requireNonNull(configuration, "configuration == null");
		this.configuration.addConfigurationObserver(this);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Scene newCornellBox() {
		final
		Scene scene = new Scene();
		scene.addShape(Sphere.newInstance(SpecularMaterial.newInstance(Material.REFRACTIVE_INDEX_GLASS, RGBSpectrum.black()), SolidTexture.newInstance(1, 1, new RGBSpectrum(0.75D, 0.25D, 0.25D)), 1.e5D, new Point(1.e5D + 1.0D, 40.8D, 81.6D)));
		scene.addShape(Sphere.newInstance(DiffuseMaterial.newInstance(Material.REFRACTIVE_INDEX_GLASS, RGBSpectrum.black()), SolidTexture.newInstance(1, 1, new RGBSpectrum(0.25D, 0.25D, 0.75D)), 1.e5D, new Point(-1.e5D + 99.0D, 40.8D, 81.6D)));
		scene.addShape(Sphere.newInstance(SpecularMaterial.newInstance(Material.REFRACTIVE_INDEX_GLASS, RGBSpectrum.black()), SolidTexture.newInstance(1, 1, new RGBSpectrum(0.75D, 0.75D, 0.75D)), 1.e5D, new Point(50.0D, 40.8D, 1.e5D)));
		scene.addShape(Sphere.newInstance(DiffuseMaterial.newInstance(Material.REFRACTIVE_INDEX_GLASS, RGBSpectrum.black()), SolidTexture.newInstance(1, 1, new RGBSpectrum(0.5D, 0.5D, 0.5D)), 1.e5D, new Point(50.0D, 40.8D, -1.e5D + 170.0D)));
		scene.addShape(Sphere.newInstance(DiffuseMaterial.newInstance(Material.REFRACTIVE_INDEX_GLASS, RGBSpectrum.black()), SolidTexture.newInstance(1, 1, new RGBSpectrum(0.75D, 0.75D, 0.75D)), 1.e5D, new Point(50.0D, 1.e5D, 81.6D)));
		scene.addShape(Sphere.newInstance(DiffuseMaterial.newInstance(Material.REFRACTIVE_INDEX_GLASS, RGBSpectrum.black()), SolidTexture.newInstance(1, 1, new RGBSpectrum(0.75D, 0.75D, 0.75D)), 1.e5D, new Point(50.0D, -1.e5D + 81.6D, 81.6D)));
		scene.addShape(Sphere.newInstance(DiffuseMaterial.newInstance(Material.REFRACTIVE_INDEX_GLASS, RGBSpectrum.black()), NormalMapTexture.newInstance(SimpleTexture.newInstance("resources/jar/org/macroing/gdt/engine/geometry/Texture.jpg")/*SolidTexture.newInstance(1, 1, new RGBSpectrum(0.5D * 0.999D, 1.0D * 0.999D, 0.5D * 0.999D))*/, "resources/jar/org/macroing/gdt/engine/geometry/NormalMap.jpg")/*SolidTexture.newInstance(1, 1, new RGBSpectrum(0.5D * 0.999D, 1.0D * 0.999D, 0.5D * 0.999D))*/, 16.5D, new Point(27.0D, 16.5D, 47.0D)));
		scene.addShape(Sphere.newInstance(RefractiveMaterial.newInstance(Material.REFRACTIVE_INDEX_GLASS, RGBSpectrum.black()), SolidTexture.newInstance(1, 1, new RGBSpectrum(1.0D * 0.999D, 1.0D * 0.999D, 1.0D * 0.999D)), 16.5D, new Point(73.0D, 16.5D, 78.0D)));
		scene.addShape(Sphere.newInstance(DiffuseMaterial.newInstance(Material.REFRACTIVE_INDEX_GLASS, new RGBSpectrum(12.0D, 12.0D, 12.0D)), SolidTexture.newInstance(1, 1, RGBSpectrum.black()), 600.0D, new Point(50.0D, 681.6D - 0.27D, 81.6D)));
		scene.addShape(Triangle.newInstance(DiffuseMaterial.newInstance(Material.REFRACTIVE_INDEX_GLASS, new RGBSpectrum(12.0D, 12.0D, 12.0D)), SolidTexture.newInstance(1, 1, RGBSpectrum.black()), Point.valueOf(30.0D, 20.0D, 10.0D), Point.valueOf(50.0D, 20.0D, 50.0D), Point.valueOf(40.0D, 40.0D, 30.0D)));
		
		return scene;
	}
	
	public static Scene newInstance() {
		return new Scene();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static boolean doIsProbabilisticallyTerminatingRay(final PRNG pRNG, final Spectrum spectrum) {
		final double r = spectrum.getCoefficient(0);
		final double g = spectrum.getCoefficient(1);
		final double b = spectrum.getCoefficient(2);
		final double component = r > g && r > b ? r : g > b ? r : b;
		
		if(pRNG.nextDouble() < component) {
			spectrum.divide(component);
			
			return false;
		}
		
		return true;
	}
}