package ui;

import javax.swing.*;
import java.util.ArrayList;

public class SComboBox extends JPanel {
    private JComboBox<String> comboBox;
    private ArrayList<String> items;

    public SComboBox(ArrayList<String> items) {
        comboBox = new JComboBox(items.toArray());
        this.items = items;
        add(comboBox);
    }

    //EFFECTS: returns the comboBox's selected value
    public String getSelectedValue() {
        return (String) comboBox.getSelectedItem();
    }

}
