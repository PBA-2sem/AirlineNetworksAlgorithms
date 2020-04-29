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
import javafx.util.Pair;

public class DFSearch {

    private final AirportGraph graph;
    private Map<String, String> visitedFrom;
    private Map<String, Pair<String, String>> visitedFromWithAirline;
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
    
    public DFSearch(AirportGraph graph, String jeff) {
        ArrayList<String> keys = new ArrayList<>(graph.getVertices().keySet());
        this.graph = graph;
        visitedFromWithAirline = new HashMap<>();
        for (int v = 0; v < keys.size(); v++) {
            visitedFromWithAirline.put(keys.get(v), new Pair("", ""));
        }
        edges = new ArrayStack<>(5_000);
    }

    private void register(Edge edge) {
        if (visitedFrom.get(edge.to) == null || !visitedFrom.get(edge.to).equals("")) {
            return;
        }
        // only register if 'to' has not been registered already
        edges.push(edge);
        visitedFrom.put(edge.to, edge.from);
    }
    
    private void registerWithSameAirline(Edge edge) {
        if (visitedFromWithAirline.get(edge.to) == null || !visitedFromWithAirline.get(edge.to).getKey().equals("")) {
            return;
        }
        // only register if 'to' has not been registered already
        edges.push(edge);
        visitedFromWithAirline.put(edge.to, new Pair(edge.from, edge.airline));
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
    
    public void searchFromSameAirline(String v) {
        registerWithSameAirline(new Edge(v, v, ""));
        while (!edges.isEmpty()) {
            Edge step = edges.pop();
            for (AirportGraph.EdgeNode node : graph.adjacentsWithEdgeNode(step.to)) {
                registerWithSameAirline(new Edge(step.to, node.destination, node.airline));
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
    
    public String showPathToWithSameAirline(String w, String airline) {
        String path = w + " ||| Airline company: " + airline;
        while (visitedFromWithAirline.get(w) != null
                && !visitedFromWithAirline.get(w).getKey().equals(w) 
                && !visitedFromWithAirline.get(w).getKey().equals("")
                && visitedFromWithAirline.get(w).getValue().equals(airline)) {
            
            String currAirline = visitedFromWithAirline.get(w).getValue();
            w = visitedFromWithAirline.get(w).getKey();
            path = "" + w + " (" + currAirline + ") -> " + path;
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

    public void printWithAirline(PrintStream out) {
        int count = 0;
        for (Map.Entry<String, AirportGraph.EdgeNode> entry : graph.getVertices().entrySet()) {
            String key = entry.getKey();
            String keyPath = showPathToWithSameAirline(key, entry.getValue().airline);
            if (!key.equals(keyPath.substring(0, 3))) {
                out.println("" + key + ": " + keyPath);
            }
            count++;
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
