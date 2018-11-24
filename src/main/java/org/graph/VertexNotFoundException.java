package org.graph;

public class VertexNotFoundException extends NullPointerException {
    public VertexNotFoundException(String s) {
        super(s);
    }

    public VertexNotFoundException(String s, String... args) {
        super(String.format(s, args));
    }

}
