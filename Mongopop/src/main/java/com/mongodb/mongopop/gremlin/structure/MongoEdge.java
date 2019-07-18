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

    public MongoEdge(Document document, MongoGraph graph) {
        super(document, graph);
        collection = graph.edges;
    }

    protected MongoEdge(String label, Object inVertex, Object outVertex, Document document, MongoGraph graph, Object... keyValues) {
        super(document, graph);
        for(int i = 0; i < keyValues.length; i += 2) {
            Object[] chunk = Arrays.copyOfRange(keyValues, i, Math.min(keyValues.length, i + 2));
            String key = chunk[0].toString();
            String toAppend = (key == T.id.getAccessor()) ? "_id" : key;
            document.append(toAppend, chunk[1]);
        }
        collection = graph.edges;
        document.append(T.label.toString(), label);
        document.append("inVertex", inVertex);
        document.append("outVertex", outVertex);
    }

    public Object id() {
        return super.id();
    }

    public Vertex inVertex() {
        return graph.vertices(document.get("inVertex")).next();
    }

    public Vertex outVertex() {
        return graph.vertices(document.get("outVertex")).next();
    }

    public Iterator<Vertex> vertices(Direction direction) {
        //TODO(caching)
        ArrayList<Vertex> ans = new ArrayList<Vertex>();
        if (direction == Direction.IN || direction == Direction.BOTH) {
            ans.addAll(IteratorUtils.toList(graph.vertices(this.document.get("inVertex"))));
        }
        if (direction == Direction.OUT || direction == Direction.BOTH) {
            ans.addAll(IteratorUtils.toList(graph.vertices(this.document.get("outVertex"))));
        }
        return ans.iterator();
    }

    public Graph graph() {
        return super.graph();
    }
    
    public String label() {
        return super.label();
    }

    public void remove() {
        super.remove();
    }

    public <V> Iterator<Property<V>> properties(String... propertyKeys) {
        Document document = null;
        //TODO: need to a null check below, either collection or find is null
        document = collection.find(Filters.eq(document.get("_id"))).first();
        Iterator<Property<V>> result = document.entrySet().stream().filter(x -> x.getKey() != "_id" && x.getKey() != "label" && x.getKey() != "inVertex" && x.getKey() != "outVertex")
                .map(x -> (Property<V>)(new TinkerProperty<V>(this, x.getKey(), (V)(x.getValue())))).collect(Collectors.toList()).iterator();
        return result;
    }

    public <V> Property<V> property(String key, V value) {
        //TODO(REALLY!!!???)
        // document = collection.findOneAndUpdate(Filters.eq(document.get("_id")),
        //         Updates.set(key, value),
        //         FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        return new MongoProperty<V>(this, value, key); //TODO
    }

}
