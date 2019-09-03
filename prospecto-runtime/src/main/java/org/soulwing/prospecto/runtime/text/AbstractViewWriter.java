/*
 * File created on Mar 19, 2016
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
package org.soulwing.prospecto.runtime.text;

import java.util.Iterator;

import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.ViewException;
import org.soulwing.prospecto.api.ViewWriter;
import org.soulwing.prospecto.api.options.Options;
import org.soulwing.prospecto.api.options.WriterKeys;

/**
 * An object that produces a representation of a view on a specified target.
 * <p>
 * This class is designed to allow a callback-driven interpretation of a
 * view's event stream.
 * <p>
 * A writer is <em>not</em> thread safe.
 *
 * @author Carl Harris
 */
public abstract class AbstractViewWriter implements ViewWriter {

  private final View view;
  private final Options options;

  /**
   * Constructs a new writer.
   * @param view the subject view
   * @param options configuration options
   *
   */
  protected AbstractViewWriter(View view,
      Options options) {
    this.view = view;
    this.options = options;
  }

  public Options getOptions() {
    return options;
  }

  /**
   * Writes the view to the target output stream.
   * <p>
   * The underlying output stream is <em>not</em> closed by this method.
   * @throws ViewException if an error occurs in writing the view.
   * @throws IllegalStateException if this method has already been invoked on
   *   this writer instance
   */
  @Override
  public final void writeView() throws ViewException {
    try {
      beforeViewEvents();
      final Iterator<View.Event> events = view.iterator();
      while (events.hasNext()) {
        final View.Event event = events.next();
        switch (event.getType()) {
          case BEGIN_OBJECT:
            onBeginObject(event);
            break;
          case END_OBJECT:
            onEndObject(event);
            break;
          case BEGIN_ARRAY:
            onBeginArray(event);
            break;
          case END_ARRAY:
            onEndArray(event);
            break;
          case VALUE:
            doValue(event);
            break;
          case META:
            onMeta(event);
            break;
          case DISCRIMINATOR:
            onDiscriminator(event);
            break;
          default:
            throw new IllegalStateException("unrecognized event type: "
                + event.getType().name());
        }
      }
      afterViewEvents();
    }
    catch (ViewException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new ViewException(ex);
    }
  }

  private void doValue(View.Event event) throws Exception {
    if (event.getValue() != null
        || getOptions().isEnabled(WriterKeys.INCLUDE_NULL_PROPERTIES)) {
      onValue(event);
    }
  }

  /**
   * Notifies the recipient that the view's event stream will start.
   */
  protected void beforeViewEvents() throws Exception {
  }

  /**
   * Notifies the recipient that the view's event stream has ended.
   * @throws Exception
   */
  protected void afterViewEvents() throws Exception {
  }

  /**
   * Notifies the recipient that the next {@code event} is of type
   * {@link View.Event.Type#BEGIN_OBJECT}.
   * @param event the subject event
   * @throws Exception
   */
  protected abstract void onBeginObject(View.Event event) throws Exception;

  /**
   * Notifies the recipient that the next {@code event} is of type
   * {@link View.Event.Type#END_OBJECT}.
   * @param event the subject event
   * @throws Exception
   */
  protected abstract void onEndObject(View.Event event) throws Exception;

  /**
   * Notifies the recipient that the next {@code event} is of type
   * {@link View.Event.Type#BEGIN_ARRAY}.
   * @param event the subject event
   * @throws Exception
   */
  protected abstract void onBeginArray(View.Event event) throws Exception;

  /**
   * Notifies the recipient that the next {@code event} is of type
   * {@link View.Event.Type#END_ARRAY}.
   * @param event the subject event
   * @throws Exception
   */
  protected abstract void onEndArray(View.Event event) throws Exception;

  /**
   * Notifies the recipient that the next {@code event} is of type
   * {@link View.Event.Type#VALUE}.
   * @param event the subject event
   * @throws Exception
   */
  protected abstract void onValue(View.Event event) throws Exception;

  /**
   * Notifies the recipient that the next {@code event} is of type
   * {@link View.Event.Type#META}.
   * @param event the subject event
   * @throws Exception
   */
  protected abstract void onMeta(View.Event event) throws Exception;

  /**
   * Notifies the recipient that the next {@code event} is of type
   * {@link View.Event.Type#DISCRIMINATOR}.
   * @param event the subject event
   * @throws Exception
   */
  protected abstract void onDiscriminator(View.Event event) throws Exception;

  /**
   * Gets the source view for this writer.
   * @return source view
   */
  protected final View getView() {
    return view;
  }

}
