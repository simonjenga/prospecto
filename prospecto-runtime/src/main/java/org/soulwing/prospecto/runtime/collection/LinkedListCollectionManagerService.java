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

import java.util.LinkedList;
import java.util.List;

import org.soulwing.prospecto.api.collection.CollectionManager;

/**
 * A {@link CollectionManagerService} backed by a {@link LinkedList}.
 *
 * @author Carl Harris
 */
public class LinkedListCollectionManagerService
    implements CollectionManagerService {

  private final List<CollectionManager<?, ?>> managers = new LinkedList<>();

  @Override
  public CollectionManager<?, ?> findManager(Class<?> ownerClass,
      Class<?> elementClass) {
    for (final CollectionManager<?, ?> manager : managers) {
      if (manager.supports(ownerClass, elementClass)) return manager;
    }
    return null;
  }

  @Override
  public void append(CollectionManager manager) {
    managers.add(manager);
  }

  @Override
  public void prepend(CollectionManager manager) {
    managers.add(0, manager);
  }

  @Override
  public boolean remove(CollectionManager manager) {
    return managers.remove(manager);
  }

  @Override
  public List<CollectionManager<?, ?>> toList() {
    return managers;
  }

}
