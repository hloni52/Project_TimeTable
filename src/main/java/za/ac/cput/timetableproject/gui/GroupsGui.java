//
//package za.ac.cput.timetableproject.gui;
//
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import za.ac.cput.timetableproject.dao.GroupsDao;
//import za.ac.cput.timetableproject.domain.Group;
//
//public class GroupsGui extends JPanel {
//
//    private JButton add_New, change, delete;
//    private DefaultTableModel tableModel;
//    private JTable table;
//    private JScrollPane pane;
//
//    public GroupsGui() {
//        setLayout(new BorderLayout());
//
//        // Initialize buttons
//        add_New = new JButton("Add New");
//        change = new JButton("Change");
//        delete = new JButton("Delete");
//
//        // Initialize table model and table
//        tableModel = new DefaultTableModel();
//        table = new JTable(tableModel);
//        pane = new JScrollPane(table);
//        setSize(450, 250);
//
//        // Set up the GUI layout
//        setGui();
//    }
//
//    private void setGui() {
//        // Set up table model columns
//        tableModel.addColumn("GroupID");
//        tableModel.addColumn("Group Name");
//
//        // Create panel for buttons
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new GridLayout(1, 3)); // Three buttons in one row
//        buttonPanel.add(add_New);
//        buttonPanel.add(change);
//        buttonPanel.add(delete);
//
//        // Create main panel and add components
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BorderLayout());
//        mainPanel.add(pane, BorderLayout.CENTER);
//        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//        this.add(mainPanel, BorderLayout.CENTER);
//
//        // Add action listeners for buttons
//        add_New.addActionListener(new ActionListener() {
//    public void actionPerformed(ActionEvent e) {
//        JPanel innerPanel = new JPanel(new GridLayout(2, 2));
//        JTextField groupIdField = new JTextField();
//        JTextField groupNameField = new JTextField();
//
//        JLabel groupIdLabel = new JLabel("Group ID:");
//        JLabel groupNameLabel = new JLabel("Group Name:");
//
//        innerPanel.add(groupIdLabel);
//        innerPanel.add(groupIdField);
//        innerPanel.add(groupNameLabel);
//        innerPanel.add(groupNameField);
//
//        int result = JOptionPane.showConfirmDialog(null, innerPanel,
//                "Enter Group Details", JOptionPane.OK_CANCEL_OPTION);
//
//        if (result == JOptionPane.OK_OPTION) {
//            try {
//                int groupId = Integer.parseInt(groupIdField.getText());
//                String groupName = groupNameField.getText();
//
//                if (groupName.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Group Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                GroupsDao dao = new GroupsDao();
//
//                if (dao.isGroupIdExists(groupId)) {
//                    JOptionPane.showMessageDialog(null, "Group ID already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                Group newGroup = new Group(groupId, groupName);
//                if (dao.save(newGroup)) {
//                    tableModel.addRow(new Object[]{groupId, groupName});
//                    JOptionPane.showMessageDialog(null, "Group successfully added.");
//                } else {
//                    JOptionPane.showMessageDialog(null, "Error occurred while saving the group.");
//                }
//
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(null, "Group ID must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//});
//
//
//
//       change.addActionListener(new ActionListener() {
//    public void actionPerformed(ActionEvent e) {
//        // Change group logic
//        int selectedRow = table.getSelectedRow();
//
//        if (selectedRow != -1) {
//            String groupId = table.getValueAt(selectedRow, 0).toString();
//            int groupId2 = Integer.parseInt(groupId);
//
//            JPanel innerPanel = new JPanel(new GridLayout(1, 2));
//            JTextField groupNameField = new JTextField();
//
//            JLabel labelNewName = new JLabel("New Group Name:");
//
//            innerPanel.add(labelNewName);
//            innerPanel.add(groupNameField);
//
//            int result = JOptionPane.showConfirmDialog(null, innerPanel, "Update Group Details", JOptionPane.OK_CANCEL_OPTION);
//
//            if (result == JOptionPane.OK_OPTION) {
//                try {
//                    String newGroupName = groupNameField.getText().trim();
//
//                    GroupsDao dao = new GroupsDao();
//                    dao.updateGroup(groupId2, newGroupName);
//
//                    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
//                    tableModel.setValueAt(newGroupName, selectedRow, 1);
//
//                    JOptionPane.showMessageDialog(null, "Group updated successfully!");
//
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
//                }
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Please select a group to update.");
//        }
//    }
//});
//
//        delete.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Delete group logic
//                 int selectedRow = table.getSelectedRow();
//
//                if (selectedRow != -1) {
//                    int groupId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
//
//                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this group?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
//
//                    if (confirm == JOptionPane.YES_OPTION) {
//                        try {
//                            GroupsDao dao = new GroupsDao();
//                            dao.deleteGroup(groupId);
//
//                            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
//                            tableModel.removeRow(selectedRow);
//
//                            JOptionPane.showMessageDialog(null, "Group deleted successfully!");
//
//                        } catch (Exception ex) {
//                            JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
//                        }
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "Please select a group to delete.");
//                }
//            
//            }
//        });
//    }
//}

