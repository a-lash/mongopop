package com.mongodb.mongopop.gremlin.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;

import org.apache.commons.collections.IteratorUtils;
import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerProperty;
import org.bson.Document;

public class MongoEdge extends MongoElement implements Edge {

    protected MongoEdge(Document document, MongoGraph graph) {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public <V> Property<V> property(String key, V value) {
        return null;
    }

    @Override
    public Iterator<Vertex> vertices(Direction direction) {
        return null;
    }

    @Override
    public <V> Iterator<Property<V>> properties(String... propertyKeys) {
        return null;
    }

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



}
