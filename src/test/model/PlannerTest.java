package model;

import model.exception.ExistenceException;
import model.exception.InvalidInputException;
import model.exception.WrongTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlannerTest {
    private Planner planner;
    private Planner plannerDup;
    private Planner myOtherPlanner;
    private Event temp1;
    private Event temp1dup;
    private Event temp2;

    @BeforeEach
    void runBefore() {
        planner = new Planner("test");
        plannerDup = new Planner("test");
        myOtherPlanner = new Planner("test2");
        temp1 = new DayEvent(2,2,2,"n","d");
        temp1dup = new DayEvent(2,2,2,"n","d");
        temp2 = new DayEvent(3,2,3,"n","d");
    }

//    @Test
//    void testGetEvents() {
//
//    }


    @Test
    void testGetName() {
        assertEquals(planner.getName(), "test");
    }

    @Test
    void testAddEvent() {
        try {
            planner.addEvent("6/6/1200/Name/Description/1");
        } catch (InvalidInputException e) {
            fail("Invalid Input");
        } catch (WrongTypeException e) {
            fail("Wrong Type");
        }
        ArrayList<Event> events = planner.getEvents();
        assertEquals(events.size(),1);
    }

    @Test
    void testAddDayEventType() {
        try {
            planner.addEvent("8/8/1984/Name/Description/1");
        } catch (InvalidInputException e) {
            fail("Invalid Input");
        } catch (WrongTypeException e) {
            fail("Wrong Type");
        }
        assertTrue(planner.getEvents().get(0) instanceof DayEvent);
    }

    @Test
    void testAddRecurringType() {
        try {
            planner.addEvent("8/8/1984/Name/Description/2");
        } catch (InvalidInputException e) {
            fail("Invalid Input");
        } catch (WrongTypeException e) {
            fail("Wrong Type");
        }
        assertTrue(planner.getEvents().get(0) instanceof RecurringEvent);
    }

    @Test
    void testAddTaskType() {
        try {
            planner.addEvent("8/8/1984/Name/Description/3");
        } catch (InvalidInputException e) {
            fail("Invalid Input");
        } catch (WrongTypeException e) {
            fail("Wrong Type");
        }
        assertTrue(planner.getEvents().get(0) instanceof TaskList);
    }

    @Test
    void testAddEventWrongTypeOver() {
        try {
            planner.addEvent("8/8/1984/Name/Description/20");
            fail();
        } catch (WrongTypeException e) {
        } catch (InvalidInputException e) {
            fail();
        }

    }

    @Test
    void testAddEventWrongTypeUnder() {
        try {
            planner.addEvent("8/8/1984/Name/Description/0");
            fail();
        } catch (WrongTypeException e) {
        } catch (InvalidInputException e) {
            fail();
        }

    }

    @Test
    void testAddEventInputLineTooLong() {
        try {
            planner.addEvent("8/8/8/8/Name/Description/4/2/5");
            fail();
        } catch (InvalidInputException e) {
        } catch (WrongTypeException e) {
            fail();
        }
    }

    @Test
    void testAddEventInputLineTooShort() {
        try {
            planner.addEvent("8/8/Name/Description");
            fail();
        } catch (InvalidInputException e) {
        } catch (WrongTypeException e) {
            fail();
        }
    }

    @Test
    void testAddEventAddContains() {
        Event temp = new DayEvent(2, 2, 2, "n", "d");
        planner.addEvent(temp);
        assertEquals(planner.getEvents().size(), 1);
        assertEquals(temp.getPlanners().size(), 1);
        planner.addEvent(new DayEvent(2, 2, 2, "n", "d"));
        assertEquals(planner.getEvents().size(), 1);
    }

    @Test
    void testAddEventAddNotContains() {
        assertEquals(planner.getEvents().size(), 0);
        Event temp = new DayEvent(2, 2, 2, "n", "d");
        planner.addEvent(temp);
        assertEquals(planner.getEvents().size(), 1);
        assertEquals(temp.getPlanners().size(), 1);
    }


    @Test
    void testHasEventTrue() {
        try {
            planner.addEvent("8/8/1984/Name/Description/1");
        } catch (InvalidInputException e) {
            fail("Invalid Input");
        } catch (WrongTypeException e) {
            fail("Wrong Type");
        }
        assertTrue(planner.hasEvent("Name"));
    }

    @Test
    void testHasEventFalse() {
        try {
            planner.addEvent("8/8/1984/Name/Description/1");
        } catch (InvalidInputException e) {
            fail("Invalid Input");
        } catch (WrongTypeException e) {
            fail("Wrong Type");
        }
        assertFalse(planner.hasEvent("hello"));
    }

    @Test
    void testLoadNoExistence() {
        try {
            planner.load("nullFile");
            fail();
        } catch (ExistenceException e) {
        } catch (IOException e) {
            fail();
        } catch (WrongTypeException e) {
            fail();
        } catch (InvalidInputException e) {
            fail();
        }
    }

    @Test
    void testLoadWrongInput() {
        try {
            planner.load("testLoadInvalidInput");
            fail();
        } catch (InvalidInputException e) {
        } catch (WrongTypeException e) {
            fail();
        } catch (ExistenceException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testLoadWrongType() {
        try {
            planner.load("testLoadWrongType");
            fail();
        } catch (InvalidInputException e) {
            fail();
        } catch (WrongTypeException e) {
        } catch (ExistenceException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testLoadExists() {
        try {
            List<String> loadedLines = planner.load("testLoad");
            assertEquals(loadedLines.get(0), "6/7/1990/loadTest/loadTestDesc/1");
            Event event = planner.getEvents().get(0);
            assertEquals(event.getDay(), 6);
            assertEquals(event.getMonth(), 7);
            assertEquals(event.getYear(), 1990);
            assertEquals(event.getName(), "loadTest");
            assertEquals(event.getDescription(), "loadTestDesc");
        } catch (ExistenceException e) {
            fail();
        } catch (InvalidInputException e) {
            fail();
        } catch (WrongTypeException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testExistsEmptyFile() {
        try {
            List<String> loadedLines = planner.load("testEmptyFile");
            assertEquals(planner.getEvents().size(), 0);
            assertEquals(loadedLines.size(), 0);
        } catch (ExistenceException e) {
            fail();
        } catch (InvalidInputException e) {
            fail();
        } catch (WrongTypeException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }


    @Test
    void testSave() {
        ArrayList<String> testNames = new ArrayList<>();
        testNames.add("Name1");
        testNames.add("Name2");
        try {
            planner.addEvent("8/8/1990/Name1/Desc1/1");
            planner.addEvent("7/7/1991/Name2/Desc2/1");
            planner.save("testOutput");
            myOtherPlanner.load("testOutput");
        } catch (InvalidInputException e) {
            fail();
        } catch (ExistenceException e) {
            fail();
        } catch (WrongTypeException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
        assertTrue(myOtherPlanner.hasEvent(planner.getEvents().get(0).getName()));
        assertTrue(myOtherPlanner.hasEvent(planner.getEvents().get(1).getName()));

    }

    @Test
    void testEqualsSameObject() {
        assertTrue(planner.equals(planner));
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(planner.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(planner.equals(9));
    }

    @Test
    void testEqualsSameParameters() {
        assertTrue(planner.equals(plannerDup));
    }

    @Test
    void testEqualsDifferentParameters() {
        assertFalse(planner.equals(myOtherPlanner));
    }

    @Test
    void testHashCode() {
        assertEquals(planner.hashCode(), plannerDup.hashCode());
    }
}
