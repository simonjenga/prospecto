/*
 * File created on Apr 8, 2016
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
package org.soulwing.prospecto.runtime.applicator;

import java.util.Deque;

import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.ViewEntity;
import org.soulwing.prospecto.api.template.MetaNode;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.listener.TransformationService;

/**
 * An abstract base for applicators of meta nodes.
 *
 * @param <N> meta node type
 * @author Carl Harris
 */
abstract class AbstractMetaApplicator<N extends MetaNode>
    extends AbstractViewEventApplicator<N> {

  private final TransformationService transformationService;

  AbstractMetaApplicator(N node, TransformationService transformationService) {
    super(node);
    this.transformationService = transformationService;
  }

  @Override
  final Object onToModelValue(ViewEntity parentEntity, View.Event triggerEvent,
      Deque<View.Event> events, ScopedViewContext context) throws Exception {
    return transformationService.valueToInject(parentEntity, Object.class,
        triggerEvent.getValue(), node, context);
  }

  @Override
  public final void inject(Object target, Object value,
      ScopedViewContext context) throws Exception {
    node.getHandler().consumeValue(node, target, value, context);
  }

}
