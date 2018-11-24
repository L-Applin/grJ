package org.graph;

import java.util.*;
import java.util.stream.Collectors;

import static org.graph.Vertices.VertexBuilder;

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
     *
     * @param <T>
     */
    static class ImmutableBuilder<T> implements GraphBuilder<T> {

        /**
         * A collection that accumulates all vertices of the Graph before instanciate a
         * immutable List of all vertices.
         * todo : better implementation ? List : O(n) - Set : O(1) ?
         */
        private List<Vertex<T>> toAddVertices;
        private List<T[]> toAddEdges;

        private Map<T, List<T>> matrix;

        private String id;
        public GraphBuilder<T> setId(String id) { this.id = id; return this; }


        public ImmutableBuilder() {
            toAddVertices = new ArrayList<>();
            toAddEdges = new ArrayList<>();
            matrix = new HashMap<>();
        }

        public Graph<T> build() {

            List<VertexBuilder<T>> verticesBuilders = matrix.keySet().stream()
                    .map(VertexBuilder::new)
                    .collect(Collectors.toList());

            matrix.forEach((vertex, neigh) ->{
                VertexBuilder<T> current = findBuilder(verticesBuilders, vertex);
            });

            return null;
        }

        private VertexBuilder<T> findBuilder(List<VertexBuilder<T>> list, T vertexValue){
            return null;
        }

        public GraphBuilder<T> addVertex(T elem) {

            if (!matrix.containsKey(elem)) {
                matrix.put(elem, new ArrayList<>());
            }

            return this;
        }


        @SuppressWarnings({"unchecked"})
        public GraphBuilder<T> addEdge(T elem1, T elem2) {

            if (!matrix.containsKey(elem1)) {
                addVertex(elem1);
            }

            if (!matrix.containsKey(elem2)) {
                addVertex(elem2);
            }

            matrix.get(elem1).add(elem2);
            matrix.get(elem2).add(elem1);

            return this;

        }


        public GraphBuilder<T> removeVertex(T vertex) {
            toAddVertices.remove(vertex);
            return this;
        }


        public GraphBuilder<T> removeEdge(T v1, T v2) {
            List<T[]> toRemove = new ArrayList<>();
            toAddEdges.forEach(v -> {
                if (v[0].equals(v1) && v[1].equals(v2)) {
                    toRemove.add(v);
                }
            });
            toAddEdges.removeAll(toRemove);
            return this;
        }

        @Override
        public GraphBuilder<T> replaceVertexValue(T oldVertexValue, T newValue) {
            // todo
            return this;
        }

        /**
         * @param elem
         * @return
         */
        private VertexBuilder<T> getVertex(T elem) {

            // todo : O(n), there are better implementation possible.
            return null;
        }


    }


    /**
     * A basic immutable implementation of a undirected, unweighted grpahe backed by a list of
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
