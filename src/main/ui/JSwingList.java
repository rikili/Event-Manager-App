package ui;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class JSwingList extends JPanel {
    Dimension dim;
    protected JList list;
    protected DefaultListModel listModel;
    private UserInOut ui;

    public JSwingList(UserInOut ui) {
        super(new BorderLayout());
        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(10);
        JScrollPane scrollPane = new JScrollPane(list);
        dim = list.getPreferredScrollableViewportSize();
        dim.width = 300;
        scrollPane.setPreferredSize(dim);
        scrollPane.setMinimumSize(dim);
        this.ui = ui;
        add(scrollPane, BorderLayout.CENTER);
    }


    //EFFECTS: returns the value of the selected item
    public String getSelected() {
        return (String) list.getSelectedValue();
    }


    //MODIFIES: this
    //EFFECTS: adds text as a value onto the list
    public void addElement(String text) {
        int index = list.getSelectedIndex();
        listModel.addElement(text);
        list.setSelectedIndex(index);
        list.ensureIndexIsVisible(index);
    }

}