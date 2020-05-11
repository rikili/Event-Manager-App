package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecurringEventTest extends EventTest{

    @BeforeEach
    void runBefore() {
        event = new RecurringEvent(1,12,1984,"Test Name","Test Desc.");
        eventDup = new RecurringEvent(1,12,1984,"Test Name","Test Desc.");
        eventDiffDay = new RecurringEvent(2,12,1984,"Test Name","Test Desc.");
        eventDiffMonth = new RecurringEvent(1,4,1984,"Test Name","Test Desc.");
        eventDiffYear = new RecurringEvent(1,12,1934,"Test Name","Test Desc.");
        eventDiffName = new RecurringEvent(1,12,1984,"Test","Test Desc.");
        eventDiffDesc = new RecurringEvent(1,12,1984,"Test Name","Test");
    }

    @Test
    void testGetDay() {
        assertEquals(event.getDay(), 1);
    }

    @Test
    void testGetOccurrences() {
        ArrayList<String> test = new ArrayList<>();
        test.add("12/1");
        test.add("12/8");
        test.add("12/15");
        test.add("12/22");
        test.add("12/29");
    }

    @Test
    void testGetMonth() {
        assertEquals(event.getMonth(), 12);
    }

    @Test
    void testGetYear() {
        assertEquals(event.getYear(), 1984);
    }

    @Test
    void testGetName(){
        assertEquals(event.getName(), "Test Name");
    }

    @Test
    void testGetDescription() {
        assertEquals(event.getDescription(), "Test Desc.");
    }

    @Test
    void testToString() {
        assertEquals(event.toSaveString(), "12/1/1984/Test Name/Test Desc./2");
    }

    @Test
    void testEditNameChange() {
        event.edit("1","change");
        assertEquals(event.getName(),"change");
    }

    @Test
    void testEditDateChange() {
        event.edit("2", "1/1/1");
        assertEquals(event.getDay(), 1);
        assertEquals(event.getMonth(),1);
        assertEquals(event.getYear(),1);
    }
}
