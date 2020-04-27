/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.alg.graphs;

import dk.cphbusiness.alg.basics.ArrayStack;
import dk.cphbusiness.alg.basics.Stack;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DFSearch {

    private final AirportGraph graph;
    private Map<String, String> visitedFrom;
    private Stack<Edge> edges;

    public DFSearch(AirportGraph graph) {
        ArrayList<String> keys = new ArrayList<String>(graph.getVertices().keySet());
        this.graph = graph;
        visitedFrom = new HashMap<>();
        for (int v = 0; v < keys.size(); v++) {
            visitedFrom.put(keys.get(v), "");
        }
        edges = new ArrayStack<>(2_000);
    }

    private class Edge {

        String from;
        String to;

        Edge(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "" + from + " -> " + to;
        }
    }

    private void register(Edge edge) {
        if (visitedFrom.get(edge.to) == null || !visitedFrom.get(edge.to).equals("")) {
            return;
        }
        // only register if 'to' has not been registered already
        edges.push(edge);
        visitedFrom.put(edge.to, edge.from);
    }

    public void searchFrom(String v) {
        register(new Edge(v, v));
        while (!edges.isEmpty()) {
            Edge step = edges.pop();
            for (String w : graph.adjacents(step.to)) {
                register(new Edge(step.to, w));
            }
        }
    }

    public String showPathTo(String w) {
        String path = w;
        while (!visitedFrom.get(w).equals(w) && !visitedFrom.get(w).equals("")) {
            w = visitedFrom.get(w);
            path = "" + w + " -> " + path;
        }
        return path;
    }

    public void print(PrintStream out) {
        int count = 0;
        for (Map.Entry<String, AirportGraph.EdgeNode> entry : graph.getVertices().entrySet()) {
            String key = entry.getKey();
            String keyPath = showPathTo(entry.getKey());
            if (!key.equals(keyPath)) {
                out.println("Airline : " + entry.getValue().airline + " - " + key + ": " + keyPath);
                count++;
            }
        }
        System.out.println(count);
    }

    public void printWithAirline(PrintStream out, String airline) {
        int count = 0;
        for (Map.Entry<String, AirportGraph.EdgeNode> entry : graph.getVertices().entrySet()) {
            String key = entry.getKey();
            if (entry.getValue().airline.equals(airline)) {
                String keyPath = showPathTo(entry.getKey());
                if (!key.equals(keyPath)) {
                    out.println("" + key + ": " + keyPath);
                    count++;
                }

            }
        }
        System.out.println(count);
    }

//  public static void main(String[] args) {
//    Graph g = new AdjacencyGraph(6);
//    g.addUndirectedEdge(0, 5);
//    g.addUndirectedEdge(0, 1);
//    g.addUndirectedEdge(0, 2);
//    g.addUndirectedEdge(2, 3);
//    g.addUndirectedEdge(2, 4);
//    g.addUndirectedEdge(3, 4);
//    g.addUndirectedEdge(5, 3);
//
//    DepthFirstSearch dfs = new DepthFirstSearch(g);
//    dfs.searchFrom(0);
//    dfs.print(System.out);
//    }
}
