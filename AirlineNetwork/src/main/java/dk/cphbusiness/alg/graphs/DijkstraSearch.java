package dk.cphbusiness.alg.graphs;

import com.sun.istack.internal.NotNull;
import java.util.PriorityQueue;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DijkstraSearch {

    private AirportGraph graph;
    private String source;
    private Map<String, String> edgeTo;
    private Map<String, Double> distTo;
    private PriorityQueue<Path> pqMin = new PriorityQueue<>();

    public DijkstraSearch(AirportGraph graph, String source) {
        this.graph = graph;
        this.source = source;
        ArrayList<String> keys = new ArrayList<>(graph.getVertices().keySet());
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
        for (String v : keys) {
            edgeTo.put(source, "");
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
        edgeTo.put(source, source);
        distTo.put(source, 0.0);
        pqMin.add(new Path(source, 0.0));
        build();
    }

    private class Path implements Comparable<Path> {

        int v;
        double weight;

        Path(int v, double weight) {
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(@NotNull Path other) {
            if (this.weight < other.weight) {
                return -1;
            }
            if (this.weight > other.weight) {
                return 1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "" + v + ": " + weight;
        }

    }

    private void build() {
        while (!pqMin.isEmpty()) {
            Path path = pqMin.poll();
            relax(path);
        }
    }

    private void relax(Path path) {
        Iterable<EdgeNode> adj = graph.adjacents(path.v);
        for (EdgeNode edge : adj) {
            double newDistance = distTo[edge.from] + edge.weight;
            if (distTo[edge.to] > newDistance) {
                // update distTo and edgeTo...
                distTo[edge.to] = newDistance;
                edgeTo[edge.to] = edge.from;
                // update priority queue
                pqMin.add(new Path(edge.to, newDistance));
            }
        }
    }

    public String showPathTo(int w) {
        String path = "" + w;
        while (edgeTo[w] != w && edgeTo[w] != -1) {
            w = edgeTo[w];
            path = "" + w + " -> " + path;
        }
        return path;
    }

    public void print(PrintStream out) {
        for (int v = 0; v < graph.getV(); v++) {
            out.println("" + v + ": " + showPathTo(v));
        }
    }

//    public static void main(String[] args) {
////        WeightedGraph g = new WeightedGraph(6);
////        g.addUndirectedEdge(0, 2, 3.0);
////        g.addUndirectedEdge(0, 1, 2.0);
////        g.addUndirectedEdge(0, 5, 1.0);
////        g.addUndirectedEdge(2, 4, 4.0);
////        g.addUndirectedEdge(2, 3, 3.0);
////        g.addUndirectedEdge(3, 4, 5.0);
////        g.addUndirectedEdge(5, 3, 2.0);
////
////        DijsktraShortestPath dsp = new DijsktraShortestPath(g, 0);
////        dsp.print(System.out);
//    }
}
