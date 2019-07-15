package gremlin.structure;

import org.apache.tinkerpop.gremlin.structure.*;

import java.util.Iterator;

public class MongoVertex implements Vertex {

    public Edge addEdge(String label, Vertex inVertex, Object... keyValues) {
        return null;
    }

    public <V> VertexProperty<V> property(VertexProperty.Cardinality cardinality, String key, V value, Object... keyValues) {
        return null;
    }

    public Iterator<Edge> edges(Direction direction, String... edgeLabels) {
        return null;
    }

    public Iterator<Vertex> vertices(Direction direction, String... edgeLabels) {
        return null;
    }

    public Object id() {
        return null;
    }

    public String label() {
        return null;
    }

    public Graph graph() {
        return null;
    }

    public void remove() {

    }

    public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {
        return null;
    }


}
