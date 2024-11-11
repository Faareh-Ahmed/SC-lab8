package graph;

import static org.junit.Assert.*;
import org.junit.Test;

public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    

    
    @Test
    public void testToStringEmptyGraph() {
        Graph<String> graph = emptyInstance();
        assertEquals("Vertices: [], Edges: []", graph.toString());
    }

    @Test
    public void testToStringGraphWithVertices() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("Vertices: [A, B], Edges: []", graph.toString());
    }

    @Test
    public void testToStringGraphWithEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        assertEquals("Vertices: [A, B], Edges: [A -> B (5)]", graph.toString());
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
        assertEquals("Vertices: [A, B], Edges: [B -> A (3)]", graph.toString());
    }
    

    
    @Test
    public void testEdgeCreationAndAttributes() {
        Edge edge = new Edge("A", "B", 5);
        assertEquals("A", edge.getSource());
        assertEquals("B", edge.getTarget());
        assertEquals(5, edge.getWeight());
    }

    @Test(expected = AssertionError.class)
    public void testEdgeNegativeWeight() {
        new Edge("A", "B", -5); // Should fail due to assertion
    }
}
