package gremlin.structure;

import org.apache.tinkerpop.gremlin.structure.StructureStandardSuite;
import org.junit.runner.RunWith;
import gremlin.structure.*;
import org.apache.tinkerpop.gremlin.GraphProviderClass;

@RunWith(StructureStandardSuite.class)
@GraphProviderClass(provider = MongoGraphProvider.class, graph = MongoGraph.class) public class MongoGraphStructureStandardTest {}