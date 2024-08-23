/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.timetableproject.dao;

/**
 *
 * @author hloni
 */
import java.sql.Connection;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import za.ac.cput.timetableproject.connection.DatabaseConnection;
import za.ac.cput.timetableproject.domain.TimeTable;
import za.ac.cput.timetableproject.domain.*;

public class TimeTableDao {

   private PreparedStatement ps;
    private Connection con;

    public TimeTableDao() {
        try {
            if (this.con == null || this.con.isClosed()) {
                this.con = DatabaseConnection.createConnection();
                createTable();
                JOptionPane.showMessageDialog(null, "Connection Established");
            }
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "SQL error occurred: " + k.getMessage());
        }
    }

    public void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE TimeTable ("
                + "timeTableId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "groupId INT, "
                + "subjectId INT, "
                + "lectureId INT, "
                + "venueId INT, "
                + "slotId INT, "
                + "FOREIGN KEY (groupId) REFERENCES Groups(groupId), "
                + "FOREIGN KEY (subjectId) REFERENCES Subject(subjectId), "
                + "FOREIGN KEY (lectureId) REFERENCES Lecture(lectureId), "
                + "FOREIGN KEY (venueId) REFERENCES Venue(venueId), "
                + "FOREIGN KEY (slotId) REFERENCES Slot(slotId))";
        ps = con.prepareStatement(createTableSQL);
        ps.execute();
    }

    // Method to insert a TimeTable record
   public void insert(TimeTable table) throws SQLException {
    // Exclude timeTableId from the INSERT statement
    String insertSQL = "INSERT INTO TimeTable (groupId, subjectId, lectureId, venueId, slotId) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, table.getGroupId());
        ps.setInt(2, table.getSubjectId());
        ps.setInt(3, table.getLectureId());
        ps.setInt(4, table.getVenueId());
        ps.setInt(5, table.getSlotId());
        ps.executeUpdate();
        
        // Retrieve the generated key
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                table.setTimeTableId(generatedId); // Set the auto-generated ID
            }
        }
    }
}

    // Method to update a TimeTable record
    public void update(TimeTable table) throws SQLException {
        String updateSQL = "UPDATE TimeTable SET groupId = ?, subjectId = ?, lectureId = ?, venueId = ?, slotId = ? WHERE timeTableId = ?";
        ps = con.prepareStatement(updateSQL);
        ps.setInt(1, table.getGroupId());
        ps.setInt(2, table.getSubjectId());
        ps.setInt(3, table.getLectureId());
        ps.setInt(4, table.getVenueId());
        ps.setInt(5, table.getSlotId());
        ps.setInt(6, table.getTimeTableId());
        ps.executeUpdate();
    }

    // Method to delete a TimeTable record
    public void delete(int timeTableId) throws SQLException {
        String deleteSQL = "DELETE FROM TimeTable WHERE timeTableId = ?";
        ps = con.prepareStatement(deleteSQL);
        ps.setInt(1, timeTableId);
        ps.executeUpdate();
    }

    // Method to retrieve all TimeTable records
    public List<TimeTable> getAll() throws SQLException {
        String selectSQL = "SELECT * FROM TimeTable";
        ps = con.prepareStatement(selectSQL);
        ResultSet rs = ps.executeQuery();

        List<TimeTable> timeTables = new ArrayList<>();
        while (rs.next()) {
            TimeTable timeTable = new TimeTable();
            timeTable.setTimeTableId(rs.getInt("timeTableId"));
            timeTable.setGroupId(rs.getInt("groupId"));
            timeTable.setSubjectId(rs.getInt("subjectId"));
            timeTable.setLectureId(rs.getInt("lectureId"));
            timeTable.setVenueId(rs.getInt("venueId"));
            timeTable.setSlotId(rs.getInt("slotId"));
            timeTables.add(timeTable);
        }
        return timeTables;
    }
}