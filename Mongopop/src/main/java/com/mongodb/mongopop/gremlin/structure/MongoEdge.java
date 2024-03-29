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
import org.bson.types.ObjectId;
public class MongoEdge extends MongoElement implements Edge {

    public MongoEdge(Document document, MongoGraph graph) {
        super(document, graph);
        collection = graph.edges;
        document.append("_id",T.id.getAccessor());
        document.append(T.label.toString(), "DEFAULT LABEL");
    }

    protected MongoEdge(String label, Object inVertex, Object outVertex, Document document, MongoGraph graph, Object... keyValues) {
        super(document, graph);
        for(int i = 0; i < keyValues.length; i += 2) {
            Object[] chunk = Arrays.copyOfRange(keyValues, i, Math.min(keyValues.length, i + 2));
            String key = chunk[0].toString();
            document.append(key, chunk[1]);
        }
        document.append("_id", new ObjectId());
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
        Document doc = collection.find(Filters.eq(document.get("_id"))).first();
        return doc.entrySet().stream()
            .filter(x -> x.getKey() != "_id" && x.getKey() != "label" && x.getKey() != "inVertex" && x.getKey() != "outVertex")
            .map(x -> (Property<V>)(new TinkerProperty<V>(this, x.getKey(), (V)(x.getValue()))))
            .collect(Collectors.toList()).iterator();
    }

    public <V> Property<V> property(String key, V value) {
        collection.updateOne(Filters.eq(document.get("_id")), Updates.set(key, value));
        return new MongoProperty<V>(this, key, value); //TODO
    }

}
