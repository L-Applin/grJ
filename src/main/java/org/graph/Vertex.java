package org.graph;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Abstract representation of a node (vertex) in the Graph data structure.
 * Custom implementation of Vertex interface should respect that <code>v1.equals(v2)</code> is true if and only if
 * <code>v1.value().equals(v2.value())</code>.As such, it is usually important to override the default
 * {@link Object#equals(Object)} implementation of the Type that will be stored inside a Vertex.
 *
 * @param <T> The type of element contained into each vertex.
 */
public interface Vertex<T> {

    /**
     * Returns a collection of all vertices connected to this Vertex. The contract stipulates that a directed graph
     * should only returns the child vertices, wheras an undirected grpah sould return all connected vertices.
     * @return The collection containing all
     */
    Collection<Vertex<T>> neighbours();

    /**
     * This method is the primary entry point for getting back the value associated with the vertex itself.
     * Modifications the the returned value may or may not modify the value in the graph based on the
     * specific implementation used.
     * @return The value contained in this vertex
     */
    T value();


    /**
     * Set or changes the value contained in this vertex.
     * @param newValue the new value that this vertex will contain.
     */
    void setValue(T newValue);


    /**
     * Add a new neighbours to this Vertex. In case of an undirected graph, the vertex passed in parameters should
     * also have this vertex in his neighbours.
     * @param newNeighbour the new vertex to add to this vertex neighbours
     * @return All of this vertex's neighbours with the newly added neighbour
     */
    Collection<Vertex<T>> addNeighbours(Vertex<T> newNeighbour);


    default String toDescriptiveString(){
        return "{" +
                value() + " : " + neighbours().stream().map(Vertex::value).collect(Collectors.toList()) +
                '}';

    }


}
