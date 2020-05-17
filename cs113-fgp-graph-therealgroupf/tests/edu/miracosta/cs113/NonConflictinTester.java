package edu.miracosta.cs113;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
public class NonConflictinTester {

    @Before
    public void start(){
        new ParsePDFOutput("refinedClassOfferings.txt");
        new AvailableClasses();
    }
    @Test
    public void testSchedules() { //all junits need to be done in one else there are overflow errors
        NonConflictingSchedules tester = new NonConflictingSchedules("CS 220,BIO 111,ART 100");
        assertEquals("Combinations are missing wrong", tester.testCombos.size(), 3);
        String toCheck = tester.testCombos.get(0).toString();
        String startingName = toCheck.substring(31,45);
        String toCheck2 = tester.testCombos.get(1).toString();
        assertEquals("default scores incorrect", toCheck.substring(toCheck.length()-4), "3.89");
        //check the order of classes
        assertEquals("class order incorrect", toCheck, "CS 220,2525,MW 3:30 pm–5:20 pm,CHAPETON LAM,3.89");
        assertEquals("class order incorrect", toCheck2, "BIO 111,1131,TTh 1:30 pm–2:45 pm,CLARK R,3.89");
        //check that names update and that scores do as well
        new GetProfScore();
        String toCheck3 = tester.testCombos.get(0).toString();
        String endName = toCheck3.substring(31,45);
        assertNotEquals("prof ratings did not update", toCheck,toCheck3);
        assertNotEquals("prof names did not update", startingName,endName);

    }
}
