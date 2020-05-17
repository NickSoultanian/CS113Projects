package edu.miracosta.cs113;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;

import static org.junit.Assert.*;

public class DoubleLinkedListTest {

    private static final String[] STRING_VALUES = {"first", "second", "third", "fourth" , "fifth"};
    private static final int[] INT_VALUES = {100, 200, 300, 400, 500};
    private static final char[] CHAR_VALUES = {'A', 'B', 'C', 'D', 'E'};
    private static final double[] DOUBLE_VALUES = {1.1, 2.2, 3.3, 4.4, 5.5};

    private static final String TO_STRING_EMPTY = "[]";
    private static final String[] TO_STRING_ADD1 = { "[first]",
                                                    "[100]",
                                                    "[A]",
                                                    "[1.1]"};
    private static final String[] TO_STRING_ADD3 = { "[first, second, third]",
                                                    "[100, 200, 300]",
                                                    "[A, B, C]",
                                                    "[1.1, 2.2, 3.3]"};

    private static final String STRING_INSERT_VAL = "w00t";
    private static final int INT_INSERT_VAL = 555;
    private static final char CHAR_INSERT_VAL = 'X';
    private static final double DOUBLE_INSERT_VAL = -3.14;

    private static final int STRING_INSERT_INDEX = 0, INT_INSERT_INDEX = 1, CHAR_INSERT_INDEX = 2, DOUBLE_INSERT_INDEX = 3;
    private static final String[] TO_STRING_INSERT = { "[w00t, first, second, third]",
                                                    "[100, 555, 200, 300]",
                                                    "[A, B, X, C]",
                                                    "[1.1, 2.2, 3.3, -3.14]"};


    private List<String> stringList;
    private List<Integer> intList;
    private List<Character> charList;
    private List<Double> doubleList;

    /* HELPER METHODS */
    public void buildLists(int num) {
         for (int i = 0; i < num; i++) {
            stringList.add(STRING_VALUES[i]);
            intList.add(INT_VALUES[i]);
            charList.add(CHAR_VALUES[i]);
            doubleList.add(DOUBLE_VALUES[i]);
        }
    }

    /**
     * with the @Before tag, this method will called before EVERY SINGLE @Test method that runs
     */
    @Before
    public void setUp() {
        stringList = new DoubleLinkedList<String>();
        intList = new DoubleLinkedList<Integer>();
        charList = new DoubleLinkedList<Character>();
        doubleList = new DoubleLinkedList<Double>();
    }





    @Test
    public void testNewLinkedListBySize() {
        assertTrue("String list should start as empty", stringList.isEmpty());
        assertTrue("Integer list should start as empty", intList.isEmpty());
        assertTrue("Character list should start as empty", charList.isEmpty());
        assertTrue("Double list should start as empty", doubleList.isEmpty());
    }

    @Test
    public void testAddingOneBySize() {
        buildLists(1);

        assertEquals("String list should have size of 1", 1, stringList.size());
        assertEquals("Integer list should have size of 1", 1, intList.size());
        assertEquals("Character list should have size of 1", 1, charList.size());
        assertEquals("Double list should have size of 1", 1, doubleList.size());
    }

    @Test
    public void testAddingThreeBySize() {
        buildLists(3);

        assertEquals("String list should have size of 3", 3, stringList.size());
        assertEquals("Integer list should have size of 3", 3, intList.size());
        assertEquals("Character list should have size of 3", 3, charList.size());
        assertEquals("Double list should have size of 3", 3, doubleList.size());
    }

    @Test
    public void testNewLinkedListByToString() {
        assertEquals("String list expected toString doesn't match actual", TO_STRING_EMPTY, stringList.toString());
        assertEquals("Integer list expected toString doesn't match actual", TO_STRING_EMPTY, intList.toString());
        assertEquals("Character list expected toString doesn't match actual", TO_STRING_EMPTY, charList.toString());
        assertEquals("Double list expected toString doesn't match actual", TO_STRING_EMPTY, doubleList.toString());
    }

    @Test
    public void testAddingOneByToString() {
        buildLists(1);

        assertEquals("String list expected toString doesn't match actual", TO_STRING_ADD1[0], stringList.toString());
        assertEquals("Integer list expected toString doesn't match actual", TO_STRING_ADD1[1], intList.toString());
        assertEquals("Character list expected toString doesn't match actual", TO_STRING_ADD1[2], charList.toString());
        assertEquals("Double list expected toString doesn't match actual", TO_STRING_ADD1[3], doubleList.toString());
    }

