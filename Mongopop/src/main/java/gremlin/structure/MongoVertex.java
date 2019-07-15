package gremlin.structure;

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

    protected MongoVertex(Document document, MongoGraph graph) {
        super(document, graph);
    }

    public MongoEdge addEdge(String label, Vertex inVertex, Object... keyValues) {
        //TODO(implement)
        return null;
//        MongoEdge mongoEdge = new MongoEdge(label, this.id(), inVertex.id(), this.graph(), keyValues);
//        mongoEdge.save();
//        return mongoEdge;
    }


    public <V> VertexProperty<V> property(VertexProperty.Cardinality cardinality, String key, V value, Object... keyValues) {
        //TODO(implement)
        document = collection.findOneAndUpdate(Filters.eq(document.get("_id")),
                Updates.set(key, value),
        new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        return new MongoVertexProperty<V>(this, key, value, cardinality);
    }

    @Override
    public Iterator<Edge> edges(Direction direction, String... edgeLabels) {
        // Not relevant to our implementation
        return null;
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

    public void remove() {
        collection.deleteOne(Filters.eq(document.get("_id")));
    }

    public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {
        Document document = null;
        document = collection.find(Filters.eq(document.get("_id"))).first();
        Iterator<VertexProperty<V>> result = document.entrySet().stream().filter(x -> x.getKey() != "_id" && x.getKey() != "label"
                && (Arrays.stream(propertyKeys).anyMatch(x.getKey()::equals) || propertyKeys.length == 0))
                .map(x -> (VertexProperty<V>)(new MongoVertexProperty<V>(this, x.getKey(), (V)(x.getValue())))).collect(Collectors.toList()).iterator();
        return result;
    }


}
