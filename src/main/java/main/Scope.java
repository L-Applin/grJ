package main;

import org.graph.Graph;
import org.graph.GraphBuilder;
import org.graph.Graphs;

public class Scope {

    public static void main(String[] args) {

        Graph<Integer> g = Graphs.builder(Integer.class)
                .weighted()
                .directed()
                .addVertex()
                .addEdge(1, 2)
                .immutable()
                .build();

        g.log();

    }

}
