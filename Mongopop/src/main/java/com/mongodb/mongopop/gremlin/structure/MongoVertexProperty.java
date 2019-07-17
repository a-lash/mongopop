package com.mongodb.mongopop.gremlin.structure;

import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MongoVertexProperty<T> implements VertexProperty {

    MongoVertex _vertex;
    String _key;
    T _value;

    public MongoVertexProperty(MongoVertex vertex, String key, T value) {
        _vertex = vertex;
        _key = key;
        _value = value;
    }

    public MongoVertexProperty(MongoVertex vertex, String key, T value, Cardinality cardinality) {

    }
    @Override
    public String key() {
        return _key;
    }

    @Override
    public Object value() throws NoSuchElementException {
        return _value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public Vertex element() {
        return _vertex;
    }

    @Override
    public void remove() {

    }

    @Override
    public Iterator<Property> properties(String... propertyKeys) {
        return null;
    }

    @Override
    public Object id() {
        return "";
    }

    @Override
    public <V> Property<V> property(String key, V value) {
        return null;
    }
}
//TODO: properties should not be equal to each other if they have different ids and they should be if ids are equal