/*
 * File created on Mar 30, 2016
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
package org.soulwing.prospecto.runtime.node;

import org.soulwing.prospecto.api.ViewNode;
import org.soulwing.prospecto.api.listener.ViewNodePropertyEvent;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.entity.MutableViewEntity;

/**
 * TODO: DESCRIBE THE TYPE HERE
 * @author Carl Harris
 */
public class ObjectUpdater {

  public static Object update(ViewNode node, Object target, Object currentValue,
      MutableViewEntity entity, ScopedViewContext context) throws Exception {
    final Object newValue = entity != null ?
        entity.getType().newInstance() : null;

    if (newValue != null) {
      entity.inject(newValue, context);
    }

    if (newValue != null && currentValue != null
        && newValue.equals(currentValue)) {
      entity.inject(currentValue, context);
    }
    else {
      if (currentValue != null) {
        context.getListeners().entityDiscarded(
            new ViewNodePropertyEvent(node, target, currentValue, context));
      }
      if (newValue != null) {
        context.getListeners().entityCreated(
            new ViewNodePropertyEvent(node, target, newValue, context));
      }
      currentValue = newValue;
    }

    return currentValue;
  }
}
