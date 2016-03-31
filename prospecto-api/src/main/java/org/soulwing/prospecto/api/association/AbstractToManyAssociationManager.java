/*
 * File created on Mar 31, 2016
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
package org.soulwing.prospecto.api.association;

import java.util.Iterator;

import org.soulwing.prospecto.api.ViewEntity;

/**
 * An abstract base for {@link ToManyAssociationManager} implementations.
 *
 * @author Carl Harris
 */
public abstract class AbstractToManyAssociationManager<T, E>
    extends AbstractAssociationManager<T, E>
    implements ToManyAssociationManager<T, E> {

  /**
   * Finds the associate of the given owner that is logically equivalent
   * to given view entity.
   * <p>
   * This implementation instantiates and populates an instance of type
   * {@code E} using the implementation of {@link #newAssociate(Object, ViewEntity)}
   * on the supertype and compares it to each associate of {@code owner}
   * using {@link #equals(Object)} until a match is found.
   *
   * @param owner the subject owner
   * @param associateEntity a view entity representing the state of the
   *    associate in the view
   * @return the associate of {@code code} that is logically equivalent to
   *    the given view entity or {@code null} if no such associate exists
   * @throws Exception
   */
  @Override
  @SuppressWarnings("unchecked")
  public E findAssociate(T owner, ViewEntity associateEntity) throws Exception {
    E associate = newAssociate(owner, associateEntity);
    final Iterator<E> i = iterator(owner);
    while (i.hasNext()) {
      E candidate = i.next();
      if (candidate.equals(associate)) {
        return candidate;
      }
    }
    return null;
  }

}