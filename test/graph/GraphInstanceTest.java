package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public abstract class GraphInstanceTest {

    public abstract Graph<String> emptyInstance();

    // Test empty graph vertices
    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    // Test adding a single vertex
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected add to succeed", graph.add("A"));
        assertEquals("expected one vertex in graph", 
                Set.of("A"), graph.vertices());
    }

    // Test adding a duplicate vertex
    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse("expected add to fail on duplicate vertex", graph.add("A"));
        assertEquals("expected one vertex in graph", 
                Set.of("A"), graph.vertices());
    }

    // Test removing a vertex
    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("expected remove to succeed", graph.remove("A"));
        assertEquals("expected no vertices in graph after removal", 
                Collections.emptySet(), graph.vertices());
    }

    // Test adding an edge between two vertices
    @Test
    public void testAddEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("expected edge weight to be zero initially", 
                0, graph.set("A", "B", 5));
        assertEquals("expected new edge weight", 
                5, graph.set("A", "B", 10));
    }

    // Test removing an edge by setting its weight to zero
    @Test
    public void testRemoveEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        assertEquals("expected edge weight before removal", 
                5, graph.set("A", "B", 0));
        assertEquals("expected no edge after removal", 
                0, graph.set("A", "B", 0));
    }

    // Test self-loop edge
    @Test
    public void testSelfLoopEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertEquals("expected self-loop weight to be zero initially", 
                0, graph.set("A", "A", 7));
        assertEquals("expected new self-loop weight", 
                7, graph.set("A", "A", 9));
    }

    // Test vertices method after adding multiple vertices
    @Test
    public void testVerticesAfterMultipleAdds() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        assertEquals("expected three vertices in graph", 
                Set.of("A", "B", "C"), graph.vertices());
    }

    // Test adding edge to non-existent vertex
    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeToNonExistentVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.set("A", "NonExistent", 5);
    }

    // Test removing a non-existent vertex
    @Test
    public void testRemoveNonExistentVertex() {
        Graph<String> graph = emptyInstance();
        assertFalse("expected remove to fail on non-existent vertex", 
                graph.remove("NonExistent"));
    }

    // Test isolated vertices with no edges
    @Test
    public void testIsolatedVertices() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("expected two isolated vertices", 
                Set.of("A", "B"), graph.vertices());
    }
}
