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
package org.macroing.gdt.engine.configuration;

/**
 * An entity observing the state change updates of a {@link Configuration} instance.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface ConfigurationObserver {
	/**
	 * Called by a {@link Configuration} instance when it's been updated.
	 * <p>
	 * If {@code configuration} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made. Although, if it's {@code null}, that's a bug, either in our or some third-party code.
	 * 
	 * @param configuration the {@code Configuration} that was updated prior to this method call
	 * @throws NullPointerException thrown if, and only if, {@code configuration} is {@code null}
	 */
	void onUpdate(final Configuration configuration);
}