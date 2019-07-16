package com.mongodb.mongopop.gremlin;

import com.mongodb.mongopop.gremlin.structure.MongoGraph;
import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.structure.Graph;

public class MongoGraphFactory {

    public static Graph open(Configuration conf) {
        return new MongoGraph(conf);
    }
}