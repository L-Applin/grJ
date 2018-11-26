package org.graph;

/**
 * A Graph builder is used to construct more complex graph and define properties for that graph before
 * instanciatiion.
 * @param <T> the type of the Graph {@link java.util.Collection}.
 */
public interface GraphBuilder<T> {

    /**
     *
     * @return
     */
    GraphBuilder <T> immutable            ();

    /**
     *
     * @return
     */
    GraphBuilder <T> directed             ();

    /**
     *
     * @return
     */
    Graphs.WeightedBuilder<T> weighted             ();

    GraphBuilder <T> addVertex            (T vertex);
    GraphBuilder <T> addVertex            (T... vertices);
    GraphBuilder <T> addEdge              (T v1, T v2);

    GraphBuilder <T> removeVertex         (T vertex);
    GraphBuilder <T> removeEdge           (T v1, T v2);


    boolean isImmutable();
    boolean isDirected();
    boolean isWeighted();


    /**
     *
     * @return
     */
    Graph <T> build();




}
