/*
 * File created on Apr 5, 2016
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
package org.soulwing.prospecto.runtime.template;

import org.soulwing.prospecto.api.ModelEditor;
import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.ViewContext;
import org.soulwing.prospecto.api.ViewException;
import org.soulwing.prospecto.api.ViewTemplate;
import org.soulwing.prospecto.api.ViewTemplateException;
import org.soulwing.prospecto.api.node.ContainerNode;
import org.soulwing.prospecto.api.node.ViewNodeVisitor;
import org.soulwing.prospecto.runtime.context.ConcreteScopedViewContextFactory;
import org.soulwing.prospecto.runtime.context.ScopedViewContextFactory;
import org.soulwing.prospecto.runtime.editor.ConcreteModelEditorFactory;
import org.soulwing.prospecto.runtime.editor.ModelEditorFactory;
import org.soulwing.prospecto.runtime.node.AbstractViewNode;
import org.soulwing.prospecto.runtime.node.ConcreteArrayOfObjectsNode;
import org.soulwing.prospecto.runtime.node.ConcreteArrayOfReferencesNode;
import org.soulwing.prospecto.runtime.node.ConcreteContainerNode;
import org.soulwing.prospecto.runtime.node.ConcreteObjectNode;
import org.soulwing.prospecto.runtime.node.ConcreteReferenceNode;
import org.soulwing.prospecto.runtime.node.RootArrayOfObjectNode;
import org.soulwing.prospecto.runtime.node.RootArrayOfReferencesNode;
import org.soulwing.prospecto.runtime.view.ConcreteView;

/**
 * A {@link ViewTemplate} implementation.
 *
 * @author Carl Harris
 */
public class ConcreteViewTemplate implements ComposableViewTemplate {

  private final AbstractViewNode root;
  private final ScopedViewContextFactory viewContextFactory;
  private final ModelEditorFactory modelEditorFactory;

  public ConcreteViewTemplate(AbstractViewNode root) {
    this(root, new ConcreteScopedViewContextFactory());
  }

  ConcreteViewTemplate(AbstractViewNode root,
      ScopedViewContextFactory viewContextFactory) {
    this(root, viewContextFactory, new ConcreteModelEditorFactory());
  }

  ConcreteViewTemplate(AbstractViewNode root,
      ScopedViewContextFactory viewContextFactory,
      ModelEditorFactory modelEditorFactory) {
    this.root = root;
    this.viewContextFactory = viewContextFactory;
    this.modelEditorFactory = modelEditorFactory;
  }


  public AbstractViewNode getRoot() {
    return root;
  }

  @Override
  public View generateView(Object source, ViewContext context)
      throws ViewException {
    try {
      return new ConcreteView(root.evaluate(source,
          viewContextFactory.newContext(context)));
    }
    catch (Exception ex) {
      throw new ViewException(ex);
    }
  }

  @Override
  public ModelEditor generateEditor(View source, ViewContext context) {
    return generateEditor(source, context, null);
  }

  @Override
  public ModelEditor generateEditor(View source, ViewContext context,
      String dataKey) {
    return modelEditorFactory.newEditor(root, source, context, dataKey);
  }

  @Override
  public ConcreteContainerNode object(String name, String namespace) {
    assertRootIsContainerViewNode(name);
    final ConcreteObjectNode node = new ConcreteObjectNode(name, namespace,
        root.getModelType());
    copyInto(node);
    return node;
  }

  @Override
  public ConcreteContainerNode reference(String name, String namespace) {
    assertRootIsContainerViewNode(name);
    final ConcreteReferenceNode node = new ConcreteReferenceNode(name, namespace,
        root.getModelType());
    copyInto(node);
    return node;
  }

  @Override
  public ConcreteContainerNode arrayOfObjects(String name, String elementName,
      String namespace) {
    assertRootIsContainerViewNode(name);
    final ConcreteArrayOfObjectsNode node = new ConcreteArrayOfObjectsNode(name, elementName,
        namespace, root.getModelType());
    copyInto(node);
    return node;
  }

  @Override
  public ConcreteContainerNode arrayOfReferences(String name, String elementName,
      String namespace) {
    assertRootIsContainerViewNode(name);
    ConcreteArrayOfReferencesNode node = new ConcreteArrayOfReferencesNode(name, elementName,
        namespace, root.getModelType());
    copyInto(node);
    return node;
  }

  @Override
  public ViewTemplate arrayOfObjectsTemplate(String name, String elementName,
      String namespace) {
    assertRootIsContainerViewNode(name);
    RootArrayOfObjectNode root = new RootArrayOfObjectNode(name, elementName,
        namespace, getRoot().getModelType());
    copyInto(root);
    return new ConcreteViewTemplate(root);
  }

  @Override
  public ViewTemplate arrayOfReferencesTemplate(String name, String elementName,
      String namespace) {
    assertRootIsContainerViewNode(name);
    RootArrayOfReferencesNode root = new RootArrayOfReferencesNode(name,
        elementName, namespace, getRoot().getModelType());
    copyInto(root);
    return new ConcreteViewTemplate(root);
  }

  private void copyInto(ConcreteContainerNode node) {
    assert root instanceof ContainerNode;
    node.addChildren((ContainerNode) root);
    node.putAll(root);
  }

  private void assertRootIsContainerViewNode(String name) {
    if (!(root instanceof ConcreteContainerNode)) {
      throw new ViewTemplateException("referenced view template for node '"
          + name + "' must have a root node of object or array-of-object type");
    }
  }

  @Override
  public Object traverseBreadthFirst(ViewNodeVisitor visitor, Object state) {
    // TODO
    throw new UnsupportedOperationException();
  }

  @Override
  public Object traverseDepthFirst(ViewNodeVisitor visitor, Object state) {
    // TODO
    throw new UnsupportedOperationException();
  }

}