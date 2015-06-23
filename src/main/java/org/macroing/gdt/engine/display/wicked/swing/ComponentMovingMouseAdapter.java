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

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

final class ComponentMovingMouseAdapter extends MouseAdapter implements ComponentListener {
	private Dimension snapSize = new Dimension(1, 1);
	private Insets edgeInsets = new Insets(0, 0, 0, 0);
	private Operation operation;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ComponentMovingMouseAdapter() {
		
	}
	
	public ComponentMovingMouseAdapter(final Component... components) {
		addComponents(components);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Dimension getSnapSize() {
		return this.snapSize;
	}
	
	public Insets getEdgeInsets() {
		return this.edgeInsets;
	}
	
	public void addComponents(final Component... components) {
		for(final Component component : components) {
			component.addComponentListener(this);
			component.addMouseListener(this);
			component.addMouseMotionListener(this);
		}
	}
	
	@Override
	public void componentHidden(final ComponentEvent e) {
		setSizeFor(e.getComponent(), true);
	}
	
	@Override
	public void componentMoved(final ComponentEvent e) {
		setSizeFor(e.getComponent(), true);
	}
	
	@Override
	public void componentResized(final ComponentEvent e) {
		setSizeFor(e.getComponent(), true);
	}
	
	@Override
	public void componentShown(final ComponentEvent e) {
		setSizeFor(e.getComponent(), true);
	}
	
	@Override
	public void mouseDragged(final MouseEvent e) {
		final Operation operation = this.operation;
		
		if(operation != null) {
			final Component component = operation.getComponent();
			
			final
			Container container = component.getParent();
			container.setComponentZOrder(component, 0);
			
			final Dimension snapSize = getSnapSize();
			final Dimension size = doGetSize(component);
			
			final Insets edgeInsets = getEdgeInsets();
			
			final int currentCursorX = e.getXOnScreen();
			final int currentCursorY = e.getYOnScreen();
			final int originalComponentWidth = operation.getOriginalComponentWidth();
			final int originalComponentHeight = operation.getOriginalComponentHeight();
			final int originalComponentX = operation.getOriginalComponentX();
			final int originalComponentY = operation.getOriginalComponentY();
			final int originalCursorX = operation.getOriginalCursorX();
			final int originalCursorY = operation.getOriginalCursorY();
			final int parentWidth = size.width;
			final int parentHeight = size.height;
			final int dragDistanceX = doGetDragDistance(currentCursorX, originalCursorX, snapSize.width);
			final int dragDistanceY = doGetDragDistance(currentCursorY, originalCursorY, snapSize.height);
			
			final State state = operation.getState();
			
			switch(state) {
				case DEFAULT: {
					break;
				}
				case MOVE: {
					int currentComponentX = originalComponentX + dragDistanceX;
					int currentComponentY = originalComponentY + dragDistanceY;
					
					if(currentComponentX < edgeInsets.left) {
						currentComponentX = edgeInsets.left;
					} else if(currentComponentX + originalComponentWidth + edgeInsets.right > parentWidth) {
						currentComponentX = parentWidth - originalComponentWidth;
					}
					
					if(currentComponentY < edgeInsets.top) {
						currentComponentY = edgeInsets.top;
					} else if(currentComponentY + originalComponentHeight + edgeInsets.bottom > parentHeight) {
						currentComponentY = parentHeight - originalComponentHeight;
					}
					
					ComponentUtilities.setLocation(component, currentComponentX, currentComponentY);
					
					break;
				}
				case RESIZE_EAST: {
					int currentComponentWidth = originalComponentWidth + dragDistanceX;
					
					if(originalComponentX + currentComponentWidth + edgeInsets.right >= parentWidth) {
						currentComponentWidth = parentWidth - originalComponentX;
					} else if(currentComponentWidth < snapSize.width) {
						currentComponentWidth = snapSize.width;
					}
					
					ComponentUtilities.setSize(component, currentComponentWidth, originalComponentHeight);
					
					break;
				}
				case RESIZE_NORTH: {
					int currentComponentY = originalComponentY + dragDistanceY;
					
					if(currentComponentY < edgeInsets.top) {
						currentComponentY = edgeInsets.top;
					} else if(currentComponentY > originalComponentY + originalComponentHeight - snapSize.height) {
						currentComponentY = originalComponentY + originalComponentHeight - snapSize.height;
					}
					
					final int currentComponentHeight = originalComponentHeight + (originalComponentY - currentComponentY) ;
					
					ComponentUtilities.setLocation(component, originalComponentX, currentComponentY);
					ComponentUtilities.setSize(component, originalComponentWidth, currentComponentHeight);
					
					break;
				}
				case RESIZE_NORTH_EAST: {
					int currentComponentWidth = originalComponentWidth + dragDistanceX;
					int currentComponentY = originalComponentY + dragDistanceY;
					
					if(originalComponentX + currentComponentWidth + edgeInsets.right >= parentWidth) {
						currentComponentWidth = parentWidth - originalComponentX;
					} else if(currentComponentWidth < snapSize.width) {
						currentComponentWidth = snapSize.width;
					}
					
					if(currentComponentY < edgeInsets.top) {
						currentComponentY = edgeInsets.top;
					} else if(currentComponentY > originalComponentY + originalComponentHeight - snapSize.height) {
						currentComponentY = originalComponentY + originalComponentHeight - snapSize.height;
					}
					
					final int currentComponentHeight = originalComponentHeight + (originalComponentY - currentComponentY) ;
					
					ComponentUtilities.setLocation(component, originalComponentX, currentComponentY);
					ComponentUtilities.setSize(component, currentComponentWidth, currentComponentHeight);
					
					break;
				}
				case RESIZE_NORTH_WEST: {
					int currentComponentX = originalComponentX + dragDistanceX;
					int currentComponentY = originalComponentY + dragDistanceY;
					
					if(currentComponentX < edgeInsets.left) {
						currentComponentX = edgeInsets.left;
					} else if(currentComponentX > originalComponentX + originalComponentWidth - snapSize.width) {
						currentComponentX = originalComponentX + originalComponentWidth - snapSize.width;
					}
					
					if(currentComponentY < edgeInsets.top) {
						currentComponentY = edgeInsets.top;
					} else if(currentComponentY > originalComponentY + originalComponentHeight - snapSize.height) {
						currentComponentY = originalComponentY + originalComponentHeight - snapSize.height;
					}
					
					final int currentComponentWidth = originalComponentWidth + (originalComponentX - currentComponentX) ;
					final int currentComponentHeight = originalComponentHeight + (originalComponentY - currentComponentY) ;
					
					ComponentUtilities.setLocation(component, currentComponentX, currentComponentY);
					ComponentUtilities.setSize(component, currentComponentWidth, currentComponentHeight);
					
					break;
				}
				case RESIZE_SOUTH: {
					int currentComponentHeight = originalComponentHeight + dragDistanceY;
					
					if(originalComponentY + currentComponentHeight + edgeInsets.bottom >= parentHeight) {
						currentComponentHeight = parentHeight - originalComponentY;
					} else if(currentComponentHeight < snapSize.height) {
						currentComponentHeight = snapSize.height;
					}
					
					ComponentUtilities.setSize(component, originalComponentWidth, currentComponentHeight);
					
					break;
				}
				case RESIZE_SOUTH_EAST: {
					int currentComponentWidth = originalComponentWidth + dragDistanceX;
					int currentComponentHeight = originalComponentHeight + dragDistanceY;
					
					if(originalComponentX + currentComponentWidth + edgeInsets.right >= parentWidth) {
						currentComponentWidth = parentWidth - originalComponentX;
					} else if(currentComponentWidth < snapSize.width) {
						currentComponentWidth = snapSize.width;
					}
					
					if(originalComponentY + currentComponentHeight + edgeInsets.bottom >= parentHeight) {
						currentComponentHeight = parentHeight - originalComponentY;
					} else if(currentComponentHeight < snapSize.height) {
						currentComponentHeight = snapSize.height;
					}
					
					ComponentUtilities.setSize(component, currentComponentWidth, currentComponentHeight);
					
					break;
				}
				case RESIZE_SOUTH_WEST: {
					int currentComponentX = originalComponentX + dragDistanceX;
					int currentComponentHeight = originalComponentHeight + dragDistanceY;
					
					if(currentComponentX < edgeInsets.left) {
						currentComponentX = edgeInsets.left;
					} else if(currentComponentX > originalComponentX + originalComponentWidth - snapSize.width) {
						currentComponentX = originalComponentX + originalComponentWidth - snapSize.width;
					}
					
					if(originalComponentY + currentComponentHeight + edgeInsets.bottom >= parentHeight) {
						currentComponentHeight = parentHeight - originalComponentY;
					} else if(currentComponentHeight < snapSize.height) {
						currentComponentHeight = snapSize.height;
					}
					
					final int currentComponentWidth = originalComponentWidth + (originalComponentX - currentComponentX) ;
					
					ComponentUtilities.setLocation(component, currentComponentX, originalComponentY);
					ComponentUtilities.setSize(component, currentComponentWidth, currentComponentHeight);
					
					break;
				}
				case RESIZE_WEST: {
					int currentComponentX = originalComponentX + dragDistanceX;
					
					if(currentComponentX < edgeInsets.left) {
						currentComponentX = edgeInsets.left;
					} else if(currentComponentX > originalComponentX + originalComponentWidth - snapSize.width) {
						currentComponentX = originalComponentX + originalComponentWidth - snapSize.width;
					}
					
					final int currentComponentWidth = originalComponentWidth + (originalComponentX - currentComponentX) ;
					
					ComponentUtilities.setLocation(component, currentComponentX, originalComponentY);
					ComponentUtilities.setSize(component, currentComponentWidth, originalComponentHeight);
					
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	
	@Override
	public void mouseEntered(final MouseEvent e) {
		
	}
	
	@Override
	public void mouseExited(final MouseEvent e) {
		
	}
	
	@Override
	public void mouseMoved(final MouseEvent e) {
		final Component component = e.getComponent();
		
		if(component != null && component instanceof JComponent) {
			final JComponent jComponent = JComponent.class.cast(component);
			
			final boolean isMovable = Boolean.class.cast(ComponentUtilities.getClientProperty(jComponent, ComponentUtilities.KEY_MOVABLE, Boolean.FALSE)).booleanValue();
			final boolean isResizable = Boolean.class.cast(ComponentUtilities.getClientProperty(jComponent, ComponentUtilities.KEY_RESIZABLE, Boolean.FALSE)).booleanValue();
			
			final int margin = 5;
			final int originalComponentWidth = component.getWidth();
			final int originalComponentHeight = component.getHeight();
			final int originalComponentX = component.getX();
			final int originalComponentY = component.getY();
			
			int originalCursorX = e.getX();
			int originalCursorY = e.getY();
			
			State state = State.DEFAULT;
			
			if(isResizable && originalCursorX < margin && originalCursorY < margin) {
				component.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
				
				state = State.RESIZE_NORTH_WEST;
			} else if(isResizable && originalCursorX < margin && originalCursorY > originalComponentHeight - margin) {
				component.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
				
				state = State.RESIZE_SOUTH_WEST;
			} else if(isResizable && originalCursorX > originalComponentWidth - margin && originalCursorY > originalComponentHeight - margin) {
				component.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
				
				state = State.RESIZE_SOUTH_EAST;
			} else if(isResizable && originalCursorX > originalComponentWidth - margin && originalCursorY < margin) {
				component.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
				
				state = State.RESIZE_NORTH_EAST;
			} else if(isResizable && originalCursorX < margin) {
				component.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
				
				state = State.RESIZE_WEST;
			} else if(isResizable && originalCursorY < margin) {
				component.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
				
				state = State.RESIZE_NORTH;
			} else if(isResizable && originalCursorX > originalComponentWidth - margin) {
				component.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				
				state = State.RESIZE_EAST;
			} else if(isResizable && originalCursorY > originalComponentHeight - margin) {
				component.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
				
				state = State.RESIZE_SOUTH;
			} else if(isMovable) {
				component.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				
				state = State.MOVE;
			} else {
				component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
				state = State.DEFAULT;
			}
			
			originalCursorX = e.getXOnScreen();
			originalCursorY = e.getYOnScreen();
			
			this.operation = new Operation(component, originalComponentWidth, originalComponentHeight, originalComponentX, originalComponentY, originalCursorX, originalCursorY, state);
		}
	}
	
	public void removeComponents(final Component... components) {
		for(final Component component : components) {
			component.removeComponentListener(this);
			component.removeMouseListener(this);
			component.removeMouseMotionListener(this);
		}
	}
	
	public void setEdgeInsets(final Insets edgeInsets) {
		this.edgeInsets = edgeInsets;
	}
	
	public void setSizeFor(final Component component, final boolean isIncreasingOnly) {
		final Dimension snapSize = getSnapSize();
		
		final int snapWidth = snapSize.width;
		final int snapHeight = snapSize.height;
		final int componentWidth = component.getWidth();
		final int componentHeight = component.getHeight();
		final int widthRemainder = componentWidth % snapWidth;
		final int heightRemainder = componentHeight % snapHeight;
		
		int newWidth = -1;
		int newHeight = -1;
		
		if(widthRemainder != 0) {
			final int halfComponentWidth = componentWidth / 2;
			
			if(!isIncreasingOnly && widthRemainder < halfComponentWidth) {
				newWidth = componentWidth - widthRemainder;
			} else {
				newWidth = componentWidth + snapWidth - widthRemainder;
			}
		}
		
		if(heightRemainder != 0) {
			final int halfComponentHeight = componentHeight / 2;
			
			if(!isIncreasingOnly && heightRemainder < halfComponentHeight) {
				newHeight = componentHeight - heightRemainder;
			} else {
				newHeight = componentHeight + snapHeight - heightRemainder;
			}
		}
		
		if(newWidth != -1 || newHeight != -1) {
			newWidth = newWidth == -1 ? componentWidth : newWidth;
			newHeight = newHeight == -1 ? componentHeight : newHeight;
			
			ComponentUtilities.setSize(component, newWidth, newHeight);
		}
	}
	
	public void setSnapSize(final Dimension snapSize) {
		this.snapSize = snapSize;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Dimension doGetSize(final Component source) {
		Dimension size = null;
		
		if(source instanceof Window) {
			final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			
			final Rectangle maximumWindowBounds = graphicsEnvironment.getMaximumWindowBounds();
			
			size = new Dimension(maximumWindowBounds.width, maximumWindowBounds.height);
		} else {
			size = source.getParent().getSize();
		}
		
		return size;
	}
	
	private static int doGetDragDistance(final int larger, final int smaller, final int snapSize) {
		final int halfWay = snapSize / 2;
		
		int dragDistance = larger - smaller;
		
		dragDistance += (dragDistance < 0) ? -halfWay : halfWay;
		dragDistance = (dragDistance / snapSize) * snapSize;
		
		return dragDistance;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class Operation {
		private final Component component;
		private final int originalComponentHeight;
		private final int originalComponentWidth;
		private final int originalComponentX;
		private final int originalComponentY;
		private final int originalCursorX;
		private final int originalCursorY;
		private final State state;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Operation(final Component component, final int originalComponentWidth, final int originalComponentHeight, final int originalComponentX, final int originalComponentY, final int originalCursorX, final int originalCursorY, final State state) {
			this.component = component;
			this.originalComponentWidth = originalComponentWidth;
			this.originalComponentHeight = originalComponentHeight;
			this.originalComponentX = originalComponentX;
			this.originalComponentY = originalComponentY;
			this.originalCursorX = originalCursorX;
			this.originalCursorY = originalCursorY;
			this.state = state;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public Component getComponent() {
			return this.component;
		}
		
		public int getOriginalComponentHeight() {
			return this.originalComponentHeight;
		}
		
		public int getOriginalComponentWidth() {
			return this.originalComponentWidth;
		}
		
		public int getOriginalComponentX() {
			return this.originalComponentX;
		}
		
		public int getOriginalComponentY() {
			return this.originalComponentY;
		}
		
		public int getOriginalCursorX() {
			return this.originalCursorX;
		}
		
		public int getOriginalCursorY() {
			return this.originalCursorY;
		}
		
		public State getState() {
			return this.state;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static enum State {
		DEFAULT,
		MOVE,
		RESIZE_EAST,
		RESIZE_NORTH,
		RESIZE_NORTH_EAST,
		RESIZE_NORTH_WEST,
		RESIZE_SOUTH,
		RESIZE_SOUTH_EAST,
		RESIZE_SOUTH_WEST,
		RESIZE_WEST;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private State() {
			
		}
	}
}