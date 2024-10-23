package com.atm.dao;

import com.atm.bean.Transfers;
import com.atm.utility.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferDAO {

    // Method to insert a transfer record
    public void insertTransfer(Transfers transfer) {
        Connection conn = ConnectionPool.connectDB();
        String query = "INSERT INTO Transfers (from_account_id, to_account_id, amount, transfer_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, transfer.getFromAccountId());
            ps.setInt(2, transfer.getToAccountId());
            ps.setDouble(3, transfer.getAmount());
            ps.setString(4, transfer.getTransferDate());
            ps.executeUpdate();
            System.out.println("Transfer record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean addTransfer(Transfers transfer) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Get connection from connection pool
            conn = ConnectionPool.connectDB();

            // SQL query to insert a new transfer record
            String sql = "INSERT INTO transfers (from_account_id, to_account_id, amount, transfer_date) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, transfer.getFromAccountId());
            ps.setInt(2, transfer.getToAccountId());
            ps.setDouble(3, transfer.getAmount());
            ps.setString(4, transfer.getTransferDate());  // Assuming transferDate is stored as a string

            // Execute update and return success status
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
    }

    // Method to update a transfer record
    public void updateTransfer(Transfers transfer) {
        Connection conn = ConnectionPool.connectDB();
        String query = "UPDATE Transfers SET from_account_id = ?, to_account_id = ?, amount = ?, transfer_date = ? WHERE transfer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, transfer.getFromAccountId());
            ps.setInt(2, transfer.getToAccountId());
            ps.setDouble(3, transfer.getAmount());
            ps.setString(4, transfer.getTransferDate());
            ps.setInt(5, transfer.getTransferId());
            ps.executeUpdate();
            System.out.println("Transfer record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a transfer record by transfer ID
    public void deleteTransfer(int transferId) {
        Connection conn = ConnectionPool.connectDB();
        String query = "DELETE FROM Transfers WHERE transfer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, transferId);
            ps.executeUpdate();
            System.out.println("Transfer record deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to find a transfer record by transfer ID
    public Transfers findTransfer(int transferId) {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM Transfers WHERE transfer_id = ?";
        Transfers transfer = null;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, transferId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                transfer = new Transfers(
                    rs.getInt("transfer_id"),
                    rs.getInt("from_account_id"),
                    rs.getInt("to_account_id"),
                    rs.getDouble("amount"),
                    rs.getString("transfer_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transfer;
    }

    // Method to retrieve all transfer records
    public List<Transfers> findAllTransfers() {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM Transfers";
        List<Transfers> transferList = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Transfers transfer = new Transfers(
                    rs.getInt("transfer_id"),
                    rs.getInt("from_account_id"),
                    rs.getInt("to_account_id"),
                    rs.getDouble("amount"),
                    rs.getString("transfer_date")
                );
                transferList.add(transfer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transferList;
    }

    // Main method to test DAO operations
    public static void main(String[] args) {
        TransferDAO transferDAO = new TransferDAO();

        // Insert transfer example
//        Transfers newTransfer = new Transfers(0, 1, 2, 500.00, "2024-10-02");
//        transferDAO.insertTransfer(newTransfer);

        // Update transfer example
//        newTransfer.setAmount(700.00);
//        newTransfer.setTransferDate("2024-10-03");
//        transferDAO.updateTransfer(newTransfer);

        // Find transfer by transfer ID
        Transfers foundTransfer = transferDAO.findTransfer(1);
        if (foundTransfer != null) {
            System.out.println("Transfer found: From Account " + foundTransfer.getFromAccountId() + " to Account " + foundTransfer.getToAccountId());
        }

        // Find all transfers
        List<Transfers> transferList = transferDAO.findAllTransfers();
        for (Transfers transfer : transferList) {
            System.out.println("Transfer ID: " + transfer.getTransferId() + ", Amount: " + transfer.getAmount());
        }

        // Delete transfer example
//        transferDAO.deleteTransfer(1);
    }
}
