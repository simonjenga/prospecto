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
package org.soulwing.prospecto.api.template;

/**
 * A {@link ViewNode} representing a simple value.
 *
 * @author Carl Harris
 */
public interface ValueNode extends UpdatableNode {

  /**
   * Gets the data type of the value associated with this node.
   * @return data type
   */
  Class<?> getDataType();

  /**
   * Gets the value associated with this node from the given model.
   * @param model model which contains the subject value
   * @return value
   * @throws Exception
   */
  Object getValue(Object model) throws Exception;

  /**
   * Sets the value associated with this node in the given model.
   * @param model model which will be injected with the subject value
   * @param value value to inject
   * @throws Exception
   */
  void setValue(Object model, Object value) throws Exception;

}