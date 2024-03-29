/*
 * Copyright 2008-present MongoDB, Inc.
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

package com.mongodb.mongopop.gremlin.structure;

import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Property;

public class MongoProperty<T> implements Property<T> {

    Element element;
    T value;
    String key;

    protected MongoProperty(Element el, String k, T val) {
        element = el;
        key = k;
        value = val;
    }

    public Element element() {
        return element;
    }

    public void remove() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    public T value() {
        return value;
    }

    public boolean isPresent() {
        return !(this == null);
    }

    public String key() {
        return key;
    }
}