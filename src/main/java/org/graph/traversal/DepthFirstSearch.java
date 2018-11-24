package org.graph.traversal;

import org.graph.Graph;
import org.graph.Vertex;
import org.graph.VertexNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DepthFirstSearch<T> extends AlgorithmicGraphSearch<T>{


    public DepthFirstSearch(Graph<T> graph) {
        super(graph);
    }

    /**
     * Run the consumer on every {@link Vertex} of the graph in dfs order
     *
     * @param start the starting point of
     * @param consumer the action to be applied on each vertices
     */
    public void apply(T start, Consumer<Vertex<T>> consumer) {

        Vertex<T> fromElem = graph.getVertex(start);
        if (fromElem == null){
            throw new VertexNotFoundException("Cannot found vertex with element %s", start.toString());
        }

        applyAsVertex(fromElem, consumer);

    }

    private void applyAsVertex(Vertex<T> fromElem, Consumer<Vertex<T>> consumer){
        if (fromElem == null){
            fromElem = findUnvisitedVertex();
        }

        recursiveDFS(fromElem, consumer);

        if (visited.size() < graph.size()){
            applyAsVertex(findUnvisitedVertex(), consumer);
        }

    }

    private void recursiveDFS(Vertex<T> fromElem, Consumer<Vertex<T>> consumer){
        if (!visited.containsKey(fromElem)) {

            visited.put(fromElem, true);

            if (consumer != null) {
                consumer.accept(fromElem);
            }

            fromElem.neighbours().forEach(n -> recursiveDFS(n, consumer));

        }

    }



    public Graph<T> tree(T from){
        // todo
        return null;
    }


}
