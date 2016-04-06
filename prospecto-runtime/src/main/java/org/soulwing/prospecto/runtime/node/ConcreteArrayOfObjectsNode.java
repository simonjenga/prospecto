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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.ViewEntity;
import org.soulwing.prospecto.api.listener.ViewNodeEvent;
import org.soulwing.prospecto.api.listener.ViewNodePropertyEvent;
import org.soulwing.prospecto.api.node.ArrayOfObjectsNode;
import org.soulwing.prospecto.api.node.ViewNode;
import org.soulwing.prospecto.api.node.ViewNodeVisitor;
import org.soulwing.prospecto.runtime.accessor.Accessor;
import org.soulwing.prospecto.runtime.accessor.ConcreteMultiValuedAccessorFactory;
import org.soulwing.prospecto.runtime.accessor.MultiValuedAccessor;
import org.soulwing.prospecto.runtime.accessor.MultiValuedAccessorFactory;
import org.soulwing.prospecto.runtime.association.ConcreteToManyAssociationUpdater;
import org.soulwing.prospecto.runtime.association.ToManyAssociationUpdater;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.entity.MutableViewEntity;

/**
 * A view node that represents an array of objects.
 *
 * @author Carl Harris
 */
public class ConcreteArrayOfObjectsNode extends ConcreteContainerNode
    implements UpdatableViewNode, ArrayOfObjectsNode {

  private final ToManyAssociationUpdater associationUpdater;
  private final MultiValuedAccessorFactory accessorFactory;
  private final UpdatableViewNodeTemplate template;

  private final String elementName;

  private MultiValuedAccessor multiValuedAccessor;

  /**
   * Constructs a new instance.
   * @param name node name
   * @param elementName name for the elements of the array
   * @param namespace namespace for {@code name} and {@code elementName}
   * @param modelType model type of the array elements
   */
  public ConcreteArrayOfObjectsNode(String name, String elementName,
      String namespace, Class<?> modelType) {
    this(name, elementName, namespace, modelType,
        new ConcreteToManyAssociationUpdater(),
        ConcreteMultiValuedAccessorFactory.INSTANCE,
        ConcreteUpdatableViewNodeTemplate.INSTANCE);
  }

  ConcreteArrayOfObjectsNode(String name, String elementName,
      String namespace, Class<?> modelType,
      ToManyAssociationUpdater associationUpdater,
      MultiValuedAccessorFactory accessorFactory,
      UpdatableViewNodeTemplate template) {
    super(name, namespace, modelType, new ArrayList<ViewNode>());
    this.elementName = elementName;
    this.associationUpdater = associationUpdater;
    this.accessorFactory = accessorFactory;
    this.template = template;
  }

  @Override
  public Object accept(ViewNodeVisitor visitor, Object state) {
    return visitor.visitArrayOfObjects(this, state);
  }

  /**
   * Gets the {@code elementName} property.
   * @return property value
   */
  public String getElementName() {
    return elementName;
  }

  @Override
  public void setAccessor(Accessor accessor) {
    super.setAccessor(accessor);
    this.multiValuedAccessor = accessorFactory.newAccessor(accessor, getModelType());
  }

  protected MultiValuedAccessor getMultiValuedAccessor() {
    return multiValuedAccessor;
  }

  @Override
  protected List<View.Event> onEvaluate(Object model,
       ScopedViewContext context)
      throws Exception {
    final Iterator<Object> i = getModelIterator(model);
    if (i == null) {
      return Collections.singletonList(newEvent(View.Event.Type.VALUE));
    }

    final List<View.Event> viewEvents = new LinkedList<>();
    viewEvents.add(newEvent(View.Event.Type.BEGIN_ARRAY));

    while (i.hasNext()) {
      Object elementModel = i.next();
      final ViewNodePropertyEvent elementEvent = new ViewNodePropertyEvent(
          ViewNodeEvent.Mode.VIEW_GENERATION, this, model, elementModel, context);
      viewEvents.add(newEvent(View.Event.Type.BEGIN_OBJECT, elementName));
      viewEvents.addAll(evaluateChildren(
          context.getListeners().didExtractValue(elementEvent),
          context));
      viewEvents.add(newEvent(View.Event.Type.END_OBJECT, elementName));
    }

    viewEvents.add(newEvent(View.Event.Type.END_ARRAY));
    return viewEvents;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<MutableViewEntity> toModelValue(ViewEntity parentEntity,
      View.Event triggerEvent, Deque<View.Event> events,
      ScopedViewContext context) throws Exception {

    final ArrayOfObjectNodeUpdateMethod method =
        new ArrayOfObjectNodeUpdateMethod(this, parentEntity, triggerEvent,
            events, context, template);

    return (List<MutableViewEntity>)
        template.toModelValue(this, parentEntity, context, method);
  }

  @Override
  public void inject(Object target, Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  @SuppressWarnings("unchecked")
  public void inject(Object target, Object value, ScopedViewContext context)
      throws Exception {
    associationUpdater.update(this, target,
        (List<MutableViewEntity>) value, multiValuedAccessor, context);
  }

  @SuppressWarnings("unchecked")
  protected Iterator<Object> getModelIterator(Object source) throws Exception {
    return multiValuedAccessor.iterator(source);
  }

}