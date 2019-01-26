/*
 * File created on Apr 7, 2016
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
package org.soulwing.prospecto.runtime.entity;

import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.template.ViewNode;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;

/**
 * A factory that produces {@link InjectableViewEntity} objects.
 *
 * @author Carl Harris
 */
public interface ViewEntityFactory {

  /**
   * Creates a new view entity.
   * @param node the subject node
   * @param triggerEvent
   * @param events events associated with the nodes
   * @param context view context
   * @return view entity
   * @throws Exception
   */
  InjectableViewEntity newEntity(ViewNode node, View.Event triggerEvent, Iterable<View.Event> events,
      ScopedViewContext context) throws Exception;

}
