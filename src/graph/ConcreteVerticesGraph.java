package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;


public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();

    // Checks the representation invariant: ensures there are no duplicate vertex labels.
    private void checkRep() {
        Set<String> labels = new HashSet<>();
        for (Vertex v : vertices) {
            assert !labels.contains(v.getLabel()) : "Duplicate vertex label found";
            labels.add(v.getLabel());
        }
    }

    // Adds a vertex to the graph if it doesn't already exist.
    @Override
    public boolean add(String vertex) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(vertex)) {
                return false; // Vertex already exists
            }
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }
    
    // Sets an edge from source to target with a given weight, returning the previous weight.
    @Override
    public int set(String source, String target, int weight) {
        Vertex srcVertex = null;
        Vertex tgtVertex = null;

        for (Vertex v : vertices) {
            if (v.getLabel().equals(source)) {
                srcVertex = v;
            }
            if (v.getLabel().equals(target)) {
                tgtVertex = v;
            }
        }

        // Throw exception if either source or target vertex does not exist
        if (srcVertex == null || tgtVertex == null) {
            throw new IllegalArgumentException("Both source and target vertices must exist.");
        }

        // Set the edge from source to target, and return the previous weight.
        int previousWeight = srcVertex.setTarget(target, weight);
        checkRep();
        return previousWeight;
    }

    // Removes a vertex from the graph, and also removes any edges pointing to it.
    @Override
    public boolean remove(String vertex) {
        Vertex toRemove = null;
        for (Vertex v : vertices) {
            if (v.getLabel().equals(vertex)) {
                toRemove = v;
                break;
            }
        }
        
        if (toRemove == null) {
            return false;
        }
        
        vertices.remove(toRemove);
        for (Vertex v : vertices) {
            v.removeTarget(vertex);
        }
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        Set<String> result = new HashSet<>();
        for (Vertex v : vertices) {
            result.add(v.getLabel());
        }
        return result;
    }
    
    // Returns a map of source vertices with their corresponding weights that point to the given target.
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();
        for (Vertex v : vertices) {
            if (v.getTargets().containsKey(target)) {
                result.put(v.getLabel(), v.getTargets().get(target));
            }
        }
        return result;
    }
    
    // Returns a map of target vertices with their corresponding weights that are pointed to by the given source.
    @Override
    public Map<String, Integer> targets(String source) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(source)) {
                return new HashMap<>(v.getTargets());
            }
        }
        return new HashMap<>();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ConcreteVerticesGraph:\n");
        for (Vertex v : vertices) {
            sb.append(v.toString()).append("\n");
        }
        return sb.toString();
    }
}

/**
 * Vertex represents a vertex in a directed graph with String label.
 * It is mutable and can have edges to other vertices with associated weights.
 */
class Vertex {
    
    private final String label;
    private final Map<String, Integer> targets = new HashMap<>();
    

    // Check representation invariant
    private void checkRep() {
        for (int weight : targets.values()) {
            assert weight >= 0 : "Edge weight cannot be negative";
        }
    }
    
    public Vertex(String label) {
        this.label = label;
        checkRep();
    }
    
    public String getLabel() {
        return label;
    }
    
    public Map<String, Integer> getTargets() {
        return new HashMap<>(targets); // return a copy to prevent rep exposure
    }
    
    // Sets the target vertex with the specified weight, returning the previous weight.
    public int setTarget(String target, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative.");
        }
        int previousWeight = targets.getOrDefault(target, 0);
        if (weight == 0) {
            targets.remove(target);
        } else {
            targets.put(target, weight);
        }
        checkRep();
        return previousWeight;
    }


    
    public boolean removeTarget(String target) {
        return targets.remove(target) != null;
    }
    
    @Override
    public String toString() {
        return "Vertex(" + label + ", targets=" + targets + ")";
    }
}
