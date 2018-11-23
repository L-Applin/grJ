package org.graph;

import java.util.Map;
import java.util.function.Function;

class GraphVisitor<T> {

    private Map<Vertex<T>, Boolean> visited;
    private Graph<T> graph;

    public GraphVisitor(Graph<T> graph) {
        this.graph = graph;
    }

    public void setVisited(Vertex<T> vertex){
        visited.put(vertex, true);
    }

    public boolean visited(Vertex<T> vertex){
        return visited.get(vertex);
    }


    private void recursiveDFS(Function<Vertex<T>, Vertex<T>> consumer, Vertex<T> start){
        for (Vertex<T> current : start.neighbours()){
            if (!visited(current)){
                visited(current);
                recursiveDFS(consumer, current);
            }
        }
        start = consumer.apply(start);

    }

}
