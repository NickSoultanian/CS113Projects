package edu.miracosta.cs113;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;



public class HashTableTesting {

    private static final String[] STRING_VALUES = {"first", "second", "third", "fourth" , "fifth", "fifth"};
    private static final Integer[] INT_VALUES = {100, 200, 300, 400, 500, 500};
    private static final Character[] CHAR_VALUES = {'A', 'B', 'C', 'D', 'E' , 'E'};

    private static final String STRING_INSERT_VAL = "w00t";
    private static final Integer INT_INSERT_VAL = 555;
    private static final Character CHAR_INSERT_VAL = 'X';


    private static final int STRING_INSERT_INDEX = 0, INT_INSERT_INDEX = 1, CHAR_INSERT_INDEX = 2, DOUBLE_INSERT_INDEX = 3;
    private static final String[] TO_STRING_INSERT = { "[w00t, first, second, third]",
            "[100, 555, 200, 300]",
            "[A, B, X, C]",
            "[1.1, 2.2, 3.3, -3.14]"};


    private HashTableChain<Integer, String> hash = new HashTableChain<Integer, String>();
    private Hashtable<Integer, String> stringTableJava;
    private Hashtable<Integer, Integer> intTableJava;
    private Hashtable<Integer, Character> charTableJava;

    private HashTableChain<Integer, String> stringTable;
    private HashTableChain<Integer, Integer> integerTable;
    private HashTableChain<Integer, Character> charTable;


    @Before
    public void setUp() {
        stringTable = new HashTableChain<Integer, String>();
        integerTable = new HashTableChain<Integer, Integer>();
        charTable = new HashTableChain<Integer, Character>();

        stringTableJava = new Hashtable<Integer, String>();
        intTableJava = new Hashtable<Integer, Integer>();
        charTableJava = new Hashtable<Integer, Character>();

        buildTables();
    }

    public void buildTables() {
        for (int i = 0; i < 6; i++) {
            stringTable.put(STRING_VALUES[i].hashCode(), STRING_VALUES[i]);
            integerTable.put(INT_VALUES[i].hashCode(),INT_VALUES[i]);
            charTable.put(CHAR_VALUES[i].hashCode(),CHAR_VALUES[i]);

            stringTableJava.put(STRING_VALUES[i].hashCode(), STRING_VALUES[i]);
            intTableJava.put(INT_VALUES[i].hashCode(),INT_VALUES[i]);
            charTableJava.put(CHAR_VALUES[i].hashCode(),CHAR_VALUES[i]);
        }
    }

    @Test
    public void testIsEmpty() {
        assertTrue("String table should start as empty", hash.isEmpty());
    }

    @Test
    public void testContainsKey() {

        assertEquals("Returns true if the object key is in the table", stringTable.containsKey(STRING_VALUES[1].hashCode()));
        assertEquals("Returns true if the object key is in the table", integerTable.containsKey(INT_VALUES[1].hashCode()));
        assertEquals("Returns true if the object key is in the table", charTable.containsKey(CHAR_VALUES[1].hashCode()));

    }

    @Test
    public void testContainsValue() {

        assertEquals("Returns true if the object value is in the table", stringTableJava.containsValue(STRING_VALUES[0]));
        assertEquals("Returns true if the object value is in the table", intTableJava.containsValue(INT_VALUES[0]));
        assertEquals("Returns true if the object value is in the table", charTableJava.containsValue(CHAR_VALUES[0]));

    }

    @Test
    public void testSize() {

        assertEquals("String table should have size of 6", 5, stringTable.size());
        assertEquals("Integer table should have size of 6", 5, integerTable.size());
        assertEquals("Character table should have size of 6", 5, charTable.size());
    }

    @Test
    public void testEntrySet() {


        assertEquals("String Table should meet in accordance with String Set", stringTableJava.entrySet(), stringTable.entrySet());
        assertEquals("integer Table should meet in accordance with integer Set", intTableJava.entrySet(), integerTable.entrySet());
        assertEquals("char Table should meet in accordance with char Set", charTableJava.entrySet(), charTable.entrySet());
    }

    @Test
    public void testKeySet() {

        assertEquals("StringTable keySet should equal StringSet keySet", stringTableJava.keySet(), stringTable.keySet());
        assertEquals("IntegerTable keySet should equal IntegerSet keySet", intTableJava.keySet(), integerTable.keySet());
        assertEquals("CharTable keySet should equal CharSet keySet", charTableJava.keySet(), charTable.keySet());
    }



    @Test
    public void testGet() {


        assertEquals("get the object at an items hashCode().", STRING_VALUES[2], stringTable.get(STRING_VALUES[2].hashCode()));
        assertEquals("get the object at an items hashCode().", INT_VALUES[2], integerTable.get(INT_VALUES[2].hashCode()));
        assertEquals("get the object at an items hashCode().", CHAR_VALUES[2], charTable.get(CHAR_VALUES[2].hashCode()));
    }

    @Test
    public void testPut() {


        stringTable.put(STRING_INSERT_VAL.hashCode(),STRING_INSERT_VAL);
        integerTable.put(INT_INSERT_VAL.hashCode(),INT_INSERT_VAL);
        charTable.put(CHAR_INSERT_VAL.hashCode(),CHAR_INSERT_VAL);
        assertEquals("Returns true if the new object is in the table", stringTable.containsValue(STRING_INSERT_VAL));
        assertEquals("Returns true if the new object is in the table", integerTable.containsValue(INT_INSERT_VAL));
        assertEquals("Returns true if the new object is in the table", charTable.containsValue(CHAR_INSERT_VAL));
    }

    @Test
    public void testEquals() {


        for(int i = 0; i < 6; i++) {

        }

        assertEquals("Expected String Table (java.util.HashTable) and Actual String table (your implementation) don't match", stringTableJava, stringTable);
        assertEquals("Expected Integer Table (java.util.HashTable) and Actual Integer table (your implementation) don't match", intTableJava, integerTable);
        assertEquals("Expected Character Table (java.util.HashTable) and Actual Character table (your implementation) don't match", charTableJava, charTable);

    }

    @Test
    public void testRemoveByValue() {


        stringTable.remove(STRING_VALUES[0].hashCode(), STRING_VALUES[0]);
        integerTable.remove(INT_VALUES[0].hashCode(), INT_VALUES[0]);
        charTable.remove(CHAR_VALUES[0].hashCode(), CHAR_VALUES[0]);



        assertEquals("String list expected toString doesn't match actual", true);
    }



    @Test
    public void testClear() {



        assertEquals("String table should have size of 6", 5, stringTable.size());
        assertEquals("Integer table should have size of 6", 5, integerTable.size());
        assertEquals("Character table should have size of 6", 5, charTable.size());


        stringTable.clear();
        integerTable.clear();
        charTable.clear();

        assertTrue("String table should be empty", stringTable.isEmpty());
        assertTrue("Integer table should be empty", integerTable.isEmpty());
        assertTrue("Character table should be empty", charTable.isEmpty());

    }
}
