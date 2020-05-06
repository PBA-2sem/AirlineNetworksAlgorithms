package dk.cphbusiness.alg.graphs;

/**
 *
 * @author stanislavnovitski
 */
public class EdgeNode {

    String destination;
    String source;
    String airline;
    Float distance;
    Float time;
    EdgeNode next;

    EdgeNode(String destination, String source, String airline, Float distance, Float time, EdgeNode next) {
        this.destination = destination;
        this.source = source;
        this.airline = airline;
        this.distance = distance;
        this.time = time;
        this.next = next;
    }

    @Override
    public String toString() {
        return this.destination + "; " + this.airline + "; " + this.next;
    }
}
