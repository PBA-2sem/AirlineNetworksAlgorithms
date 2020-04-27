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

public class BSSearch {
  private final AirportGraph graph;
  private Map<String, String> visitedFrom;
  private Queue<Edge> edges;

  public BSSearch(AirportGraph graph) {
      
    ArrayList<String> keys = new ArrayList<String>(graph.getVertices().keySet());
    
    
    this.graph = graph;
    
    visitedFrom = new HashMap<>();
    
    for (int v = 0; v < keys.size(); v++) visitedFrom.put(keys.get(v), "");
    edges = new ArrayQueue<>(1_000);
    
      System.out.println(visitedFrom.toString());
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
      return ""+from+" -> "+to;
      }
    }

  private void register(Edge edge) {
    if (!visitedFrom.get(edge.to).equals("")) return;
    // only register if 'to' has not been registered already
    edges.enqueue(edge);
    visitedFrom.put(edge.to, edge.from);
  }

  public void searchFrom(String v) {
    register(new Edge(v, v));
    while (!edges.isEmpty()) {
      Edge step = edges.dequeue();
      for (String w : graph.adjacents(step.to)) {
          register(new Edge(step.to, w));
          System.out.println(new Edge(step.to, w));
      }
      }
    }

  public String showPathTo(String w) {
    String path = ""+w;
    while (!visitedFrom.get(w).equals(w) && !visitedFrom.get(w).equals("")) {
      w = visitedFrom.get(w);
      path = ""+w+" -> "+path;
      }
    return path;
    }

  public void print(PrintStream out) {
    for (Map.Entry<String,AirportGraph.EdgeNode> entry : graph.getVertices().entrySet()) { 
        out.println("" + entry.getKey() + ": " + showPathTo(entry.getKey()));
    }
  }

  public static void main(String[] args) {
    Graph g = new MatrixGraph(6);
    g.addUndirectedEdge(0, 5);
    g.addUndirectedEdge(0, 1);
    g.addUndirectedEdge(0, 2);
    g.addUndirectedEdge(2, 3);
    g.addUndirectedEdge(2, 4);
    g.addUndirectedEdge(3, 4);
    g.addUndirectedEdge(5, 3);

    DepthFirstSearch bfs = new DepthFirstSearch(g);
    bfs.searchFrom(0);
    bfs.print(System.out);
    }

  }
