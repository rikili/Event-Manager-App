package model;

import java.util.ArrayList;

public class TaskList extends Event {
    ArrayList<String> tasks;
    ArrayList<String> taskTimes;

    public TaskList(int day, int month, int year, String name, String description) {
        super(day,month,year,name,description);
        tasks = new ArrayList<>();
        taskTimes = new ArrayList<>();
    }


    //MODIFIES: this
    //EFFECTS: adds a task to the TaskList event
    public void addTask(String taskName, String taskTime) {
        tasks.add(taskName);
        taskTimes.add(taskTime);
    }

    //MODIFIES: this
    //EFFECTS: removes a task from the TaskList event
    public void removeTask(String taskName) {
        int pos = tasks.indexOf(taskName);
        tasks.remove(pos);
        taskTimes.remove(pos);
    }

    public ArrayList<String> getTask() {
        return tasks;
    }

    public ArrayList<String> getTaskTimes() {
        return taskTimes;
    }

    //EFFECTS: prints the tasks in the event with it's name and time
    public ArrayList<String> printDetails() {
        ArrayList<String> taskPrintLns = super.printBasicDetails();
        taskPrintLns.add("Type: Task Event");
        for (int x = 0; x < tasks.size(); x++) {
            taskPrintLns.add(tasks.get(x) + " || " + taskTimes.get(x));
        }
        return taskPrintLns;
    }

    @Override
    //MODIFIES: this
    //EFFECTS: modifies name or date or adds tasks for the TaskList event
    public void edit(String choice, String edit) {
        if (choice.equals("1")) {
            this.name = edit;
        }
        if (choice.equals("2")) {
            String[] newDate = edit.split("/");
            this.month = Integer.parseInt(newDate[0]);
            this.day = Integer.parseInt(newDate[1]);
            this.year = Integer.parseInt(newDate[2]);
        }
        if (choice.equals("3")) {
            String[] newTask = edit.split("/");
            addTask(newTask[0], newTask[1]);
        }
    }


    //EFFECTS: generates a save string for the task event
    public String toSaveString() {
        return super.makeSaveString("3");
    }

//    //EFFECTS: returns a string in the format for saving
//    public String toSaveString() {
//        return (Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year)
//                + "/" + name + "/" + description + "/3");
//    }
}
