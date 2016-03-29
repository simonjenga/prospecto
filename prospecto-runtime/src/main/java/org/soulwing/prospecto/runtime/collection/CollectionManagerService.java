/*
 * File created on Mar 29, 2016
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
package org.soulwing.prospecto.runtime.collection;

import org.soulwing.prospecto.api.collection.CollectionManager;
import org.soulwing.prospecto.api.collection.CollectionManagers;

/**
 * A collection manager service.
 *
 * @author Carl Harris
 */
public interface CollectionManagerService extends CollectionManagers {

  /**
   * Finds the appropriate collection manager for the given owner type and
   * element type.
   * <p>
   * Registered managers are consulted in order. The first manager that
   * claims to support the given owner and element types is returned.
   *
   * @param ownerClass owner class
   * @param elementClass element class
   * @return manager or {@code null} if no manager claims to support the
   *    given types
   */
   CollectionManager<?, ?> findManager(Class<?> ownerClass,
      Class<?> elementClass);

}
