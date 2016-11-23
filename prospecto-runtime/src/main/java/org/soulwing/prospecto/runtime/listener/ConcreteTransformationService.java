/*
 * File created on Apr 6, 2016
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
package org.soulwing.prospecto.runtime.listener;

import org.soulwing.prospecto.api.ViewEntity;
import org.soulwing.prospecto.api.listener.ViewMode;
import org.soulwing.prospecto.api.listener.ViewNodePropertyEvent;
import org.soulwing.prospecto.api.template.ViewNode;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;

/**
 * A {@link TransformationService} implementation.
 *
 * @author Carl Harris
 */
public class ConcreteTransformationService implements TransformationService {

  public static final ConcreteTransformationService INSTANCE =
      new ConcreteTransformationService();

  private ConcreteTransformationService() {}

  @Override
  public Object valueToExtract(Object owner, Object modelValue,
      ViewNode node, ScopedViewContext context) throws Exception {

    final Object extractedValue = context.getListeners().didExtractValue(
        new ViewNodePropertyEvent(ViewMode.GENERATE, node,
            owner, modelValue, context));

    final Object viewValue = context.getValueTypeConverters().toViewValue(
        extractedValue, node, context);

    context.getListeners().propertyVisited(
        new ViewNodePropertyEvent(ViewMode.GENERATE, node,
            modelValue, viewValue, context));

    return viewValue;
  }

  @Override
  public Object valueToInject(ViewEntity ownerEntity, Class<?> type, Object viewValue,
      ViewNode node, ScopedViewContext context)
      throws Exception {

    final Object convertedValue = context.getValueTypeConverters()
        .toModelValue(type, viewValue, node, context);

    final Object valueToInject = context.getListeners().willInjectValue(
        new ViewNodePropertyEvent(ViewMode.APPLY, node,
            ownerEntity, convertedValue, context));

    context.getListeners().propertyVisited(
        new ViewNodePropertyEvent(ViewMode.APPLY, node,
            ownerEntity, valueToInject, context));

    return valueToInject;
  }

  @Override
  public String keyToExtract(Object owner, Object modelKey, ViewNode node,
      ScopedViewContext context) throws Exception {

    // TODO -- consider whether there should be listener events for keys

    final Object viewKey = context.getValueTypeConverters().toViewValue(
        modelKey, node, context);
    if (viewKey == null) {
      throw new NullPointerException("key cannot be null");
    }

    return viewKey.toString();
  }

  @Override
  public Object keyToInject(ViewEntity ownerEntity, Class<?> type,
      String viewKey, ViewNode node, ScopedViewContext context) throws Exception {

    // TODO -- consider whether there should be listener events for keys

    return context.getValueTypeConverters()
        .toModelValue(type, viewKey, node, context);
  }

}
