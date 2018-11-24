package org.graph;

import java.lang.reflect.Array;
import java.util.Objects;

public final class Edges {

    private Edges()
    { throw new UnsupportedOperationException("Cannot create instances of static class Edges"); }


    /**
     * A basic immutable implementation of the Edge interface
     * @param <T>
     */
    static class BasicImmutableEdge<T> implements Edge<T> {
        private final Vertex<T> first, second;
        public BasicImmutableEdge(Vertex<T> first, Vertex<T> second) {
            this.first = first;
            this.second = second;
        }

        @SuppressWarnings({"unchecked"})
        public T[] asElement() {
            return (T[]) new Object[]{first.value(), second.value()};
        }

        @SuppressWarnings({"unchecked"})
        public Vertex<T>[] vertices() {
            Vertex<T>[] arr = (Vertex<T>[]) Array.newInstance(first.getClass(), 2);
            arr[0] = first;
            arr[1] = second;
            return arr;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BasicImmutableEdge<?> that = (BasicImmutableEdge<?>) o;
            return Objects.equals(first, that.first) &&
                    Objects.equals(second, that.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public String toString() {
            return toStringDectipition();
        }

    }

    static class BasicImmutableUndirectedEdge<T> extends BasicImmutableEdge<T> {
        public BasicImmutableUndirectedEdge(Vertex<T> first, Vertex<T> second) {
            super(first, second);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BasicImmutableUndirectedEdge<T> that = (BasicImmutableUndirectedEdge<T>) o;
            return undirectedEquals(this, that);
        }
    }


    public static <T> boolean undirectedEquals(Edge<T> e1, Edge<T> e2){
        return  e1.vertices()[0].equals(e2.vertices()[0]) && e1.vertices()[1].equals(e2.vertices()[1]) ||
                e1.vertices()[0].equals(e2.vertices()[1]) && e1.vertices()[1].equals(e2.vertices()[0]);
    }

    public static <T> Edge<T> basicImmutable(T v1, T v2){
        return new BasicImmutableEdge<>(Vertices.mutableVertex(v1), Vertices.mutableVertex(v2));
    }

}
