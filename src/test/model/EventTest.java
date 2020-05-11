package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class EventTest {
    protected Planner planner;
    protected Event event;
    protected Event eventDup;
    protected Event eventDiffDay;
    protected Event eventDiffMonth;
    protected Event eventDiffYear;
    protected Event eventDiffName;
    protected Event eventDiffDesc;

    @BeforeEach
    void setup() {
        planner = new Planner("name");
    }

    @Test
    void testAddPlannerContains() {
        event.addPlanner(planner);
        assertEquals(event.getPlanners().size(), 1);
        event.addPlanner(planner);
        assertEquals(event.getPlanners().size(), 1);
    }

    @Test
    void testAddPlannerDoesNotContains() {
        event.addPlanner(planner);
        assertEquals(event.getPlanners().size(), 1);
        assertEquals(planner.getEvents().size(), 1);
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(event.equals(event));
    }

    @Test
    void testEqualsNull() {
        assertFalse(event.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(event.equals(5));
    }

    @Test
    void testSameFields() {
        assertTrue(event.equals(eventDup));
    }

    @Test
    void testDiffDay() {
        assertFalse(event.equals(eventDiffDay));
    }

    @Test
    void testDiffMonth() {
        assertFalse(event.equals(eventDiffMonth));
    }

    @Test
    void testDiffYear() {
        assertFalse(event.equals(eventDiffYear));
    }

    @Test
    void testDiffName() {
        assertFalse(event.equals(eventDiffName));
    }

    @Test
    void testDiffDesc() {
        assertFalse(event.equals(eventDiffDesc));
    }

    @Test
    void testHashCodeMatch() {
        assertEquals(event.hashCode(),eventDup.hashCode());
    }
}


