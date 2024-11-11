/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ConcreteEdgesGraph implements Graph<String> {
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Constructor for the ConcreteEdgesGraph, ensures representation invariant holds.
    public ConcreteEdgesGraph() {
        checkRep();
    }

    // Checks the representation invariant: every edge's source and target should exist in the vertices set.
    private void checkRep() {
        for (Edge e : edges) {
            assert vertices.contains(e.getSource()) && vertices.contains(e.getTarget());
        }
    }

    @Override public boolean add(String vertex) {
        if (vertices.add(vertex)) {
            checkRep();
            return true;
        }
        return false;
    }

    // Sets an edge between source and target with a given weight, replacing the old weight if an edge already exists.
    @Override
    public int set(String source, String target, int weight) {
        if (!vertices.contains(source) || !vertices.contains(target)) {
            throw new IllegalArgumentException("Source or target vertex does not exist.");
        }

        // Update existing edge weight if found, otherwise add a new edge.
        for (Edge e : edges) {
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                int oldWeight = e.getWeight();
                edges.remove(e);
                if (weight > 0) {
                    edges.add(new Edge(source, target, weight));
                }
                checkRep();
                return oldWeight;
            }
        }
        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
        }
        checkRep();
        return 0;
    }


    // Removes a vertex and all its associated edges from the graph.
    @Override public boolean remove(String vertex) {
        if (vertices.remove(vertex)) {
            edges.removeIf(e -> e.getSource().equals(vertex) || e.getTarget().equals(vertex));
            checkRep();
            return true;
        }
        return false;
    }

    @Override public Set<String> vertices() {
        return new HashSet<>(vertices);
    }

    // Returns a map of all source vertices with their corresponding weights that point to the given target.
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge e : edges) {
            if (e.getTarget().equals(target)) {
                sources.put(e.getSource(), e.getWeight());
            }
        }
        return sources;
    }

    // Returns a map of all target vertices with their corresponding weights that are pointed to by the given source.
    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge e : edges) {
            if (e.getSource().equals(source)) {
                targets.put(e.getTarget(), e.getWeight());
            }
        }
        return targets;
    }

    @Override public String toString() {
        return "Vertices: " + vertices + ", Edges: " + edges;
    }
}



class Edge {
    private final String source;
    private final String target;
    private final int weight;

    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    public String getSource() { return source; }
    public String getTarget() { return target; }
    public int getWeight() { return weight; }

    private void checkRep() {
        assert weight > 0;
        assert source != null && !source.isEmpty();
        assert target != null && !target.isEmpty();
    }

    @Override
    public String toString() {
        return source + " -> " + target + " (" + weight + ")";
    }
}

