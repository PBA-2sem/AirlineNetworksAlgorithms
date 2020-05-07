/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.alg.graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Orchi
 */
public class Tester {
    
    public static void main(String[] args) {
        
    // Creating graph
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

//      search with same airline
        System.out.println(bfsearch.showPathToWithSameAirline("ZLT", "WJ"));

//      Depth First ####
        DFSearch dfsearch = new DFSearch(g);
//
        dfsearch.searchFrom("YZV");
//      Print all
//        dfsearch.print(System.out);

        System.out.println(dfsearch.showPathToWithSameAirline("ZLT", "WJ"));
        
//        DijkstraSearch with distance as weight
        DijkstraSearch distanceSearch = new DijkstraSearch(g, "YZV", "distance");
//        distanceSearch.print(System.out);
        System.out.println(distanceSearch.showPathTo("CPH"));
        
//        DijkstraSearch with time as weight        
        DijkstraSearch timeSearch = new DijkstraSearch(g, "YZV", "time");
        System.err.println(timeSearch.showPathTo("CPH"));
        
//        MINIMUM SPANNING TREE
        MST mst = new MST(g);
//        mst.kruskalMST();
        
    }
    
}
