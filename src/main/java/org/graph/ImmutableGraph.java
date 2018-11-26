package org.graph;

import java.util.*;

public abstract class ImmutableGraph<T> implements Graph<T> {

    protected final String id;

    public ImmutableGraph(String id) {
        this.id = id;
    }

    // Immutable graph
    public boolean add(Vertex<T> tVertex)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    public boolean remove(Object o)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    public boolean addAll(Collection<? extends Vertex<T>> c)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    public boolean removeAll(Collection<?> c)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    public void clear()
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    public boolean retainAll(Collection<?> c)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    public void addEdge(Vertex<T> v1, Vertex<T> v2)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }




    /**
     * A basic immutable implementation of a undirected, unweighted graph backed by a list of
     * Vertices that contains references to their neighbours.
     *
     * @param <T>
     */
    final static class _BasicImmutableUndirectedUnweightedGraph<T> extends ImmutableGraph<T> {

        private Set<Vertex<T>> vertices;

        public String id() {
            return id;
        }


        public _BasicImmutableUndirectedUnweightedGraph(Set<Vertex<T>> vertices, String id) {
            super(id);
            this.vertices = Collections.unmodifiableSet(vertices);
        }


        // graph implementation
        public Collection<Edge<T>> edges() {
            return null;
        }

        public Collection<Vertex<T>> vertices() {
            return vertices;
        }

        public Collection<Vertex<T>> neighbours(Vertex<T> Vertex) {
            for (Vertex<T> v : vertices) {
                if (v.equals(Vertex)) {
                    return v.neighbours();
                }
            }
            return null;
        }



        @Override
        public int edgesSize() {
            //todo
            return 0;
        }

        @Override
        public boolean isDirected() {
            return false;
        }

        @Override
        public boolean hasCycle() {
            return false;
        }

        @Override
        public boolean isWeigthed() {
            return false;
        }

        @Override
        public String toString() {
            return toDescriptiveString();
        }


    }

}
