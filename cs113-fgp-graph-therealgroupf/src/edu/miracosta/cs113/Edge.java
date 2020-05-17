package edu.miracosta.cs113;

import java.util.Objects;

public class Edge {
    private NonConflictingSchedules.OfferedClass dest;
    private NonConflictingSchedules.OfferedClass source;
    private double weight;

    public Edge(NonConflictingSchedules.OfferedClass source, NonConflictingSchedules.OfferedClass dest){

        this.source = source;
        this.dest = dest;
        weight = 1.0;

    }

    public Edge(NonConflictingSchedules.OfferedClass source, NonConflictingSchedules.OfferedClass dest, double w){

        if (Double.isNaN(w)) throw new IllegalArgumentException("Weight is NaN");
        this.source = source;
        this.dest = dest;
        weight = w;
    }

    public NonConflictingSchedules.OfferedClass getDest(){
        return dest;
    }

    public NonConflictingSchedules.OfferedClass getSource(){
        return source;
    }

    public double getWeight(){
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.getWeight(), getWeight()) == 0 &&
                Objects.equals(getDest(), edge.getDest()) &&
                Objects.equals(getSource(), edge.getSource());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDest(), getSource(), getWeight());
    }

    @Override
    public String toString() {
        return "Edge{" +
                "dest=" + dest +
                ", source=" + source +
                ", weight=" + weight +
                '}';
    }
}
