package model;

import java.util.ArrayList;

public class DayEvent extends Event {

    //Constructs a DayEvent
    //EFFECTS: DayEvent is assigned day, month, year, name, and description based on params
    public DayEvent(int day, int month, int year, String name, String description) {
        super(day,month,year,name,description);
    }


    //EFFECTS: generates a save string for the dayEvent type
    public String toSaveString() {
        return super.makeSaveString("1");
    }

    //EFFECTS: returns a list of the details of the event, info includes day,month, year, name, and description
    public ArrayList<String> printDetails() {
        return super.printBasicDetails();
    }

//    @Override
//    //EFFECTS: returns a string with all the details of an event
//    public String toSaveString() {
//        return (Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year)
//                + "/" + name + "/" + description + "/1");
//    }

//    //EFFECTS: WIP function
//    public ArrayList<String> printDetails() {
//        ArrayList<String> temp = new ArrayList<>();
//        temp.add("not implemented yet");
//        return temp;
//    }


}