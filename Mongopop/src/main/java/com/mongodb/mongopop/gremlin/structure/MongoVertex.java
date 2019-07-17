package com.mongodb.mongopop.gremlin.structure;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality;
import org.bson.Document;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MongoVertex extends MongoElement implements Vertex {

    @Override
    public Object id() {
        return null;
    }

    @Override
    public String label() {
        return null;
    }

    @Override
    public Graph graph() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public Edge addEdge(String label, Vertex inVertex, Object... keyValues) {
        return null;
    }

    @Override
    public <V> VertexProperty<V> property(Cardinality cardinality, String key, V value, Object... keyValues) {
        return null;
    }

    @Override
    public Iterator<Edge> edges(Direction direction, String... edgeLabels) {
        return null;
    }

    @Override
    public Iterator<Vertex> vertices(Direction direction, String... edgeLabels) {
        return null;
    }

    @Override
    public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {
        return null;
    }




}
