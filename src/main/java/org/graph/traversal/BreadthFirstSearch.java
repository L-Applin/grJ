package org.graph.traversal;

import org.graph.Graph;
import org.graph.Vertex;
import org.graph.VertexNotFoundException;

import java.util.*;
import java.util.function.Consumer;

public class BreadthFirstSearch<T> extends AlgorithmicGraphSearch<T> {

    /**
     * The graph on which to apply the DFS
     */
    private Graph<T> graph;


    /**
     * Kepp track of which Vertices have been visited
     */
    private Map<Vertex<T>, Boolean> visited;


    /**
     * The queue used for the BFS algorithm
     */
    private Queue<Vertex<T>> q;


    public BreadthFirstSearch(Graph<T> graph) {
        super(graph);
        q = new LinkedList<>();
    }

    private void bfs(Vertex<T> start, Consumer<Vertex<T>> consumer){

        q.addAll(start.neighbours());
        Vertex<T> head;
        while ((head = q.remove()) != null){
            if (!visited.containsKey(head)){
                visited.put(head, true);
                consumer.accept(head);
                q.addAll(head.neighbours());
            }
        }

        if (visited.size() < graph.size()){

        }

    }


    @Override
    public List<Vertex<T>> toList(T from) {
        return null;
    }


    @Override
    public void apply(T start, Consumer<Vertex<T>> consumer) {

        Vertex<T> fromElem = graph.getVertex(start);

        if (fromElem == null){
            throw new VertexNotFoundException("Cannot found vertex with element %s", start.toString());
        }

        bfs(fromElem, consumer);

    }

}
