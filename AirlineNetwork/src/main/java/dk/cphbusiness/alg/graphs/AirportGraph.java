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

public class AirportGraph implements Graph<EdgeNode> {

    private int V;
    private int E = 0;
    private Map<String, EdgeNode> vertices;

    public AirportGraph() {
        this.V = V;
        vertices = new HashMap<>();
    }

    @Override
    public int getV() {
        return vertices.size();
    }

    @Override
    public int getE() {
        return E;
    }

    public Map<String, EdgeNode> getVertices() {
        return vertices;
    }

    @Override
    public void addEdge(EdgeNode source, EdgeNode destination) {
        EdgeNode node = new EdgeNode(destination.destination, source.source, destination.airline, destination.distance, destination.time, vertices.get(source.source));
        vertices.put(source.source, node);
        E++;
    }

    @Override
    public Iterable<EdgeNode> adjacents(EdgeNode v) {
        List<EdgeNode> adjacents = new ArrayList<>();
        EdgeNode node = vertices.get(v.source);
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
            text += "" + entry.getKey() + ": " + adjacents(new EdgeNode("", entry.getKey(), "", Float.NaN, Float.NaN, null)) + "\n";
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
            while (line != null) {
                String[] arr = line.split(";");

                String airline = arr[0];
                String source = arr[1];
                String destination = arr[2];
                Float distance = Float.parseFloat(arr[3]);
                Float time = Float.parseFloat(arr[4]);

                // EDGE WITH RANDOM CONNECTIONS
                g.addEdge(new EdgeNode("", source, "", 0F, 0F, null), new EdgeNode(destination, source, airline, distance, time, null));

                sourceAirportCodes.add(source);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // https://www3.cs.stonybrook.edu/~skiena/combinatorica/animations/search.html
//         Breadth First ####
        BFSearch bfsearch = new BFSearch(g);
        bfsearch.searchFrom("YZV");
        //Print all
//        bfsearch.print(System.out);
        // search with same airline
        System.out.println(bfsearch.showPathToWithSameAirline("ZLT", "WJ"));
//
//      Depth First ####
//        DFSearch dfsearch = new DFSearch(g);
//
//        dfsearch.searchFrom("YZV");
//        //Print all
//        dfsearch.print(System.out);

//        System.out.println(dfsearch.showPathToWithSameAirline("ZLT", "WJ"));
    }

}
