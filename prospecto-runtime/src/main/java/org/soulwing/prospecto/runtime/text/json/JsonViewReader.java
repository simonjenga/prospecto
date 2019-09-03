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
package org.soulwing.prospecto.runtime.text.json;

import java.io.InputStream;
import java.util.Collections;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;

import org.soulwing.prospecto.ViewOptionsRegistry;
import org.soulwing.prospecto.api.options.Options;
import org.soulwing.prospecto.api.options.ViewDefaults;
import org.soulwing.prospecto.api.options.ViewKeys;
import org.soulwing.prospecto.api.text.InputStreamSource;
import org.soulwing.prospecto.runtime.text.AbstractViewReader;

/**
 * A {@link org.soulwing.prospecto.api.ViewReader} that parses a JSON object.
 *
 * @author Carl Harris
 */
class JsonViewReader extends AbstractViewReader {

  private static JsonParserFactory parserFactory = Json.createParserFactory(
      Collections.<String, Object>emptyMap());

  private final Source source;

  private String name;

  JsonViewReader(InputStream inputStream, Options options) {
    this(new InputStreamSource(inputStream), options);
  }

  JsonViewReader(Source source, Options options) {
    super(options);
    if (!(source instanceof InputStreamSource)) {
      throw new IllegalArgumentException("only the "
          + InputStreamSource.class.getSimpleName() + " source type is supported");
    }
    this.source = source;
  }

  @Override
  protected void onReadView() throws Exception {
    final JsonParser parser = parserFactory.createParser(
        ((InputStreamSource) source).getInputStream());
    while (parser.hasNext()) {
      final JsonParser.Event event = parser.next();
      switch (event) {
        case KEY_NAME:
          keyName(parser);
          break;
        case VALUE_STRING:
          valueString(parser);
          break;
        case VALUE_NUMBER:
          valueNumber(parser);
          break;
        case VALUE_FALSE:
          valueBoolean(parser, Boolean.FALSE);
          break;
        case VALUE_TRUE:
          valueBoolean(parser, Boolean.TRUE);
          break;
        case VALUE_NULL:
          valueNull(parser);
          break;
        case START_OBJECT:
          startObject(parser);
          break;
        case START_ARRAY:
          startArray(parser);
          break;
        case END_OBJECT:
          endObject(parser);
          break;
        case END_ARRAY:
          endArray(parser);
          break;
        default:
          throw new AssertionError("unrecognized event type");
      }
    }
  }

  private boolean isDiscriminator() {
    return name != null && name.equals(
        ViewOptionsRegistry.getOptions().get(
            ViewKeys.DISCRIMINATOR_NAME, ViewDefaults.DISCRIMINATOR_NODE_NAME)
            .toString());
  }

  private String getName() {
    final String name = this.name;
    this.name = null;
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  private void startObject(JsonParser parser) {
    beginObject(getName());
  }

  private void endObject(JsonParser parser) {
    end();
  }

  private void startArray(JsonParser parser) {
    beginArray(getName());
  }

  private void endArray(JsonParser parser) {
    end();
  }

  private void keyName(JsonParser parser) {
    setName(parser.getString());
  }

  private void valueString(JsonParser parser) {
    if (isDiscriminator()) {
      discriminator(parser.getString());
    }
    else {
      value(getName(), parser.getString());
    }
  }

  private void valueNumber(JsonParser parser) {
    if (parser.isIntegralNumber()) {
      if (isDiscriminator()) {
        discriminator(parser.getLong());
      }
      else {
        value(getName(), parser.getLong());
      }
    }
    else {
      value(getName(), parser.getBigDecimal());
    }
  }

  private void valueBoolean(JsonParser parser, Boolean value) {
    value(getName(), value);
  }

  private void valueNull(JsonParser parser) {
    nullValue(getName());
  }

}