package za.ac.cput.timetableproject.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.timetableproject.dao.GroupsDao;
import za.ac.cput.timetableproject.domain.Group;

public class GroupsGui extends JPanel {

    private JButton addNew, change, delete;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane pane;

    public GroupsGui() {
        setLayout(new BorderLayout());

        // Initialize buttons
        addNew = new JButton("Add New");
        change = new JButton("Change");
        delete = new JButton("Delete");

        // Set button size
        Dimension buttonSize = new Dimension(100, 30);
        addNew.setPreferredSize(buttonSize);
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

    private void setGui() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3)); // GridLayout to align buttons horizontally

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Add columns to table model
        tableModel.addColumn("GroupID");
        tableModel.addColumn("Group Name");

        // Add buttons to panel
        panel.add(addNew);
        panel.add(change);
        panel.add(delete);

        // Add components to main panel
        mainPanel.add(pane, BorderLayout.CENTER);
        mainPanel.add(panel, BorderLayout.SOUTH);

        // Add main panel to the current panel
        this.add(mainPanel, BorderLayout.CENTER);

        // Add action listeners for buttons
        addNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add new group logic
                JPanel innerPanel = new JPanel(new GridLayout(2, 2));
                JTextField groupId = new JTextField();
                JTextField groupName = new JTextField();

                JLabel lblGroupId = new JLabel("Group ID");
                JLabel lblGroupName = new JLabel("Group Name");

                innerPanel.add(lblGroupId);
                innerPanel.add(groupId);
                innerPanel.add(lblGroupName);
                innerPanel.add(groupName);

                int result = JOptionPane.showConfirmDialog(null, innerPanel,
                        "Enter Group Details", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int id = Integer.parseInt(groupId.getText());
                        String name = groupName.getText();

                        Group newGroup = new Group(id, name);
                        GroupsDao dao = new GroupsDao();

                        if (dao.isGroupIdExists(id)) {
                            JOptionPane.showMessageDialog(null, "Group ID already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dao.save(newGroup);
                        tableModel.addRow(new Object[]{id, name});
                        JOptionPane.showMessageDialog(null, "Group successfully added");

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number for Group ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
                    }
                }
            }
        });

        change.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Change group logic
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    int oldGroupId = (int) table.getValueAt(selectedRow, 0);

                    JPanel innerPanel = new JPanel(new GridLayout(1, 2));
                    JTextField newName = new JTextField();

                    JLabel labelNewName = new JLabel("New Group Name:");

                    innerPanel.add(labelNewName);
                    innerPanel.add(newName);

                    int result = JOptionPane.showConfirmDialog(null, innerPanel, "Update Group Details", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            String updatedName = newName.getText().trim();

                            GroupsDao dao = new GroupsDao();
                            dao.updateGroup(oldGroupId, updatedName);

                            tableModel.setValueAt(updatedName, selectedRow, 1);

                            JOptionPane.showMessageDialog(null, "Group updated successfully!");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
                        }
                    }
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Delete group logic
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    int groupId = (int) table.getValueAt(selectedRow, 0);

                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this group?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            GroupsDao dao = new GroupsDao();
                            dao.deleteGroup(groupId);

                            tableModel.removeRow(selectedRow);

                            JOptionPane.showMessageDialog(null, "Group deleted successfully!");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a group to delete.");
                }
            }
        });
    }
}
