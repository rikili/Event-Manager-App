package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.ArrayList;

public class Button extends JPanel implements ActionListener {
    protected JButton button;
    protected UserInOut ui;
    protected ArrayList<TextField> textFields;
    protected TextField textField;
    protected SComboBox comboBox;

    public Button(String name, String command, ArrayList<TextField> textFields, SComboBox comboBox, UserInOut ui) {
        button = new JButton(name);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        this.textFields = textFields;
        button.setActionCommand(command);
        button.addActionListener(this);
        button.setToolTipText("Press the button already");
        this.ui = ui;
        this.comboBox = comboBox;
        add(button);
    }

    public Button(String name, String command, TextField textField, UserInOut ui) {
        button = new JButton(name);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        this.textField = textField;
        button.setActionCommand(command);
        button.addActionListener(this);
        button.setToolTipText("Press the button already");
        this.ui = ui;
        add(button);
    }

    public Button(String name, String command, UserInOut ui) {
        button = new JButton(name);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setActionCommand(command);
        button.addActionListener(this);
        button.setToolTipText("Press the button already");
        this.ui = ui;
        add(button);
    }

    //EFFECTS: checks for the command of the button pressed
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(UserInOut.makePlanner)) {
            ui.plannerSubmit(textField.getText());
        } else if (e.getActionCommand().equals(UserInOut.confirmEvent)) {
            ui.callCreateEvent(textFields, comboBox);
        } else if (e.getActionCommand().equals(UserInOut.changeName)) {
            ui.eventNameChange(textField.getText());
        } else if (e.getActionCommand().equals(UserInOut.changeDate)) {
            ui.eventDateChange(textField.getText());
        } else {
            actionPerformedContinued(e);
        }
    }

    //EFFECTS: checks for the command of the button pressed
    private void actionPerformedContinued(ActionEvent e) {
        if (e.getActionCommand().equals(UserInOut.submitSave)) {
            try {
                ui.callToSave(textField.getText());
            } catch (IOException g) {
                throw new RuntimeException();
            }
        } else if (e.getActionCommand().equals(UserInOut.submitLoad)) {
            try {
                ui.callToLoad(textField.getText());
            } catch (Exception g) {
                throw new RuntimeException();
            }
        } else {
            ui.actionForButton(e);
        }
    }
}
