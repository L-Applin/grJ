package org.graph;

import java.util.*;
import java.util.stream.Collectors;

import static org.graph.ImmutableGraph.*;
import static org.graph.MutableGraph._BasicMutableUnweightedUndirectedGraph;
import static org.graph.ImmutableVertex.BasicImmutableVertex;


public final class Graphs {

    public static final byte DIRECTED = 0x01;
    public static final byte WEIGHTED = 0x01 << 1;


    private Graphs()
    { throw new UnsupportedOperationException("Cannot create instances of static class Graphs"); }

    /**
     * Creates an builder for an immutable graphs. Edges and vertives can be added to the graph until the call to
     * {@link ImmutableBuilder#build()} is made wihch locks every adding or
     *
     * @param <T>
     * @return
     */
    public static <T>
    GraphBuilder<T> immutableBuilder() {
        return new ImmutableBuilder<>();
    }

    /**
     * Creates an immutable Graph from a mutable graph instance.
     * @param graph
     * @param <T>
     * @return
     */
    public static <T>
    ImmutableGraph<T> immutableFrom(MutableGraph<T> graph){

        Collection<Vertex<T>> mutableVertices = graph.vertices();
        Collection<ImmutableVertex<T>> immutableVertices = mutableVertices.stream().map(BasicImmutableVertex::empty).collect(Collectors.toList());

        mutableVertices.forEach(v -> {

            Set<ImmutableVertex<T>> tempNeigh = new HashSet<>();

            v.neighbours().forEach(neigh -> {
                ImmutableVertex<T> currentNeigh = findImmutableVertex(neigh.value(), immutableVertices);
                tempNeigh.add(currentNeigh);
            });

            ((BasicImmutableVertex<T>) findImmutableVertex(v.value(), immutableVertices)).editNeighboursReflection(tempNeigh);

        });

        return new _BasicImmutableUndirectedUnweightedGraph<>(new HashSet<>(immutableVertices), graph.id);
    }

    private static <T> ImmutableVertex<T> findImmutableVertex(T valueToFind, Collection<ImmutableVertex<T>> coll){
        for (ImmutableVertex<T> v : coll){
            if (v.value().equals(valueToFind)){
                return v;
            }
        }
        return null;

    }

    public static <T>
    MutableGraph<T> mutable(int... configs){
        if (configs.length == 0){
            return new _BasicMutableUnweightedUndirectedGraph<>(new HashSet<>());
        }
        // todo : weighted and directed graphs
        return null;
    }





}
























