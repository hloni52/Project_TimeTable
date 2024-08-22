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
                JOptionPane.showMessageDialog(null, "Connection Established");
            }
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "SQL error occurred: " + k.getMessage());
        }
    }

    public void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE TimeTable ("
                + "timeTableId INT PRIMARY KEY, "
                + "groupId INT, "
                + "subjectId INT, "
                + "lecturerId INT, "
                + "venueId INT, "
                + "slotId INT, "
                + "FOREIGN KEY (groupId) REFERENCES Groups(groupId), "
                + "FOREIGN KEY (subjectId) REFERENCES Subjects(subjectId), "
                + "FOREIGN KEY (lecturerId) REFERENCES Lecturers(lecturerId), "
                + "FOREIGN KEY (venueId) REFERENCES Venues(venueId), "
                + "FOREIGN KEY (slotId) REFERENCES Slots(slotId))";
        ps = con.prepareStatement(createTableSQL);
        ps.execute();
    }

    // Method to insert a TimeTable record
    public void insert(TimeTable table) throws SQLException {
        String insertSQL = "INSERT INTO TimeTable (timeTableId, groupId, subjectId, lecturerId, venueId, slotId) VALUES (?, ?, ?, ?, ?, ?)";
        ps = con.prepareStatement(insertSQL);
        ps.setInt(1, table.getTimeTableId());
        ps.setInt(2, table.getGroupId());
        ps.setInt(3, table.getSubjectId());
        ps.setInt(4, table.getLectureId());
        ps.setInt(5, table.getVenueId());
        ps.setInt(6, table.getSlotId());
        ps.executeUpdate();
    }

    // Method to update a TimeTable record
    public void update(TimeTable table) throws SQLException {
        String updateSQL = "UPDATE TimeTable SET groupId = ?, subjectId = ?, lecturerId = ?, venueId = ?, slotId = ? WHERE timeTableId = ?";
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
            timeTable.setLectureId(rs.getInt("lecturerId"));
            timeTable.setVenueId(rs.getInt("venueId"));
            timeTable.setSlotId(rs.getInt("slotId"));
            timeTables.add(timeTable);
        }
        return timeTables;
    }
}