    @Test
    public void testAddingThreeByToString() {
        buildLists(3);

        assertEquals("String list expected toString doesn't match actual", TO_STRING_ADD3[0], stringList.toString());
        assertEquals("Integer list expected toString doesn't match actual", TO_STRING_ADD3[1], intList.toString());
        assertEquals("Character list expected toString doesn't match actual", TO_STRING_ADD3[2], charList.toString());
        assertEquals("Double list expected toString doesn't match actual", TO_STRING_ADD3[3], doubleList.toString());
    }



    @Test
    public void testInsertingByToString() {
        buildLists(3);

        stringList.add(STRING_INSERT_INDEX,  STRING_INSERT_VAL);
        intList.add(INT_INSERT_INDEX,  INT_INSERT_VAL);
        charList.add(CHAR_INSERT_INDEX,  CHAR_INSERT_VAL);
        doubleList.add(DOUBLE_INSERT_INDEX,  DOUBLE_INSERT_VAL);

        assertEquals("String list expected toString doesn't match actual", TO_STRING_INSERT[0], stringList.toString());
        assertEquals("Integer list expected toString doesn't match actual", TO_STRING_INSERT[1], intList.toString());
        assertEquals("Character list expected toString doesn't match actual", TO_STRING_INSERT[2], charList.toString());
        assertEquals("Double list expected toString doesn't match actual", TO_STRING_INSERT[3], doubleList.toString());
    }


    @Test
    public void testClear() {
        buildLists(3);

        assertEquals("String list should have size of 3", 3, stringList.size());
        assertEquals("Integer list should have size of 3", 3, intList.size());
        assertEquals("Character list should have size of 3", 3, charList.size());
        assertEquals("Double list should have size of 3", 3, doubleList.size());

        stringList.clear();
        intList.clear();
        charList.clear();
        doubleList.clear();

        assertTrue("String list should be empty", stringList.isEmpty());
        assertTrue("Integer list should be empty", intList.isEmpty());
        assertTrue("Character list should be empty", charList.isEmpty());
        assertTrue("Double list should be empty", doubleList.isEmpty());
    }

    @Test
    public void testEquals() {
        buildLists(5);

        List<String> stringListJava = new LinkedList<String>();
        List<Integer> intListJava = new LinkedList<Integer>();
        List<Character> charListJava = new LinkedList<Character>();
        List<Double> doubleListJava = new LinkedList<Double>();

        for(int i = 0; i < 5; i++) {
            stringListJava.add(STRING_VALUES[i]);
            intListJava.add(INT_VALUES[i]);
            charListJava.add(CHAR_VALUES[i]);
            doubleListJava.add(DOUBLE_VALUES[i]);
        }

        assertEquals("Expected String list (java.util.LinkedList) and Actual String list (your implementation) don't match", stringListJava, stringList);
        assertEquals("Expected Integer list (java.util.LinkedList) and Actual Integer list (your implementation) don't match", intListJava, intList);
        assertEquals("Expected Character list (java.util.LinkedList) and Actual Character list (your implementation) don't match", charListJava, charList);
        assertEquals("Expected Double list (java.util.LinkedList) and Actual Double list (your implementation) don't match", doubleListJava, doubleList);
    }

    @Test
    public void testContains() {
        buildLists(5);

        assertFalse("Expected value NOT to be in String list, but your code claims it is", stringList.contains(STRING_INSERT_VAL));
        assertFalse("Expected value NOT to be in Integer list, but your code claims it is", intList.contains(INT_INSERT_VAL));
        assertFalse("Expected value NOT to be in Character list, but your code claims it is", charList.contains(CHAR_INSERT_VAL));
        assertFalse("Expected value NOT to be in Double list, but your code claims it is", doubleList.contains(DOUBLE_INSERT_VAL));

        stringList.add(STRING_INSERT_VAL);
        intList.add(INT_INSERT_VAL);
        charList.add(CHAR_INSERT_VAL);
        doubleList.add(DOUBLE_INSERT_VAL);

        assertTrue("Expected value to be in String list, but your code claims it is NOT", stringList.contains(STRING_INSERT_VAL));
        assertTrue("Expected value to be in Integer list, but your code claims it is NOT", intList.contains(INT_INSERT_VAL));
        assertTrue("Expected value to be in Character list, but your code claims it is NOT", charList.contains(CHAR_INSERT_VAL));
        assertTrue("Expected value to be in Double list, but your code claims it is NOT", doubleList.contains(DOUBLE_INSERT_VAL));
    }

