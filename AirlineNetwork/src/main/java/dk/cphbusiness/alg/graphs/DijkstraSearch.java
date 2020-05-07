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
            if (v.equals("SPI")) {
                System.out.println("jeff");
            }
            edgeTo.put(source, "");
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
        edgeTo.put(source, source);
        distTo.put(source, 0.0);
        pqMin.add(new Path(source, 0.0));
        build();
    }

    private class Path implements Comparable<Path> {

        String v;
        double weight;

        Path(String v, double weight) {
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

//  private void relax(Path path) {
//    Iterable<WeightedGraph.Edge> adj = graph.adjacents(path.v);
//    for (WeightedGraph.Edge edge : adj) {
//      double newDistance = distTo[edge.from] + edge.weight;
//      if (distTo[edge.to] > newDistance) {
//        // update distTo and edgeTo...
//        distTo[edge.to] = newDistance;
//        edgeTo[edge.to] = edge.from;
//        // update priority queue
//        pqMin.add(new Path(edge.to, newDistance));
//        }
//      }
//    }
//  
    private void relax(Path path) {
        Iterable<EdgeNode> adj = graph.adjacents(new EdgeNode("", path.v, "", Float.NaN, Float.NaN, null));
        for (EdgeNode edge : adj) {
            double newDistance = distTo.get(edge.source) + edge.distance;
            if (distTo.get(edge.destination) != null) {

                if (distTo.get(edge.destination) > newDistance) {
                    // update distTo and edgeTo...
                    distTo.put(edge.destination, newDistance);
                    edgeTo.put(edge.destination, edge.source);
                    // update priority queue
                    pqMin.add(new Path(edge.destination, newDistance));
                }
            }
        }
    }

    public String showPathTo(String destination) {
        String path = "" + destination;
        while (edgeTo.get(destination) != null && !edgeTo.get(destination).equals("") && !edgeTo.get(destination).equals(destination)) {
            destination = edgeTo.get(destination);
            path = "" + destination + " -> " + path;
        }
        return path;
    }

    public void print(PrintStream out) {
        ArrayList<String> keys = new ArrayList<>(graph.getVertices().keySet());
        for (String k : keys) {
            out.println("" + k + ": " + showPathTo(k));
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
