package org.graph.traversal;


import org.graph.Graph;
import org.graph.Vertex;

import java.util.List;
import java.util.function.Consumer;

public interface SearchAlgorithm<T> {


    /**
     * Returns all {@link Vertex} from the graph ordered based on the specification of the search algorithm.
     * @return the list
     */
    List<Vertex<T>> toList(T from);


    /**
     * Aplly the consumer on every {@link Vertex} of the {@link org.graph.Graph} in the order
     * defined by the search algorithm.
     * @param start the starting point of the search algorithm
     * @param consumer the action to perform
     */
    void apply(T start, Consumer<Vertex<T>> consumer);




}
