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
package org.macroing.gdt.engine.display.wicked.swing;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.macroing.gdt.engine.display.Display;
import org.macroing.gdt.engine.display.DisplayObserver;
import org.macroing.gdt.engine.display.Pixel;
import org.macroing.gdt.engine.display.PixelIterable;
import org.macroing.gdt.engine.display.wicked.Button;
import org.macroing.gdt.engine.display.wicked.CheckBox;
import org.macroing.gdt.engine.display.wicked.Component;
import org.macroing.gdt.engine.display.wicked.Container;
import org.macroing.gdt.engine.display.wicked.Panel;
import org.macroing.gdt.engine.display.wicked.Pin;
import org.macroing.gdt.engine.display.wicked.Window;
import org.macroing.gdt.engine.display.wicked.WickedDisplay;
import org.macroing.gdt.engine.input.Mouse;
import org.macroing.gdt.engine.input.MousePointer;

/**
 * A {@link WickedDisplay} implementation on top of Java Swing.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SwingWickedDisplay extends WickedDisplay {
	private static final int SWING_WORKER_COUNT = Runtime.getRuntime().availableProcessors();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<PixelIterable> pixelIterables = new ArrayList<>();
	private final Map<String, Component<?>> components = new LinkedHashMap<>();
	private final MousePointer mousePointer = MousePointerImpl.newInstance();
	private final SwingWorker<?, ?>[] swingWorkers = new SwingWorker<?, ?>[SWING_WORKER_COUNT];
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private SwingWickedDisplay() {
		addWindow();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link Button} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code Button}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param id the ID of the {@code Button} to return
	 * @return a {@code Button} instance given an ID
	 * @throws IllegalArgumentException thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code Button}
	 * @throws NullPointerException thrown if, and only if, {@code id} is {@code null}
	 */
	@Override
	public Button<?> getButton(final String id) {
		return doRequireValidComponent(id, Button.class);
	}
	
	/**
	 * Returns a {@link CheckBox} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code CheckBox}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param id the ID of the {@code CheckBox} to return
	 * @return a {@code CheckBox} instance given an ID
	 * @throws IllegalArgumentException thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code CheckBox}
	 * @throws NullPointerException thrown if, and only if, {@code id} is {@code null}
	 */
	@Override
	public CheckBox<?> getCheckBox(final String id) {
		return doRequireValidComponent(id, CheckBox.class);
	}
	
	/**
	 * Returns a {@link Component} instance given an {@code Object}.
	 * <p>
	 * If {@code componentObject} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that {@code Object}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * To clarify, {@code componentObject} should be equal to the {@code Object} returned by the {@code getComponentObject()} method of a {@code Component} instance, in order for that {@code Component} instance to be returned by this method.
	 * <p>
	 * There should be at most one {@code Component} for which this criterion is satisfied, otherwise there may be a bug in the implementation.
	 * 
	 * @param componentObject the {@code Object} assigned to the {@code Component} to return
	 * @return a {@code Component} instance given an {@code Object}
	 * @throws IllegalArgumentException thrown if, and only if, no {@code Component} has been assigned that {@code Object}
	 * @throws NullPointerException thrown if, and only if, {@code componentObject} is {@code null}
	 */
	@Override
	public Component<?> getComponentByComponentObject(final Object componentObject) {
		for(final Component<?> component : this.components.values()) {
			if(component.getComponentObject().equals(componentObject)) {
				return component;
			}
		}
		
		throw new IllegalArgumentException(String.format("No such Object: %s", componentObject));
	}
	
	/**
	 * Returns a {@link Container} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code Container}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param id the ID of the {@code Container} to return
	 * @return a {@code Container} instance given an ID
	 * @throws IllegalArgumentException thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code Container}
	 * @throws NullPointerException thrown if, and only if, {@code id} is {@code null}
	 */
	@Override
	public Container<?> getContainer(final String id) {
		return doRequireValidComponent(id, Container.class);
	}
	
	/**
	 * Returns the height currently set for this {@code SwingWickedDisplay} instance.
	 * 
	 * @return the height currently set for this {@code SwingWickedDisplay} instance
	 */
	@Override
	public int getHeight() {
		final Window<?> window = getWindow();
		
		final JFrame jFrame = JFrame.class.cast(window.getComponentObject());
		
		if(SwingUtilities.isEventDispatchThread()) {
			return jFrame.getHeight();
		}
		final int[] height = new int[1];
		
		doInvokeAndWait(() -> {
			height[0] = jFrame.getHeight();
		});
		
		return height[0];
	}
	
	/**
	 * Returns the width currently set for this {@code SwingWickedDisplay} instance.
	 * 
	 * @return the width currently set for this {@code SwingWickedDisplay} instance
	 */
	@Override
	public int getWidth() {
		final Window<?> window = getWindow();
		
		final JFrame jFrame = JFrame.class.cast(window.getComponentObject());
		
		if(SwingUtilities.isEventDispatchThread()) {
			return jFrame.getWidth();
		}
		
		final int[] width = new int[1];
		
		doInvokeAndWait(() -> {
			width[0] = jFrame.getWidth();
		});
		
		return width[0];
	}
	
	/**
	 * Returns a {@code List} with all currently assigned {@link PixelIterable} instances.
	 * <p>
	 * Modifying the returned {@code List} should not affect this {@code SwingWickedDisplay} instance.
	 * 
	 * @return a {@code List} with all currently assigned {@code PixelIterable} instances
	 */
	@Override
	public List<PixelIterable> getPixelIterables() {
		return new ArrayList<>(this.pixelIterables);
	}
	
	/**
	 * Returns a {@link Panel} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code Panel}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param id the ID of the {@code Panel} to return
	 * @return a {@code Panel} instance given an ID
	 * @throws IllegalArgumentException thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code Panel}
	 * @throws NullPointerException thrown if, and only if, {@code id} is {@code null}
	 */
	@Override
	public Panel<?> getPanel(final String id) {
		return doRequireValidComponent(id, Panel.class);
	}
	
	/**
	 * Returns the {@link PixelIterable} with the given index.
	 * <p>
	 * If {@code index} is invalid, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the {@code PixelIterable} to return
	 * @return the {@code PixelIterable} with the given index
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is invalid
	 */
	@Override
	public PixelIterable getPixelIterableAt(final int index) {
		return this.pixelIterables.get(index);
	}
	
	//TODO: Comment...
	@Override
	public SwingWickedDisplay addButton(final String id, final String parentId) {
		if(id != null && parentId != null && !this.components.containsKey(id) && this.components.containsKey(parentId)) {
			final Component<?> component = this.components.get(parentId);
			
			if(component instanceof Container) {
				final Button<?> button = new ButtonImpl(id, this);
				
				JButton.class.cast(button.getComponentObject()).addComponentListener(new ComponentListenerImpl(button));
				
				this.components.put(id, button);
				
				final
				Container<?> container = Container.class.cast(component);
				container.addComponent(button);
			}
		}
		
		return this;
	}
	
	//TODO: Comment...
	@Override
	public SwingWickedDisplay addCheckBox(final String id, final String parentId) {
		if(id != null && parentId != null && !this.components.containsKey(id) && this.components.containsKey(parentId)) {
			final Component<?> component = this.components.get(parentId);
			
			if(component instanceof Container) {
				final CheckBox<?> checkBox = new CheckBoxImpl(id, this);
				
				JCheckBox.class.cast(checkBox.getComponentObject()).addComponentListener(new ComponentListenerImpl(checkBox));
				
				this.components.put(id, checkBox);
				
				final
				Container<?> container = Container.class.cast(component);
				container.addComponent(checkBox);
			}
		}
		
		return this;
	}
	
	//TODO: Comment...
	@Override
	public SwingWickedDisplay addPanel(final String id, final String parentId) {
		if(id != null && parentId != null && !this.components.containsKey(id) && this.components.containsKey(parentId)) {
			final Component<?> component = this.components.get(parentId);
			
			if(component instanceof Container) {
				final Panel<?> panel = new PanelImpl(id, this);
				
				JPanel.class.cast(panel.getComponentObject()).addComponentListener(new ComponentListenerImpl(panel));
				
				this.components.put(id, panel);
				
				final
				Container<?> container = Container.class.cast(component);
				container.addComponent(panel);
			}
		}
		
		return this;
	}
	
	//TODO: Comment...
	@Override
	public SwingWickedDisplay addWindow(final String id) {
		if(id != null && !this.components.containsKey(id)) {
			final Window<?> window = new WindowImpl(id, this);
			
			this.components.put(id, window);
		}
		
		return this;
	}
	
	//TODO: Comment...
	@Override
	public SwingWickedDisplay updateComponentPin(final Component<?> component) {
		if(component != null) {
			final int x = component.getX();
			final int y = component.getY();
			final int width = component.getWidth();
			final int height = component.getHeight();
			
			final String id = component.getId();
			
			doUpdateComponentPin(id + ".NorthWest", x, y);
			doUpdateComponentPin(id + ".North", x + width / 2, y);
			doUpdateComponentPin(id + ".NorthEast", x + width, y);
			doUpdateComponentPin(id + ".East", x + width, y + height / 2);
			doUpdateComponentPin(id + ".SouthEast", x + width, y + height);
			doUpdateComponentPin(id + ".South", x + width / 2, y + height);
			doUpdateComponentPin(id + ".SouthWest", x, y + height);
			doUpdateComponentPin(id + ".West", x, y + height / 2);
		}
		
		return this;
	}
	
	/**
	 * Call this method to clear what's been rendered so far.
	 */
	@Override
	public void clear() {
		for(final PixelIterable pixelIterable : this.pixelIterables) {
			for(final Pixel pixel : pixelIterable) {
				pixel.clear();
				pixel.update();
			}
		}
	}
	
	/**
	 * Configures this {@code SwingWickedDisplay} instance.
	 * <p>
	 * This method needs to be called after you have changed any properties of this {@code SwingWickedDisplay} instance.
	 */
	@Override
	public void configure() {
		final int width = isSuperSamplingWithDownScaling() && !isRenderingInRealtime() ? getWidth() * getWidthScale() : getWidth() / getWidthScale();
		final int height = isSuperSamplingWithDownScaling() && !isRenderingInRealtime() ? getHeight() * getHeightScale() : getHeight() / getHeightScale();
		
		final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		final int[] rGB = toRGB(bufferedImage);
		
		this.pixelIterables.clear();
		this.pixelIterables.addAll(PixelIterable.createPixelIterablesFor(width, height, rGB, SWING_WORKER_COUNT));
		
		doInvokeAndWait(() -> {
			final Window<?> window = getWindow();
			
			final
			JFrame jFrame = JFrame.class.cast(window.getComponentObject());
			jFrame.getContentPane().removeAll();
			jFrame.getContentPane().add(BufferedImageJPanel.newInstance(bufferedImage, isRenderingInRealtime(), getWidth(), getHeight()));
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setFocusTraversalKeysEnabled(false);
			jFrame.setIgnoreRepaint(true);
			jFrame.setLocationRelativeTo(null);
		});
	}
	
	/**
	 * Hides this {@code SwingWickedDisplay} instance.
	 * <p>
	 * In addition to hiding itself, it will also:
	 * <ul>
	 * <li>Dispose of any used resources.</li>
	 * </ul>
	 * <p>
	 * If this method is called while in a hidden state, nothing will happen.
	 */
	@Override
	public void hide() {
		doInvokeAndWait(() -> {
			final Window<?> window = getWindow();
			
			final JFrame jFrame = JFrame.class.cast(window.getComponentObject());
			
			if(jFrame.isVisible()) {
				doCancelSwingWorkers();
				
				jFrame.setVisible(false);
				jFrame.dispose();
			}
		});
	}
	
	/**
	 * Renders this {@code SwingWickedDisplay} instance.
	 */
	@Override
	public void render() {
		final Window<?> window = getWindow();
		
		final
		JFrame jFrame = JFrame.class.cast(window.getComponentObject());
		jFrame.repaint();
	}
	
	/**
	 * Sets a new height for this {@code SwingWickedDisplay} instance.
	 * 
	 * @param height the new height
	 */
	@Override
	public void setHeight(final int height) {
		doInvokeAndWait(() -> {
			final Window<?> window = getWindow();
			
			final
			JFrame jFrame = JFrame.class.cast(window.getComponentObject());
			jFrame.setSize(jFrame.getWidth(), height);
		});
	}
	
	/**
	 * Sets the title for this {@code SwingWickedDisplay} instance.
	 * <p>
	 * If {@code title} is {@code null}, the empty {@code String} {@code ""} will be set.
	 * 
	 * @param title the title for this {@code Display} instance
	 */
	@Override
	public void setTitle(final String title) {
		doInvokeAndWait(() -> {
			final Window<?> window = getWindow();
			
			final
			JFrame jFrame = JFrame.class.cast(window.getComponentObject());
			jFrame.setTitle(title);
		});
	}
	
	/**
	 * Sets a new width for this {@code SwingWickedDisplay} instance.
	 * 
	 * @param width the new width
	 */
	@Override
	public void setWidth(final int width) {
		doInvokeAndWait(() -> {
			final Window<?> window = getWindow();
			
			final
			JFrame jFrame = JFrame.class.cast(window.getComponentObject());
			jFrame.setSize(width, jFrame.getHeight());
		});
	}
	
	/**
	 * Shows this {@code SwingWickedDisplay} instance.
	 * <p>
	 * In addition to showing itself, it will also:
	 * <ul>
	 * <li>Set a {@link MousePointer} in the {@link Mouse} instance.</li>
	 * </ul>
	 * <p>
	 * If this method is called while in a shown state, nothing will happen.
	 */
	@Override
	public void show() {
		doInvokeAndWait(() -> {
			final Window<?> window = getWindow();
			
			final JFrame jFrame = JFrame.class.cast(window.getComponentObject());
			
			if(!jFrame.isVisible()) {
				Mouse.getInstance().setMousePointer(this.mousePointer);
				
				jFrame.setVisible(true);
				jFrame.createBufferStrategy(2);
				
				doConfigureAndExecuteSwingWorkers();
			}
		});
	}
	
	/**
	 * Returns a {@link Window} instance given an ID.
	 * <p>
	 * If {@code id} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@link Component} with that ID already exists, and it's not a {@code Window}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If no {@code Component} has been assigned that ID, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param id the ID of the {@code Window} to return
	 * @return a {@code Window} instance given an ID
	 * @throws IllegalArgumentException thrown if, and only if, either no {@code Component} has been assigned the given ID, or a {@code Component} has been assigned the given ID, but it's not a {@code Window}
	 * @throws NullPointerException thrown if, and only if, {@code id} is {@code null}
	 */
	@Override
	public Window<?> getWindow(final String id) {
		return doRequireValidComponent(id, Window.class);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code SwingWickedDisplay} instance.
	 * 
	 * @return a new {@code SwingWickedDisplay} instance
	 */
	public static SwingWickedDisplay newInstance() {
		return new SwingWickedDisplay();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private <T> T doRequireValidComponent(final String id, final Class<T> clazz) {
		final Object object = this.components.get(Objects.requireNonNull(id, "id == null"));
		
		if(object == null) {
			throw new IllegalArgumentException(String.format("A Component of type '%s' and ID '%s' does not exist.", clazz.getName(), id));
		} else if(!clazz.isAssignableFrom(object.getClass())) {
			throw new IllegalArgumentException(String.format("A Component of type '%s' and ID '%s' cannot be assigned from a Component of type '%s'.", clazz.getName(), id, object.getClass().getName()));
		}
		
		return clazz.cast(object);
	}
	
	private void doCancelSwingWorkers() {
		synchronized(this.swingWorkers) {
			for(int i = 0; i < this.swingWorkers.length; i++) {
				if(this.swingWorkers[i] != null) {
					this.swingWorkers[i].cancel(false);
					this.swingWorkers[i] = null;
				}
			}
		}
	}
	
	private void doConfigureAndExecuteSwingWorkers() {
		final Window<?> window = getWindow();
		
		final JFrame jFrame = JFrame.class.cast(window.getComponentObject());
		
		synchronized(this.swingWorkers) {
			for(int i = 0; i < this.swingWorkers.length; i++) {
				if(this.swingWorkers[i] == null) {
					this.swingWorkers[i] = new SwingWorkerImpl(this, i, jFrame);
					this.swingWorkers[i].execute();
				}
			}
		}
	}
	
	private void doUpdateComponentPin(final String id, final int x, final int y) {
		final
		Pin pin = getPin(id);
		pin.setX(x);
		pin.setY(y);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static int[] toRGB(final BufferedImage bufferedImage) {
		final WritableRaster writableRaster = bufferedImage.getRaster();
		
		final DataBuffer dataBuffer = writableRaster.getDataBuffer();
		
		final DataBufferInt dataBufferInt = DataBufferInt.class.cast(dataBuffer);
		
		final int[] rGB = dataBufferInt.getData();
		
		return rGB;
	}
	
	private static void doInvokeAndWait(final Runnable runnable) {
		try {
			SwingUtilities.invokeAndWait(runnable);
		} catch(final InterruptedException | InvocationTargetException e) {
			
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class SwingWorkerImpl extends SwingWorker<Void, Void> {
		private final Display display;
		private final int index;
		private final JFrame jFrame;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		SwingWorkerImpl(final Display display, final int index, final JFrame jFrame) {
			this.display = display;
			this.index = index;
			this.jFrame = jFrame;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		protected Void doInBackground() throws Exception {
			final DisplayObserver displayObserver = this.display.getDisplayObserver();
			
			final PixelIterable pixelIterable = this.display.getPixelIterableAt(this.index);
			
			while(!isCancelled()) {
				displayObserver.render(pixelIterable, pixel -> {
					pixel.update();
					
					this.jFrame.repaint();
//					this.jFrame.repaint(pixel.getX(), pixel.getY(), 1, 1);
				});
			}
			
			return null;
		}
	}
}