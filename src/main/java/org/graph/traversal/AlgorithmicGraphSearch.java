package org.graph.traversal;

import org.graph.Graph;
import org.graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AlgorithmicGraphSearch<T> implements SearchAlgorithm<T>{

    /**
     * The graph on which to apply the DFS
     */
    protected Graph<T> graph;

    /**
     * Kepp track of which Vertices have been visited
     */
    protected Map<Vertex<T>, Boolean> visited;


    public AlgorithmicGraphSearch(Graph<T> graph) {
        this.graph = graph;
        this.visited = new HashMap<>();
    }

    protected Vertex<T> findUnvisitedVertex(){
        for (Vertex<T> v : graph.vertices()){
            if (!visited.containsKey(v) || !visited.get(v)){
                return v;
            }
        }
        return null;
    }

    /**
     * Return a list of all {@link Vertex} of the graph ordered based on the dfs order.
     * @return
     */
    public List<Vertex<T>> toList(T from){
        ListConsumer listConsumer = new ListConsumer();
        apply(from, listConsumer);
        return listConsumer.getVertices();
    }

    protected class ListConsumer implements Consumer<Vertex<T>> {

        private List<Vertex<T>> vertices = new ArrayList<>();
        List<Vertex<T>> getVertices() { return vertices; }

        @Override
        public void accept(Vertex<T> t) {
            vertices.add(t);
        }

    }


}
