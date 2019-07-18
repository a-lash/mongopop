package com.mongodb.mongopop.gremlin.traversal;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;

public class MongoGraphTraversal implements GraphTraversal {
    
    public MongoGraphTraversal() {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    
}