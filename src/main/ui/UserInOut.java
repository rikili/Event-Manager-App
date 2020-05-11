package ui;

import model.*;
import model.Event;
import model.exception.ExistenceException;
import model.exception.InvalidInputException;
import model.exception.WrongTypeException;
import network.WebPageRead;

import java.awt.*;
import java.io.IOException;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.util.List;


//Code for Swing Components and Attributes referenced from: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class UserInOut extends JFrame {
    //Commands
    protected static final String newPlanner = "newPlan";
    protected static final String makePlanner = "makePlan";
    protected static final String netTry = "netTry";
    protected static final String selectPlanner = "selectPlanner";
    protected static final String makeEvent = "makeEvent";
    protected static final String editEvent = "editEvent";
    protected static final String confirmEvent = "confirmEvent";
    protected static final String changeName = "changeName";
    protected static final String changeDate = "changeDate";
    protected static final String viewDetails = "viewDetail";
    protected static final String savePlanner = "savePlanner";
    protected static final String loadPlanner = "loadPlanner";
    protected static final String submitSave = "submitSave";
    protected static final String submitLoad = "submitLoad";

    private Map<String, Planner> plannerList;
    private Map<String, JSwingList> plannerEvents;
    // private Map<String, JFrame> frames;
    private ArrayList<String> frameKeys;
    private ArrayList<String> keys;
    protected Planner curPlanner;
    private Scanner scanner;
    private String jsonLine;
    private JSONObject stats;
    private JSwingList uiPlannerJSwingList;

    //Constructs a UserInOut object
    //EFFECTS: declares fields for UserInOut
    public UserInOut() {
        // frames = new HashMap<>();
        frameKeys = new ArrayList<>();
        curPlanner = null;
        keys = new ArrayList<>();
        plannerList = new HashMap<>();
        plannerEvents = new HashMap<>();
        scanner = new Scanner(System.in);
        uiPlannerJSwingList = new JSwingList(this);

    }

    //MODIFIES: this
    //EFFECTS: sets up json file from web giving weather details
    private void runWriteToFile() {
        try {
            jsonLine = WebPageRead.readJson("https://api.openweathermap.org/data/2.5/weather?q=Vancouver,ca&mode=json&APPID=7dab149f0c4af9b749526c38263240a3");
            parseToJsonArray();
        } catch (IOException e) {
            System.out.println("The call has failed");
        } catch (JSONException e) {
            System.out.println("The call has run into a JSONException");
        }
    }

    //MODIFIES: this
    //EFFECTS: creates JSONObject from jsonLine
    private void parseToJsonArray() throws JSONException {
        stats = new JSONObject(jsonLine);
        parsedInfo();
    }

    //EFFECTS: generates window showing the pulled results from the web
    private void parsedInfo() throws JSONException {
        JFrame weather = new JFrame("Weather in Vancouver");
        JPanel mainPanel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.add(new JLabel("Current Weather Conditions in Vancouver: "));
        panel1.add(new JLabel(stats.getJSONArray("weather").getJSONObject(0).getString("main")));
        panel2.add(new JLabel("Weather Description : "));
        panel2.add(new JLabel(stats.getJSONArray("weather").getJSONObject(0).getString("description")));
        mainPanel.add(panel1);
        mainPanel.add(panel2);
        weather.add(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        weather.setResizable(false);
        weather.pack();
        weather.setVisible(true);
    }


    //EFFECTS: generates window that displays planners and 3 options as buttons:
    //         1. make a new planner
    //         2. run the web reading functions
    //         3. open the selected planner
    public void run() {
        //initFrames();
        JFrame main = new JFrame("Planner Program");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPane = new JPanel();
        JPanel buttonPane = new JPanel();
        JPanel listPane = new JPanel();

        listPane.add(new JLabel("Planners:"));
        listPane.add(uiPlannerJSwingList);

        makeBasicButton("Make a New Planner", newPlanner, buttonPane);
        makeBasicButton("Current Weather Conditions", netTry, buttonPane);
        makeBasicButton("Select Current Planner", selectPlanner, buttonPane);

        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        mainPane.add(buttonPane);
        mainPane.add(listPane);

        main.add(mainPane);
        main.setResizable(false);
        main.pack();
        main.setVisible(true);
    }

//    private void initFrames() {
//        frameKeys.addAll(Arrays.asList("MakePlanner","OpenPlanner","MakeEvent"));
//        frameKeys.addAll(Arrays.asList("EditEvent","DetailEvent"));
//        frames.put("Main", new JFrame("Planner Program"));
//        frames.put("MakePlanner", new JFrame("New Planner"));
//        frames.put("OpenPlanner", new JFrame("Planner"));
//        frames.put("MakeEvent", new JFrame("New Event"));
//        frames.put("EditEvent", new JFrame("Edit Event"));
//        frames.put("DetailEvent", new JFrame("Event Details"));
//        for (int x = 0; x < frameKeys.size(); x++) {
//            frames.get(frameKeys.get(x)).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        }
//    }

    //EFFECTS: generates window that asks for a new planner's name
    private void createNewPlanner() {
        JFrame newPlanner = new JFrame("New Planner");
        JPanel makePlan = new JPanel();
        TextField textBar = new TextField(this, 30);
        makePlan.add(new Button("Create Planner", makePlanner, textBar, this));
        makePlan.add(new JLabel("Name of Planner: "));
        makePlan.add(textBar);
        newPlanner.add(makePlan);
        newPlanner.pack();
        newPlanner.setResizable(false);
        newPlanner.setVisible(true);
    }

    //EFFECTS: commits the name given by user to generate a new planner
    public void plannerSubmit(String text) {
        if (!keySearch(text)) {
            plannerList.put(text, new Planner(text));
            keys.add(text);
            uiPlannerJSwingList.addElement(text);
            plannerEvents.put(text, new JSwingList(this));
            System.out.println("added " + text);
        }
    }

    //EFFECTS: returns true if name is a name for a planner, false if not
    private boolean keySearch(String name) {
        for (String key : keys) {
            if (plannerList.get(key).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: changes curPlanner to be the selected planner
    private void plannerSelect() {
        curPlanner = plannerList.get(uiPlannerJSwingList.getSelected());
        openPlanner();
    }

    //EFFECTS: generates a window to show the details of a planner
    private void openPlanner() {
        JFrame planner = new JFrame("Planner");
        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JSwingList eventJSwingList = plannerEvents.get(curPlanner.getName());
        makeBasicButton("Add Event", makeEvent, buttonPanel);
        makeBasicButton("Edit Event", editEvent, buttonPanel);
        makeBasicButton("See Details", viewDetails, buttonPanel);
        makeBasicButton("Save", savePlanner, buttonPanel);
        makeBasicButton("Load", loadPlanner, buttonPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        panel.add(buttonPanel);
        panel.add(eventJSwingList);
        planner.add(panel);
        planner.pack();
        planner.setResizable(false);
        planner.setVisible(true);
    }

    //EFFECTS: calls a function that corresponds to the corresponding button command
    public void actionForButton(ActionEvent e) {
        switch (e.getActionCommand()) {
            case newPlanner:
                createNewPlanner();
                break;
            case netTry:
                runWriteToFile();
                break;
            case selectPlanner:
                plannerSelect();
                break;
            case makeEvent:
                createEvent();
                break;
            default:
                actionForButtonExtend(e);
        }
    }

    //EFFECTS: calls a function that corresponds to the corresponding button command
    private void actionForButtonExtend(ActionEvent e) {
        switch (e.getActionCommand()) {
            case editEvent:
                newEditEvent();
                break;
            case viewDetails:
                displayDetails();
                break;
            case savePlanner:
                callSave();
                break;
            case loadPlanner:
                callLoad();
                break;
            default:
        }
    }

    //EFFECTS: generates a window that asks user for name to generate a save file for the selected planner
    private void callSave() {
        JFrame save = new JFrame("Save Planner");
        JPanel main = new JPanel();
        TextField inp = new TextField(this, 30);
        main.add(new JLabel("Input Filename To Save: "));
        main.add(inp);
        main.add(new Button("Submit", submitSave, inp, this));
        save.add(main);
        save.pack();
        save.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: takes in user input of the filename to save to
    protected void callToSave(String filename) throws IOException {
        curPlanner.save(filename);
    }

    //EFFECTS: generates window asking user for file name to load from, places loaded events into selected planner
    private void callLoad() {
        JFrame load = new JFrame("Load Planner");
        JPanel main = new JPanel();
        TextField inp = new TextField(this, 30);
        main.add(new JLabel("Input Filename To Load: "));
        main.add(inp);
        main.add(new Button("Submit", submitLoad, inp, this));
        load.add(main);
        load.pack();
        load.setVisible(true);

    }

    //EFFECTS: takes in user input of the file to load from
    protected void callToLoad(String filename) throws IOException {
        try {
            curPlanner.load(filename);
        } catch (ExistenceException e) {
            System.out.println("The file specified does not exist");
        } catch (InvalidInputException f) {
            System.out.println("The file specified has an invalid entry");
        } catch (WrongTypeException g) {
            System.out.println("The file specified has a wrong type in the entry");
        }
        displayLoad();
    }

    //EFFECTS: displays the loaded events onto UI list
    private void displayLoad() {
        JSwingList tempList = plannerEvents.get(curPlanner.getName());
        ArrayList<String> eventNames = new ArrayList<>();
        for (Event e : curPlanner.getEvents()) {
            eventNames.add(e.getName());
        }
        for (int x = 0; x < eventNames.size(); x++) {
            if (!tempList.listModel.contains(eventNames.get(x))) {
                tempList.addElement(eventNames.get(x));
            }
        }
    }

    //EFFECTS: generates a window that asks for users inputs to make a new event
    private void createEvent() {
        JPanel panel = new JPanel();
        JFrame event = new JFrame("New Event");
        TextField name = new TextField(this, 20);
        TextField desc = new TextField(this, 20);
        TextField date = new TextField(this, 20);
        SComboBox typeTemp = new SComboBox(new ArrayList<>(Arrays.asList("1", "2", "3")));
        TextField type = new TextField(this, 2);
        type.setText(typeTemp.getSelectedValue());
        ArrayList<TextField> fields = new ArrayList<>(Arrays.asList(date, name, desc, type));


        panel.add(makePanelLabelText(new JLabel("Name: "), name));
        panel.add(makePanelLabelText(new JLabel("Desc: "), desc));
        panel.add(makePanelLabelText(new JLabel("Date: "), date));
        panel.add(makePanelLabelCombo(new JLabel("Type: "), typeTemp));
        panel.add(new Button("Submit", confirmEvent, fields, typeTemp, this));

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        event.add(panel);
        event.pack();
        event.setVisible(true);
    }

    //REQUIRES: textFields.size() > 0;
    //EFFECTS: creates event by user's inputs
    public void callCreateEvent(ArrayList<TextField> textFields, SComboBox comboBox) {
        textFields.get(textFields.size() - 1).setText(comboBox.getSelectedValue());
        String temp = "";
        for (TextField field : textFields) {
            temp = temp + field.getText() + "/";
        }
        makeEvent(temp.substring(0, temp.length() - 1));
    }

    //MODIFIES: this
    //EFFECTS: adds event to planner according to input
    public void makeEvent(String eventDetail) {
        JSwingList curJSwingList = plannerEvents.get(curPlanner.getName());
        String eventName = Planner.returnName(eventDetail);
        try {
            if (notInCurPlanner(eventName)) {
                curJSwingList.addElement(eventName);
            }
            curPlanner.addEvent(eventDetail);
        } catch (InvalidInputException e) {
            System.out.println("The input entered has an invalid amount of inputs");
        } catch (WrongTypeException f) {
            System.out.println("The event type inputted is invalid");
        }
    }

    //EFFECTS: returns true if the given name is the name for an event in curPlanner, false o/w
    private boolean notInCurPlanner(String name) {
        for (Event e : curPlanner.getEvents()) {
            if (e.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: generates a new window that gives edit options to selected event
    private void newEditEvent() {
        JFrame edit = new JFrame("Edit Event");
        JPanel panel = new JPanel();
        TextField eventName = new TextField(this, 20);
        TextField eventDate = new TextField(this, 20);
        panel.add(makePanelLabelText(new JLabel("Change Name to: "), eventName));
        panel.add(new Button("Submit", changeName, eventName, this));
        panel.add(makePanelLabelText(new JLabel("Change Date to: "), eventDate));
        panel.add(new Button("Submit", changeDate, eventDate, this));
        edit.add(panel);
        edit.pack();
        edit.setVisible(true);
    }

    //REQUIRES: text.length() > 0
    //MODIFIES: this
    //EFFECTS: changes the name of the selected event
    public void eventNameChange(String text) {
        JSwingList curJSwingList = plannerEvents.get(curPlanner.getName());
        String selEvent = curJSwingList.getSelected();
        curPlanner.getEvents().get(indexOfEvent(selEvent)).edit("1", text);

        int index = curJSwingList.list.getSelectedIndex();
        curJSwingList.listModel.remove(index);
        curJSwingList.listModel.insertElementAt(text, index);
        curJSwingList.list.setSelectedIndex(index);
        curJSwingList.list.ensureIndexIsVisible(index);
    }

    //REQUIRES: name is a name of an event in the planner
    //EFFECTS: searches curPlanner for an event that has name of name and returns its index
    private int indexOfEvent(String name) {
        int counter = 0;
        for (Event e : curPlanner.getEvents()) {
            if (e.getName().equals(name)) {
                break;
            } else {
                counter++;
            }
        }
        return counter;
    }

    //REQUIRES: text is of a particular format (day/month/year)
    //MODIFIES: this
    //EFFECTS: changes the date on the selected event
    public void eventDateChange(String text) {
        JSwingList curJSwingList = plannerEvents.get(curPlanner.getName());
        String selEvent = curJSwingList.getSelected();
        curPlanner.getEvents().get(indexOfEvent(selEvent)).edit("2", text);
    }

    //EFFECTS: generates a window that displays the selected event details
    public void displayDetails() {
        JFrame display = new JFrame("Event Details");
        Dimension dim = new Dimension(300, 200);
        display.setPreferredSize(dim);
        JSwingList curJSwingList = plannerEvents.get(curPlanner.getName());
        String selEvent = curJSwingList.getSelected();
        ArrayList<String> details = curPlanner.getEvents().get(indexOfEvent(selEvent)).printDetails();
        JTextArea displayArea = new JTextArea();
        for (String detail : details) {
            displayArea.append(detail + '\n');
        }
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(300, 700));
        display.add(scrollPane);
        display.pack();
        display.setVisible(true);


    }

    //EFFECTS: creates a panel with the given label and comboBox
    private JPanel makePanelLabelCombo(JLabel label, SComboBox comboBox) {
        JPanel tempPanel = new JPanel();
        tempPanel.add(label);
        tempPanel.add(comboBox);
        return tempPanel;
    }

    //EFFECTS: generates a JPanel with the given label and textfield
    private JPanel makePanelLabelText(JLabel label, TextField text) {
        JPanel temp = new JPanel();
        temp.add(label);
        temp.add(text);
        return temp;
    }

    //MODIFIES: this
    //EFFECTS: generates a button of a given command and places it on the given panel
    private void makeBasicButton(String name, String command, JPanel pane) {
        Button button = new Button(name, command, this);
        button.setOpaque(true);
        pane.add(button);
    }


    // OLD METHODS BELOW --------------------------------------------------------------------------------

    //Code Sourced from LittleLoggingCalculator (URL: https://github.students.cs.ubc.ca/CPSC210/B04-SimpleCalculatorSolutionLecLab/blob/master/src/starter/LoggingCalculator.java)
    //MODIFIES: this
    //EFFECTS: Lists and records operations and choices from the user
//
//    public void run() throws IOException {
//        while (isRunning) {
//            System.out.println("Choose from the following operations:");
//            System.out.println(" (1) Make new planner | (2) Select a planner | (3) runWriteToFile | (4) Quit");
//            String inp = scanner.nextLine();
//            handlePlanner(inp);
//            while (curPlanner != null) {
//                String input;
//                System.out.println("Choose from the following operations by entering corresponding number:");
//                System.out.println("(1) Make new event | (2) Return all event names "
//                        + "| (3) Check for event by name | (4) Save | (5) Load | (6) Quit | (7) Edit | (8) Details");
//                input = scanner.nextLine();
//                chooseOption(input);
//                System.out.println("--------------------------------------------------");
//            }
//        }
//    }
//
//    //REQUIRES: input of "1", "2", "3"
//    //MODIFIES: this
//    //EFFECTS: Calls the appropriate methods for the given user input
//    private void handlePlanner(String option) {
//        switch (option) {
//            case "1":
//                makeNewPlanner();
//                break;
//            case "2":
//                printPlannerList();
//                selectPlanner();
//                break;
//            case "3":
//                runWritetoFile();
//                break;
//            case "4":
//                isRunning = false;
//                break;
//            default:
//                System.out.println("That is not an available option");
//        }
//
//    }
//
//    private void runWritetoFile() {
//        try {
//            jsonLine = WebPageRead.readJson("https://api.openweathermap.org/data/2.5/weather?q=Vancouver,ca&mode=json&APPID=7dab149f0c4af9b749526c38263240a3");
//            parseToJsonArray();
//        } catch (IOException e) {
//            System.out.println("The call has failed");
//        } catch (JSONException e) {
//            System.out.println("The call has run into a JSONException");
//        }
//    }
//
//
//    private void makeNewPlanner() {
//        System.out.println("What would you like to call this new planner?");
//        String name = scanner.nextLine();
//        if (!keySearch(name)) {
//            plannerList.put(curKey, new Planner(name));
//            curKey++;
//        }
//    }
//
//
//    private void printPlannerList() {
//        int count = 0;
//        for (int x = 0; x < curKey; x++) {
//            System.out.print("(" + Integer.toString(count) + ")" + " ");
//            System.out.print(plannerList.get(x).getName() + " || ");
//            count++;
//        }
//        System.out.println();
//    }
//
//    private void selectPlanner() {
//        System.out.println("please input the index of the planner to select");
//        String select = scanner.nextLine();
//        curPlanner = plannerList.get(Integer.parseInt(select));
//    }
//
//    //REQUIRES: input of "1", "2", or "3"
//    //MODIFIES: this
//    //EFFECTS: Calls the appropriate methods for a given user choice of operation.
//    //         1 for making an event
//    //         2 for printing names of all events
//    //         3 for searching for the existence of an event
//    //         4 to save the planner to a specified file
//    //         5 to load a planner from the specified file
//    //         6 to quit
//    //         7 to edit a particular event
//    //         8 to print out details of a particular event
//    private void chooseOption(String input) throws IOException {
//        if (input.equals("1")) {
//            optionOne();
//        } else if (input.equals("2")) {
//            System.out.println(printEvents());
//        } else if (input.equals("3")) {
//            optionThree();
//        } else if (input.equals("4")) {
//            callToSave();
//        } else if (input.equals("5")) {
//            callToLoad();
//        } else if (input.equals("6")) {
//            System.out.println("Moving back to Planner selection");
//            curPlanner = null;
//        } else if (input.equals("7")) {
//            editEvent();
//        } else if (input.equals("8")) {
//            detailsEvent();
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: adds event to planner according to input
//    private void optionOne() {
//        System.out.println("Please enter the details for the event by (Date/Month/Year/Name/Description/Type)");
//        System.out.println("Event Types consist of [1] Day Event [2] Recurring Event [3] Task List");
//        String eventDetail = scanner.nextLine();
//        try {
//            curPlanner.addEvent(eventDetail);
//        } catch (InvalidInputException e) {
//            System.out.println("The input entered has an invalid amount of inputs");
//        } catch (WrongTypeException f) {
//            System.out.println("The event type inputted is invalid");
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: Returns list of all event names
//    private String printEvents() {
//        String returnList = "|";
//        ArrayList<Event> events = curPlanner.getEvents();
//        for (Event event : events) {
//            returnList = returnList + event.getName() + "|";
//        }
//        return returnList;
//    }
//
//    //MODIFIES: this
//    //EFFECTS: adds event to planner according to input
//    private void optionThree() {
//        System.out.println("What is the name of the event you are looking for?");
//        String eventName = scanner.nextLine();
//        System.out.println(curPlanner.hasEvent(eventName) ? "The Event Exists" : "The Event Does Not Exist");
//    }
//
//
//    //EFFECTS: takes in user input of the filename to save to
//    private void callToSave()  throws IOException {
//        System.out.println("Please enter filename to save to:");
//        String filename = scanner.nextLine();
//        curPlanner.save(filename);
//    }
//
//    //EFFECTS: takes in user input of the file to load from
//    private void callToLoad() throws IOException {
//        try {
//            System.out.println("Please enter the name of the file to load:");
//            String filename = scanner.nextLine();
//            curPlanner.load(filename);
//        } catch (ExistenceException e) {
//            System.out.println("The file specified does not exist");
//        } catch (InvalidInputException f) {
//            System.out.println("The file specified has an invalid entry");
//        } catch (WrongTypeException g) {
//            System.out.println("The file specified has a wrong type in the entry");
//        }
//        //return planner.load(filename);
//    }
//
//    //EFFECTS: Edits Name, Date, or tasks (For a taskList type) of a particular event in planner
//    private void editEvent() {
//        System.out.println(printEvents());
//        System.out.println("Please give the index of the task you wish to edit");
//        String index = scanner.nextLine();
//        System.out.println("What would you like to edit?");
//        System.out.println("[1] Name | [2] Date | [3] if TaskList, the tasks");
//        String choice = scanner.nextLine();
//        try {
//            handleEditChoice(choice, index);
//        } catch (WrongTypeException e) {
//            System.out.println("The selected object is not a TaskList");
//        } catch (InvalidInputException e) {
//            System.out.println("The selected option does not exist");
//        } finally {
//            System.out.println("Event Edit Complete");
//        }
//        //planner.getEvents().get(Integer.parseInt(index)).edit(choice);
//    }
//
//    //EFFECTS: Handles choice from the user of a particular event and prints it's details
//    private void detailsEvent() {
//        System.out.println(printEvents());
//        System.out.println("Please give the index of the task you wish to display");
//        String index = scanner.nextLine();
//        for (String println : curPlanner.getDetailedEvents().get(Integer.parseInt(index)).printDetails()) {
//            System.out.println(println);
//        }
//    }
//
//
//    //EFFECTS: Handles choice of which detail in an event to modify
//    //         1 for renaming an event
//    //         2 for changing the date
//    //         3 for adding tasks to a TaskList event
//    private void handleEditChoice(String choice, String index) throws WrongTypeException, InvalidInputException {
//        if (choice.equals("1")) {
//            System.out.println("What would you like to rename the Event to?");
//            String rename = scanner.nextLine();
//            curPlanner.getEvents().get(Integer.parseInt(index)).edit(choice, rename);
//        }
//        if (choice.equals("2")) {
//            System.out.println("What would you like the new date to be?");
//            curPlanner.getEvents().get(Integer.parseInt(index)).edit(choice, scanner.nextLine());
//        }
//        if (choice.equals("3")) {
//            if (curPlanner.getEvents().get(Integer.parseInt(index)) instanceof TaskList) {
////            if (curPlanner.getEvents().get(Integer.parseInt(index)).getEventType().equals("Task Event")) {
//                handleTaskEdit(curPlanner.getEvents().get(Integer.parseInt(index)));
//            } else {
//                throw new WrongTypeException();
//            }
//        } else {
//            throw new InvalidInputException();
//        }
//    }
//
//    //EFFECTS: Handles adding tasks to a TaskList event
//    private void handleTaskEdit(Event t) {
//        while (true) {
//            System.out.println("[1] to add a new task | [2] to quit");
//            String choice = scanner.nextLine();
//            if (choice.equals("1")) {
//                System.out.println("Please enter the task with format (name/time)");
//                String input = scanner.nextLine();
//                t.edit("3", input);
//            } else {
//                break;
//            }
//        }
//    }
}