    @Test
    public void testGet() {
        buildLists(5);

        assertEquals("Expected value in String list doesn't match get() from your list", STRING_VALUES[3], stringList.get(3));
        assertEquals("Expected value in Integer list doesn't match get() from your list", new Integer(INT_VALUES[3]), intList.get(3));
        assertEquals("Expected value in Character list doesn't match get() from your list", new Character(CHAR_VALUES[3]), charList.get(3));
        assertEquals("Expected value in Double list doesn't match get() from your list", new Double(DOUBLE_VALUES[3]), doubleList.get(3));
    }

    @Test
    public void testGetError() {
        try {
            stringList.get(0);
            fail("String list get() test should have thrown exception for out of bounds (empty list, index == 0)");
        } catch (IndexOutOfBoundsException ioobe) { /*Test Passed*/ }

        try {
            intList.get(1);
            fail("Integer list get() test should have thrown exception for out of bounds (empty list, index == 1)");
        } catch (IndexOutOfBoundsException ioobe) { /*Test Passed*/ }

        buildLists(5);

        try {
            charList.get(5);
            fail("Character list get() test should have thrown exception for out of bounds (index >= size)");
        } catch (IndexOutOfBoundsException ioobe) { /*Test Passed*/ }

        try {
            doubleList.get(-1);
            fail("Double list get() test should have thrown exception for out of bounds (index < 0)");
        } catch (IndexOutOfBoundsException ioobe) { /*Test Passed*/ }
    }

    @Test
    public void testIndexOf() {
        buildLists(4);

        assertEquals("Expected value in String list doesn't match indexOf() from your list", 0, stringList.indexOf(STRING_VALUES[0]));
        assertEquals("Expected value in Integer list doesn't match indexOf() from your list", 1, intList.indexOf(INT_VALUES[1]));
        assertEquals("Expected value in Character list doesn't match indexOf() from your list", 2, charList.indexOf(CHAR_VALUES[2]));
        assertEquals("Expected value in Double list doesn't match indexOf() from your list", 3, doubleList.indexOf(DOUBLE_VALUES[3]));
    }

    @Test
    public void testIndexOfRepeats() {
        buildLists(4);

        stringList.add(1, STRING_VALUES[0]);
        stringList.add(5, STRING_VALUES[0]);
        System.out.println("new string list = " + stringList); //leaving these here so you can see the repeated values, indexOf should return the FIRST value
        intList.add(2, INT_VALUES[1]);
        intList.add(5, INT_VALUES[1]);
        System.out.println("new int list = " + intList);
        charList.add(3, CHAR_VALUES[2]);
        charList.add(5, CHAR_VALUES[2]);
        System.out.println("new char list = " + charList);
        doubleList.add(4, DOUBLE_VALUES[3]);
        doubleList.add(5, DOUBLE_VALUES[3]);
        System.out.println("new double list = " + doubleList);

        assertEquals("Expected value in String list doesn't match indexOf() from your list", 0, stringList.indexOf(STRING_VALUES[0]));
        assertEquals("Expected value in Integer list doesn't match indexOf() from your list", 1, intList.indexOf(INT_VALUES[1]));
        assertEquals("Expected value in Character list doesn't match indexOf() from your list", 2, charList.indexOf(CHAR_VALUES[2]));
        assertEquals("Expected value in Double list doesn't match indexOf() from your list", 3, doubleList.indexOf(DOUBLE_VALUES[3]));
    }


    @Test
    public void testLastIndexOf() {
        buildLists(4);

        assertEquals("Expected value in String list doesn't match lastIndexOf() from your list", 0, stringList.lastIndexOf(STRING_VALUES[0]));
        assertEquals("Expected value in Integer list doesn't match lastIndexOf() from your list", 1, intList.lastIndexOf(INT_VALUES[1]));
        assertEquals("Expected value in Character list doesn't match lastIndexOf() from your list", 2, charList.lastIndexOf(CHAR_VALUES[2]));
        assertEquals("Expected value in Double list doesn't match lastIndexOf() from your list", 3, doubleList.lastIndexOf(DOUBLE_VALUES[3]));
    }

