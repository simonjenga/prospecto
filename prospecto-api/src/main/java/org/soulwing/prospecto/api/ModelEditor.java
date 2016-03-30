/*
 * File created on Mar 17, 2016
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

/**
 * An editor for a model.
 * <p>
 * An editor applies the model state in a source view to the model that backs
 * a target view.
 *
 * @author Carl Harris
 */
public interface ModelEditor {

  Object create() throws ModelEditorException;

  /**
   * Updates the given model.
   * @param model model (which must be an instance of the type associated with
   *    the root of the target view).
   * @throws ModelEditorException
   */
  void update(Object model) throws ModelEditorException;

}
