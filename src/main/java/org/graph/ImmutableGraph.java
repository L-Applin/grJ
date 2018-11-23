package org.graph;

import java.util.Collection;

interface ImmutableGraph<T> extends Graph<T> {

    // Immutable graph
    default boolean add(Vertex<T> tVertex)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    default boolean remove(Object o)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    default boolean addAll(Collection<? extends Vertex<T>> c)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    default boolean removeAll(Collection<?> c)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    default void clear()
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

    default boolean retainAll(Collection<?> c)
    { throw new UnsupportedOperationException("Cannot modify immutable Graph"); }

}
