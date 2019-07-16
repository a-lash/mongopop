package gremlin.structure;

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
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraphVariables;
import org.bson.Document;

@Graph.OptIn(Graph.OptIn.SUITE_STRUCTURE_STANDARD)
public class MongoGraph implements Graph {

    private final MongoClient client;
    final MongoCollection<Document> vertices;
    final MongoCollection<Document> edges;
    final MongoDatabase db;
    private final TinkerGraphVariables variables;
    private final Configuration conf;

    private static final String MONGODB_CONFIG_PREFIX = "gremlin.mongodb";

    protected MongoGraph(Configuration conf) {
        ConnectionString url = new ConnectionString(conf.getString(MONGODB_CONFIG_PREFIX + ".connectionUrl"));
        this.client = MongoClients.create(url);
        this.db = client.getDatabase(url.getDatabase());
        this.vertices = db.getCollection("vertices");
        this.edges = db.getCollection("edges");
        this.variables = new TinkerGraphVariables();
        this.conf = conf;
    }

    public Vertex addVertex(Object... keyValues) {
        MongoVertex mongoVertex = new MongoVertex(this, *keyValues);
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

    public Iterator<Edge> edges(Object... edgeIds) {
        return null;
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
