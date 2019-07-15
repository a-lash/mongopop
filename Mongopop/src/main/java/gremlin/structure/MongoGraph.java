package gremlin.structure;

import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class MongoGraph implements Graph {

    public Vertex addVertex(Object... keyValues) {
        return null;
    }

    public <C extends GraphComputer> C compute(Class<C> graphComputerClass) throws IllegalArgumentException {
        return null;
    }

    public GraphComputer compute() throws IllegalArgumentException {
        return null;
    }

    public Iterator<Vertex> vertices(Object... vertexIds) {
        return null;
    }

    public Iterator<Edge> edges(Object... edgeIds) {
        return null;
    }

    public Transaction tx() {
        return null;
    }

    public void close() throws Exception {

    }

    public Variables variables() {
        return null;
    }

    public Configuration configuration() {
        return null;
    }

}
