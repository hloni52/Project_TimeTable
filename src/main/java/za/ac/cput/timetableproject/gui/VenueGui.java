
package za.ac.cput.timetableproject.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class VenueGui extends JPanel {

    private JLabel venueLabel;
    private JButton addNewButton, changeButton, deleteButton, loadDataButton;
    private JPanel buttonPanel, tablePanel, mainPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane pane;

    public VenueGui() {
        setLayout(new BorderLayout());

        // Initialize Components
        venueLabel = new JLabel("Venue Management");

        addNewButton = new JButton("Add New");
        changeButton = new JButton("Change");
        deleteButton = new JButton("Delete");
        loadDataButton = new JButton("Load Data");

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4)); // Layout for the buttons
        buttonPanel.add(addNewButton);
        buttonPanel.add(changeButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadDataButton);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        pane = new JScrollPane(table);

        // Set up the GUI layout
        setGui();

        // Load data
        loadData();
    }

    private void setGui() {
        // Set up mainPanel layout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(pane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add components to the frame
        this.add(venueLabel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void loadData() {
        // Example data loading (can be replaced with actual data loading logic)
        tableModel.addColumn("VenueID");
        tableModel.addColumn("Venue Name");
        tableModel.addColumn("Location");
        tableModel.addColumn("Capacity");

        

        // Add action listeners
        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add New button logic
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Change button logic
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete button logic
            }
        });

        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Load Data button logic
            }
        });
    }
}
