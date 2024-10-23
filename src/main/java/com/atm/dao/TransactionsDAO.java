package com.atm.dao;

import com.atm.bean.Transactions;
import com.atm.utility.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionsDAO {

    // Method to insert a transaction record
    public void insertTransaction(Transactions transaction) {
        Connection conn = ConnectionPool.connectDB();
        String query = "INSERT INTO Transactions (account_id, transaction_type, amount, transaction_date, account_number) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, transaction.getAccountId());
            ps.setString(2, transaction.getTransactionType());
            ps.setDouble(3, transaction.getAmount());
            ps.setString(4, transaction.getTransactionDate());
            ps.setString(5, transaction.getAccountNumber());  // Ensure account_number is set correctly
            ps.executeUpdate();
            System.out.println("Transaction record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    // Method to insert a transaction record
    public int insertTransactionforRegister(Transactions transaction) {
         Connection conn = null;
        PreparedStatement stmt = null;
        int isAdded = 0;

        try {
            conn = ConnectionPool.connectDB();
            String sql = "INSERT INTO transactions (account_id, transaction_type, amount, transaction_date, balance_after, account_number) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transaction.getAccountId());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setString(4, transaction.getTransactionDate()); // Assumes transactionDate is a string in correct format
            stmt.setDouble(5, transaction.getBalanceAfter());
            stmt.setString(6, transaction.getAccountNumber());  // Ensure account_number is set correctly

             isAdded = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isAdded;
    }
    

    // Method to add a transaction record with balance after the transaction
    public boolean addTransaction(Transactions transaction) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isAdded = false;

        try {
            conn = ConnectionPool.connectDB();
            String sql = "INSERT INTO transactions (account_id, transaction_type, amount, transaction_date, balance_after, account_number) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transaction.getAccountId());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setString(4, transaction.getTransactionDate()); // Assumes transactionDate is a string in correct format
            stmt.setDouble(5, transaction.getBalanceAfter());
            stmt.setString(6, transaction.getAccountNumber());  // Ensure account_number is set correctly

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isAdded;
    }

    // Method to update a transaction record
    public void updateTransaction(Transactions transaction) {
        Connection conn = ConnectionPool.connectDB();
        String query = "UPDATE Transactions SET account_id = ?, transaction_type = ?, amount = ?, transaction_date = ?, account_number = ? WHERE transaction_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, transaction.getAccountId());
            ps.setString(2, transaction.getTransactionType());
            ps.setDouble(3, transaction.getAmount());
            ps.setString(4, transaction.getTransactionDate());
            ps.setString(5, transaction.getAccountNumber());  // Corrected to include account_number in update
            ps.setInt(6, transaction.getTransactionId());
            ps.executeUpdate();
            System.out.println("Transaction record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a transaction record by transaction ID
    public void deleteTransaction(int transactionId) {
        Connection conn = ConnectionPool.connectDB();
        String query = "DELETE FROM Transactions WHERE transaction_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, transactionId);
            ps.executeUpdate();
            System.out.println("Transaction record deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to find a transaction record by transaction ID
    public Transactions findTransaction(int transactionId) {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM Transactions WHERE transaction_id = ?";
        Transactions transaction = null;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                transaction = new Transactions(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getString("transaction_date"),
                        rs.getString("account_number"),  // Now includes account_number in result mapping
                        rs.getDouble("balance_after")  // Assuming balance_after is part of the schema
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    // Method to retrieve transactions by account number
    public List<Transactions> getTransactionsByAccountNumber(String accountNumber) {
        List<Transactions> transactionList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.connectDB();

            String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, accountNumber);

            rs = ps.executeQuery();

            while (rs.next()) {
                Transactions transaction = new Transactions();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setAccountId(rs.getInt("account_id"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransactionDate(rs.getString("transaction_date"));
                transaction.setBalanceAfter(rs.getDouble("balance_after"));
                transaction.setAccountNumber(rs.getString("account_number"));  // Ensure account_number is mapped

                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transactionList;
    }

    // Method to retrieve transactions by account ID
    public List<Transactions> getTransactionsByAccountId(int accountId) {
        List<Transactions> transactionList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.connectDB();

            String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY transaction_date DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Transactions transaction = new Transactions();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setAccountId(rs.getInt("account_id"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransactionDate(rs.getString("transaction_date"));
                transaction.setBalanceAfter(rs.getDouble("balance_after"));
                transaction.setAccountNumber(rs.getString("account_number"));

                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transactionList;
    }

    // Method to retrieve all transactions
    public List<Transactions> findAllTransactions() {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM Transactions";
        List<Transactions> transactionList = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Transactions transaction = new Transactions(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getString("transaction_date"),
                        rs.getString("account_number"),  // Now includes account_number in result mapping
                        rs.getDouble("balance_after")  // Assuming balance_after is part of the schema
                );
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    // Main method to test DAO operations
    public static void main(String[] args) {
        TransactionsDAO transactionsDAO = new TransactionsDAO();
        
        Transactions transaction = new Transactions();
        //account_id, transaction_type, amount, 
        //transaction_date, balance_after, account_number
//        transaction.setAccountId(4);
//        transaction.setTransactionType("Deposit");
//        transaction.setBalanceAfter(1000);
//        transaction.setAmount(12832);
//        transaction.setTransactionDate("2024-12-10");
//        transaction.setAccountNumber("237426382");
//        int x = transactionsDAO.insertTransactionforRegister(transaction);
//        System.out.println(x);
        
        

        // Example: Insert a new transaction
        // Transactions newTransaction = new Transactions(0, 1, "Deposit", 1000.00, "2024-10-02", 1234567890, 5000.00);
        // transactionsDAO.insertTransaction(newTransaction);

        // Example: Fetch transactions by account number
//        List<Transactions> transactionList = transactionsDAO.getTransactionsByAccountNumber("1234567890");
//        for (Transactions transaction : transactionList) {
//            System.out.println(transaction.getAccountId() + " " + transaction.getTransactionType());
//        }

        // Example: Fetch transaction by transaction ID
//        Transactions foundTransaction = transactionsDAO.findTransaction(1);
//        if (foundTransaction != null) {
//            System.out.println("Transaction found: " + foundTransaction.getTransactionType());
//        }
    }
}
