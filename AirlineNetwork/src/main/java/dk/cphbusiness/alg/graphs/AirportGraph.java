/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.alg.graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AirportGraph implements Graph<EdgeNode>{

    private int V;
    private int E = 0;
//    private final EdgeNode[] vertices;
    private Map<String, EdgeNode> vertices;

    public AirportGraph() {
        this.V = V;
        vertices = new HashMap<>();
    }

    public int getV() {
        return vertices.size();
    }

    public int getE() {
        return E;
    }

    public Map<String, EdgeNode> getVertices() {
        return vertices;
    }

    public void addEdge(String airline, String source, String destination, Float distance, Float time) {
        EdgeNode node = new EdgeNode(destination, source, airline, distance, time, vertices.get(source));
        vertices.put(source, node);
        E++;
    }

    public void addEdgeWithSameAirline(String airline, String source, String destination, Float distance, Float time) {
        EdgeNode node = null;
        if (vertices.get(source) == null) {
            node = new EdgeNode(destination, source, airline, distance, time, vertices.get(source));
        } else {

            if (vertices.get(source).airline == airline) {
                node = vertices.get(source);
            } else {
//                System.out.println("Not same airline : " + airline);
                return;
            }
        }
        vertices.put(source, node);
        E++;
    }

    public Iterable<String> adjacents(String source) {
        List<String> adjacents = new ArrayList<>();
        EdgeNode node = vertices.get(source);
        while (node != null) {
            adjacents.add(node.destination);
            node = node.next;
        }
        return adjacents;
    }
    
    public Iterable<EdgeNode> adjacentsWithEdgeNode(String source) {
        List<EdgeNode> adjacents = new ArrayList<>();
        EdgeNode node = vertices.get(source);
        while (node != null) {
            adjacents.add(node);
            node = node.next;
        }
        return adjacents;
    }

    @Override
    public String toString() {
        String text = "";
        for (Map.Entry<String, EdgeNode> entry : vertices.entrySet()) {
            if (text.length() == 0) {
                text += "Airline : " + entry.getValue().airline + " - ";
            }
            text += "" + entry.getKey() + ": " + adjacents(entry.getKey()) + "\n";
        }
        return text;
    }
    
    public EdgeNode createEmptyEdgeNode() {
        return new EdgeNode("", "", "", -1F, -1F, null);
    }

    public static void main(String[] args) {

        AirportGraph g = new AirportGraph();

        Set<String> sourceAirportCodes = new HashSet<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "./src/main/resources/routes.txt"));
            String headers = reader.readLine();

            String line = reader.readLine();
            int count = 0;
            while (line != null) {
                String[] arr = line.split(";");

                String airline = arr[0];
                String source = arr[1];
                String destination = arr[2];
                Float distance = Float.parseFloat(arr[3]);
                Float time = Float.parseFloat(arr[4]);

                // EDGE WITH RANDOM CONNECTIONS
                g.addEdge(airline, source, destination, distance, time);
                // EDGE WITH SAME AIRLINE
//                g.addEdgeWithSameAirline(airline, source, destination, distance, time);

                sourceAirportCodes.add(source);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // https://www3.cs.stonybrook.edu/~skiena/combinatorica/animations/search.html
//         Breadth First ####
//        BFSearch bfsearch = new BFSearch(g);
////        Run for all 
//        bfsearch.searchFrom("BAY");
//        bfsearch.print(System.out);
        
//        BFSearch bfsearch = new BFSearch(g, "");
        
//      Run for all 
//        bfsearch.searchFromSameAirline("YZV");
//        bfsearch.printWithAirline(System.out);

        // search with same airline
//        System.out.println(bfsearch.showPathToWithSameAirline("ZLT", "WJ"));
//
//      Depth First ####
        DFSearch dfsearch = new DFSearch(g, "jeff");
//        //Run for all 
//
        dfsearch.searchFromSameAirline("YZV");
        dfsearch.printWithAirline(System.out);
        
        
//        System.out.println(dfsearch.showPathToWithSameAirline("ZLT", "WJ"));
        
    }

    @Override
    public void addEdge(EdgeNode v, EdgeNode w) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<EdgeNode> adjacents(EdgeNode v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
