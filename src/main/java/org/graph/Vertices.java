package org.graph;

import java.util.*;

public class Vertices {

    public Vertices()
    { throw new UnsupportedOperationException("Cannot instanciate static class Vertices"); }


    static class BasicImmutableVertex<T> implements Vertex<T>{

        /**
         * The value of the value.
         */
        private final T value;

        private final Set<Vertex<T>> neighbours;

        public BasicImmutableVertex(T value, Set<Vertex<T>> neighbours) {
            this.neighbours = Collections.unmodifiableSet(neighbours);
            this.value = value;
        }

        public Collection<Vertex<T>> neighbours() { return neighbours; }
        public T value() { return value; }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Vertex){
                return false;
            } else {
                return value.equals(obj);
            }
        }
    }


    static class VertexBuilder<T> {

        /**
         *
         */
        private Set<Vertex<T>> neigboursToAdd;

        private T vertexValue;
        public T getVertexValue() { return vertexValue; }
        public void setVertexValue(T vertexValue) { this.vertexValue = vertexValue; }

        public VertexBuilder() {
            this.neigboursToAdd = new HashSet<>();
        }

        public VertexBuilder(T vertexValue) {
            this.neigboursToAdd = new HashSet<>();
            this.vertexValue = vertexValue;
        }


        public Vertex<T> build(){
            return new BasicImmutableVertex<>(vertexValue, neigboursToAdd);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VertexBuilder<?> that = (VertexBuilder<?>) o;
            return Objects.equals(vertexValue, that.vertexValue);
        }

        @Override
        public int hashCode() {

            return Objects.hash(vertexValue);
        }
    }
}
