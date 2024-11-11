package graph;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Map;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
 
    
    @Test
    public void testToStringEmptyGraph() {
        Graph<String> graph = emptyInstance();
        assertEquals("ConcreteVerticesGraph:\n", graph.toString());
    }

    @Test
    public void testToStringGraphWithVertices() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("ConcreteVerticesGraph:\nVertex(A, targets={})\nVertex(B, targets={})\n", graph.toString());
    }

    @Test
    public void testToStringGraphWithEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        assertEquals("ConcreteVerticesGraph:\nVertex(A, targets={B=5})\nVertex(B, targets={})\n", graph.toString());
    }

    @Test
    public void testToStringAfterEdgeModification() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        graph.set("A", "B", 10); // Modify weight
        graph.set("B", "A", 3);  // Add another edge
        graph.set("A", "B", 0);  // Remove edge
        assertEquals("ConcreteVerticesGraph:\nVertex(A, targets={})\nVertex(B, targets={A=3})\n", graph.toString());
    }
    

    
    @Test
    public void testVertexLabelAndTargets() {
        Vertex vertex = new Vertex("A");
        assertEquals("A", vertex.getLabel());
        
        // Adding and modifying targets
        vertex.setTarget("B", 5);
        assertEquals(Map.of("B", 5), vertex.getTargets());
        
        vertex.setTarget("C", 3);
        assertEquals(Map.of("B", 5, "C", 3), vertex.getTargets());
        
        vertex.setTarget("B", 0); // Remove target B
        assertEquals(Map.of("C", 3), vertex.getTargets());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVertexNegativeEdgeWeight() {
        Vertex vertex = new Vertex("A");
        vertex.setTarget("B", -1); // Should throw IllegalArgumentException
    }

}
