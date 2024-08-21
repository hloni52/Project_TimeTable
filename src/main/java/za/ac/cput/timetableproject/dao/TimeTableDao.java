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

public class TimeTableDao {

    PreparedStatement ps;
    Connection con;

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
                + "venueId INT, "
                + "subjectId INT, "
                + "lectureId INT, "
                + "FOREIGN KEY (venueId) REFERENCES Venue(venueId), "
                + "FOREIGN KEY (subjectId) REFERENCES Subject(subjectId), "
                + "FOREIGN KEY (lectureId) REFERENCES Lecture(lectureId))";
        ps = con.prepareStatement(createTableSQL);
        ps.execute();
    }

    // Method to insert a TimeTable record
    public void insert(TimeTable table) throws SQLException {
        String insertSQL = "INSERT INTO TimeTable (timeTableId, venueId, subjectId, lectureId) VALUES (?, ?, ?, ?)";
        ps = con.prepareStatement(insertSQL);
        ps.setInt(1, table.getTimeTableId());
        ps.setInt(2, table.getVenueId());
        ps.setInt(3, table.getSubjectId());
        ps.setInt(4, table.getLectureId());
        ps.executeUpdate();
    }

    
    public List<TimeTable> getAll() throws SQLException {
        String selectSQL = "SELECT * FROM TimeTable";
        ps = con.prepareStatement(selectSQL);
        ResultSet rs = ps.executeQuery();

        List<TimeTable> timeTables = new ArrayList<>();
        while (rs.next()) {
            TimeTable timeTable = new TimeTable();
            timeTable.setTimeTableId(rs.getInt("timeTableId"));
            timeTable.setVenueId(rs.getInt("venueId"));
            timeTable.setSubjectId(rs.getInt("subjectId"));
            timeTable.setLectureId(rs.getInt("lectureId"));
            timeTables.add(timeTable);
        }
        return timeTables;
    }
}
