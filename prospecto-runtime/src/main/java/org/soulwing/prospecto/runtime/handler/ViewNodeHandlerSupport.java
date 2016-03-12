/*
 * File created on Mar 11, 2016
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
package org.soulwing.prospecto.runtime.handler;

import java.util.Iterator;

import org.soulwing.prospecto.api.handler.ViewNodeEvent;
import org.soulwing.prospecto.api.handler.ViewNodeHandler;

/**
 * A utility that provides handler notification support for a collection
 * of {@Link ViewNodeHandler} objects.
 *
 * @author Carl Harris
 */
public class ViewNodeHandlerSupport {

  private final Iterable<ViewNodeHandler> handlers;

  public ViewNodeHandlerSupport(Iterable<ViewNodeHandler> handlers) {
    this.handlers = handlers;
  }

  public boolean willVisitNode(ViewNodeEvent event) {
    final Iterator<ViewNodeHandler> handlers = this.handlers.iterator();
    boolean visiting = true;
    while (visiting && handlers.hasNext()) {
      visiting = handlers.next().beforeVisit(event);
    }
    return visiting;
  }

  public void nodeVisited(ViewNodeEvent event) {
    for (final ViewNodeHandler handler : this.handlers) {
      handler.afterVisit(event);
    }
  }

}