package com.mongodb.mongopop.gremlin.structure;

import com.mongodb.ConnectionString;
import com.mongodb.MongoInternalException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.mongopop.gremlin.MongoGraphFactory;
import com.mongodb.mongopop.gremlin.structure.*;
import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.AbstractGraphProvider;
import org.apache.tinkerpop.gremlin.LoadGraphWith.GraphData;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerProperty;
import org.mockito.internal.matchers.Any;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraphVariables;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Property;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class MongoGraphProvider extends AbstractGraphProvider {

    private static final String MONGODB_CONFIG_PREFIX = "gremlin.mongodb";

    @Override
    public void clear(Graph graph, Configuration configuration) throws Exception {
        ConnectionString url = new ConnectionString(configuration.getString(MONGODB_CONFIG_PREFIX + ".connectionUrl"));
        MongoClient client = MongoClients.create(url);
        client.getDatabase(url.getDatabase()).drop();
        client.close();
        return;
    }

    @Override
    public Set<Class> getImplementations() {
        Class classes[] = { MongoEdge.class, MongoGraph.class, MongoVertex.class, TinkerGraphVariables.class,
                TinkerProperty.class, MongoElement.class, MongoVertexProperty.class };
        return new HashSet<Class>(Arrays.asList(classes));
    }

    @Override
    public Map<String, Object> getBaseConfiguration(String graphName, Class<?> test, String testMethodName,
            GraphData loadGraphWith) {
        URL resource = this.getClass().getClassLoader().getResource("config.properties");
       
        InputStream stream = null;
        try {
            stream = resource.openStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Properties props = new Properties();
        try {
            props.load(stream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        HashMap<String, Object> configuration = new HashMap<String, Object>();
        configuration.put(Graph.GRAPH, MongoGraph.class.getName());
        for (Map.Entry<Object, Object> prop : props.entrySet()) {
            if (prop.getKey().toString().startsWith("gremlin.mongodb")) {
                configuration.put(prop.getKey().toString(), props.getProperty(prop.getKey().toString()));
            }
        }
        configuration.put(Graph.GRAPH, MongoGraph.class.getName());
        
        return configuration;
    }
    
}
