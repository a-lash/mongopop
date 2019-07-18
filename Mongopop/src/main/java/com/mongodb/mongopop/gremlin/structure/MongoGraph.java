package com.mongodb.mongopop.gremlin.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.mongopop.gremlin.MongoGraphFactory;

import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.GraphFactoryClass;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraphVariables;
import org.bson.Document;
import org.bson.types.ObjectId;

@Graph.OptIn(Graph.OptIn.SUITE_STRUCTURE_STANDARD)
@GraphFactoryClass(MongoGraphFactory.class)
@Graph.OptOut(test = "org.apache.tinkerpop.gremlin.structure.TransactionTest",
method = "*",
reason = "not implemented")
public class MongoGraph implements Graph {

    private final MongoClient client;
    final MongoCollection<Document> vertices;
    final MongoCollection<Document> edges;
    final MongoDatabase db;
    private final TinkerGraphVariables variables;
    private final Configuration conf;

    private static final String MONGODB_CONFIG_PREFIX = "gremlin.mongodb";

    public MongoGraph(Configuration conf) {
        System.out.println("is running...");
        ConnectionString url = new ConnectionString(conf.getString(MONGODB_CONFIG_PREFIX + ".connectionUrl"));
        this.client = MongoClients.create(url);
        this.db = client.getDatabase(url.getDatabase());
        this.vertices = db.getCollection("vertices");
        this.edges = db.getCollection("edges");
        this.variables = new TinkerGraphVariables();
        this.conf = conf;
    }

    public Vertex addVertex(Object... keyValues) {
        MongoVertex mongoVertex = new MongoVertex(new Document(), this, keyValues);
        mongoVertex.save();
        return mongoVertex;
    }

    public Vertex addVertex(String label) {
        MongoVertex mongoVertex = new MongoVertex(new Document(), this, label);
        mongoVertex.save();
        return mongoVertex;
    }

    public Vertex addVertex() {
        MongoVertex mongoVertex = new MongoVertex(new Document(), this, "DEFAULT VERTEX");
        mongoVertex.save();
        return mongoVertex;
    }

    public <C extends GraphComputer> C compute(Class<C> graphComputerClass) throws IllegalArgumentException {
        //TODO(implemented)
        throw Graph.Exceptions.graphComputerNotSupported();
    }

    public GraphComputer compute() throws IllegalArgumentException {
        //TODO(implemented)        
        throw Graph.Exceptions.graphComputerNotSupported();
    }

    public Iterator<Vertex> vertices(Object... vertexIds) {
        if (vertexIds.length == 0) {
            return vertices.find().map(it -> (Vertex)new MongoVertex(it, this)).iterator();
        }
        else {
            if (vertexIds[0] instanceof Vertex) {
                List<Object> ids = Arrays.asList(vertexIds).stream().map(it -> ((Vertex) it).id()).collect(Collectors.toList());
                return vertices.find(Filters.in("_id", ids)).map(it -> (Vertex)new MongoVertex(it, this)).iterator();
            }
            else {
                List<Object> ids = Arrays.asList(vertexIds);
                return vertices.find(Filters.in("_id", ids)).map(it -> (Vertex)new MongoVertex(it, this)).iterator();
            }
        }
    }

    public Iterator<Edge> edges(Object... edgeIds) {
        if (edgeIds.length == 0) {
            return edges.find().map(it -> (Edge)new MongoEdge(it, this)).iterator();
        }
        else {
            if (edgeIds[0] instanceof Edge) {
                List<Object> ids = Arrays.asList(edgeIds).stream().map(it -> ((Edge) it).id()).collect(Collectors.toList());
                return edges.find(Filters.in("_id", ids)).map(it -> (Edge)new MongoEdge(it, this)).iterator();
            }
            else {
                List<Object> ids = Arrays.asList(edgeIds);
                return edges.find(Filters.in("_id", ids)).map(it -> (Edge)new MongoEdge(it, this)).iterator();
            }
        }
    }

//    public Iterator<Edge> edges(Direction direction, Object... edgeIds) {
//        if (edgeIds.length == 0) {
//            return this.edges
//                    .find(Filters.eq("inVertex", ObjectId(this.id().toString())))
//                    .map(it -> new MongoEdge(it, this)).collect(Collectors.toList()).iterator();
//        }
//        ArrayList<Object> labels = new ArrayList(Arrays.asList(edgeIds));
//        ArrayList<Edge> ans = new ArrayList<>();
//        if (direction == Direction.IN || direction == Direction.BOTH) {
//            ans.addAll(graph.edges
//                    .find(Filters.and(Filters.eq("outVertex", ObjectId(this.id().toString())), Filters.in("label", labels)))
//                    .map(it -> new MongoEdge(it, graph)));
//        }
//        if (direction == Direction.OUT || direction == Direction.BOTH) {
//            ans.addAll(graph.edges
//                    .find(Filters.and(Filters.eq("inVertex", ObjectId(this.id().toString())), Filters.in("label", labels)))
//                    .map(it -> new MongoEdge(it, graph)));
//        }
//        return ans.iterator();
//        /*// TODO move to MongoEdge
//        return graph.edges
//                .find(Filters.and(Filters.eq("inVertex", ObjectId(this.id().toString())), Filters.`in`("label", labels)))
//                .map { MongoEdge(it, graph) }
//                .iterator()*/
//    }

    public Transaction tx() {
        //TODO(implemented)
            throw Graph.Exceptions.transactionsNotSupported();
    }

    public void close() throws Exception {
        client.close();
    }

    public Variables variables() {
        return variables;
    }

    public Configuration configuration() {
        return conf;
    }

    public MongoCollection<Document> getVertices() {
        return vertices;
    }

    public MongoCollection<Document> getEdges() {
        return edges;
    }
}
