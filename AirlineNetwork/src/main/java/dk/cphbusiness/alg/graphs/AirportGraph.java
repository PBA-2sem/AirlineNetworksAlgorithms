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
import java.util.List;
import java.util.Map;

public class AirportGraph {

    private int V;
    private int E = 0;
//    private final EdgeNode[] vertices;
    private Map<String, EdgeNode> vertices;

    public AirportGraph() {
        this.V = V;
        vertices = new HashMap<>();
    }

    class EdgeNode {

        String destination;
        String airline;
        Float distance;
        Float time;
        EdgeNode next;

        EdgeNode(String destination, String airline, Float distance, Float time, EdgeNode next) {
            this.destination = destination;
            this.airline = airline;
            this.distance = distance;
            this.time = time;
            this.next = next;
        }
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
        EdgeNode node = new EdgeNode(destination, airline, distance, time, vertices.get(source));
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

    @Override
    public String toString() {
        String text = "";
        for (Map.Entry<String,EdgeNode> entry : vertices.entrySet()) { 
            text += "" + entry.getKey() + ": " + adjacents(entry.getKey()) + "\n";
        }
        return text;
    }
    
    public static void main(String[] args) {
        
        AirportGraph g = new AirportGraph();
        
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                            "C:\\Users\\Orchi\\Desktop\\Airline-Networks-Algorithms\\AirlineNetwork\\src\\main\\java\\dk\\cphbusiness\\alg\\data\\routes.txt"));
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
                    
                    g.addEdge(airline, source, destination, distance, time);
                    
                    line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        // Breadth First
//        BFSearch bssearch = new BFSearch(g);
//        bssearch.searchFrom("DME");
//        bssearch.print(System.out);
        
//      Depth First
        DFSearch dfserach = new DFSearch(g);
        dfserach.searchFrom("KZN");
        dfserach.print(System.out);
    }
}