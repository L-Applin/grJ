package org.graph;

public interface GraphBuilder<T> {

    GraphBuilder <T> addVertex            (T vertex);
    GraphBuilder <T> addEdge              (T v1, T v2);
    GraphBuilder <T> removeVertex         (T vertex);
    GraphBuilder <T> removeEdge           (T v1, T v2);
    GraphBuilder <T> replaceVertexValue   (T oldVertexValue, T newValue);

    Graph <T> build();


}
