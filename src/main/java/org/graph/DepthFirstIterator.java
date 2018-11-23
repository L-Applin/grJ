package org.graph;

import java.util.Iterator;

class DepthFirstIterator<T> implements Iterator<Vertex<T>> {

    public boolean hasNext() {
        return false;
    }

    public Vertex<T> next() {
        return null;
    }

    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
