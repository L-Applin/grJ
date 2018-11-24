package org.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represent a series of changes to be applied to a graph
 */
public class GraphChangeLog<T> {

    private MutableGraph<T> graph;

    private Set<Vertex<T>> verticesToAdd;
    private Set<Edge<T>> edgesToAdd;

    private Set<Vertex<T>> verticesToRemove;
    private Set<Edge<T>> edgesToRemove;



    public GraphChangeLog(MutableGraph<T> graph) {
        // todo : DEPENDENCYINJECTION :(
        this(graph, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    }


    public GraphChangeLog(MutableGraph<T> graph, Set<Vertex<T>> verticesToAdd, Set<Edge<T>> edgesToAdd, Set<Vertex<T>> verticesToARemove, Set<Edge<T>> edgesToRemove) {
        this.graph = graph;
        this.verticesToAdd = verticesToAdd;
        this.edgesToAdd = edgesToAdd;
        this.verticesToRemove = verticesToARemove;
        this.edgesToRemove = edgesToRemove;
    }


    public GraphChangeLog<T> vertex(T v){
        verticesToAdd.add(Vertices.mutableVertex(v));
        return this;
    }


    @SafeVarargs
    public final GraphChangeLog<T> vertices(T... vs){
        Arrays.stream(vs).forEach(this::vertex);
        return this;
    }

    public GraphChangeLog<T> edge(T v1, T v2){
        edgesToAdd.add(Edges.basicImmutable(v1, v2));
        return this;
    }

    public GraphChangeLog<T> removeVertex(T elem){
        verticesToRemove.add(Vertices.mutableVertex(elem));
        return this;
    }

    public GraphChangeLog<T> removeEdge(T v1, T v2){
        edgesToRemove.add(Edges.basicImmutable(v1, v2));
        return this;
    }


    public void commitChanges(){
        System.out.println(verticesToAdd);
        System.out.println(edgesToAdd);

        graph.removeAll(verticesToRemove);
        edgesToRemove.forEach(graph::removeEdge);

        graph.addAll(verticesToAdd);
        edgesToAdd.forEach(graph::addEdge);

    }

}
