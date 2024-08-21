/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.timetableproject.dao;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import za.ac.cput.timetableproject.connection.DatabaseConnection;
import za.ac.cput.timetableproject.domain.Stream;

/**
 *
 * @author hloni
 */
public class StreamDao {
    
    PreparedStatement ps;
    Connection con;
    
    
    public StreamDao() {
        try {
            if (this.con == null || this.con.isClosed()) {  // Check if the connection is null or closed
                this.con = DatabaseConnection.createConnection();
                JOptionPane.showMessageDialog(null, "Connection Established");
            }
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "SQL error occurred: " + k.getMessage());
        }
    }
     public  void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Stream (" +
                                "streamId INT PRIMARY KEY, " +
                                "streamName VARCHAR(50))";
        ps = con.prepareStatement(createTableSQL);
        ps.execute();
    }

    // Method to insert a Stream record
    public void insert(Stream st) throws SQLException {
        String insertSQL = "INSERT INTO Stream (streamId, streamName) VALUES (?, ?)";
        ps = con.prepareStatement(insertSQL);
        ps.setInt(1,st.getStreamId() );
        ps.setString(2,st.getStreamName() );
        ps.executeUpdate();
    }

    // Method to retrieve all Stream records
    public  List<Stream> getAll() throws SQLException {
        String selectSQL = "SELECT * FROM Stream";
        ps  = con.prepareStatement(selectSQL);
        ResultSet resultSet = ps.executeQuery();

        List<Stream> streams = new ArrayList<>();
        while (resultSet.next()) {
            Stream stream = new Stream();
            stream.setStreamId(resultSet.getInt("streamId"));
            stream.setStreamName(resultSet.getString("streamName"));
            streams.add(stream);
        }
        return streams;
    }
}