    @Test
    public void testLastIndexOfRepeats1() {
        buildLists(4);

        stringList.add(1, STRING_VALUES[0]);
        System.out.println("new string list = " + stringList); //leaving these here so you can see the repeated values, indexOf should return the FIRST value
        intList.add(2, INT_VALUES[1]);
        System.out.println("new int list = " + intList);
        charList.add(3, CHAR_VALUES[2]);
        System.out.println("new char list = " + charList);
        doubleList.add(4, DOUBLE_VALUES[3]);
        System.out.println("new double list = " + doubleList);

        assertEquals("Expected value in String list doesn't match lastIndexOf() from your list", 1, stringList.lastIndexOf(STRING_VALUES[0]));
        assertEquals("Expected value in Integer list doesn't match lastIndexOf() from your list", 2, intList.lastIndexOf(INT_VALUES[1]));
        assertEquals("Expected value in Character list doesn't match lastIndexOf() from your list", 3, charList.lastIndexOf(CHAR_VALUES[2]));
        assertEquals("Expected value in Double list doesn't match lastIndexOf() from your list", 4, doubleList.lastIndexOf(DOUBLE_VALUES[3]));
    }

    @Test
    public void testLastIndexOfRepeats2() {
        buildLists(4);

        stringList.add(4, STRING_VALUES[0]);
        System.out.println("new string list = " + stringList); //leaving these here so you can see the repeated values, indexOf should return the FIRST value
        intList.add(4, INT_VALUES[1]);
        System.out.println("new int list = " + intList);
        charList.add(4, CHAR_VALUES[2]);
        System.out.println("new char list = " + charList);
        doubleList.add(4, DOUBLE_VALUES[3]);
        System.out.println("new double list = " + doubleList);

        assertEquals("Expected value in String list doesn't match lastIndexOf() from your list", 4, stringList.lastIndexOf(STRING_VALUES[0]));
        assertEquals("Expected value in Integer list doesn't match lastIndexOf() from your list", 4, intList.lastIndexOf(INT_VALUES[1]));
        assertEquals("Expected value in Character list doesn't match lastIndexOf() from your list", 4, charList.lastIndexOf(CHAR_VALUES[2]));
        assertEquals("Expected value in Double list doesn't match lastIndexOf() from your list", 4, doubleList.lastIndexOf(DOUBLE_VALUES[3]));
    }

    @Test
    public void testErrorsIndexOfLastIndexOf() {
        buildLists(5);

        assertEquals("Expected value in String list doesn't match indexOf() from your list", -1, stringList.indexOf(STRING_INSERT_VAL));
        assertEquals("Expected value in Integer list doesn't match indexOf() from your list", -1, intList.indexOf(INT_INSERT_VAL));
        assertEquals("Expected value in Character list doesn't match indexOf() from your list", -1, charList.indexOf(CHAR_INSERT_VAL));
        assertEquals("Expected value in Double list doesn't match indexOf() from your list", -1, doubleList.indexOf(DOUBLE_INSERT_VAL));

        assertEquals("Expected value in String list doesn't match lastIndexOf() from your list", -1, stringList.lastIndexOf(STRING_INSERT_VAL));
        assertEquals("Expected value in Integer list doesn't match lastIndexOf() from your list", -1, intList.lastIndexOf(INT_INSERT_VAL));
        assertEquals("Expected value in Character list doesn't match lastIndexOf() from your list", -1, charList.lastIndexOf(CHAR_INSERT_VAL));
        assertEquals("Expected value in Double list doesn't match lastIndexOf() from your list", -1, doubleList.lastIndexOf(DOUBLE_INSERT_VAL));

    }

    @Test
    public void testRemoveByIndex() {
        buildLists(5);

        assertEquals("Remove from String list was not successful, removed wrong value", STRING_VALUES[0], stringList.remove(0));
        assertEquals("Remove from Integer list was not successful, removed wrong value", new Integer(INT_VALUES[1]), intList.remove(1));
        assertEquals("Remove from Character list was not successful, removed wrong value", new Character(CHAR_VALUES[2]), charList.remove(2));
        assertEquals("Remove from Double list was not successful, removed wrong value", new Double(DOUBLE_VALUES[3]), doubleList.remove(3));
    }

