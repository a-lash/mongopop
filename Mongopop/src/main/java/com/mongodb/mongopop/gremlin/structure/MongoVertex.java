package com.mongodb.mongopop.gremlin.structure;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.apache.tinkerpop.gremlin.structure.*;
import org.bson.Document;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MongoVertex extends MongoElement implements Vertex{

    protected MongoVertex(Document document, MongoGraph graph, Object... keyValues) {
        super(document, graph);
        Document properties = new Document();
        for(int i = 0; i < keyValues.length; i += 2) {
            Object[] chunk = Arrays.copyOfRange(keyValues, i, Math.min(keyValues.length, i + 2));
            String key = chunk[0].toString();
            if(key == T.id.getAccessor()) {
                document.append("_id", chunk[1]);
            }
            else {
                properties.append(key, chunk[1]);
            }

            document.append("properties", properties);
        }
    }

    private MongoCollection<Document> collection = graph.edges;

    @Override
    public Iterator<Edge> edges(Direction direction, String... edgeLabels) {
        // Not relevant to our implementation
        return null;
    }

    public <V> VertexProperty<V> property(String key, V value, Object... keyValues) {
        document = collection.findOneAndUpdate(Filters.eq(document.get("_id")),
                Updates.set(key, value),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        return new MongoVertexProperty<V>(this, key, value);
    }

    public void remove() {
        collection.deleteOne(Filters.eq(document.get("_id")));
    }

    public MongoEdge addEdge(String label, Vertex inVertex, Object... keyValues) {
        //TODO(waiting on Edge implementation)
        return null;
//        MongoEdge mongoEdge = new MongoEdge(label, this.id(), inVertex.id(), this.graph(), keyValues);
//        mongoEdge.save();
//        return mongoEdge;
    }

    public <V> VertexProperty<V> property(VertexProperty.Cardinality cardinality, String key, V value, Object... keyValues) {
        document = collection.findOneAndUpdate(Filters.eq(document.get("_id")),
                Updates.set(key, value),
        new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        return new MongoVertexProperty<V>(this, key, value, cardinality);
    }

    public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {
        Document document = null;
        document = collection.find(Filters.eq(document.get("_id"))).first();
        Iterator<VertexProperty<V>> result = document.entrySet().stream().filter(x -> x.getKey() != "_id" && x.getKey() != "label"
                && (Arrays.stream(propertyKeys).anyMatch(x.getKey()::equals) || propertyKeys.length == 0))
                .map(x -> (VertexProperty<V>)(new MongoVertexProperty<V>(this, x.getKey(), (V)(x.getValue())))).collect(Collectors.toList()).iterator();
        return result;
    }

    public Iterator<Vertex> vertices(Direction direction, String... edgeLabels) {
        Iterator<Edge> edgesIterator = edges(direction, edgeLabels);
        Iterable<Edge> iterable = () -> edgesIterator;
        Stream<Edge> targetStream = StreamSupport.stream(iterable.spliterator(), false);
        Iterator<Vertex> vertices = targetStream.map(x -> x.outVertex()).collect(Collectors.toList()).iterator();
        return vertices;
    }

    public Object id() {
        return super.id();
    }

    public String label() {
        return super.label();
    }

    public Graph graph() {
        return super.graph();
    }


}
