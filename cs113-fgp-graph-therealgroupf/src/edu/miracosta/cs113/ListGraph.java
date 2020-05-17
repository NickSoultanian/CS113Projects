package edu.miracosta.cs113;
import java.util.*;
/** A ListGraph is an extension of the AbstractGraph abstract class
 that uses an array of lists to represent the edges.
 */
public class ListGraph extends AbstractGraph {
// Data Field
    /**
     * An array of Lists to contain the edges that
     * originate with each vertex.
     */
    protected ArrayList<NonConflictingSchedules.OfferedClass> edges;
    protected HashMap<NonConflictingSchedules.OfferedClass, LinkedList<Edge>> map;

    /**
     * Construct a graph with the specified number of vertices and directionality.
     *
     * @param numVerts The number of vertices
     * @param directed The directionality flag
     */
    public ListGraph(int numVerts, boolean directed) {
        super(numVerts, directed);
        edges = new ArrayList<NonConflictingSchedules.OfferedClass>(numVerts);
        map = new HashMap<NonConflictingSchedules.OfferedClass, LinkedList<Edge>>();
        for (int i = 0; i < numVerts; i++) {
            edges.add(NonConflictingSchedules.allClasses.get(i));
        }
        for (int i = 0; i < numVerts; i++) {
            map.put(edges.get(i), new LinkedList<Edge>());
        }
    }

    /**
     * Determine whether an edge exists.
     *
     * @return true if there is an edge from source to dest
     * /*
     */
public boolean isEdge(NonConflictingSchedules.OfferedClass source, NonConflictingSchedules.OfferedClass dest) {
        for(int i = 0; i < edges.size(); i++){
            if(edges.get(i).equals(source)){
                return edges.get(i).equals(new Edge(source, dest));
            }
        }
            return false;
        }

    public boolean insert(Edge edge) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).equals(edge.getSource())) {
                LinkedList currentList = map.get(edges.get(i));
                for (int j = 0; j < currentList.size(); j++) {
                    if (currentList.get(j).equals(edge.getDest())) {
                        return false;
                    }
                }
                currentList.add(edge);
                return true;
            }
        }
        return false;
    }

    public Iterator<Edge> edgeIterator(NonConflictingSchedules.OfferedClass source) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

            return null;
    }

    /**
     * Get the edge between two vertices.
     *
     * @return the edge between these two vertices
     * or null if an edge does not exist.
     */

    public Edge getEdge(NonConflictingSchedules.OfferedClass source, NonConflictingSchedules.OfferedClass dest) {
        Edge target = new Edge( source, dest, Double.POSITIVE_INFINITY);
        for(int i = 0; i < edges.size(); i++){
            if(edges.get(i).equals(target.getSource())){
                for(int j = 0; j < map.get(i).size(); j++){
                    if(map.get(i).get(j).getDest().equals(target.getDest())){
                        return target;
                    }
                }
            }
        }

        return null;
    }

    public ArrayList<NonConflictingSchedules.OfferedClass> getEdges() {
        return edges;
    }

    public HashMap<NonConflictingSchedules.OfferedClass, LinkedList<Edge>> getMap() {
        return map;
    }



    @Override
    public String toString() {
        String toPrint = "";
        for (int i = 0; i < edges.size(); i++) {
            toPrint = toPrint + map.get(edges.get(i)) + "\n";
        }
        return toPrint;
    }

}