    @Test
    public void testRemoveByValue() {
        buildLists(3);

        stringList.add(STRING_INSERT_INDEX,  STRING_INSERT_VAL);
        intList.add(INT_INSERT_INDEX,  INT_INSERT_VAL);
        charList.add(CHAR_INSERT_INDEX,  CHAR_INSERT_VAL);
        doubleList.add(DOUBLE_INSERT_INDEX,  DOUBLE_INSERT_VAL);

        System.out.println("before remove, string list = " + stringList); //leaving these here so you can see the lists before remove
        System.out.println("before remove, int list = " + intList);
        System.out.println("before remove, char list = " + charList);
        System.out.println("before remove, double list = " + doubleList);

        assertEquals("String list expected toString doesn't match actual", TO_STRING_INSERT[0], stringList.toString());
        assertEquals("Integer list expected toString doesn't match actual", TO_STRING_INSERT[1], intList.toString());
        assertEquals("Character list expected toString doesn't match actual", TO_STRING_INSERT[2], charList.toString());
        assertEquals("Double list expected toString doesn't match actual", TO_STRING_INSERT[3], doubleList.toString());

        assertTrue("String list remove returned false when value is actually present in list", stringList.remove(STRING_INSERT_VAL));
        assertTrue("Integer list remove returned false when value is actually present in list", intList.remove(new Integer(INT_INSERT_VAL)));
        assertTrue("Character list remove returned false when value is actually present in list", charList.remove(new Character(CHAR_INSERT_VAL)));
        assertTrue("Double list remove returned false when value is actually present in list", doubleList.remove(DOUBLE_INSERT_VAL));

        System.out.println("\nafter remove, string list = " + stringList); //leaving these here so you can see the lists after remove
        System.out.println("after remove, int list = " + intList);
        System.out.println("after remove, char list = " + charList);
        System.out.println("after remove, double list = " + doubleList);

        assertEquals("String list expected toString doesn't match actual, incorrect value removed", TO_STRING_ADD3[0], stringList.toString());
        assertEquals("Integer list expected toString doesn't match actual, incorrect value removed", TO_STRING_ADD3[1], intList.toString());
        assertEquals("Character list expected toString doesn't match actual, incorrect value removed", TO_STRING_ADD3[2], charList.toString());
        assertEquals("Double list expected toString doesn't match actual, incorrect value removed", TO_STRING_ADD3[3], doubleList.toString());
    }

    @Test
    public void testRemoveErrors() {
        buildLists(3);

        //check for exceptions by remove(int)
        try {
            stringList.remove(3);
            fail("String list remove() test should have thrown exception for out of bounds (index >= size)");
        } catch (IndexOutOfBoundsException ioobe) { /*Test Passed*/ }

        try {
            intList.remove(-1);
            fail("Integer list remove() test should have thrown exception for out of bounds (index < 0)");
        } catch (IndexOutOfBoundsException ioobe) { /*Test Passed*/ }

        //check for false by remove(Object)
        assertFalse("Character list remove returned true when value is NOT present in list", charList.remove(new Character(CHAR_INSERT_VAL)));
        assertFalse("Double list remove returned true when value is NOT present in list", doubleList.remove(DOUBLE_INSERT_VAL));
    }

    @Test
    public void testSet() {
        buildLists(5);

        stringList.set(STRING_INSERT_INDEX,  STRING_INSERT_VAL);
        intList.set(INT_INSERT_INDEX,  INT_INSERT_VAL);
        charList.set(CHAR_INSERT_INDEX,  CHAR_INSERT_VAL);
        doubleList.set(DOUBLE_INSERT_INDEX,  DOUBLE_INSERT_VAL);

        assertEquals("Expected value in String list doesn't match get() from your list", STRING_INSERT_VAL, stringList.get(STRING_INSERT_INDEX));
        assertEquals("Expected value in Integer list doesn't match get() from your list", new Integer(INT_INSERT_VAL), intList.get(INT_INSERT_INDEX));
        assertEquals("Expected value in Character list doesn't match get() from your list", new Character(CHAR_INSERT_VAL), charList.get(CHAR_INSERT_INDEX));
        assertEquals("Expected value in Double list doesn't match get() from your list", new Double(DOUBLE_INSERT_VAL), doubleList.get(DOUBLE_INSERT_INDEX));
    }

    @Test
    public void testSetError() {
        try {
            stringList.set(0, STRING_INSERT_VAL);
            fail("String list get() test should have thrown exception for out of bounds (empty list, index == 0)");
        } catch (IndexOutOfBoundsException ioobe) { /*Test Passed*/ }

        try {
            intList.set(1,  INT_INSERT_VAL);
            fail("Integer list get() test should have thrown exception for out of bounds (empty list, index == 1)");
        } catch (IndexOutOfBoundsException ioobe) { /*Test Passed*/ }

        buildLists(5);

        try {
            charList.set(5,  CHAR_INSERT_VAL);
            fail("Character list get() test should have thrown exception for out of bounds (index >= size)");
        } catch (IndexOutOfBoundsException ioobe) { /*Test Passed*/ }

        try {
            doubleList.set(-1,  DOUBLE_INSERT_VAL);
            fail("Double list get() test should have thrown exception for out of bounds (index < 0)");
        } catch (IndexOutOfBoundsException ioobe) { /*Test Passed*/ }
    }


    /**
     * TODO:
     *  Write JUnit tests for the ListIterator methods
     */

}