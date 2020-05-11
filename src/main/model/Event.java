package model;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Event {

    protected int month;
    protected int day;
    protected int year;
    protected String name;
    protected String description;
    protected ArrayList<Planner> planners = new ArrayList<>();

    public Event(int day, int month, int year, String name, String description) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getYear() {
        return year;
    }

    public ArrayList<Planner> getPlanners() {
        return planners;
    }

    //MODIFIES: this
    //EFFECTS: modifies name or date of an event
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
    }

    //EFFECTS: adds planner in planners if it is not already in planners
    public void addPlanner(Planner planner) {
        if (!planners.contains(planner)) {
            planners.add(planner);
            planner.addEvent(this);
        }
    }



    //EFFECTS: compares if two objects are events if they have matching info (day, month, year, name, description)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return matchingFields(event);
    }

    private boolean matchingFields(Event event) {
        if (month != event.month) {
            return false;
        }
        if (day != event.day) {
            return false;
        }
        if (year != event.year) {
            return false;
        }
        if (!name.equals(event.name)) {
            return false;
        } else {
            return description.equals(event.description);
        }
    }

    //EFFECTS: generates hash code for an event object
    @Override
    public int hashCode() {
        int result = month;
        result = 31 * result + day;
        result = 31 * result + year;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    //EFFECTS: creates a list with each index being a piece of basic info for the event
    //         info includes (name, description, month, day, year)
    protected ArrayList<String> printBasicDetails() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Name: " + name);
        temp.add("Description: " + description);
        temp.add("Month: " + month);
        temp.add("Day: " + day);
        temp.add("Year: " + year);
        return temp;
    }

    public abstract ArrayList<String> printDetails();

    public abstract String toSaveString();

    //EFFECTS: returns a string in the format for saving
    protected String makeSaveString(String typeNum) {
        return (Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year)
                + "/" + name + "/" + description + "/" + typeNum);
    }

//    public abstract ArrayList<String> printDetails();
}
