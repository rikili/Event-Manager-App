package model;


import model.exception.ExistenceException;
import model.exception.InvalidInputException;
import model.exception.WrongTypeException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Planner extends Observable implements Saveable, Loadable {
    private ArrayList<Event> events;
    private String name;
    private String testString = "Planner has a new event!";

    public Planner(String name) {
        events = new ArrayList<>();
        this.name = name;
    }

    //REQUIRES: a string that is of the format for making an event
    //EFFECTS: returns the name of the event
    public static String returnName(String eventDetail) {
        ArrayList<String> eventComponents = new ArrayList<>();
        for (String detail:eventDetail.split("/")) {
            eventComponents.add(detail);
        }
        return eventComponents.get(3);
    }

    //REQUIRES: a string that is of the format for making an event
    //MODIFIES: this
    //EFFECTS: Checks the eventDetail for proper inputs and creates an event
    public void addEvent(String eventDetail) throws InvalidInputException, WrongTypeException {
        ArrayList<String> eventComponents = new ArrayList<>();
        for (String detail:eventDetail.split("/")) {
            eventComponents.add(detail);
        }
        if (eventComponents.size() == 6) {
            int day = Integer.parseInt(eventComponents.get(0));
            int month = Integer.parseInt(eventComponents.get(1));
            int year = Integer.parseInt(eventComponents.get(2));
            String type = eventComponents.get(5);
            if (Integer.parseInt(type) <= 3 && Integer.parseInt(type) >= 1) {
                createEventOfType(day, month, year, eventComponents.get(3), eventComponents.get(4), type);
            } else {
                throw new WrongTypeException();
            }
        } else {
            throw new InvalidInputException();
        }
    }


    //MODIFIES: this
    //EFFECTS: checks if planner has the event, adds if not
    protected void addEvent(Event returnEvent) {
        if (!events.contains(returnEvent)) {
            notifyObs();
            events.add(returnEvent);
            returnEvent.addPlanner(this);
        }
    }

    //EFFECTS: notifies observers of changes to planner
    private void notifyObs() {
        Observer planAlert = new PlannerAlert("Test");
        addObserver(planAlert);
        setChanged();
        notifyObservers("test");
    }

    //MODIFIES: this
    //EFFECTS: Creates an event of type specified in the input and adds it to the events in the planner
    private Event createEventOfType(int day, int month, int year, String name, String description, String type) {
        Event returnEvent;
        if (type.equals("1")) {
            returnEvent = new DayEvent(day, month, year, name, description);
        } else if (type.equals("2")) {
            returnEvent = new RecurringEvent(day, month, year, name, description);
            RecurringEvent tempRec = new RecurringEvent(day, month, year, name, description);
        } else {
            returnEvent = new TaskList(day, month, year, name, description);
            TaskList tempTask = new TaskList(day, month, year, name, description);
        }
        addEvent(returnEvent);
        return returnEvent;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public String getName() {
        return name;
    }

    //EFFECTS: Returns true if the inputted string matches a name of an event, false otherwise
    public Boolean hasEvent(String eventName) {
        for (Event event : events) {
            if (eventName.equals(event.getName())) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: Saves each event in the planner to a specified file, if file doesn't exist, creates a new file of given
    //         input
    public void save(String filename) throws IOException {
        PrintWriter writer = new PrintWriter("data\\" + filename + ".txt", "UTF-8");
        for (Event event : events) {
            writer.println(event.toSaveString());
        }
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: Reads from the specified file and adds events read into the planner
    public List<String> load(String filename)
            throws IOException, ExistenceException, InvalidInputException, WrongTypeException {
        String strFile = "data\\" + filename + ".txt";
        File checkExist = new File(strFile);
        if (checkExist.exists()) {
            List<String> lines = Files.readAllLines(Paths.get(strFile));
            for (String line : lines) {
                this.addEvent(line);
            }
            return lines;
        } else {
            throw new ExistenceException();
        }
    }


    //EFFECTS: compares if the inputted object is equal to the called object by comparing the planner's names
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Planner planner = (Planner) o;

        return name.equals(planner.name);
    }

    //EFFECT: generates hash code for the planner object
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
