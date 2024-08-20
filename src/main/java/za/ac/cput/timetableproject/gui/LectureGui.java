package za.ac.cput.timetableproject.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LectureGui extends JPanel {

    private JButton add_New, change, delete;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane pane;

    public LectureGui() {
        setLayout(new BorderLayout());

        // Initialize buttons
        add_New = new JButton("Add New");
        change = new JButton("Change");
        delete = new JButton("Delete");

        // Set button size
        Dimension buttonSize = new Dimension(100, 30);
        add_New.setPreferredSize(buttonSize);
        change.setPreferredSize(buttonSize);
        delete.setPreferredSize(buttonSize);

        // Initialize table and scroll pane
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        pane = new JScrollPane(table);
        setSize(450, 250);

        // Set up the GUI layout
        setGui();
    }

    public void setGui() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3)); // GridLayout to align buttons horizontally

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Add columns to table model
        tableModel.addColumn("LectureID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Surname");
        tableModel.addColumn("Initials");

        // Add buttons to panel
        panel.add(add_New);
        panel.add(change);
        panel.add(delete);

        // Add components to main panel
        mainPanel.add(pane, BorderLayout.CENTER);
        mainPanel.add(panel, BorderLayout.SOUTH);

        // Add main panel to the current panel
        this.add(mainPanel, BorderLayout.CENTER);

        // Add action listeners for buttons
        add_New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add new lecture logic
                JOptionPane.showMessageDialog(null, "Add New Lecture clicked.");
            }
        });

        change.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Change lecture logic
                JOptionPane.showMessageDialog(null, "Change Lecture clicked.");
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Delete lecture logic
                JOptionPane.showMessageDialog(null, "Delete Lecture clicked.");
            }
        });
    }
}
