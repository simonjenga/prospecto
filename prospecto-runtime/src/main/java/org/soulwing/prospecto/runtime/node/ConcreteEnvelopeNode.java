/*
 * File created on Mar 9, 2016
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

import java.util.LinkedList;
import java.util.List;

import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.node.EnvelopeNode;
import org.soulwing.prospecto.api.node.ViewNodeVisitor;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.entity.MutableViewEntity;

/**
 * A view node that represents an envelope.
 * <p>
 * An envelope inserts another object node into the textual representation of
 * a view without a corresponding change in model context.
 *
 * @author Carl Harris
 */
public class ConcreteEnvelopeNode extends ConcreteObjectNode
    implements EnvelopeNode {

  /**
   * Constructs a new instance.
   * @param name node name
   * @param namespace namespace for {@code name}
   * @param modelType model type
   */
  public ConcreteEnvelopeNode(String name, String namespace, Class<?> modelType) {
    super(name, namespace, modelType);
  }

  @Override
  public Object accept(ViewNodeVisitor visitor, Object state) {
    return visitor.visitEnvelope(this, state);
  }

  @Override
  protected List<View.Event> onEvaluate(Object model, ScopedViewContext context)
      throws Exception {
    final List<View.Event> events = new LinkedList<>();
    events.add(newEvent(View.Event.Type.BEGIN_OBJECT));
    events.addAll(evaluateChildren(model, context));
    events.add(newEvent(View.Event.Type.END_OBJECT));
    return events;
  }

  @Override
  public void inject(Object target, Object value, ScopedViewContext context)
      throws Exception {
    ((MutableViewEntity) value).inject(target, context);
  }

}