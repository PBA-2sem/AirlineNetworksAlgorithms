package dk.cphbusiness.alg.graphs;

class Edge {

    String from;
    String to;
    String airline = "";

    Edge(String from, String to, String airline) {
        this.from = from;
        this.to = to;
        this.airline = airline;
    }

    Edge(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        if (this.airline.length() > 0) {
            return "" + from + " -> " + to + " with : " + this.airline;
        } else {
            return "" + from + " -> " + to;
        }
    }
}
