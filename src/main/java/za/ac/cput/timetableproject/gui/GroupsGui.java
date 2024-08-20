
package za.ac.cput.timetableproject.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GroupsGui extends JPanel {

    private JButton add_New, change, delete;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane pane;

    public GroupsGui() {
        setLayout(new BorderLayout());

        // Initialize buttons
        add_New = new JButton("Add New");
        change = new JButton("Change");
        delete = new JButton("Delete");

        // Initialize table model and table
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        pane = new JScrollPane(table);
        setSize(450, 250);

        // Set up the GUI layout
        setGui();
    }

    private void setGui() {
        // Set up table model columns
        tableModel.addColumn("GroupID");
        tableModel.addColumn("Group Capacity");

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3)); // Three buttons in one row
        buttonPanel.add(add_New);
        buttonPanel.add(change);
        buttonPanel.add(delete);

        // Create main panel and add components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(pane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel, BorderLayout.CENTER);

        // Add action listeners for buttons
        add_New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add new group logic
                JOptionPane.showMessageDialog(null, "Add New Group clicked.");
            }
        });

        change.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Change group logic
                JOptionPane.showMessageDialog(null, "Change Group clicked.");
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Delete group logic
                JOptionPane.showMessageDialog(null, "Delete Group clicked.");
            }
        });
    }
}
