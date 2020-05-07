# Mini Project #4 - Airline Networks

## Technology used
 
- Java

## Walkthrough of assignment solutions

- Find if an airport can be reached from another using only a single
airline company. You should compare<br><br>
(a) Breadth-first

To see if an aiport can be reached from another using the same airline company with the **Breadth-first** algorithm, run the following code in the **Tester.java** file 
```java
        BFSearch bfsearch = new BFSearch(g);
        bfsearch.searchFrom("YZV");
        //Print all
        //bfsearch.print(System.out);

        //search with same airline
        System.out.println(bfsearch.showPathToWithSameAirline("ZLT", "WJ"));
```

This will show whether you can reach the aiport "ZLT" from "YZV" only with the airline company, "WJ".

(b) Depth-first

To see if an aiport can be reached from another using the same airline company with the **Depth-first** algorithm, run the following code in the **Tester.java** file 
```java
        Depth First ####
        DFSearch dfsearch = new DFSearch(g);

        dfsearch.searchFrom("YZV");
        //Print all
        //dfsearch.print(System.out);

        System.out.println(dfsearch.showPathToWithSameAirline("ZLT", "WJ"));
```
This will show whether you can reach the aiport "ZLT" from "YZV" only with the airline company, "WJ".

- Finding shortest path (distance) from one location to another (Dijkstra’s algirithm)

Using dijkstra we have two instances with each using a different weight, this fist example is for distance and similarly we have an example below with time
```java
//      DijkstraSearch with distance as weight
        DijkstraSearch distanceSearch = new DijkstraSearch(g, "YZV", "distance");
//      distanceSearch.print(System.out);
        System.out.println(distanceSearch.showPathTo("CPH"));
```
This display us the shortest "cheapest" route from "YZV" to "CPH" airports

- Finding shortest path (time) from one location to another, assuming
that each transfer takes one hour.

To test with **time** as weight, simply change **"distance"** with **"time"** in the previous code example.

- Finding airline that has widest coverage (Minimum Spanning Tree)

To show the MST of the airline data, run the following code in the **Tester.java** file: 

```java
//        MINIMUM SPANNING TREE
        MST mst = new MST(g);
        mst.kruskalMST();
```

## Author Details

Group: Team Wing It
- Alexander Winther Hørsted-Andersen (cph-ah353@cphbusiness.dk)
- Mathias Bigler (cph-mb493@cphbusiness.dk)
- Stanislav Novitski (cph-sn183@cphbusiness.dk)