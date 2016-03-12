/*
 * File created on Mar 10, 2016
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

import java.util.Collections;
import java.util.List;

import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.ViewNode;
import org.soulwing.prospecto.api.handler.ViewNodeEvent;
import org.soulwing.prospecto.runtime.accessor.Accessor;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.event.ConcreteViewEvent;
import org.soulwing.prospecto.runtime.handler.ViewNodeHandlerSupport;

/**
 * An abstract base for {@link ViewNode} implementations.
 *
 * @author Carl Harris
 */
public abstract class AbstractViewNode implements ViewNode {

  private final String name;
  private final String namespace;
  private final Class<?> modelType;

  private Accessor accessor;

  /**
   * Constructs a new instance.
   * @param name node name (may be {@code null})
   * @param namespace namespace (may be {@code null})
   * @param modelType associated model type (may be {@code null})
   */
  protected AbstractViewNode(String name, String namespace,
      Class<?> modelType) {
    this.name = name;
    this.namespace = namespace;
    this.modelType = modelType;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getNamespace() {
    return namespace;
  }

  @Override
  public Class<?> getModelType() {
    return modelType;
  }

  public Accessor getAccessor() {
    return accessor;
  }

  public void setAccessor(Accessor accessor) {
    this.accessor = accessor;
  }

  public final List<View.Event> evaluate(Object model,
      ScopedViewContext context) throws Exception {

    context.push(name, modelType);
    final ViewNodeEvent event = new ViewNodeEvent(this, model, context);
    final ViewNodeHandlerSupport handlers = new ViewNodeHandlerSupport(
        context.getViewNodeHandlers());

    if (!handlers.willVisitNode(event)) {
      return Collections.emptyList();
    }

    final List<View.Event> events = onEvaluate(model, context);
    handlers.nodeVisited(event);
    context.pop();
    return events;
  }

  protected abstract List<View.Event> onEvaluate(Object source,
      ScopedViewContext context) throws Exception;

  public abstract AbstractViewNode copy(String name);

  protected View.Event newEvent(View.Event.Type type) {
    return newEvent(type, name, null);
  }

  protected View.Event newEvent(View.Event.Type type, String name) {
    return newEvent(type, name, null);
  }

  protected View.Event newEvent(View.Event.Type type, String name,
      Object value) {
    return new ConcreteViewEvent(type, name, namespace, value);
  }

}