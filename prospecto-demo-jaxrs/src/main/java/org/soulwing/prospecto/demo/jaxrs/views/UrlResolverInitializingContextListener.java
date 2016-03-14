/*
 * File created on Mar 13, 2016
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
package org.soulwing.prospecto.demo.jaxrs.views;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.soulwing.prospecto.UrlResolverProducer;
import org.soulwing.prospecto.spi.UrlResolverProvider;

/**
 * A {@link ServletContextListener} that initializes the
 * {@link UrlResolverProducer}.
 *
 * @author Carl Harris
 */
@WebListener
public class UrlResolverInitializingContextListener
    implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent event) {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("servletContext", event.getServletContext());
    // TODO need to get this from the context in a smart way
    properties.put("applicationPath", "/api");
    UrlResolverProducer.init(properties);
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    UrlResolverProducer.destroy();
  }

}