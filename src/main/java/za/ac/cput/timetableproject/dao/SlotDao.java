
package za.ac.cput.timetableproject.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import za.ac.cput.timetableproject.connection.DatabaseConnection;
import za.ac.cput.timetableproject.domain.Slot;

public class SlotDao {

    private Connection con;
    private PreparedStatement ps;

    public SlotDao() {
        try {
            if (this.con == null || this.con.isClosed()) {
                this.con = DatabaseConnection.createConnection();
                createSlotTable();
                JOptionPane.showMessageDialog(null, "Connection Established");
            }
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "SQL error occurred: " + k.getMessage());
        }
    }

    public void createSlotTable() {
        String createTableSQL = "CREATE TABLE Slot (" +
                "slotId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " + // Auto-increment for primary key
                "slotTime VARCHAR(20), " + // Use VARCHAR to store time ranges as strings
                "dayOfWeek VARCHAR(10))";  // Day of the week as a string

        try {
            ps = con.prepareStatement(createTableSQL);
            ps.execute();
        } catch (SQLException k) {
            if (k.getSQLState().equals("X0Y32")) { // SQLState for table already exists
                JOptionPane.showMessageDialog(null, "Table already exists.");
            } else {
                JOptionPane.showMessageDialog(null, "SQL error occurred: " + k.getMessage());
            }
        } finally {
            closeResources(ps);
        }
    }
     public int getSlotIdBySlotTime(String slotTime) throws SQLException {
        String selectSQL = "SELECT slotId FROM Slot WHERE slotTime = ?";
        ps = con.prepareStatement(selectSQL);
        ps.setString(1, slotTime);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("slotId");
        } else {
            throw new SQLException("No slot found with the time: " + slotTime);
        }
    }
    // Method to insert a Slot record
    public void insert(Slot slot) {
        String insertSQL = "INSERT INTO Slot (slotTime, dayOfWeek) VALUES (?, ?)";

        try {
            ps = con.prepareStatement(insertSQL);
            ps.setString(1, slot.getSlotTime());
            ps.setString(2, slot.getDayOfWeek());
            
            ps.executeUpdate();
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "SQL error occurred while inserting: " + k.getMessage());
        } finally {
            closeResources(ps);
        }
    }

    // Method to retrieve all Slot records
    public List<Slot> getAll() throws SQLException {
        String selectSQL = "SELECT * FROM Slot";
        ps = con.prepareStatement(selectSQL);
        ResultSet rs = ps.executeQuery();

        List<Slot> list = new ArrayList<>();
        while (rs.next()) {
            int key = rs.getInt("slotId");
            String slotTime = rs.getString("slotTime");
            String dayOfWeek = rs.getString("dayOfWeek");
            
            list.add(new Slot(key, slotTime, dayOfWeek));
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

