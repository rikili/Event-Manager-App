package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest extends EventTest{
    private TaskList taskList;

    @BeforeEach
    void runBefore() {
        taskList = new TaskList(8,8,1984,"Test Name","Test Desc.");
        event = new TaskList(8,8,1984,"Test Name","Test Desc.");
        eventDup = new TaskList(8,8,1984,"Test Name","Test Desc.");
        eventDiffDay = new TaskList(2,8,1984,"Test Name","Test Desc.");
        eventDiffMonth = new TaskList(8,4,1984,"Test Name","Test Desc.");
        eventDiffYear = new TaskList(8,8,1934,"Test Name","Test Desc.");
        eventDiffName = new TaskList(8,8,1984,"Test","Test Desc.");
        eventDiffDesc = new TaskList(8,8,1984,"Test Name","Test");
    }

    @Test
    void testGetDay() {
        assertEquals(taskList.getDay(), 8);
    }

    @Test
    void testGetMonth() {
        assertEquals(taskList.getMonth(), 8);
    }

    @Test
    void testGetYear() {
        assertEquals(taskList.getYear(), 1984);
    }

    @Test
    void testGetName(){
        assertEquals(taskList.getName(), "Test Name");
    }

    @Test
    void testGetDescription() {
        assertEquals(taskList.getDescription(), "Test Desc.");
    }

    @Test
    void testToString() {
        assertEquals(taskList.toSaveString(), "8/8/1984/Test Name/Test Desc./3");
    }

    @Test
    void testAddTask() {
        taskList.addTask("Task1", "12:00");
        assertEquals(taskList.getTask().get(0),"Task1");
        assertEquals(taskList.getTaskTimes().get(0),"12:00");
    }

    @Test
    void testRemoveTask() {
        taskList.addTask("Task2", "16:00");
        taskList.addTask("Task1", "12:00");
        taskList.removeTask("Task2");
        assertEquals(taskList.getTask().get(0),"Task1");
        assertEquals(taskList.getTaskTimes().get(0),"12:00");
    }

    @Test
    void testPrintDetails() {
        ArrayList<String> testActual = new ArrayList<>();
        testActual.add("Task1 || 12:00");
        testActual.add("Task2 || 16:00");
        taskList.addTask("Task1","12:00");
        taskList.addTask("Task2", "16:00");
        assertEquals(taskList.printDetails().get(0), testActual.get(0));
        assertEquals(taskList.printDetails().get(1), testActual.get(1));
    }

    @Test
    void testEditNameChange() {
        taskList.edit("1","change");
        assertEquals(taskList.getName(),"change");
    }

    @Test
    void testEditDateChange() {
        taskList.edit("2", "1/1/1");
        assertEquals(taskList.getDay(), 1);
        assertEquals(taskList.getMonth(),1);
        assertEquals(taskList.getYear(),1);
    }

    @Test
    void testEditTasks() {
        taskList.edit("3","Task1/12:00");
        assertEquals(taskList.getTask().get(0),"Task1");
    }
}
