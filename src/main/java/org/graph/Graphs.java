package org.graph;

import java.util.*;
import java.util.stream.Collectors;

import static org.graph.ImmutableGraph.*;
import static org.graph.MutableGraph._BasicMutableUnweightedUndirectedGraph;
import static org.graph.ImmutableVertex.BasicImmutableVertex;


public final class Graphs {

    public static final byte DIRECTED = 0x01;
    public static final byte WEIGHTED = 0x01 << 1;
    public static final byte MUTABLE = 0x01 << 2;



    private Graphs()
    { throw new UnsupportedOperationException("Cannot create instances of static class Graphs"); }



    public static <T>
    GraphBuilder<T> builder() {
        return new BasicBuilderImplementation<>();
    }

    public static <T>
    GraphBuilder<T> builder(Class<T> cls) {
        return new BasicBuilderImplementation<>();
    }


    /**
     * Creates an immutable Graph from a mutable graph instance.
     * @param graph
     * @param <T>
     * @return
     */
    public static <T>
    ImmutableGraph<T> immutableFrom(MutableGraph<T> graph){

        Collection<Vertex<T>> mutableVertices = graph.vertices();
        Collection<ImmutableVertex<T>> immutableVertices = mutableVertices.stream().map(BasicImmutableVertex::empty).collect(Collectors.toList());

        mutableVertices.forEach(v -> {

            Set<ImmutableVertex<T>> tempNeigh = new HashSet<>();

            v.neighbours().forEach(neigh -> {
                ImmutableVertex<T> currentNeigh = findImmutableVertex(neigh.value(), immutableVertices);
                tempNeigh.add(currentNeigh);
            });

            ((BasicImmutableVertex<T>) findImmutableVertex(v.value(), immutableVertices)).editNeighboursReflection(tempNeigh);

        });

        // todo : manage weigthed and directed (four cases ...)
        return new _BasicImmutableUndirectedUnweightedGraph<>(new HashSet<>(immutableVertices), graph.id);
    }

    private static <T>
    ImmutableVertex<T> findImmutableVertex(T valueToFind, Collection<ImmutableVertex<T>> coll){
        for (ImmutableVertex<T> v : coll){
            if (v.value().equals(valueToFind)){
                return v;
            }
        }
        return null;

    }

    public static <T>
    MutableGraph<T> mutable(int... configs){
        if (configs.length == 0){
            return new _BasicMutableUnweightedUndirectedGraph<>(new HashMap<>());
        }
        // todo : weighted and directed graphs
        return null;
    }



    /**
     *
     * @param <T>
     */
    static class BasicBuilderImplementation<T> implements GraphBuilder<T> {


        protected Set<T> vertices;
        protected Set<Pair<T>> edges;


        /**
         * Basic inplementation uses HashSet for keeping track of edges and vertices. As such,
         * {@link Object#equals(Object)} and {@link Object#hashCode()} method ovverides are very important
         * to determine which edges and vertices are added to the graph.
         */
        BasicBuilderImplementation() {
            immutable = directed = weighted = false;
            vertices = new HashSet<>();
            edges = new HashSet<>();
        }

        private boolean edited = false;

        /**
         * {@inheritDoc}
         */
        public GraphBuilder<T> immutable() { immutable = true; return this; }
        public boolean isImmutable() { return immutable; }
        protected boolean immutable;


        /**
         * {@inheritDoc}
         */
        public GraphBuilder<T> directed()  {
            if (edited) throw new GraphConstructionException("cannot change graph parameters once edited");
            directed = true;
            return this;
        }
        public boolean isDirected() { return directed; }
        protected boolean directed;


        /**
         * {@inheritDoc}
         */
        public WeightedBuilder<T> weighted()  {
            if (edited) throw new GraphConstructionException("cannot change graph parameters once edited");
            weighted = true;
            return new WeightedBuilder<>(this);
        }
        public boolean isWeighted() { return weighted; }
        protected boolean weighted;



        public GraphBuilder<T> addVertex(T vertex) {
            vertices.add(vertex);
            return this;
        }

        @Override
        public GraphBuilder<T> addVertex(T... vertices) {

            return this;
        }

        public GraphBuilder<T> addEdge(T v1, T v2) {
            if (weighted) { throw new IllegalArgumentException("Weighted graph edges must contains weight"); }
            vertices.add(v1);
            vertices.add(v2);
            edges.add(new Pair<>(v1, v2));
            if (!directed){
                edges.add(new Pair<>(v2, v1));
            }
            edited = true;
            return this;
        }



        public GraphBuilder<T> removeVertex(T vertex) {
            vertices.remove(vertex);
            edited = true;
            return this;
        }


        public GraphBuilder<T> removeEdge(T v1, T v2) {
            edges.remove(new Pair<>(v1, v2));
            edited = true;
            return this;
        }

        public Graph<T> build() {

            //todo
            return null;
        }

    }

    public static class WeightedBuilder<T> extends BasicBuilderImplementation<T> {

        private Number default_edge_Weight;

        public WeightedBuilder(BasicBuilderImplementation<T> parentBuilder) {
            super();
            weighted = true;
            immutable = parentBuilder.immutable;
            directed = parentBuilder.directed;
        }

        public WeightedBuilder<T> defaultWeight(Number weight){
            this.default_edge_Weight = weight;
            return this;
        }

        /**
         * Add an edge between the two element in the resulting Graph with the sepcific weight.
         * If {@link GraphBuilder#weighted()} was not called, weigth argument will be ignore and will have the same
         * result as if {@link GraphBuilder#addEdge(Object, Object)} was called with only v1 and v2.
         * @param v1
         * @param v2
         * @param weight
         * @return
         */
        public WeightedBuilder<T> addEdge (T v1, T v2, Number weight){
            // todo
            return this;
        }


        public WeightedBuilder<T> removeEdge(T v1, T v2) {
            //todo
            return this;
        }


        public boolean isDirected() { return directed; }
        public boolean isWeighted() { return true; }

        public WeightedBuilder<T> removeVertex(T vertex)    { return (WeightedBuilder<T>) super.removeVertex(vertex); }
        public WeightedBuilder<T> immutable()               { return (WeightedBuilder<T>) super.immutable(); }
        public WeightedBuilder<T> directed()                { return (WeightedBuilder<T>) super.directed(); }
        public WeightedBuilder<T> weighted()                { return this; }
        public WeightedBuilder<T> addVertex(T vertex)       { return (WeightedBuilder<T>) super.addVertex(vertex); }
        public WeightedBuilder<T> addVertex(T... vertices)  { return (WeightedBuilder<T>) super.addVertex(vertices); }

        public WeightedBuilder<T> addEdge(T v1, T v2) {
            if (Objects.isNull(default_edge_Weight)) {
                throw new IllegalArgumentException("Weighted graph edges must contains weight");
            }

            addEdge(v1, v2, default_edge_Weight);
            return this;
        }


        public Graph<T> build() {
            //todo
            return super.build();
        }

    }



    private static class Pair<T> {

        private final T first;
        private final T second;

        Pair(T first, T second) { this.first = first;this.second = second; }

        public String toString() { return "[ " + first + " : " + second +" ]"; }

        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + (first != null ? first.hashCode() : 0);
            hash = 31 * hash + (second != null ? second.hashCode() : 0);
            return hash;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof Pair) {
                Pair pair = (Pair) o;
                if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
                if (second != null ? !second.equals(pair.second) : pair.second != null) return false;
                return true;
            }
            return false;
        }

    }

}
























