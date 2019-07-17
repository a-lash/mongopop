package com.mongodb.mongopop.gremlin.structure;

import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MongoVertexProperty<T> implements VertexProperty {

    @Override
    public String key() {
        return null;
    }

    @Override
    public Object value() throws NoSuchElementException {
        return null;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public void remove() {

    }

    @Override
    public Object id() {
        return null;
    }

    @Override
    public <V> Property<V> property(String key, V value) {
        return null;
    }

    @Override
    public Vertex element() {
        return null;
    }

    @Override
    public Iterator properties(String... propertyKeys) {
        return null;
    }

}
//TODO: properties should not be equal to each other if they have different ids and they should be if ids are equal