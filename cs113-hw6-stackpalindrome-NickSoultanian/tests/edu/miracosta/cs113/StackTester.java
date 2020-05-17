package edu.miracosta.cs113;


import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StackTester {

    private StackInterface<Integer> stack;

    //this method will run at the start of every test!
    @Before
    public void setup() {
        stack = new ArrayListStack<Integer>();
    }

    @Test
    public void testEmpty() {
        assertTrue("Should start as empty", stack.empty());
    }

    @Test
    public void testPeekError() {
        try {
            stack.peek();
            fail("Peek should have thrown EmptyStackException!");
        } catch (EmptyStackException ese) { /* Test Passed! */ }
    }

    @Test
    public void testPushAndNotEmpty() {
        stack.push(5);
        assertFalse("Should not be empty", stack.empty());
    }

    @Test
    public void testPushAndPeekOnce() {
        stack.push(5);
        assertEquals("Expected and actual don't match, only one value was pushed!",
                         5L, (long)stack.peek()); //5L is a 5 but created as a long type literal
    }

    @Test
    public void testPushAndPeekMany() {
        for(int i = 0; i < 5; i++) {
            stack.push(i);
            assertEquals("Expected and actual don't match, somethings wrong with pushing multiple values?",
                            (long)i, (long)stack.peek());
        }
    }

    @Test
    public void testPopError() {
        try {
            stack.pop();
            fail("Pop should have thrown EmptyStackException!");
        } catch (EmptyStackException ese) { /* Test Passed! */ }
    }

    @Test
    public void testPopOnce() {
        stack.push(71);

        assertEquals("Expected and actual don't match, somethings wrong with popping or pushing one value?",
                            71L, (long)stack.pop()); //71L is a 71 but created as a long type literal
    }

    @Test
    public void testPopMany() {
        for(int i = 50; i <= 63; i++) {
            stack.push(i);
        }

        //values come out in reverse order!
        for(int i = 63; i >= 50; i--) {
            assertEquals("Expected and actual don't match, somethings wrong with popping or pushing multiple values?",
                    (long)i, (long)stack.pop());
        }
    }
}