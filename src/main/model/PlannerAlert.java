package model;

import java.util.Observable;
import java.util.Observer;

public class PlannerAlert implements Observer {
    private String name;

    public PlannerAlert(String name) {
        this.name = name;
    }

    //EFFECTS: sends a message when called
    public void update(Observable o, Object args) {
        System.out.println("Planner has a new event!");
    }
}
