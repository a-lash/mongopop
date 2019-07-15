package gremlin.structure;

import org.apache.tinkerpop.gremlin.structure.*;

import java.util.Iterator;

public class MongoVertex implements Vertex {

    public Edge addEdge(String label, Vertex inVertex, Object... keyValues) {
        //TODO(implement)
        return null;
    }

    public <V> VertexProperty<V> property(VertexProperty.Cardinality cardinality, String key, V value, Object... keyValues) {
        //TODO(implement)
        return null;
    }

    public Iterator<Edge> edges(Direction direction, String... edgeLabels) {
        //TODO(implement)
        return null;
    }

    public Iterator<Vertex> vertices(Direction direction, String... edgeLabels) {
        //TODO(implement)
        return null;
    }

    public Object id() {
        //TODO(implement)
        return null;
    }

    public String label() {
        //TODO(implement)
        return null;
    }

    public Graph graph() {
        //TODO(implement)
        return null;
    }

    public void remove() {
        //TODO(implement)
    }

    public <V> Iterator<VertexProperty<V>> properties(String... propertyKeys) {
        //TODO(implement)
        return null;
    }


}
