package com.mongodb.mongopop.gremlin.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;
import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraphVariables;
import org.bson.Document;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal.Symbols.map;

@Graph.OptIn(Graph.OptIn.SUITE_STRUCTURE_STANDARD)
public class MongoGraph implements Graph {

    private final MongoClient client;
    final MongoCollection<Document> vertices;
    final MongoCollection<Document> edges;
    final MongoDatabase db;
    private final TinkerGraphVariables variables;
    private final Configuration conf;

    private static final String MONGODB_CONFIG_PREFIX = "gremlin.mongodb";

    public MongoGraph(Configuration conf) {
        ConnectionString url = new ConnectionString(conf.getString(MONGODB_CONFIG_PREFIX + ".connectionUrl"));
        this.client = MongoClients.create(url);
        this.db = client.getDatabase(url.getDatabase());
        this.vertices = db.getCollection("vertices");
        this.edges = db.getCollection("edges");
        this.variables = new TinkerGraphVariables();
        this.conf = conf;
    }

    public Vertex addVertex(Object... keyValues) {
        MongoVertex mongoVertex = null;
        if (keyValues.length > 0 && keyValues[0].getClass().equals(T.label)) {
            mongoVertex = new MongoVertex(new Document(), this, keyValues[1].toString());
        } else {
            mongoVertex = new MongoVertex(new Document(), this, keyValues);
        }

        mongoVertex.save();
        return mongoVertex;
    }

    public <C extends GraphComputer> C compute(Class<C> graphComputerClass) throws IllegalArgumentException {
        //TODO(implemented)        
        return null;
    }

    public GraphComputer compute() throws IllegalArgumentException {
        //TODO(implemented)        
        return null;
    }

    public Iterator<Vertex> vertices(Object... vertexIds) {
        String[] ids = Arrays.copyOf(vertexIds, vertexIds.length, String[].class);
        if (vertexIds.length == 0) {
            return ((Iterator<Vertex>) (vertices.find().map(it -> new MongoVertex(it, this))));
        }
        return ((Iterator<Vertex>) vertices.find(Filters.in("_id", ids)).map(it -> new MongoVertex(it, this)));
    }

    public Iterator<Edge> edges(Direction direction, Object... edgeIds) {
        if (edgeIds.length == 0) {
            return this.edges
                    .find(Filters.eq("inVertex", ObjectId(this.id().toString())))
                    .map(it -> new MongoEdge(it, this)).collect(Collectors.toList()).iterator();
        }
        ArrayList<Object> labels = new ArrayList(Arrays.asList(edgeIds));
        ArrayList<Edge> ans = new ArrayList<>();
        if (direction == Direction.IN || direction == Direction.BOTH) {
            ans.addAll(graph.edges
                    .find(Filters.and(Filters.eq("outVertex", ObjectId(this.id().toString())), Filters.in("label", labels)))
                    .map(it -> new MongoEdge(it, graph)));
        }
        if (direction == Direction.OUT || direction == Direction.BOTH) {
            ans.addAll(graph.edges
                    .find(Filters.and(Filters.eq("inVertex", ObjectId(this.id().toString())), Filters.in("label", labels)))
                    .map(it -> new MongoEdge(it, graph)));
        }
        return ans.iterator();
        /*// TODO move to MongoEdge
        return graph.edges
                .find(Filters.and(Filters.eq("inVertex", ObjectId(this.id().toString())), Filters.`in`("label", labels)))
                .map { MongoEdge(it, graph) }
                .iterator()*/
    }
    }

    public Transaction tx() {
        //TODO(implemented)
        return null;
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

}
