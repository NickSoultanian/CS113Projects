package edu.miracosta.cs113;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;

import static org.junit.Assert.*;

public class DoubleListIterator{

    private static final int[] INT_VALUES = {100, 200, 300, 400, 500};

    private LinkedList<Integer> intList;
    private ListIterator<Integer> bro;

    /* HELPER METHODS */
    public void buildLists(int num) {
        for (int i = 0; i < 3; i++) {

            intList.add(INT_VALUES[i]);
        }
    }

    @Before
    public void setUp() {
        intList = new LinkedList<Integer>();
        bro = intList.listIterator();
    }

    @Test
    public void testListIterator() {
        buildLists(2);
        assertTrue("Integer list should start as empty", bro.hasNext());
    }
    /**
     * TODO:
     *  Write JUnit tests for the ListIterator methods
     */
}
