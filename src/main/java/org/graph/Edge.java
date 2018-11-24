package org.graph;

interface Edge<T> {

    /**
     * return both vertices of the edges as an array of two vertices
     * @return
     */
    Vertex<T>[] vertices();

    /**
     * return both vertices elements of the edges as an array of two element
     * @return
     */
    T[] asElement();

    default String toStringDectipition(){
        return "["+vertices()[0].value().toString() + ", " + vertices()[1].value().toString()+"]";
    }


}
