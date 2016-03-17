/*
 * File created on Mar 17, 2016
 *
 * Copyright (c) 2016 Carl Harris, Jr
 * and others as noted
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.soulwing.prospecto.spi;

import org.soulwing.prospecto.api.ViewReaderFactory;

/**
 * A provider for a {@link ViewReaderFactory}.
 * <p>
 * A provider supports a single textual representation format.
 *
 * @author Carl Harris
 */
public interface ViewReaderFactoryProvider {

  /**
   * Gets the provider name (e.g. 'XML', 'JSON').
   * @return provider name
   */
  String getName();

  /**
   * Creates a new factory that will produce readers for the textual
   * representation supported by this provider.
   * @return factory instance
   */
  ViewReaderFactory newFactory();

}
