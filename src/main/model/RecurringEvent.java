package model;

import java.util.ArrayList;

public class RecurringEvent extends Event {
    ArrayList<ArrayList<Integer>> dayRecurrence = new ArrayList<>();
    ArrayList<Integer> monthRecurrence = new ArrayList<>();


    //Constructs a RecurringEvent
    //EFFECTS: DayEvent is assigned day, month, year for the first event in the recurrence
    //          - name and description assigned according to inputs
    public RecurringEvent(int day, int month, int year, String name, String description) {
        super(day,month,year,name,description);
        for (int x = month; x <= 12; x++) {
            ArrayList<Integer> daysInMonth = new ArrayList<>();
            for (int y = day; y < 30; y += 7) {
                daysInMonth.add(y);
            }
            dayRecurrence.add(daysInMonth);
            monthRecurrence.add(x);
        }
    }



    //EFFECTS: returns a list of the month and day of a recurring event
    public ArrayList<String> printDetails() {
        ArrayList<String> occurrencePrintLn = super.printBasicDetails();
        occurrencePrintLn.add("Type: Recurring Event");
        for (int mon : monthRecurrence) {
            for (ArrayList<Integer> dayMonth : dayRecurrence) {
                for (int d : dayMonth) {
                    occurrencePrintLn.add(Integer.toString(mon) + "/" + Integer.toString(d));
                }
            }
        }
        return occurrencePrintLn;
    }

    //EFFECTS: generates a save string for recurring event type
    public String toSaveString() {
        return super.makeSaveString("2");
    }
//    //EFFECTS: returns a string in the format for saving
//    public String toSaveString() {
//        return (Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year)
//                + "/" + name + "/" + description + "/2");
//    }
}


