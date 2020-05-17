package edu.miracosta.cs113;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Stack;

public class BreadthFirstSearch {
     private static PriorityQueue<NonConflictingSchedules.OfferedClass> visited;

     public BreadthFirstSearch(){
          visited = new PriorityQueue<NonConflictingSchedules.OfferedClass>();
     }





    public static void BFS(ArrayList<NonConflictingSchedules.OfferedClass> edges, HashMap<NonConflictingSchedules.OfferedClass, LinkedList<Edge>> map )
    {
        if( map.isEmpty() )
            return;

        //pick an arbitrary first vertex to start from

        NonConflictingSchedules.OfferedClass start = edges.get(0);
        for( NonConflictingSchedules.OfferedClass temp : map.keySet() )
        {
            start = temp;
            break;
        }

        //visit first vertex


        //start recursion
        BFS( start, map );
    }
     public static void BFS(NonConflictingSchedules.OfferedClass start, HashMap<NonConflictingSchedules.OfferedClass, LinkedList<Edge>> map) {
          Deque<NonConflictingSchedules.OfferedClass> visit = new LinkedList<NonConflictingSchedules.OfferedClass>();
          visit.add(start);
          while(!visit.isEmpty()) {
               NonConflictingSchedules.OfferedClass current = visit.getFirst();
               visited.add(current);
               // Or you can store a set of visited vertices somewhere
               LinkedList<Edge> neighbors = map.get(current);
               for (Edge neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                         visit.addLast(neighbor.getSource());
                    }
               }
          }
     }
     /*
    public void BFS( NonConflictingSchedules.OfferedClass start )
    {
        //mark the current vertex as visited
        int count = start.();
        assert count == 0;
        count++;
        start.setCount( count );

        //visit each unvisited neighboring vertex
        for( NonConflictingSchedules.OfferedClass neighbor : map.get( start ) )
        {
            if( neighbor.getCount() != 0 )
                continue;
            visit( neighbor );
        }

        //recurse into each unvisited neighboring vertex
        for( NonConflictingSchedules.OfferedClass neighbor : map.get( start ) )
        {
            if( neighbor.getCount() != 0 )
                continue;
            BFS( neighbor, map );
        }
    }
    */

}
