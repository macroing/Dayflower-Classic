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
package org.macroing.gdt.engine.display.wicked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * An instance of this {@code State} class captures a set of sub-states for different {@link Component}s at a particular time.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class State {
	private boolean hasApplied;
	private final List<ComponentInfo> componentInfos = new ArrayList<>();
	private final String id;
	private String text;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private State(final String id) {
		this.id = id;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Applies the sub-states provided by this {@code State} instance to the {@link Component}s bound to it.
	 * <p>
	 * Returns the {@code State} instance itself, such that method chanining is possible.
	 * 
	 * @return the {@code State} instance itself, such that method chanining is possible
	 */
	public State apply() {
		this.hasApplied = true;
		
		synchronized(this.componentInfos) {
			for(final ComponentInfo componentInfo : this.componentInfos) {
				componentInfo.apply();
			}
		}
		
		return this;
	}
	
	/**
	 * Binds {@code component} to this {@code State} instance.
	 * <p>
	 * Returns the {@code State} instance itself, such that method chanining is possible.
	 * <p>
	 * If {@code component} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code State} has already been applied, it will be applied to {@code component} immediately.
	 * 
	 * @param component the {@link Component} to bind to this {@code State} instance
	 * @return the {@code State} instance itself, such that method chanining is possible
	 * @throws NullPointerException thrown if, and only if, {@code component} is {@code null}
	 */
	public State bind(final Component<?> component) {
		final ComponentInfo componentInfo = new ComponentInfo(Objects.requireNonNull(component, "component == null"));
		
		synchronized(this.componentInfos) {
			if(!this.componentInfos.contains(componentInfo)) {
				this.componentInfos.add(componentInfo);
				
				if(this.hasApplied) {
					componentInfo.apply();
				}
			}
		}
		
		return this;
	}
	
	/**
	 * Binds all {@link Component}s provided by {@code components} to this {@code State} instance.
	 * <p>
	 * Returns the {@code State} instance itself, such that method chanining is possible.
	 * <p>
	 * If either {@code components} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code State} has already been applied, it will be applied to each {@code Component} immediately.
	 * 
	 * @param components a {@code Collection} of {@code Component}s to bind to this {@code State} instance
	 * @return the {@code State} instance itself, such that method chanining is possible
	 * @throws NullPointerException thrown if, and only if, either {@code components} or any of its elements are {@code null}
	 */
	public State bindAll(final Collection<? extends Component<?>> components) {
		synchronized(this.componentInfos) {
			for(final Component<?> component : components) {
				bind(component);
			}
		}
		
		return this;
	}
	
	/**
	 * Resets the sub-states applied to the {@link Component}s bound to this {@code State} instance.
	 * <p>
	 * Returns the {@code State} instance itself, such that method chanining is possible.
	 * 
	 * @return the {@code State} instance itself, such that method chanining is possible
	 */
	public State reset() {
		synchronized(this.componentInfos) {
			for(final ComponentInfo componentInfo : this.componentInfos) {
				componentInfo.reset();
			}
		}
		
		this.hasApplied = false;
		
		return this;
	}
	
	/**
	 * Sets the text-state for this {@code State} instance.
	 * <p>
	 * Returns the {@code State} instance itself, such that method chanining is possible.
	 * <p>
	 * If {@code text} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code State} has already been applied, it will be applied again to reflect the changed state.
	 * 
	 * @param text the text-state to set
	 * @return the {@code State} instance itself, such that method chanining is possible
	 * @throws NullPointerException thrown if, and only if, {@code text} is {@code null}
	 */
	public State setText(final String text) {
		this.text = Objects.requireNonNull(text, "text == null");
		
		if(this.hasApplied) {
			apply();
		}
		
		return this;
	}
	
	/**
	 * Unbinds {@code component} from this {@code State} instance.
	 * <p>
	 * Returns the {@code State} instance itself, such that method chanining is possible.
	 * <p>
	 * If {@code component} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code State} has already been applied, the state of {@code component} will be reset immediately.
	 * 
	 * @param component the {@link Component} to unbind from this {@code State} instance
	 * @return the {@code State} instance itself, such that method chanining is possible
	 * @throws NullPointerException thrown if, and only if, {@code component} is {@code null}
	 */
	public State unbind(final Component<?> component) {
		ComponentInfo componentInfo = new ComponentInfo(Objects.requireNonNull(component, "component == null"));
		
		synchronized(this.componentInfos) {
			this.componentInfos.remove(componentInfo);
		}
		
		if(this.hasApplied) {
			componentInfo.reset();
		}
		
		return this;
	}
	
	/**
	 * Unbinds all {@link Component}s provided by {@code components} from this {@code State} instance.
	 * <p>
	 * Returns the {@code State} instance itself, such that method chanining is possible.
	 * <p>
	 * If either {@code components} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code State} has already been applied, the state of each {@code Component} will be reset immediately.
	 * 
	 * @param components a {@code Collection} of {@code Component}s to unbind from this {@code State} instance
	 * @return the {@code State} instance itself, such that method chanining is possible
	 * @throws NullPointerException thrown if, and only if, either {@code components} or any of its elements are {@code null}
	 */
	public State unbindAll(final Collection<? extends Component<?>> components) {
		synchronized(this.componentInfos) {
			for(final Component<?> component : components) {
				unbind(component);
			}
		}
		
		return this;
	}
	
	/**
	 * Returns the ID of this {@code State} instance.
	 * 
	 * @return the ID of this {@code State} instance
	 */
	public String getId() {
		return this.id;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	String getText() {
		return this.text;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static State newInstance(final String id) {
		return new State(Objects.requireNonNull(id, "id == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final class ComponentInfo {
		private boolean hasApplied;
		private final Component<?> component;
		private long key;
		private String defaultText;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		ComponentInfo(final Component<?> component) {
			this.component = component;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public boolean equals(final Object object) {
			if(object == this) {
				return true;
			} else if(!(object instanceof ComponentInfo)) {
				return false;
			} else {
				return ComponentInfo.class.cast(object).component.equals(this.component);
			}
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.component);
		}
		
		public void apply() {
			doAttemptToLock();
			doAttemptToCacheDefaultText();
			doAttemptToApplyText();
			doSetApplied(true);
		}
		
		public void reset() {
			doAttemptToResetDefaultText();
			doAttemptToUnlock();
			doSetApplied(false);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private void doAttemptToApplyText() {
			if(this.component instanceof Button) {
				Button.class.cast(this.component).setText(getText(), this.key);
			} else if(this.component instanceof CheckBox) {
				CheckBox.class.cast(this.component).setText(getText(), this.key);
			}
		}
		
		private void doAttemptToCacheDefaultText() {
			if(!this.hasApplied) {
				if(this.component instanceof Button) {
					this.defaultText = Button.class.cast(this.component).getText();
				} else if(this.component instanceof CheckBox) {
					this.defaultText = CheckBox.class.cast(this.component).getText();
				} else {
					this.defaultText = null;
				}
			}
		}
		
		private void doAttemptToLock() {
			if(this.key == 0L) {
				this.key = this.component.lock();
			}
		}
		
		private void doAttemptToResetDefaultText() {
			if(this.hasApplied && this.defaultText != null) {
				if(this.component instanceof Button) {
					Button.class.cast(this.component).setText(this.defaultText, this.key);
				} else if(this.component instanceof CheckBox) {
					CheckBox.class.cast(this.component).setText(this.defaultText, this.key);
				}
			}
		}
		
		private void doAttemptToUnlock() {
			this.component.unlock(this.key);
		}
		
		private void doSetApplied(final boolean hasApplied) {
			this.hasApplied = hasApplied;
		}
	}
}