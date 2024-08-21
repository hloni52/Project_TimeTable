package za.ac.cput.timetableproject.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import za.ac.cput.timetableproject.connection.DatabaseConnection;
import za.ac.cput.timetableproject.domain.Slot;

public class SlotDao {

    Connection con;
    PreparedStatement ps;

    public SlotDao() {
        try {
            if (this.con == null || this.con.isClosed()) {  
                this.con = DatabaseConnection.createConnection();
                JOptionPane.showMessageDialog(null, "Connection Established");
            }
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "SQL error occurred: " + k.getMessage());
        }
    }

    public void createTable() {
        String createTableSQL = "CREATE TABLE Slot (" +
                                "slotId INT PRIMARY KEY, " +
                                "periodNumber INT, " +
                                "startTime VARCHAR(10), " +
                                "endTime VARCHAR(10), " +
                                "dayOfWeek VARCHAR(10))";

        try {
            ps = con.prepareStatement(createTableSQL);
            ps.execute();
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "SQL error occurred: " + k.getMessage());
        } finally {
            closeResources(ps);
        }
    }

    // Method to insert a Slot record
    public void insert(Slot slot) {
        String insertSQL = "INSERT INTO Slot (slotId, periodNumber, startTime, endTime, dayOfWeek) VALUES (?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(insertSQL);
            ps.setInt(1, slot.getSlotId());
            ps.setInt(2, slot.getPeriodNumber());
            ps.setString(3, slot.getStartTime());
            ps.setString(4, slot.getEndTime());
            ps.setString(5, slot.getDayOfWeek());
            ps.executeUpdate();
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "SQL error occurred: " + k.getMessage());
        } finally {
            closeResources(ps);
        }
    }

    // Method to retrieve all Slot records
    public List<Slot> getAll() throws SQLException {
        String selectSQL = "SELECT * FROM Slot";
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();

        List<Slot> list = new ArrayList<>();
        while (rs.next()) {
            Slot slot = new Slot();
            slot.setSlotId(rs.getInt("slotId"));
            slot.setPeriodNumber(rs.getInt("periodNumber"));
            slot.setStartTime(rs.getString("startTime"));
            slot.setEndTime(rs.getString("endTime"));
            slot.setDayOfWeek(rs.getString("dayOfWeek"));
            list.add(slot);
        }
        return list;
    }

    // Utility method to close resources
    private void closeResources(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error closing resource: " + ex.getMessage());
            }
        }
    }
}
