/*
 * File created on Mar 15, 2016
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collections;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.soulwing.prospecto.api.ViewContext;
import org.soulwing.prospecto.api.ViewNode;
import org.soulwing.prospecto.api.handler.ViewNodeEvent;
import org.soulwing.prospecto.api.handler.ViewNodeHandler;

/**
 * Unit tests for {@link ViewNodeHandlerSupport}.
 *
 * @author Carl Harris
 */
public class ViewNodeHandlerSupportTest {

  private static final Object MODEL = new Object();

  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  private ViewContext viewContext;

  @Mock
  private ViewNodeHandler handler;

  @Mock
  private ViewNode node;

  private ViewNodeEvent event;

  @Before
  public void setUp() throws Exception {
    event = new ViewNodeEvent(node, MODEL, viewContext);
  }

  @Test
  public void testWillVisitNode() throws Exception {
    context.checking(new Expectations() {
      {
        oneOf(viewContext).getViewNodeHandlers();
        will(returnValue(Arrays.asList(handler, handler, handler)));
        exactly(2).of(handler).beforeVisit(event);
        will(onConsecutiveCalls(returnValue(true), returnValue(false)));
      }
    });

    assertThat(ViewNodeHandlerSupport.willVisitNode(event), is(false));
  }

  @Test
  public void testNodeVisited() throws Exception {
    context.checking(new Expectations() {
      {
        oneOf(viewContext).getViewNodeHandlers();
        will(returnValue(Collections.singletonList(handler)));
        oneOf(handler).afterVisit(event);
      }
    });

    ViewNodeHandlerSupport.nodeVisited(event);
  }

}