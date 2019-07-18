package com.mongodb.mongopop.gremlin.traversal;

import com.mongodb.ConnectionString;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.*;
import org.bson.Document;

public class MongoGraphTraversal implements GraphTraversal {
    
    private final MongoClient client;
    final MongoCollection<Document> vertices;
    final MongoCollection<Document> edges;
    final MongoDatabase db;

    public MongoGraphTraversal() {
        ConnectionString url = new ConnectionString("mongodb://localhost:27018/test");
        this.client = MongoClients.create(url);
        this.db = client.getDatabase(url.getDatabase());
        this.vertices = db.getCollection("vertices");
        this.edges = db.getCollection("edges");
    }

    @Override
    public boolean hasNext() {
        if (this.getClass().equals(Vertex.class)) {
            Vertex v = (Vertex)(this);
            FindIterable<Document> edges = db.getCollection("edges").find(Filters.eq("inVertex", v.id()));
            return edges.iterator().hasNext();
        } 
        return true;
    }

    @Override
    public Object next() {
        if (this.getClass().equals(Vertex.class)) {
            Vertex v = (Vertex)(this);
            FindIterable<Document> edges = db.getCollection("edges").find(Filters.eq("inVertex", v.id()));
            return edges.iterator().next();
        } else {
            Edge e = (Edge)(this);
            FindIterable<Document> vertex = db.getCollection("vertices").find(Filters.eq("_id", e.outVertex().id()));
            return vertex.iterator().next();
        }
    }
    
}