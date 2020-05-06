package dk.cphbusiness.alg.graphs;

public interface Graph<T> {

    int getV(); // get number of vertices V

    int getE(); // get number of edges E

    void addEdge(T sources, T destination); // add an edge from vertice source to vertice dest

    default void addUndirectedEdge(T v, T w) {
        addEdge(v, w);
        addEdge(w, v);
    }

    Iterable<T> adjacents(T v); // list all adjacent vertices to vertice v
}
