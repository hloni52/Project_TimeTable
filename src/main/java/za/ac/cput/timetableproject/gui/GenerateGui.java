package za.ac.cput.timetableproject.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.ac.cput.timetableproject.dao.*;
import za.ac.cput.timetableproject.domain.Group;
import za.ac.cput.timetableproject.domain.Lecture;
import za.ac.cput.timetableproject.domain.Slot;
import za.ac.cput.timetableproject.domain.Subject;
import za.ac.cput.timetableproject.domain.TimeTable;
import za.ac.cput.timetableproject.domain.Venue;
import za.ac.cput.timetableproject.gui.*;
import za.ac.cput.timetableproject.dao.SlotDao;



public class GenerateGui extends JPanel {

    private JComboBox <Group>groupComboBox;
    private JComboBox<Lecture> lecturerComboBox;
    private JComboBox<String> slotComboBox;
    private JComboBox<String> typeComboBox;
    private JComboBox<String> dayComboBox;
    private JComboBox<Subject> subjectComboBox;
    private JComboBox<Venue> venueComboBox;
    private JTable timetableTable;
    private DefaultTableModel tableModel;
    ArrayList<Group> list;
    ArrayList<Lecture> lectureList;
    ArrayList<Slot> slotList;
    ArrayList<Subject> subjectList;
    ArrayList<Venue> venueList;
     String groups[] = null ,venue [] = null  , subject[] = null,lecture [] = null;
     GroupsDao gDao;
     VenueDao vDao;
      LectureDao lDao;
      SubjectDao sDao;
      SlotDao ss;

    private Map<String, Map<String, String>> groupTimetables;
    private Map<String, String> lecturerAssignments;

