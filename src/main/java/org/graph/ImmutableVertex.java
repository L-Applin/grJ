package org.graph;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

abstract class ImmutableVertex<T> implements Vertex<T> {

    public void setValue(T newValue)
    { throw new UnsupportedOperationException("Cannot modify Immutable Vertex"); }

    public Collection<Vertex<T>> addNeighbours(Vertex<T> newNeighbour)
    { throw new UnsupportedOperationException("Cannot modify Immutable Vertex"); }

    static class BasicImmutableVertex<T> extends ImmutableVertex<T> {

        private final Set<Vertex<T>> neighbours;
        private final T value;

        private Set<Vertex<T>> temporaryVertices;

        public BasicImmutableVertex(T value, Set<Vertex<T>> neighbours) {
            this.value = value;
            this.neighbours = Collections.unmodifiableSet(neighbours);
        }

        public BasicImmutableVertex(Vertex<T> vertex) {
            this.value = vertex.value();
            this.neighbours = Collections.unmodifiableSet(new HashSet<>(vertex.neighbours()));
        }


        @Override
        public Collection<Vertex<T>> neighbours() {
            return neighbours;
        }


        @Override
        public T value() {
            return value;
        }

        static <T> ImmutableVertex<T> empty(T value){
            return new BasicImmutableVertex<>(value, new HashSet<>());
        }

        static <T> ImmutableVertex<T> empty(Vertex<T> vertex){
            return new BasicImmutableVertex<>(vertex.value(), new HashSet<>());
        }


        /**
         * Hackish solution to change neighbours (even if declared final) while the graph is being built using reflection ...
         * there may be another way
         * @param vertices
         */
        synchronized void editNeighboursReflection(Set<ImmutableVertex<T>> vertices){
            try {
                Field neigh = BasicImmutableVertex.class.getDeclaredField("neighbours");
                neigh.setAccessible(true);
                neigh.set(this, Collections.unmodifiableSet(vertices));
                neigh.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException ndf){
                ndf.printStackTrace();
            }

        }

        @Override
        public String toString() {
            return toDescriptiveString();
        }
    }

}
