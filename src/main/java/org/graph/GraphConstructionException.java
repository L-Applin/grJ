package org.graph;

public class GraphConstructionException extends RuntimeException {

    private GraphBuilder builder;
    public GraphBuilder getBuilder() {
        return builder;
    }

    public GraphConstructionException(String message) {
        super(message);
    }

    public GraphConstructionException(String message, GraphBuilder builder) {
        super(message);
        this.builder = builder;
    }

}
