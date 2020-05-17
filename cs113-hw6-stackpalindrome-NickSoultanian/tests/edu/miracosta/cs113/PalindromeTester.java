package edu.miracosta.cs113;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;

public class PalindromeTester {

    ArrayListStack<Character> stack1 = new ArrayListStack<Character>();
    ArrayListStack<Character> stack2 = new ArrayListStack<Character>();
    /**
     * Checks if parameter is a palindrome using stacks and
     * ignores whitespace + case sensitivity; doesn't ignore digits/symbols
     *
     * @param testString any chars allowed,
     * @return returns true if a palindrome (ignoring whitespace and case sensitivity), false otherwise
     */

    private boolean isPalindrome(String testString) {
        if(testString == null){
            throw new IllegalArgumentException();
        }
        testString = testString.replaceAll("\\s", "");
        testString = testString.replaceAll(",", "");

        double halfIndex = testString.length() / 2;


        for(int i = 0; i < testString.length(); i++){
            stack1.push(testString.charAt(i));
        }

        for(int i = 0; i < halfIndex; i++){
            stack2.push(stack1.pop());
        }
        String oddManOut = "";
        if(halfIndex != stack1.getSize()){
            oddManOut = oddManOut + stack1.pop();
        }
        String firstHalf = "";
        String secondHalf = "";
        for(int i = 0; i < halfIndex; i++){
            firstHalf = firstHalf + stack1.pop();

        }
        System.out.println();
        for(int i = 0; i < halfIndex; i++){
            secondHalf = secondHalf + stack2.pop();
        }

        if(firstHalf == "" && secondHalf == ""){
            return true;
        }
        else if(firstHalf.equalsIgnoreCase(secondHalf)){
            return true;
        }
        return false;


    }

    @Test
    public void testErrors() {
        try {
            isPalindrome(null);
            fail("Checking null to see if it's a palindrome should throw IllegalArgumentException!");
        } catch (IllegalArgumentException iae) { /* Test Passed! */ }
    }

    @Test
    public void testSimpleTrueCases() {
        assertTrue("This test is a palindrome", isPalindrome(""));
        assertTrue("This test is a palindrome", isPalindrome(" "));
        assertTrue("This test is a palindrome", isPalindrome("A"));
        assertTrue("This test is a palindrome", isPalindrome("7"));
        assertTrue("This test is a palindrome", isPalindrome("%"));

        assertTrue("This test is a palindrome", isPalindrome("  "));
        assertTrue("This test is a palindrome", isPalindrome("BB"));
        assertTrue("This test is a palindrome", isPalindrome("33"));
        assertTrue("This test is a palindrome", isPalindrome("**"));
    }

    @Test
    public void testSimpleFalseCases() {
        assertFalse("This test is NOT a palindrome", isPalindrome("AC"));
        assertFalse("This test is NOT a palindrome", isPalindrome("71"));
        assertFalse("This test is NOT a palindrome", isPalindrome("@+"));
    }

    @Test
    public void testWhitespaceCases() {
        assertTrue("This test is a palindrome", isPalindrome(" x "));
        assertTrue("This test is a palindrome", isPalindrome(" t   t  "));
        assertTrue("This test is a palindrome", isPalindrome(" 5 5"));
        assertTrue("This test is a palindrome", isPalindrome(" #      # "));

        assertFalse("This test is NOT a palindrome", isPalindrome("m   n  "));
        assertFalse("This test is NOT a palindrome", isPalindrome("   8  7 "));
        assertFalse("This test is NOT a palindrome", isPalindrome("  ^      &  "));
    }

    @Test
    public void testCaseSensitivityCases() {
        assertTrue("This test is a palindrome", isPalindrome("ABba"));
        assertTrue("This test is a palindrome", isPalindrome("roTOR"));
        assertTrue("This test is a palindrome", isPalindrome("rAceCaR"));
    }

    @Test
    public void testComplexCases() {
        assertTrue("This test is a palindrome", isPalindrome("fOO race CAR ooF"));
        assertTrue("This test is a palindrome", isPalindrome("AbBa ZaBba"));
        assertTrue("This test is a palindrome", isPalindrome("1 3 3 7  331"));
        assertTrue("This test is a palindrome", isPalindrome("sT RJKLEeE R#@ $A$ @# REeEL K  JRT s"));

    }

}
