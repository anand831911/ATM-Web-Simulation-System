package com.atm.dao;


import com.atm.bean.ATMOperationsLog;
import com.atm.utility.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ATMOperationsLogDAO {

    // Method to insert a log record
    public void insertLog(ATMOperationsLog log) {
        Connection conn = ConnectionPool.connectDB();
        String query = "INSERT INTO atm_operations_log (account_id, operation_type, operation_time, operation_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, log.getAccountId());
            ps.setString(2, log.getOperationType());
            ps.setString(3, log.getOperationTime()); // Use operation_time instead of operation_date
            ps.setString(4, log.getOperationStatus());
            ps.executeUpdate();
            System.out.println("ATM Operation Log inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a log record
    public void updateLog(ATMOperationsLog log) {
        Connection conn = ConnectionPool.connectDB();
        String query = "UPDATE atm_operations_log SET account_id = ?, operation_type = ?, operation_time = ?, operation_status = ? WHERE log_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, log.getAccountId());
            ps.setString(2, log.getOperationType());
            ps.setString(3, log.getOperationTime()); // Use operation_time instead of operation_date
            ps.setString(4, log.getOperationStatus());
            ps.setInt(5, log.getLogId());
            ps.executeUpdate();
            System.out.println("ATM Operation Log updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a log record by log ID
    public void deleteLog(int logId) {
        Connection conn = ConnectionPool.connectDB();
        String query = "DELETE FROM atm_operations_log WHERE log_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, logId);
            ps.executeUpdate();
            System.out.println("ATM Operation Log deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to find a log record by log ID
    public ATMOperationsLog findLog(int logId) {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM atm_operations_log WHERE log_id = ?";
        ATMOperationsLog log = null;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, logId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                log = new ATMOperationsLog(
                    rs.getInt("log_id"),
                    rs.getInt("account_id"),
                    rs.getString("operation_type"),
                    rs.getString("operation_time"), // Use operation_time instead of operation_date
                    rs.getString("operation_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return log;
    }

    // Method to retrieve all log records
    public List<ATMOperationsLog> findAllLogs() {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM atm_operations_log";
        List<ATMOperationsLog> logList = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ATMOperationsLog log = new ATMOperationsLog(
                    rs.getInt("log_id"),
                    rs.getInt("account_id"),
                    rs.getString("operation_type"),
                    rs.getString("operation_time"), // Use operation_time instead of operation_date
                    rs.getString("operation_type")
                );
                logList.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logList;
    }

    // Main method to test DAO operations
    public static void main(String[] args) {
        ATMOperationsLogDAO atmOperationsLogDAO = new ATMOperationsLogDAO();

        // Insert log example
//        ATMOperationsLog newLog = new ATMOperationsLog(0, 1, "Deposit", "2024-10-02 14:30:00", "Success"); // Use appropriate time format
//        atmOperationsLogDAO.insertLog(newLog);

        // Update log example
//        newLog.setOperationStatus("Failed");
//        atmOperationsLogDAO.updateLog(newLog);

        // Find log by log ID
        ATMOperationsLog foundLog = atmOperationsLogDAO.findLog(1);
        if (foundLog != null) {
            System.out.println("Log found: Operation " + foundLog.getOperationType() + ", Status " + foundLog.getOperationStatus());
        }

        // Find all logs
        List<ATMOperationsLog> logList = atmOperationsLogDAO.findAllLogs();
        for (ATMOperationsLog log : logList) {
            System.out.println("Log ID: " + log.getLogId() + ", Operation: " + log.getOperationType() + ", Status: " + log.getOperationStatus());
        }

        // Delete log example
        //atmOperationsLogDAO.deleteLog(1);
    }
}