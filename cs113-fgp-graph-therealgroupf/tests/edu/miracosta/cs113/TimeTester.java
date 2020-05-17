package edu.miracosta.cs113;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class TimeTester {

    @Test
    public void addsZeros() {
        Time time = new Time("F 9 am–12 pm");
        assertEquals("Single digit am classes are wrong", time.toString(), "{5: 9.0-12.0}");

    }
    @Test
    public void daysOfWeek() {
        Time time = new Time("F 9 am–12 pm");
        assertEquals("day of week is wrong", time.toString(), "{5: 9.0-12.0}");
        time = new Time("M 9 am–12 pm");
        assertEquals("day of week is wrong", time.toString(), "{1: 9.0-12.0}");
        time = new Time("MT 9 am–12 pm");
        assertEquals("day of week is wrong", time.toString(), "{12: 9.0-12.0}");
        time = new Time("Daily 9 am–12 pm");
        assertEquals("day of week is wrong", time.toString(), "{12345: 9.0-12.0}");
    }
    @Test
    public void militaryTime() {
        Time time = new Time("F 9 am–1 pm");
        assertEquals("military time conversion is wrong", time.toString(), "{5: 9.0-13.0}");

    }
    @Test
    public void comparisons() {
        Time time = new Time("F 9 am–1 pm");
        Time time2 = new Time("F 9 am–2 pm");
        assertEquals("comparison is wrong", time.compare(time2), true);
        time = new Time("F 9 am–1 pm");
        time2 = new Time("T 9 am–2 pm");
        assertEquals("comparison is wrong", time.compare(time2), false);
        time = new Time("F 9 am–1 pm");
        time2 = new Time("F 8 am–8:55 am");
        assertEquals("comparison is wrong", time.compare(time2), false);


    }
}