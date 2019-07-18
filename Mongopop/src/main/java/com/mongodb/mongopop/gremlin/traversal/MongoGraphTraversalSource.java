package com.mongodb.mongopop.gremlin.traversal;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.bson.Document;

public class MongoGraphTraversalSource extends GraphTraversalSource {

    public MongoGraphTraversalSource(Graph graph) {
        super(graph);
    }

}