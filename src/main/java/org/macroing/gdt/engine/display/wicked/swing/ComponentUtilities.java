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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalLookAndFeel;

final class ComponentUtilities {
	public static final Icon J_CHECK_BOX_DISABLED_ICON = new ImageIcon(ComponentUtilities.class.getResource("JCheckBox_DisabledIcon.png"));
	public static final Icon J_CHECK_BOX_DISABLED_SELECTED_ICON = new ImageIcon(ComponentUtilities.class.getResource("JCheckBox_DisabledSelectedIcon.png"));
	public static final Icon J_CHECK_BOX_ICON = new ImageIcon(ComponentUtilities.class.getResource("JCheckBox_Icon.png"));
	public static final Icon J_CHECK_BOX_PRESSED_ICON = new ImageIcon(ComponentUtilities.class.getResource("JCheckBox_PressedIcon.png"));
	public static final Icon J_CHECK_BOX_ROLLOVER_ICON = new ImageIcon(ComponentUtilities.class.getResource("JCheckBox_RolloverIcon.png"));
	public static final Icon J_CHECK_BOX_ROLLOVER_SELECTED_ICON = new ImageIcon(ComponentUtilities.class.getResource("JCheckBox_RolloverSelectedIcon.png"));
	public static final Icon J_CHECK_BOX_SELECTED_ICON = new ImageIcon(ComponentUtilities.class.getResource("JCheckBox_SelectedIcon.png"));
	public static final Icon J_RADIO_BUTTON_DISABLED_ICON = new ImageIcon(ComponentUtilities.class.getResource("JRadioButton_DisabledIcon.png"));
	public static final Icon J_RADIO_BUTTON_DISABLED_SELECTED_ICON = new ImageIcon(ComponentUtilities.class.getResource("JRadioButton_DisabledSelectedIcon.png"));
	public static final Icon J_RADIO_BUTTON_ICON = new ImageIcon(ComponentUtilities.class.getResource("JRadioButton_Icon.png"));
	public static final Icon J_RADIO_BUTTON_PRESSED_ICON = new ImageIcon(ComponentUtilities.class.getResource("JRadioButton_PressedIcon.png"));
	public static final Icon J_RADIO_BUTTON_ROLLOVER_ICON = new ImageIcon(ComponentUtilities.class.getResource("JRadioButton_RolloverIcon.png"));
	public static final Icon J_RADIO_BUTTON_ROLLOVER_SELECTED_ICON = new ImageIcon(ComponentUtilities.class.getResource("JRadioButton_RolloverSelectedIcon.png"));
	public static final Icon J_RADIO_BUTTON_SELECTED_ICON = new ImageIcon(ComponentUtilities.class.getResource("JRadioButton_SelectedIcon.png"));
	public static final String KEY_LAYOUT_MANAGER = "LayoutManager";
	public static final String KEY_MOVABLE = "Movable";
	public static final String KEY_RESIZABLE = "Resizable";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ComponentUtilities() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static {
		MetalLookAndFeel.setCurrentTheme(new MetalThemeImpl());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static boolean isResizable(final Frame frame) {
		return frame != null && runInEDT(() -> Boolean.valueOf(frame.isResizable())).booleanValue();
	}
	
	public static boolean isSelected(final AbstractButton abstractButton) {
		return abstractButton != null && runInEDT(() -> Boolean.valueOf(abstractButton.isSelected())).booleanValue();
	}
	
	public static boolean isVisible(final Component component) {
		return component != null && runInEDT(() -> Boolean.valueOf(component.isVisible())).booleanValue();
	}
	
	public static Component[] getComponents(final Container container) {
		synchronized(container.getTreeLock()) {
			return container.getComponents();
		}
	}
	
	public static int getHeight(final Component component) {
		return component != null ? runInEDT(() -> Integer.valueOf(component.getHeight())).intValue() : 0;
	}
	
	public static int getWidth(final Component component) {
		return component != null ? runInEDT(() -> Integer.valueOf(component.getWidth())).intValue() : 0;
	}
	
	public static int getX(final Component component) {
		return component != null ? runInEDT(() -> Integer.valueOf(component.getX())).intValue() : 0;
	}
	
	public static int getY(final Component component) {
		return component != null ? runInEDT(() -> Integer.valueOf(component.getY())).intValue() : 0;
	}
	
	public static JButton newJButton() {
		return runInEDT(() -> new JButton(), jButton -> {
			jButton.setFocusable(false);
		});
	}
	
	public static JCheckBox newJCheckBox() {
		return runInEDT(() -> new JCheckBox(), jCheckBox -> {
			jCheckBox.setBorderPainted(false);
			jCheckBox.setDisabledIcon(Icons.getTranslucentIcon(jCheckBox, J_CHECK_BOX_DISABLED_ICON, 200.0F, 200.0F, 200.0F));
			jCheckBox.setDisabledSelectedIcon(Icons.getTranslucentIcon(jCheckBox, J_CHECK_BOX_DISABLED_SELECTED_ICON, 200.0F, 200.0F, 200.0F));
			jCheckBox.setFocusable(false);
			jCheckBox.setFocusPainted(false);
			jCheckBox.setForeground(Color.WHITE);
			jCheckBox.setIcon(Icons.getTranslucentIcon(jCheckBox, J_CHECK_BOX_ICON, 255.0F, 255.0F, 255.0F));
			jCheckBox.setOpaque(false);
			jCheckBox.setPressedIcon(Icons.getTranslucentIcon(jCheckBox, J_CHECK_BOX_PRESSED_ICON, 255.0F, 255.0F, 255.0F));
			jCheckBox.setRolloverIcon(Icons.getTranslucentIcon(jCheckBox, J_CHECK_BOX_ROLLOVER_ICON, 255.0F, 255.0F, 255.0F));
			jCheckBox.setRolloverSelectedIcon(Icons.getTranslucentIcon(jCheckBox, J_CHECK_BOX_ROLLOVER_SELECTED_ICON, 255.0F, 255.0F, 255.0F));
			jCheckBox.setSelectedIcon(Icons.getTranslucentIcon(jCheckBox, J_CHECK_BOX_SELECTED_ICON, 255.0F, 255.0F, 255.0F));
		});
	}
	
	public static JFrame newJFrame() {
		return runInEDT(() -> new JFrame(), jFrame -> {
			jFrame.addKeyListener(KeyListenerImpl.newInstance(jFrame));
			jFrame.addMouseListener(MouseListenerImpl.newInstance(jFrame));
			jFrame.addMouseMotionListener(MouseMotionListenerImpl.newInstance(jFrame));
			jFrame.addMouseWheelListener(MouseWheelListenerImpl.newInstance(jFrame));
			jFrame.getContentPane().setLayout(new LayoutManagerImpl());
		});
	}
	
	public static JLabel newJLabel() {
		return runInEDT(() -> new JLabel(), jLabel -> {
			jLabel.setFocusable(false);
			jLabel.setForeground(Color.WHITE);
		});
	}
	
	public static JPanel newJPanel() {
		return runInEDT(() -> new JPanel(), jPanel -> {
			jPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			jPanel.setLayout(new LayoutManagerImpl());
		});
	}
	
	public static List<Component> getAllComponentsFrom(final Component component) {
		return getAllComponentsFrom(component, new ArrayList<>());
	}
	
	public static List<Component> getAllComponentsFrom(final Component component, List<Component> components) {
		if(component instanceof Container) {
			Arrays.stream(getComponents(Container.class.cast(component))).forEach(component0 -> getAllComponentsFrom(component0, components));
		}
		
		return components;
	}
	
	public static Object getClientProperty(final JComponent jComponent, final Object key, final Object value) {
		return jComponent != null && key != null ? runInEDT(() -> jComponent.getClientProperty(key), null, value) : value;
	}
	
	public static String getText(final AbstractButton abstractButton) {
		return abstractButton != null ? runInEDT(() -> abstractButton.getText(), null, "") : "";
	}
	
	public static String getText(final JLabel jLabel) {
		return jLabel != null ? runInEDT(() -> jLabel.getText(), null, "") : "";
	}
	
	public static String getTitle(final Frame frame) {
		return frame != null ? runInEDT(() -> frame.getTitle(), null, "") : "";
	}
	
	public static <T> T runInEDT(final Supplier<T> supplier) {
		return runInEDT(supplier, object -> {});
	}
	
	public static <T> T runInEDT(final Supplier<T> supplier, final Consumer<T> consumer) {
		return runInEDT(supplier, consumer, null);
	}
	
	public static <T> T runInEDT(final Supplier<T> supplier, final Consumer<T> consumer, final T defaultObject) {
		final AtomicReference<T> atomicReference = new AtomicReference<>();
		
		if(supplier != null) {
			if(SwingUtilities.isEventDispatchThread()) {
				atomicReference.set(supplier.get());
			} else {
				invokeAndWait(() -> atomicReference.set(supplier.get()));
			}
		}
		
		if(defaultObject != null) {
			atomicReference.compareAndSet(null, defaultObject);
		}
		
		if(consumer != null) {
			consumer.accept(atomicReference.get());
		}
		
		return atomicReference.get();
	}
	
	public static void add(final Component component, final Container container) {
		if(component != null && container != null) {
			runInEDT(() -> container.add(component));
		}
	}
	
	public static void addActionListener(final AbstractButton abstractButton, final ActionListener actionListener) {
		if(abstractButton != null && actionListener != null) {
			runInEDT(() -> abstractButton.addActionListener(actionListener));
		}
	}
	
	public static void invokeAndWait(final Runnable runnable) {
		if(runnable != null) {
			try {
				SwingUtilities.invokeAndWait(runnable);
			} catch(final InvocationTargetException | InterruptedException e) {
				
			}
		}
	}
	
	public static void putClientProperty(final JComponent jComponent, final Object key, final Object value) {
		if(jComponent != null && key != null) {
			runInEDT(() -> jComponent.putClientProperty(key, value));
		}
	}
	
	public static void removeAllActionListeners(final AbstractButton abstractButton) {
		if(abstractButton != null) {
			runInEDT(() -> {
				for(final ActionListener actionListener : abstractButton.getActionListeners()) {
					abstractButton.removeActionListener(actionListener);
				}
			});
		}
	}
	
	public static void runInEDT(final Runnable runnable) {
		if(runnable != null) {
			if(SwingUtilities.isEventDispatchThread()) {
				runnable.run();
			} else {
				invokeAndWait(runnable);
			}
		}
	}
	
	public static void setIgnoreRepaint(final Component component, final boolean ignoreRepaint) {
		if(component != null) {
			runInEDT(() -> {
				getAllComponentsFrom(component).forEach(component0 -> component0.setIgnoreRepaint(ignoreRepaint));
			});
		}
	}
	
	public static void setLocation(final Component component, final int x, final int y) {
		if(component != null && x >= 0 && y >= 0) {
			runInEDT(() -> component.setLocation(x, y));
		}
	}
	
	public static void setResizable(final Frame frame, final boolean isResizable) {
		if(frame != null) {
			runInEDT(() -> frame.setResizable(isResizable));
		}
	}
	
	public static void setSelected(final AbstractButton abstractButton, final boolean isSelected) {
		if(abstractButton != null) {
			runInEDT(() -> abstractButton.setSelected(isSelected));
		}
	}
	
	public static void setSize(final Component component, final int width, final int height) {
		if(component != null && width >= 0 && height >= 0) {
			runInEDT(() -> {
				component.setMaximumSize(new Dimension(width, height));
				component.setMinimumSize(new Dimension(width, height));
				component.setPreferredSize(new Dimension(width, height));
				component.setSize(new Dimension(width, height));
			});
		}
	}
	
	public static void setText(final AbstractButton abstractButton, final String text) {
		if(abstractButton != null && text != null) {
			runInEDT(() -> abstractButton.setText(text));
		}
	}
	
	public static void setText(final JLabel jLabel, final String text) {
		if(jLabel != null && text != null) {
			runInEDT(() -> jLabel.setText(text));
		}
	}
	
	public static void setTitle(final Frame frame, final String title) {
		if(frame != null) {
			runInEDT(() -> frame.setTitle(title));
		}
	}
	
	public static void setVisible(final Component component, final boolean isVisible) {
		if(component != null) {
			runInEDT(() -> component.setVisible(isVisible));
		}
	}
}