    public GenerateGui() {
        setLayout(new BorderLayout());

        groupTimetables = new HashMap<>();
        lecturerAssignments = new HashMap<>();
        
        groupComboBox = new JComboBox();
         lecturerComboBox = new JComboBox();
         subjectComboBox = new JComboBox();
          venueComboBox = new JComboBox();
        
            // Create a JComboBox with the array of group names
            populateGroup();
            populateVenue();
            populateSubject();
             populateLecture();
        slotComboBox = new JComboBox<>(new String[]{
            "08:30 - 09:10", "09:15 - 09:55", "10:00 - 10:40", "10:45 - 11:25",
            "11:30 - 12:10", "12:15 - 12:55", "13:00 - 13:40", "13:45 - 14:25",
            "14:30 - 15:10", "15:15 - 15:55", "16:00 - 16:40", "16:45 - 17:25"
        });
        typeComboBox = new JComboBox<>(new String[]{"Lecture", "Tutorial", "Lab"});
        dayComboBox = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});
        
       

        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        inputPanel.add(new JLabel("Select Group:"));
        inputPanel.add(groupComboBox);
        inputPanel.add(new JLabel("Select Lecturer:"));
        inputPanel.add(lecturerComboBox);
        inputPanel.add(new JLabel("Select Slot:"));
        inputPanel.add(slotComboBox);
        inputPanel.add(new JLabel("Select Type:"));
        inputPanel.add(typeComboBox);
        inputPanel.add(new JLabel("Select Day:"));
        inputPanel.add(dayComboBox);
        inputPanel.add(new JLabel("Select Subject:"));
        inputPanel.add(subjectComboBox);
        inputPanel.add(new JLabel("Select Venue:"));
        inputPanel.add(venueComboBox);

        JButton generateButton = new JButton("Generate Timetable");
        JButton saveButton = new JButton("Save Timetable");
        inputPanel.add(generateButton);
        inputPanel.add(saveButton);

        add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Day", "08:30 - 09:10", "09:15 - 09:55", "10:00 - 10:40", "10:45 - 11:25",
            "11:30 - 12:10", "12:15 - 12:55", "13:00 - 13:40", "13:45 - 14:25",
            "14:30 - 15:10", "15:15 - 15:55", "16:00 - 16:40", "16:45 - 17:25"};
        tableModel = new DefaultTableModel(columnNames, 5);
        timetableTable = new JTable(tableModel);

        tableModel.setValueAt("Monday", 0, 0);
        tableModel.setValueAt("Tuesday", 1, 0);
        tableModel.setValueAt("Wednesday", 2, 0);
        tableModel.setValueAt("Thursday", 3, 0);
        tableModel.setValueAt("Friday", 4, 0);

        for (int i = 0; i < 5; i++) {
            tableModel.setValueAt("Break", i, 7);
        }

        timetableTable.setRowHeight(80);
        TableColumnModel columnModel = timetableTable.getColumnModel();
        for (int i = 1; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(200);
        }

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setVerticalAlignment(SwingConstants.TOP);
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        timetableTable.setDefaultRenderer(Object.class, renderer);
        
         add(new JScrollPane(timetableTable), BorderLayout.CENTER);
        
        generateButton.addActionListener(e -> {
            try {
                generateTimetable();
            } catch (Exception ex) {
                Logger.getLogger(GenerateGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        saveButton.addActionListener(e -> saveTimetable());
    }

    private void generateTimetable() throws Exception {
        Group group = (Group) groupComboBox.getSelectedItem();
        String selectedGroup = group.getGroupName();
        int gk = group.getGroupId();
        
        Lecture lecturer = (Lecture) lecturerComboBox.getSelectedItem();
        String selectedLecture = lecturer.getLectureName()+lecturer.getLectureSurname();
        int lk = lecturer.getLectureID();
        
        String slot = (String) slotComboBox.getSelectedItem();
        
        
        String day = (String) dayComboBox.getSelectedItem();
        
        String type = (String) typeComboBox.getSelectedItem();
        
        
        
        Subject subject = (Subject) subjectComboBox.getSelectedItem();
        int sk = subject.getSubjectCode();
        
        Venue venue = (Venue) venueComboBox.getSelectedItem();
         String selectedVenue = venue.toString();
         int vk = venue.getVenue_id();
         
        ss = new SlotDao();
         int slotKey = ss.getSlotIdBySlotTime(slot);
         
         
        
        
        ///// thisisis
        saveSlotToDatabase(slot, day);
        saveAllToTimeTableDatabase(gk,sk,lk,vk,slotKey);
        
        if (slot.equals("13:00 - 13:40")) {
            JOptionPane.showMessageDialog(this, "13:00 to 13:40 is a break. Please select a different time slot.", "Break Time", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String daySlotKey = day + "-" + slot;

        groupTimetables.putIfAbsent(selectedGroup, new HashMap<>());

        Map<String, String> timetable = groupTimetables.get(selectedGroup);

        if (timetable.containsKey(daySlotKey)) {
            JOptionPane.showMessageDialog(this, "This slot is already booked for " + group + "!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String lecturerKey = lecturer + "-" + daySlotKey;
        if (lecturerAssignments.containsKey(lecturerKey)) {
            JOptionPane.showMessageDialog(this, "Lecturer " + lecturer + " is already assigned to " + lecturerAssignments.get(lecturerKey) + " at this time!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cellValue = "<html>" + lecturer + "<br><b>" + subject + "</b><br>" + venue + " (" + type + ")" + "</html>";
        timetable.put(daySlotKey, cellValue);

        lecturerAssignments.put(lecturerKey, selectedVenue);

        int rowIndex = 0;
        int columnIndex = 0;

        switch (day) {
            case "Monday":
                rowIndex = 0;
                break;
            case "Tuesday":
                rowIndex = 1;
                break;
            case "Wednesday":
                rowIndex = 2;
                break;
            case "Thursday":
                rowIndex = 3;
                break;
            case "Friday":
                rowIndex = 4;
                break;
        }

        switch (slot) {
            case "08:30 - 09:10":
                columnIndex = 1;
                break;
            case "09:15 - 09:55":
                columnIndex = 2;
                break;
            case "10:00 - 10:40":
                columnIndex = 3;
                break;
            case "10:45 - 11:25":
                columnIndex = 4;
                break;
            case "11:30 - 12:10":
                columnIndex = 5;
                break;
            case "12:15 - 12:55":
                columnIndex = 6;
                break;
            case "13:00 - 13:40":
                columnIndex = 7;
                break;
            case "13:45 - 14:25":
                columnIndex = 8;
                break;
            case "14:30 - 15:10":
                columnIndex = 9;
                break;
            case "15:15 - 15:55":
                columnIndex = 10;
                break;
            case "16:00 - 16:40":
                columnIndex = 11;
                break;
            case "16:45 - 17:25":
                columnIndex = 12;
                break;
        }

        tableModel.setValueAt(cellValue, rowIndex, columnIndex);
        
    }

    private void saveTimetable() {
        String group = (String) groupComboBox.getSelectedItem();

        int response = JOptionPane.showConfirmDialog(this, "Do you want to save the timetable for " + group + "?", "Save Timetable", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Timetable for " + group + " has been saved successfully!", "Save Success", JOptionPane.INFORMATION_MESSAGE);

            groupComboBox.setSelectedIndex(0);
            lecturerComboBox.setSelectedIndex(0);
            slotComboBox.setSelectedIndex(0);
            typeComboBox.setSelectedIndex(0);
            dayComboBox.setSelectedIndex(0);
            subjectComboBox.setSelectedIndex(0);
            venueComboBox.setSelectedIndex(0);

            tableModel.setRowCount(0);
            tableModel.setRowCount(5);
            tableModel.setValueAt("Monday", 0, 0);
            tableModel.setValueAt("Tuesday", 1, 0);
            tableModel.setValueAt("Wednesday", 2, 0);
            tableModel.setValueAt("Thursday", 3, 0);
            tableModel.setValueAt("Friday", 4, 0);

            for (int i = 0; i < 5; i++) {
                tableModel.setValueAt("Break", i, 7);
            }
        }
    }
   
    // Inside your GUI class, where you want to populate the JComboBox
    public void populateGroup(){
        try{
            gDao = new GroupsDao();
            list = gDao.Groups();
            //groups = new String[gDao.Groups().size()];
            for(Group ll :list){
                
                groupComboBox.addItem(ll);
            }
            
            }
            catch(Exception k){
                
            }
       
    }
     public void populateLecture() {
        lDao = new LectureDao();
         lectureList = lDao.Lecture();
         for(Lecture l : lectureList){
             lecturerComboBox.addItem(l);
         }
        
     }
     public void populateSubject() {
    try {
         sDao = new SubjectDao();
        subjectList =sDao.Subjects();
        for (Subject ll : subjectList) {
            subjectComboBox.addItem(ll);
        }
        
    } catch (Exception k) {
        k.printStackTrace();  
    }
}
    public void populateVenue(){
        
        try{
            vDao  = new VenueDao();
            venueList = vDao.Venues();
            for(Venue ll: venueList){
               venueComboBox.addItem(ll);
            }
            
            
            
        }catch(SQLException k){
            
        }
    }
   

    
    private void saveSlotToDatabase(String slotTime, String dayOfWeek) {
    Slot slot = new Slot(0, slotTime, dayOfWeek); // slotId is auto-generated
    SlotDao slotDao = new SlotDao(); // Assuming you have a SlotDao class
    slotDao.insert(slot);
}
    
    private void saveAllToTimeTableDatabase(int group, int subject, int lecture, int venue, int slot) {
        TimeTable timeTableDao = new TimeTable(group,subject,lecture,venue,slot);
        
        TimeTableDao ds = new TimeTableDao();
        try{
        ds.createTable();
        ds.insert(timeTableDao);
        
        }catch(Exception l){
            
        }
        
//        ArrayList<TimeTable> list = new ArrayList();
//        list.add()
       
        
       
        
        
        
        
        
    
    

    
}
}

