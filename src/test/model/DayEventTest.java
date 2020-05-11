package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DayEventTest extends EventTest {

    @BeforeEach
    void runBefore() {
        event = new DayEvent(8,8,1984,"Test Name","Test Desc.");
        eventDup = new DayEvent(8,8,1984,"Test Name","Test Desc.");
        eventDiffDay = new DayEvent(2,8,1984,"Test Name","Test Desc.");
        eventDiffMonth = new DayEvent(8,4,1984,"Test Name","Test Desc.");
        eventDiffYear = new DayEvent(8,8,1934,"Test Name","Test Desc.");
        eventDiffName = new DayEvent(8,8,1984,"Test","Test Desc.");
        eventDiffDesc = new DayEvent(8,8,1984,"Test Name","Test");
    }

    @Test
    void testGetDay() {
        assertEquals(event.getDay(), 8);
    }

    @Test
    void testGetMonth() {
        assertEquals(event.getMonth(), 8);
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
        assertEquals(event.toSaveString(), "8/8/1984/Test Name/Test Desc./1");
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
