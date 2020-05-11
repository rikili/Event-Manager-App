package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextField extends JPanel {
    protected JTextField textField;
    protected UserInOut ui;


    public TextField(UserInOut ui, int size) {
        super(new GridBagLayout());
        this.ui = ui;

        textField = new JTextField("",size);
        textField.setText("");

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);
    }

    //EFFECTS: sets the textField's text to the inputted string
    public void setText(String text) {
        textField.setText(text);
    }

    //EFFECTS: returns the text that is in the textField
    public String getText() {
        return textField.getText();
    }
}
