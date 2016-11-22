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
package org.soulwing.prospecto;

import org.soulwing.prospecto.api.ViewTemplate;
import org.soulwing.prospecto.api.ViewTemplateBuilder;
import org.soulwing.prospecto.api.ViewTemplateException;
import org.soulwing.prospecto.spi.ViewTemplateBuilderProvider;

/**
 * A singleton object that produces {@link ViewTemplateBuilder} instances.
 *
 * @author Carl Harris
 */
public class ViewTemplateBuilderProducer {

  private static Singleton<ViewTemplateBuilderProducer> singleton =
      new Singleton<ViewTemplateBuilderProducer>() {
        @Override
        protected ViewTemplateBuilderProducer newInstance() {
          return new ViewTemplateBuilderProducer(ServiceLocator.findService(
              ViewTemplateBuilderProvider.class));
        }
      };

  private final ViewTemplateBuilderProvider provider;

  private ViewTemplateBuilderProducer(ViewTemplateBuilderProvider provider) {
    this.provider = provider;
  }

  /**
   * Creates a template builder whose root node type is of object type.
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder object(Class<?> modelType)
      throws ViewTemplateException {
    return object(null, null, modelType);
  }

  /**
   * Creates a template builder whose root node type is of object type.
   * @param name name for the root node; some textual representations
   *   (e.g. JSON) will add an extra envelope object around a named root view
   *   node
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder object(String name, Class<?> modelType)
      throws ViewTemplateException {
    return object(name, null, modelType);
  }

  /**
   * Creates a template builder whose root node type is of object type.
   * @param name name for the root node; some view types (e.g. JSON) will add
   *   an extra envelope object around a named root view node
   * @param namespace namespace for {@code name}; this is used by only some
   *   view types (e.g. XML)
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder object(String name, String namespace,
      Class<?> modelType) throws ViewTemplateException {
    return singleton.getInstance().provider.object(name, namespace, modelType);
  }

  /**
   * Creates a template builder whose root node type is of reference type.
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder reference(Class<?> modelType)
      throws ViewTemplateException {
    return reference(null, null, modelType);
  }

  /**
   * Creates a template builder whose root node type is of reference type.
   * @param name name for the root node; some textual representations
   *   (e.g. JSON) will add an extra envelope object around a named root view
   *   node
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder reference(String name, Class<?> modelType)
      throws ViewTemplateException {
    return reference(name, null, modelType);
  }

  /**
   * Creates a template builder whose root node type is of reference type.
   * @param name name for the root node; some view types (e.g. JSON) will add
   *   an extra envelope object around a named root view node
   * @param namespace namespace for {@code name}; this is used by only some
   *   view types (e.g. XML)
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder reference(String name, String namespace,
      Class<?> modelType) throws ViewTemplateException {
    return singleton.getInstance().provider.reference(name, namespace, modelType);
  }

  /**
   * Creates a template builder whose root node type is of array-of-objects type.
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder arrayOfObjects(Class<?> modelType)
      throws ViewTemplateException {
    return arrayOfObjects(null, null, null, modelType);
  }

  /**
   * Creates a template builder whose root node type is of array-of-objects type.
   * @param name name for the root node; some view types (e.g. JSON) will add
   *   an extra envelope object around a named root view node
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder arrayOfObjects(String name,
      Class<?> modelType) throws ViewTemplateException {
    return arrayOfObjects(name, null, null, modelType);
  }

  /**
   * Creates a template builder whose root node type is of array-of-objects type.
   * @param name name for the root node; some view types (e.g. JSON) will add
   *   an extra envelope object around a named root view node
   * @param elementName name for the elements in the array; this is used by only
   *   some view types (e.g. XML)
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder arrayOfObjects(String name,
      String elementName, Class<?> modelType) throws ViewTemplateException {
    return arrayOfObjects(name, elementName, null, modelType);
  }

  /**
   * Creates a template builder whose root node type is of array-of-objects type.
   * @param name name for the root node; some view types (e.g. JSON) will add
   *   an extra envelope object around a named root view node
   * @param elementName name for the elements in the array; this is used by only
   *   some view types (e.g. XML)
   * @param namespace namespace for {@code name} and {@code elementName}; this
   *   is used by only some view types (e.g. XML)
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder arrayOfObjects(String name,
      String elementName, String namespace, Class<?> modelType)
      throws ViewTemplateException {
    return singleton.getInstance().provider.arrayOfObjects(name, elementName,
        namespace, modelType);
  }

  /**
   * Creates a view template whose root node is an array-of-objects node
   * using the children of the root node of the given object template.
   * @param template source template (which must have a root node of type object)
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfObjects(ViewTemplate template) {
    return arrayOfObjects(null, null, null, template);
  }

  /**
   * Creates a view template whose root node is an array-of-objects node
   * using the children of the root node of the given object template.
   * @param name name for the object in the view (may be {@code null})
   * @param template source template (which must have a root node of type object)
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfObjects(String name, ViewTemplate template) {
    return arrayOfObjects(name, null, null, template);
  }

  /**
   * Creates a view template whose root node is an array-of-objects node
   * using the children of the root node of the given object template.
   * @param name name for the object in the view (may be {@code null})
   * @param elementName name for the elements in the array (may be {@code null})
   * @param template source template (which must have a root node of type object)
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfObjects(String name, String elementName,
      ViewTemplate template) {
    return arrayOfObjects(name, elementName, null, template);
  }

  /**
   * Creates a view template whose root node is an array-of-objects node
   * using the children of the root node of the given object template.
   * @param name name for the object in the view (may be {@code null})
   * @param elementName name for the elements in the array (may be {@code null})
   * @param namespace namespace for {@code name} (may by {@code null})
   * @param template source template (which must have a root node of type object)
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfObjects(String name, String elementName,
      String namespace, ViewTemplate template) {
    return singleton.getInstance().provider.arrayOfObjects(name, elementName,
        namespace, template);
  }

  /**
   * Creates a view template builder whose root node is an array-of-references
   * node.
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder arrayOfReferences(Class<?> modelType) {
    return arrayOfReferences(null, null, null, modelType);
  }

  /**
   * Creates a view template builder whose root node is an array-of-references
   * node.
   * @param name name for the object in the view (may be {@code null})
   * @param name
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder arrayOfReferences(String name,
      Class<?> modelType) {
    return arrayOfReferences(name, null, null, modelType);
  }

  /**
   * Creates a view template builder whose root node is an array-of-references
   * node.
   * @param name name for the object in the view (may be {@code null})
   * @param elementName name for the elements in the array (may be {@code null})
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder arrayOfReferences(String name,
      String elementName, Class<?> modelType) {
    return arrayOfReferences(name, elementName, null, modelType);
  }

  /**
   * Creates a view template builder whose root node is an array-of-references
   * node.
   * @param name name for the object in the view (may be {@code null})
   * @param elementName name for the elements in the array (may be {@code null})
   * @param namespace namespace for {@code name} and {@code elementName}
   *    (may by {@code null})
   * @param modelType model type to associate with the root node
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder arrayOfReferences(String name,
      String elementName, String namespace, Class<?> modelType) {
    return singleton.getInstance().provider.arrayOfReferences(
        name, elementName, namespace, modelType);
  }

  /**
   * Creates a view template whose root node is an array-of-references node
   * using the children of the root node of the given object template.
   * @param template source template (which must have a root node of type object)
   * @return template
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfReferences(ViewTemplate template) {
    return arrayOfReferences(null, null, null, template);
  }

  /**
   * Creates a view template whose root node is an array-of-references node
   * using the children of the root node of the given object template.
   * @param name name for the object in the view (may be {@code null})
   * @param template source template (which must have a root node of type object)
   * @return template
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfReferences(String name,
      ViewTemplate template) {
    return arrayOfReferences(name, null, null, template);
  }

  /**
   * Creates a view template whose root node is an array-of-references node
   * using the children of the root node of the given object template.
   * @param name name for the object in the view (may be {@code null})
   * @param elementName name for the elements in the array (may be {@code null})
   * @param template source template (which must have a root node of type object)
   * @return template
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfReferences(String name, String elementName,
      ViewTemplate template) {
    return arrayOfReferences(name, elementName, null, template);
  }

  /**
   * Creates a view template whose root node is an array-of-references node
   * using the children of the root node of the given object template.
   * @param name name for the object in the view (may be {@code null})
   * @param elementName name for the elements in the array (may be {@code null})
   * @param namespace namespace for {@code name} (may by {@code null})
   * @param template source template (which must have a root node of type object)
   * @return template
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfReferences(String name, String elementName,
      String namespace, ViewTemplate template) {
    return singleton.getInstance().provider.arrayOfReferences(name, elementName,
        namespace, template);
  }

  /**
   * Creates a template whose root node type is of array-of-values type.
   * @return template
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfValues() throws ViewTemplateException {
    return arrayOfValues(null, null, null);
  }

  /**
   * Creates a template whose root node type is of array-of-values type.
   * @param name name for the root node; some view types (e.g. JSON) will add
   *   an extra envelope object around a named root view node
   * @return template
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfValues(String name)
      throws ViewTemplateException {
    return arrayOfValues(name, null, null);
  }

  /**
   * Creates a template whose root node type is of array-of-values type.
   * @param name name for the root node; some view types (e.g. JSON) will add
   *   an extra envelope object around a named root view node
   * @param elementName name for the elements in the array; this is used by only
   *   some view types (e.g. XML)
   * @param namespace namespace for {@code name} and {@code elementName}; this
   *   is used by only some view types (e.g. XML)
   * @return template
   * @throws ViewTemplateException
   */
  public static ViewTemplate arrayOfValues(String name, String elementName,
      String namespace) throws ViewTemplateException {
    return singleton.getInstance().provider.arrayOfValues(name, elementName,
        namespace);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param modelType model type of the values in the map
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder mapOfObjects(Class<?> modelType)
      throws ViewTemplateException {
    return mapOfObjects(null, null, modelType);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param name name for the object in the view (may be {@code null})
   * @param modelType model type of the values in the map
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder mapOfObjects(String name, Class<?> modelType)
      throws ViewTemplateException {
    return mapOfObjects(name, null, modelType);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param name name for the object in the view (may be {@code null})
   * @param namespace namespace for {@code name} (may by {@code null})
   * @param modelType model type of the values in the map
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder mapOfObjects(String name, String namespace,
      Class<?> modelType) throws ViewTemplateException {
    return singleton.getInstance().provider.mapOfObjects(name, namespace,
        modelType);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param template source template (which must have a root node of type object)
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate mapOfObjects(ViewTemplate template)
      throws ViewTemplateException {
    return mapOfObjects(null, null, template);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param name name for the object in the view (may be {@code null})
   * @param template source template (which must have a root node of type object)
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate mapOfObjects(String name,
      ViewTemplate template) throws ViewTemplateException {
    return mapOfObjects(name, null, template);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param name name for the object in the view (may be {@code null})
   * @param namespace namespace for {@code name} (may by {@code null})
   * @param template source template (which must have a root node of type object)
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate mapOfObjects(String name, String namespace,
      ViewTemplate template) throws ViewTemplateException {
    return singleton.getInstance().provider.mapOfObjects(name, namespace,
        template);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param modelType model type of the values in the map
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder mapOfReferences(Class<?> modelType)
      throws ViewTemplateException {
    return mapOfReferences(null, null, modelType);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param name name for the object in the view (may be {@code null})
   * @param modelType model type of the values in the map
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder mapOfReferences(String name, Class<?> modelType)
      throws ViewTemplateException {
    return mapOfReferences(name, null, modelType);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param name name for the object in the view (may be {@code null})
   * @param namespace namespace for {@code name} (may by {@code null})
   * @param modelType model type of the values in the map
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplateBuilder mapOfReferences(String name, String namespace,
      Class<?> modelType) throws ViewTemplateException {
    return singleton.getInstance().provider.mapOfReferences(name, namespace,
        modelType);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param template source template (which must have a root node of type object)
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate mapOfReferences(ViewTemplate template)
      throws ViewTemplateException {
    return mapOfReferences(null, null, template);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param name name for the object in the view (may be {@code null})
   * @param template source template (which must have a root node of type object)
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate mapOfReferences(String name,
      ViewTemplate template) throws ViewTemplateException {
    return mapOfReferences(name, null, template);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a model of {@link java.util.Map} type.
   * @param name name for the object in the view (may be {@code null})
   * @param namespace namespace for {@code name} (may by {@code null})
   * @param template source template (which must have a root node of type object)
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate mapOfReferences(String name, String namespace,
      ViewTemplate template) throws ViewTemplateException {
    return singleton.getInstance().provider.mapOfReferences(name, namespace,
        template);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a map of values that can be converted to
   * simple types, arrays, and maps.
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate mapOfValues()
      throws ViewTemplateException {
    return mapOfValues(null, null);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a map of values that can be converted to
   * simple types, arrays, and maps.
   * @param name name for the object in the view (may be {@code null})
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate mapOfValues(String name)
      throws ViewTemplateException {
    return mapOfValues(name, null);
  }

  /**
   * Creates a builder for a view whose root node is an object whose keys
   * and values are obtained from a map of values that can be converted to
   * simple types, arrays, and maps.
   * @param name name for the object in the view (may be {@code null})
   * @param namespace namespace for {@code name} (may by {@code null})
   * @return template builder
   * @throws ViewTemplateException
   */
  public static ViewTemplate mapOfValues(String name, String namespace)
      throws ViewTemplateException {
    return singleton.getInstance().provider.mapOfValues(name, namespace);
  }

}
