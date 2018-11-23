package org.graph;

interface Edge<T> {

    Vertex<T>[] vertices();


    /**
     * A basic immutable implementation of the Edge interface
     * @param <T>
     */
    class BasicImmutableEdge<T> implements Edge<T> {
        private final Vertex<T> first, second;
        public BasicImmutableEdge(Vertex<T> first, Vertex<T> second) {
            this.first = first;
            this.second = second;
        }

        @SuppressWarnings({"unchecked"})
        public Vertex<T>[] vertices() {
            return (Vertex<T>[]) new Object[]{first, second};
        }
    }

}
