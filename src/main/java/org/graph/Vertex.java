package org.graph;

import java.util.Collection;

interface Vertex<T> {
    Collection<Vertex<T>> neighbours();
    T value();

}
