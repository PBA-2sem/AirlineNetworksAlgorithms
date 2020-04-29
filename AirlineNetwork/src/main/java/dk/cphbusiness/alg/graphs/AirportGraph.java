/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.alg.graphs;

import dk.cphbusiness.alg.basics.ArrayQueue;
import dk.cphbusiness.alg.basics.Queue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AirportGraph implements Graph<String> {

    private int V;
    private int E = 0;
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

        @Override
        public String toString() {
            return this.destination + "; " + this.airline + "; " + this.distance + "; " + this.time + ";";
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

    @Override
    public void addEdge(String v, String w) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addUndirectedEdge(String v, String w) {
        Graph.super.addUndirectedEdge(v, w); //To change body of generated methods, choose Tools | Templates.
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

    public boolean BFSReachWithSameAirline(String source, String destination) {
        if (source.equals(destination)) {
            return true;
        }
        return this.BFSReachWithSameAirline(source, vertices.get(source), destination);
    }

    private boolean BFSReachWithSameAirline(String sourceDest, EdgeNode source, String destination) {
        Queue<EdgeNode> toVisit = new ArrayQueue<>(50_000);
        HashSet<String> visited = new HashSet<String>();
//        HashMap<String, String> visitedFrom = new HashMap<>();

        Set<String> airlines = new HashSet<>();
        airlines.add(source.airline);
        EdgeNode airlineNode = source.next;

        while (airlineNode != null) {
            airlines.add(airlineNode.airline);
            airlineNode = airlineNode.next;
        }

        toVisit.enqueue(source);
        while (!toVisit.isEmpty()) {
            EdgeNode node = toVisit.dequeue();

            // End of EdgeNode "LinkedList" is a null therefore continue to next if the queued item is null
            if (node == null) {
                continue;
            }
            // If the node is the destination, it means we could get to this node via routes.
            if (node.destination.equals(destination)) {
                System.out.println(sourceDest + " CAN REACH " + node.destination);
                return true;
            }
            // if this node is part of the visited, go on to next in queue
            if (visited.contains(node.destination)) {
                continue;
            }

            // now visited and added to list of visited.
            visited.add(node.destination);
//            visitedFrom.put(destination)

            // Add all Vertices that are connected via this edge.
            for (String n : this.adjacents(node.destination)) {
                toVisit.enqueue(vertices.get(n));
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String text = "";
        for (Map.Entry<String, EdgeNode> entry : vertices.entrySet()) {
            text += "" + entry.getKey() + ": " + adjacents(entry.getKey()) + "\n";
        }
        return text;
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
//            while (line != null && count < 30) {
            while (line != null) {
                count++;
                String[] arr = line.split(";");

                String airline = arr[0];
                String source = arr[1];
                String destination = arr[2];
                Float distance = Float.parseFloat(arr[3]);
                Float time = Float.parseFloat(arr[4]);

                // EDGE WITH RANDOM CONNECTIONS
                g.addEdge(airline, source, destination, distance, time);

                sourceAirportCodes.add(source);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(g.toString());
        System.out.println(g.BFSReachWithSameAirline("CPH", "GEV"));
    }
}
