/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.alg.graphs;

import dk.cphbusiness.alg.basics.ArrayQueue;
import dk.cphbusiness.alg.basics.Queue;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BFSearch {

    private final AirportGraph graph;
    private Map<String, Edge> visitedFrom;
    private Queue<Edge> edges;

    public BFSearch(AirportGraph graph) {
        ArrayList<String> keys = new ArrayList<>(graph.getVertices().keySet());
        this.graph = graph;
        visitedFrom = new HashMap<>();
        for (int v = 0; v < keys.size(); v++) {
            visitedFrom.put(keys.get(v), null);
        }
        edges = new ArrayQueue<>(5_000);
    }

    private void register(Edge edge) {
        if (visitedFrom.get(edge.to) != null) {
            return;
        }
        // only register if 'to' has not been registered already
        edges.enqueue(edge);
        visitedFrom.put(edge.to, edge);
    }

    public void searchFrom(String v) {
        register(new Edge(v, v, ""));
        while (!edges.isEmpty()) {
            Edge step = edges.dequeue();
            for (EdgeNode node : graph.adjacents(new EdgeNode("", step.to, "", Float.NaN, Float.NaN, null))) {
                register(new Edge(step.to, node.destination, node.airline));
            }
        }
    }

    public String showPathToWithSameAirline(String w, String airline) {
        String path = w + " ||| Airline company: " + airline;
        // Sneaki boi
        int pathLen = path.length();

        while (visitedFrom.get(w) != null
                && !visitedFrom.get(w).from.equals(w)
                && !visitedFrom.get(w).from.equals("")) {

            if (visitedFrom.get(w).airline.equals(airline)) {
                String currAirline = visitedFrom.get(w).airline;
                w = visitedFrom.get(w).from;
                path = "" + w + " (" + currAirline + ") -> " + path;

            } else {
                w = visitedFrom.get(w).from;
            }

        }
        // Sneaki boi
        if (pathLen == path.length()) {
            return "Not possible";
        }
        return path;
    }

    public String showPathTo(String w) {
        String path = w;

        while (visitedFrom.get(w) != null
                && !visitedFrom.get(w).from.equals(w)
                && !visitedFrom.get(w).from.equals("")) {

            w = visitedFrom.get(w).from;
            path = "" + w + " -> " + path;
        }

        return path;
    }

    public void print(PrintStream out) {
        int count = 0;
        for (Map.Entry<String, EdgeNode> entry : graph.getVertices().entrySet()) {
            String key = entry.getKey();
            String keyPath = showPathTo(entry.getKey());
            if (!key.equals(keyPath)) {
                out.println(keyPath);
                count++;
            }
        }
        System.out.println(count);
    }

    public void printWithAirline(PrintStream out) {
        int count = 0;
        for (Map.Entry<String, EdgeNode> entry : graph.getVertices().entrySet()) {
            String key = entry.getKey();
            String keyPath = showPathToWithSameAirline(key, entry.getValue().airline);
            if (!key.equals(keyPath.substring(0, 3))) {
                out.println("" + key + ": " + keyPath);
                count++;
            }

        }
        System.out.println(count);
    }

}
