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
        // ConnectionString url = new ConnectionString(conf.getString(MONGODB_CONFIG_PREFIX + ".connectionUrl"));
        ConnectionString url = new ConnectionString("mongodb://localhost:27017/test");
        this.client = MongoClients.create(url);
        this.db = client.getDatabase(url.getDatabase());
        this.vertices = db.getCollection("vertices");
        this.edges = db.getCollection("edges");
        this.variables = new TinkerGraphVariables();
        this.conf = conf;
    }

    @Override
    public Vertex addVertex(Object... keyValues) {
        return null;
    }

    @Override
    public <C extends GraphComputer> C compute(Class<C> graphComputerClass) throws IllegalArgumentException {
        return null;
    }

    @Override
    public GraphComputer compute() throws IllegalArgumentException {
        return null;
    }

    @Override
    public Iterator<Vertex> vertices(Object... vertexIds) {
        return null;
    }

    @Override
    public Iterator<Edge> edges(Object... edgeIds) {
        return null;
    }

    @Override
    public Transaction tx() {
        return null;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Variables variables() {
        return null;
    }

    @Override
    public Configuration configuration() {
        return null;
    }

}
