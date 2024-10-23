package com.atm.dao;



import com.atm.bean.Accounts;
import com.atm.utility.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountsDAO {

    // Method to insert an account record
    public void insertAccount(Accounts account) {
        Connection conn = ConnectionPool.connectDB();
        String query = "INSERT INTO Accounts (user_id, account_number, balance) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, account.getUserId());
            ps.setString(2, account.getAccountNumber());
            ps.setDouble(3, account.getBalance());
            ps.executeUpdate();
            System.out.println("Account record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int insertAccountToRegister(Accounts account) {
        int x = 0;
        Connection conn = ConnectionPool.connectDB();
        String query = "INSERT INTO Accounts (user_id, account_number, balance, last_transaction) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, account.getUserId());
            ps.setString(2, account.getAccountNumber());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getLastTransaction());
            x = ps.executeUpdate();
            System.out.println("Account record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return x;
    }
    
    public Accounts getAccountByAccountNumber(String accountNumber) {
        Accounts account = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Get connection from connection pool
            conn = ConnectionPool.connectDB();

            // SQL query to select account by account_number
            String sql = "SELECT * FROM accounts WHERE account_number = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, accountNumber);

            rs = ps.executeQuery();

            if (rs.next()) {
                // Create and populate the Accounts object
                account = new Accounts();
                account.setAccountId(rs.getInt("account_id"));
                account.setUserId(rs.getInt("user_id"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setBalance(rs.getDouble("balance"));
                account.setLastTransaction(rs.getString("last_transaction"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    // Method to update an account record
    public void updateAccount(Accounts account) {
        Connection conn = ConnectionPool.connectDB();
        String query = "UPDATE Accounts SET user_id = ?, account_number = ?, balance = ? WHERE account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, account.getUserId());
            ps.setString(2, account.getAccountNumber());
            ps.setDouble(3, account.getBalance());
            ps.setInt(4, account.getAccountId());
            ps.executeUpdate();
            System.out.println("Account record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean updateBalance(int accountId, double newBalance) {
    Connection conn = null;
    PreparedStatement stmt = null;
    boolean isUpdated = false;

    try {
        conn = ConnectionPool.connectDB();
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, newBalance);
        stmt.setInt(2, accountId);  // Change from userId to accountId

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            isUpdated = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return isUpdated;
}


    // Method to delete an account record by account ID
    public void deleteAccount(int accountId) {
        Connection conn = ConnectionPool.connectDB();
        String query = "DELETE FROM Accounts WHERE account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountId);
            ps.executeUpdate();
            System.out.println("Account record deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to find an account record by account ID
    public Accounts findAccount(int accountId) {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM Accounts WHERE account_id = ?";
        Accounts account = null;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Accounts(
                    rs.getInt("account_id"),
                    rs.getInt("user_id"),
                    rs.getString("account_number"),
                    rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
    
    public Accounts getAccountByUserId(int userId) {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM Accounts WHERE user_id = ?";
        Accounts account = null;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Accounts(
                    rs.getInt("account_id"),
                    rs.getInt("user_id"),
                    rs.getString("account_number"),
                    rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    // Method to retrieve all account records
    public List<Accounts> findAllAccounts() {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM Accounts";
        List<Accounts> accountList = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Accounts account = new Accounts(
                    rs.getInt("account_id"),
                    rs.getInt("user_id"),
                    rs.getString("account_number"),
                    rs.getDouble("balance")
                );
                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    // Main method to test DAO operations
    public static void main(String[] args) {
        AccountsDAO accountsDAO = new AccountsDAO();

        // Insert account example
//        Accounts newAccount = new Accounts(0, 1, "1234567890", 10000.00);
//        accountsDAO.insertAccount(newAccount);

        // Update account example
//        newAccount.setBalance(12000.00);
//        accountsDAO.updateAccount(newAccount);

        // Find account by account ID
        Accounts foundAccount = accountsDAO.findAccount(1);
        if (foundAccount != null) {
            System.out.println("Account found: Account Number " + foundAccount.getAccountNumber() + " with balance " + foundAccount.getBalance());
        }

        // Find all accounts
        List<Accounts> accountList = accountsDAO.findAllAccounts();
        for (Accounts account : accountList) {
            System.out.println("Account ID: " + account.getAccountId() + ", Account Number: " + account.getAccountNumber() + ", Balance: " + account.getBalance());
        }

        // Delete account example
        //accountsDAO.deleteAccount(1);
    }
}
