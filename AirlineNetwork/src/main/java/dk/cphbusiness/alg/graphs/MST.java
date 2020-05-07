/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.alg.graphs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MST {

    private AirportGraph graph;
    String[] vertices;
    ArrayList<EdgeNode> allEdges = new ArrayList<>();

    MST(AirportGraph graph) {
        this.graph = graph;
        ArrayList<String> keys = new ArrayList<>(graph.getVertices().keySet());
        vertices = new String[keys.size()];
        for(int i = 0; i < vertices.length; i++) {
            vertices[i] = keys.get(i);
        }
    }

    public void addEgde(String source, String destination, Float weight) {
        EdgeNode edge = new EdgeNode(destination, source, "", weight, weight, null);
        allEdges.add(edge); //add to total edges
    }

    public void kruskalMST() {
        PriorityQueue<EdgeNode> pq = new PriorityQueue<>(allEdges.size(), Comparator.comparingDouble(o -> o.distance));

        //add all the edges to priority queue, //sort the edges on weights
        for (int i = 0; i < allEdges.size(); i++) {
            pq.add(allEdges.get(i));
        }

        //create a parent []
        Map<String, String> parent = new HashMap<>();

        //makeset
        makeSet(parent);

        ArrayList<EdgeNode> mst = new ArrayList<>();

        //process vertices - 1 edges
        int index = 0;
        while (!pq.isEmpty() && index < vertices.length - 1) {
            EdgeNode edge = pq.remove();
            //check if adding this edge creates a cycle
            String x_set = find(parent, edge.source);
            String y_set = find(parent, edge.destination);

            if (x_set == y_set) {
                //ignore, will create cycle
            } else {
                //add it to our final result
                mst.add(edge);
                index++;
                union(parent, x_set, y_set);
            }
        }
        //print MST
        System.out.println("Minimum Spanning Tree: ");
        printGraph(mst);
    }

    public void makeSet(Map<String, String> parent) {
        //Make set- creating a new element with a parent pointer to itself.
        for (int i = 0; i < vertices.length; i++) {
            parent.put(vertices[i], vertices[i]);
        }
    }

    public String find(Map<String, String> parent, String vertex) {
        //chain of parent pointers from x upwards through the tree
        // until an element is reached whose parent is itself
        if (parent.get(vertex) != null && !parent.get(vertex).equals(vertex)) {
            return find(parent, parent.get(vertex));
        };
        return vertex;
    }

    public void union(Map<String, String> parent, String x, String y) {
        String x_set_parent = find(parent, x);
        String y_set_parent = find(parent, y);
        //make x as parent of y
        parent.put(y_set_parent, x_set_parent);
    }

    public void printGraph(ArrayList<EdgeNode> edgeList) {
        for (int i = 0; i < edgeList.size(); i++) {
            EdgeNode edge = edgeList.get(i);
            System.out.println("Edge-" + i + " source: " + edge.source
                    + " destination: " + edge.destination
                    + " weight: " + edge.distance);
        }
    }

//    public static void main(String[] args) {
//        int vertices = 6;
//        Graph graph = new Graph(vertices);
//        graph.addEgde(0, 1, 4);
//        graph.addEgde(0, 2, 3);
//        graph.addEgde(1, 2, 1);
//        graph.addEgde(1, 3, 2);
//        graph.addEgde(2, 3, 4);
//        graph.addEgde(3, 4, 2);
//        graph.addEgde(4, 5, 6);
//        graph.kruskalMST();
//    }
}
