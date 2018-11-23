package org.graph;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

public interface Graph<T> extends Collection<Vertex<T>> {


    String id();

    /**
     * All edges of the graph
     * @return
     */
    Collection<Vertex<T>[]> edges();

    /**
     * All vertices of the graph
     * @return
     */
    Collection<Vertex<T>> vertices();


    /**
     * returns all neighbours of the value. Throw
     * @param vertex
     * @return all neighbours of the
     */
    Collection<Vertex<T>> neighbours(Vertex<T> vertex);

    /**
     * the amount of edges in the graph
     * @return
     */
    int edgesSize();

    /**
     * The edges density of the graph. Can be used to determine how dense or sparse the graph is.
     * @return
     */
    default double density() {
        return vertices().size() / edges().size();
    }

    boolean isDirected();
    boolean hasCycle();

    boolean isWeigthed();

    default void dfs(Function<Vertex<T>, Vertex<T>> consumer, Vertex<T> start){
        GraphVisitor<T> gp = new GraphVisitor<>(this);

    };




    // ========================== //
    // Collection implementation  //
    // ========================== //

    /**
     * The size is the number of vertices in the graphe
     * @return
     */
    @Override
    default int size() {
        return vertices().size();
    }


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

    default String toDescriptiveString(){
        StringBuilder sb = new StringBuilder();
        String base = "%s : %s\n";

        vertices().forEach(v ->{
            String content = String.format(base, v.value().toString(), v.neighbours().toString());
            sb.append(content);
        });

        return String.format("Graphs{%s}", sb.toString());
    }

}
