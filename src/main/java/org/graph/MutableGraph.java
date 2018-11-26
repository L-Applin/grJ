package org.graph;

import java.util.*;
import java.util.stream.Collectors;

import static org.graph.Edges.BasicImmutableUndirectedEdge;

/**
 * Abstract representation of a mutable graph that is the parent of all mutable graphs implementations.
 * @param <T>
 */
public abstract class MutableGraph<T> implements Graph<T> {

    protected String id;
    protected boolean modified = false;


    /**
     * Add of new vertex to the graph conataining the specified value. Will have no effect if a
     * vertex of the graph that has the specified value already exists.
     * @param value the value to be placed inside the new vertex to be added
     * @return the vertex that was just added in the graph
     */
    public Vertex<T> addVertex(T value){
        Vertex<T> v = Vertices.mutableVertex(value);
        if (!contains(v)) {
            modified=add(v);
        } else {
            System.out.println(v.toString() + " is already in the graph");
        }
        return v;
    }

    /**
     * Removes the vertex in the graph that contains the specified value. Will have no effect
     * if no vertex of that value are found in the graph.
     * @param value the value of the vertex to be removed
     * @return the vertex that was removed
     */
    public Vertex<T> removeVertex(T value){
        Vertex<T> toRemove = getVertex(value);
        if (toRemove != null){
            modified=remove(toRemove);
        }
        return toRemove;
    }


    /**
     * Add all specified values as new vertives in the graph. Uses {@link MutableGraph#addVertex(Object)}
     * as underlying implemetation.
     * @param values the values to add as new vertices to the graph. Will be unconnected.
     * @return A collection containing all of the new {@link Vertex} objects added to the graph
     */
    @SafeVarargs
    public final Collection<Vertex<T>> addVertices(T... values){
        return Arrays.stream(values).map(this::addVertex).collect(Collectors.toList());
    }

    public final Collection<Vertex<T>> addVertices(Collection<T> values){
        return values.stream().map(this::addVertex).collect(Collectors.toList());
    }


    /**
     * Add an edge connecting v1 to v2. If either values are not present in the graph as
     * @param v1
     * @param v2
     */
    public void addEdge(T v1, T v2){
        modified = true;
        Vertex<T> vertex1 = getVertex(v1);
        Vertex<T> vertex2 = getVertex(v2);

        addEdge(vertex1 == null ? Vertices.mutableVertex(v1) : vertex1,
                vertex2 == null ? Vertices.mutableVertex(v2) : vertex2);
    }

    public void addEdge(Edge<T> e) {
        addEdge(e.vertices()[0].value(), e.vertices()[1].value());
    }


    public void removeEdge(T v1, T v2){
        Vertex<T> vertex1 = getVertex(v1);
        Vertex<T> vertex2 = getVertex(v2);

        if (vertex1 == null || vertex2== null){
            return;
        }

        vertices().forEach(v -> {
            if (v.equals(vertex1)){
                v.neighbours().remove(vertex2);
            }
            if (v.equals(vertex2)){
                v.neighbours().remove(vertex1);
            }
        });
    }

    public void removeEdge(Edge<T> e){
        removeEdge(e.vertices()[0].value(), e.vertices()[1].value());
    }





    public GraphChangeLog<T> changeLog(){
        return new GraphChangeLog<>(this);
    }





    /**
     * A basic implementation of an undirected, unweighted <i>MUTABLE</i> graph backed by
     * a Map of vetices element as key and their corresponding vertices as value.
     *
     *<p></p>
     *
     * Based on HashSet !
     *
     * @param <T> the type of the element contain in the Graph Collection
     */
    static class _BasicMutableUnweightedUndirectedGraph<T> extends MutableGraph<T> {

        private Map<T, Vertex<T>> vertices;

        /**
         * Lazy calculation with updatable capabitilites.
         */
        private List<Edge<T>> edges;


        _BasicMutableUnweightedUndirectedGraph(Map<T, Vertex<T>> vertices) {
            this.vertices = vertices;
        }

        public String id() {
            return id;
        }

        public Collection<Edge<T>> edges() {

            if (edges == null || modified) {
                this.edges = calculateEdges();
                return edges;
            }

            return edges;

        }

        private List<Edge<T>> calculateEdges(){
            List<Edge<T>> edges = new ArrayList<>();
            vertices.values().forEach(v -> {
                v.neighbours().forEach(neigh -> {
                    Edge<T> e = new BasicImmutableUndirectedEdge<>(v, neigh);
                    if (!edges.contains(e)) {
                        edges.add(e);
                    }
                });
            });
            return edges;
        }

        public Collection<Vertex<T>> vertices() {
            return vertices.values();
        }

        public Collection<Vertex<T>> neighbours(Vertex<T> Vertex) {
            return null;
        }

        public void addEdge(Vertex<T> v1, Vertex<T> v2) {
            if (!vertices.containsKey(v1.value())){
                vertices.put(v1.value(), v1);
            }

            if (!vertices.containsKey(v2.value())){
                vertices.put(v2.value(), v2);
            }

            v1.addNeighbours(v2);
            v2.addNeighbours(v1);

        }


        public boolean isDirected() {
            return false;
        }

        public boolean hasCycle() {
            // todo
            return false;
        }

        public boolean isWeigthed() {
            return false;
        }

        public boolean add(Vertex<T> vertexToAdd) {
            vertices.put(vertexToAdd.value(), vertexToAdd);

            // undirected -> vertexToAdd neighbours must also contains vertexToAdd in their neighbours
            vertexToAdd.neighbours().forEach(neigh -> {
                Vertex<T> inGraph = vertices.get(neigh);
                if (Objects.nonNull(inGraph)){
                    inGraph.addNeighbours(vertexToAdd);
                } else {
                    addEdge(vertexToAdd, neigh);
                }
            });

            // todo : tcheck if it was really modified
            modified = true;
            return true;
        }

        public boolean remove(Object o) {
            T elem = vertices.remove(o).value();
            modified = Objects.nonNull(elem);
            return modified;
        }

        public boolean addAll(Collection<? extends Vertex<T>> c) {
            modified = true;
            // todo : tcheck if it was really modified
            c.forEach(vertex -> vertices.put(vertex.value(), vertex));
            return modified;
        }

        public boolean removeAll(Collection<?> c) {
            c.forEach(this::remove);
            return false;
        }

        public boolean retainAll(Collection<?> c) {
            // todo
            return false;
        }

        public void clear() {
            vertices.clear();
            edges.clear();
            modified = false;
        }
    }


}
