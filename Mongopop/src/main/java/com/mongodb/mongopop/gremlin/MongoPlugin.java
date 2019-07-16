/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mongodb.mongopop.gremlin;

import com.mongodb.mongopop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.jsr223.AbstractGremlinPlugin;
import org.apache.tinkerpop.gremlin.jsr223.DefaultImportCustomizer;
import org.apache.tinkerpop.gremlin.jsr223.ImportCustomizer;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerProperty;


@SuppressWarnings("ALL")
public final class MongoPlugin extends AbstractGremlinPlugin {

    private static final String NAME = "mongopop.mongo";

    private static final ImportCustomizer imports;

    static {
        try {
            imports = DefaultImportCustomizer.build()
                    .addClassImports(MongoEdge.class,
                            MongoElement.class,
                            MongoGraph.class,
                            MongoProperty.class,
                            TinkerProperty.class,
                            MongoVertexProperty.class,
                            MongoVertex.class).create();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static final MongoPlugin instance = new MongoPlugin();

    public MongoPlugin() {
        super(NAME, imports);
    }

    public static MongoPlugin instance() {
        return instance;
    }

    @Override
    public boolean requireRestart() {
        return true;
    }
}
