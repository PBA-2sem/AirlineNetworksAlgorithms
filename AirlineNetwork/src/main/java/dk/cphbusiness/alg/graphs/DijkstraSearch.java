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

    public DijkstraSearch(AirportGraph graph, String source, String weightType) {
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
        build(weightType);
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

    private void build(String weightType) {
        while (!pqMin.isEmpty()) {
            Path path = pqMin.poll();
            relax(path, weightType);
        }
    }

    private void relax(Path path, String type) {
        Iterable<EdgeNode> adj = graph.adjacents(new EdgeNode("", path.v, "", Float.NaN, Float.NaN, null));
        for (EdgeNode edge : adj) {
            double weightValue = 0.0;
            if (type.equals("distance")) {
                weightValue = distTo.get(edge.source) + edge.distance;
            } else if (type.equals("time")) {
                weightValue = distTo.get(edge.source) + edge.time + 1.0;
            }
            if (distTo.get(edge.destination) != null) {

                if (distTo.get(edge.destination) > weightValue) {
                    // update distTo and edgeTo...
                    distTo.put(edge.destination, weightValue);
                    edgeTo.put(edge.destination, edge.source);
                    // update priority queue
                    pqMin.add(new Path(edge.destination, weightValue));
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

}
