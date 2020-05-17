package edu.miracosta.cs113;

import org.junit.Test;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class ListGraphTesting {
    private ListGraph graph;
    private NonConflictingSchedules tester;
    //ListGraph graph;
    @Before
    public void start(){
        new ParsePDFOutput("refinedClassOfferings.txt");
        new AvailableClasses();
        tester = new NonConflictingSchedules("CS 220,BIO 111,ART 100");
        tester.buildGraph();
        graph = tester.getGraph();
    }
    //NonConflictingSchedules non = new NonConflictingSchedules();

    //NonConflictingSchedules.OfferedClass blah = new NonConflictingSchedules.OfferedClass("BIO 111,2256,MW6-5,NERY CHAP");

    //NonConflictingSchedules.OfferedClass balh1 = new NonConflictingSchedules.OfferedClass("Bio 111");


    @Test
    public void testInsert(){
        tester.buildGraph();
        graph = tester.getGraph();

        assertEquals("is Edge should be false", graph.isEdge(graph.getEdges().get(0), graph.getEdges().get(1)), false);
        assertEquals("is Edge should be false", graph.isEdge(graph.getEdges().get(4), graph.getEdges().get(2)), false);
        assertEquals("is Edge should be false", graph.isEdge(graph.getEdges().get(3), graph.getEdges().get(3)), false);
        assertEquals("is Edge should be true", graph.isEdge(graph.getEdges().get(3), graph.getEdges().get(4)), false);
        assertEquals("is Edge should be true", graph.isEdge(graph.getEdges().get(1), graph.getEdges().get(5)), false);

        graph.edgeIterator(graph.getEdges().get(0));

    }

}
