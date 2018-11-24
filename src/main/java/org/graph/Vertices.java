package org.graph;


import java.util.*;
import static org.graph.ImmutableVertex.BasicImmutableVertex;

public final class Vertices {

    public Vertices()
    { throw new UnsupportedOperationException("Cannot instanciate static class Vertices"); }



    static class VertexBuilder<T> {


        private Vertex<T> builtVertex;

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
            if (builtVertex == null){
                builtVertex = new BasicImmutableVertex<>(vertexValue, neigboursToAdd);
            }
            return builtVertex;
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


    public static <T> Vertex<T> mutableVertex(T value){
        return new BasicMutableVertex<>(value, new HashSet<>());
    }



    /**
     * A basic implementation of the Vertex interface using a
     * Set (HashSet by default_ to represent the neighbours of the vertex in the graph.
     * @param <T> the data type that is contained in the vertex of the graph.
     */
    static class BasicMutableVertex<T> implements Vertex<T> {



        /**
         * The value associated with this vertex in the graph.
         */
        protected T value;

        /**
         *
         */
        protected Set<Vertex<T>> neighbours;


        BasicMutableVertex(T value, Set<Vertex<T>> neighbours) {
            this.value = value;
            this.neighbours = neighbours;
        }

        public Collection<Vertex<T>> neighbours() { return neighbours; }
        public T value() { return value; }
        public void setValue(T newValue) { this.value = newValue; }

        public Collection<Vertex<T>> addNeighbours(Vertex<T> newNeighbour) {
            neighbours.add(newNeighbour);
            return neighbours;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BasicMutableVertex<?> that = (BasicMutableVertex<?>) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return toDescriptiveString();
        }
    }


    /**
     * This method produce a vertex comparator based on the element comparator provided
     * @param elementComparator
     * @param <T>
     * @return
     */
    public static <T> Comparator<Vertex<T>> vertexComparator(Comparator<T> elementComparator){
        return (Vertex<T> o1, Vertex<T> o2) -> elementComparator.compare(o1.value(), o2.value());
    }


}

















