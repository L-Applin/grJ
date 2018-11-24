package org.graph;


import org.graph.traversal.DepthFirstSearch;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Root interface for all Graphs.
 *
 * IMPORTANT NOTE
 * Duplicate nodes are not supported. That mean that if two vertices v1 and v2 contain elemnt o1 and o2
 * such that<code>o1.equals(o2)</code> is true, then v1 and v2 will be treated as the same Vertex. This means that,
 * for exemple, if v1 is in the graph, adding v2 to it has no effect.
 * @param <T>
 */
public interface Graph<T> extends Collection<Vertex<T>> {

    /**
     * A String identifier to recognize the graph. Optionnal
     * @return
     */
    String id();


    /**
     * All edges of the graph
     * @return
     */
    Collection<Edge<T>> edges();


    /**
     * All vertices of the graph
     * @return
     */
    Collection<Vertex<T>> vertices();


    /**
     * Returns the vertex associated with the value if it appears in the graph, null otherwise
     * @param value
     * @return
     */
    default Vertex<T> getVertex(T value){
        AtomicReference<Vertex<T>> v = new AtomicReference<>();
        vertices().forEach(vertex -> {
            if (vertex.value().equals(value)){
                v.set(vertex);
            }
        });
        return v.get();

    }


    /**
     * returns all neighbours of the value. Throw
     * @param vertex
     * @return all neighbours of the
     */
    Collection<Vertex<T>> neighbours(Vertex<T> vertex);


    /**
     * Add an edge between the two vertices. Of any of the vertices do not exist in the graph, the method
     * should create those vertices and add them to the graph
     * @param v1
     * @param v2
     * @return
     */
    void addEdge(Vertex<T> v1, Vertex<T> v2);


    /**
     * the amount of edges in the graph
     * @return
     */
    default int edgesSize(){
        return edges().size();
    };


    /**
     * The edges density of the graph. Can be used to determine how dense or sparse the graph is.
     * @return
     */
    default double density() {
        return (double) edges().size() / (double) vertices().size();
    }


    /**
     * Allows to know if the graph is directed or not
     * @return true if the graph is directed
     */
    boolean isDirected();


    /**
     * Alows to know if the graph is weighted or not
     * @return true if the edgs of the graph have a weight associated to them
     */
    boolean isWeigthed();


    /**
     *
     * @return
     */
    boolean hasCycle();


    /**
     *
     * @param elem
     * @return
     */
    default Collection<Vertex<T>> neighboursOf(T elem){
        AtomicReference<Collection<Vertex<T>>> neigh = new AtomicReference<>();
        vertices().forEach(v -> {
            if (v.value().equals(elem)){
                neigh.set(v.neighbours());
            }
        });
        return neigh.get();
    }

    /**
     *
     */
    default DepthFirstSearch<T> dfs(){
        return new DepthFirstSearch<>(this);
    }







    // ========================== //
    // Collection implementation  //
    // ========================== //

    /**
     * The size is the number of vertices in the graph.
     * Basic implementation isbased on the number of vertices in the graph.
     * @return the number of vertices in the graph
     */
    @Override
    default int size() {
        return vertices().size();
    }


    /**
     * Basic implementation based on the number of vertices in the graph.
     * @return true if the vertices Collection os empty
     */
    @Override
    default boolean isEmpty() {
        return vertices().isEmpty();
    }


    @Override
    default boolean contains(Object v) {
        return vertices().contains(v);
    }

    /**
     * Return an iterator over all vertices. This iterator is based on the collection used by
     * specific implementation of the {@link Graph#vertices()} method.
     * @return
     */
    @Override
    default Iterator<Vertex<T>> iterator() {
        return vertices().iterator();
    }

    @Override
    default Object[] toArray() {
        return vertices().toArray();
    }

    @Override
    default <U> U[] toArray(U[] a) {
        return vertices().toArray(a);
    }

    @Override
    default boolean containsAll(Collection<?> c) {
        return vertices().containsAll(c);
    }










    // =========================== //
    //    Loging functionalities   //
    // =========================== //


    default String toDescriptiveString(){
        StringBuilder sb = new StringBuilder();
        String base = "\t%s : %s\n";

        vertices().forEach(v ->{
            String content = String.format(base, v.value().toString(), v.neighbours().stream().map(Vertex::value).collect(Collectors.toList()));
            sb.append(content);
        });

        return String.format("Graphs{\n%s}", sb.toString());
    }


    /**
     * Sort Graph's vertices before printing based on the total ordering provided by the Comparator
     * @param comparator
     * @return
     */
    default String toDescriptiveString(Comparator<? super Vertex<T>> comparator){
        StringBuilder sb = new StringBuilder();
        String base = "\t%s : %s\n";

        vertices().stream().sorted(comparator).forEach(v ->{
            String content = String.format(base, v.value().toString(),
                    v.neighbours().stream().sorted(comparator).map(Vertex::value).collect(Collectors.toList()));
            sb.append(content);
        });

        return String.format("Graphs{\n%s}", sb.toString());
    }


    default void log(){
        System.out.println(toDescriptiveString());
    }

    default void log(Comparator<? super Vertex<T>> comparator){
        System.out.println(toDescriptiveString(comparator));
    }


}
