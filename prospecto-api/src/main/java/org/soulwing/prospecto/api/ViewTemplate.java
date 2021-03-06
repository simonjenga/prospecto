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
package org.soulwing.prospecto.api;

import org.soulwing.prospecto.api.template.ViewNodeVisitor;

/**
 * A view template.
 * <p>
 * A template can be used to generate a view from a model and to apply a view
 * to update a model.
 * <p>
 * A template is produced using a {@link ViewTemplateBuilder}.  Once a template
 * is created it is effectively immutable (i.e. stateless) and therefore thread
 * safe.
 * <p>
 * The template construction process uses reflection and is thus relatively
 * expensive. For this reason, templates should be constructed once and then
 * used many times to generate views and to create applicators.
 *
 * @author Carl Harris
 */
public interface ViewTemplate {

  /**
   * A traversal for a {@link ViewTemplate}.
   *
   * @author Carl Harris
   */
  interface Traversal {

    /**
     * Performs a traversal.
     * @param visitor visitor that will be invoked as nodes are visited
     * @param state input state
     * @return traversal result
     */
    Object traverse(ViewNodeVisitor visitor, Object state);

  }

  /**
   * Creates a new breadth first traversal.
   * @return traversal
   */
  Traversal breadthFirst();

  /**
   * Creates a new breadth first traversal.
   * @return traversal
   */
  Traversal depthFirst();

  /**
   * Generates a view using the given model as the root object represented by
   * the view.
   * <p>
   * The model's type must correspond to the root type specified for the
   * template. If {@code model} must be evaluated in particular context (e.g.
   * while a database transaction remains in progress, or while a JPA entity
   * manager remains open), the caller must ensure that the required context
   * is in place.
   *
   * @param model root model object
   * @param context view context
   * @return view whose event stream can be used to produce a textual
   *   representation of {@code model}
   */
  View generateView(Object model, ViewContext context);

  /**
   * Creates a view applicator using the given source view.
   * <p>
   * The given {@code view} is assumed not to have an outer envelope.
   * @param source source view (typically produced by a {@link ViewReader}
   * @param context view context
   * @return model editor
   */
  ViewApplicator createApplicator(View source, ViewContext context);

  /**
   * Creates a view applicator using the given enveloped source view.
   * @param source source view (typically produced by a {@link ViewReader}
   * @param context view context
   * @param dataKey envelope key that contains the editable view data
   * @return model editor
   */
  ViewApplicator createApplicator(View source, ViewContext context,
      String dataKey);

}
