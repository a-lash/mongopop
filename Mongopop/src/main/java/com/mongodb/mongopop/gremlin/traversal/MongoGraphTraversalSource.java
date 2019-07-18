package com.mongodb.mongopop.gremlin.traversal;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;

public class MongoGraphTraversalSource extends GraphTraversalSource {

    public MongoGraphTraversalSource(Graph graph) {
        super(graph);
    }
    
}