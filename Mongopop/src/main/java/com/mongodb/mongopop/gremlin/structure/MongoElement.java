package com.mongodb.mongopop.gremlin.structure;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.util.ElementHelper;
import org.bson.Document;

public abstract class MongoElement implements Element {
    MongoCollection<Document> collection;
    MongoGraph graph;
    Document document;

    protected MongoElement(Document document, MongoGraph graph) {
        this.document = document;
        this.graph = graph;
    }

    //TODO: document might need custom id? error is happening in the driver
    public void save() {
        collection.insertOne(document);
    }

    public Graph graph() {
        return graph();
    }

    public Object id() {
        return this.document.get("_id");
    }

    public String label() {
        return document.getString("label");
    }

    public void remove() {
        collection.deleteOne(Filters.eq(document.get("_id")));
    }

    @Override
    public boolean equals(Object other) {
        return ElementHelper.areEqual(this, other);
    }

    @Override
    public int hashCode() {
        return ElementHelper.hashCode(this);
    }


}