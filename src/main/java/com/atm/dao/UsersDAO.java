package com.atm.dao;

import com.atm.bean.Users;
import com.atm.utility.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    
    

    public boolean registerUser(Users user) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Get database connection
            conn = ConnectionPool.connectDB();

            // SQL query to insert a new user
            String sql = "INSERT INTO users (account_number, user_name, phone_number, email, pin) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getAccountNumber());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPin());

            // Execute the update
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                status = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return status;
    }

    // Method to insert a user into the Users table
    public int insertUser(Users user) {
        int x = 0;
        Connection conn = ConnectionPool.connectDB();
        String query = "INSERT INTO Users (account_number, user_name, pin, phone_number, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getAccountNumber());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPin());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getEmail());
            x = ps.executeUpdate();
            if(x>0) System.out.println("User inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return x;
    }

    // Method to update user details
    public void updateUser(Users user) {
        Connection conn = ConnectionPool.connectDB();
        String query = "UPDATE Users SET user_name = ?, pin = ?, phone_number = ?, email = ? WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPin());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getAccountNumber());
            ps.executeUpdate();
            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a user by account number
    public void deleteUser(String accountNumber) {
        Connection conn = ConnectionPool.connectDB();
        String query = "DELETE FROM Users WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, accountNumber);
            ps.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to find a user by account number
    public Users findUser(String accountNumber) {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM Users WHERE account_number = ?";
        Users user = null;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Users(
                        rs.getInt("user_id"),
                        rs.getString("account_number"),
                        rs.getString("user_name"),
                        rs.getString("pin"),
                        rs.getString("phone_number"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Method to retrieve all users
    public List<Users> findAllUsers() {
        Connection conn = ConnectionPool.connectDB();
        String query = "SELECT * FROM Users";
        List<Users> userList = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Users user = new Users(
                        rs.getInt("user_id"),
                        rs.getString("account_number"),
                        rs.getString("user_name"),
                        rs.getString("pin"),
                        rs.getString("phone_number"),
                        rs.getString("email")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public Users validateLogin(String accountNumber, String pin) {
        Users user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.connectDB();
            String sql = "SELECT * FROM users WHERE account_number = ? AND pin = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            stmt.setString(2, pin);
            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new Users();
                user.setUserId(rs.getInt("user_id"));
                user.setAccountNumber(rs.getString("account_number"));
                user.setUserName(rs.getString("user_name"));
                user.setPin(rs.getString("pin"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Main method to test DAO operations
    public static void main(String[] args) {
        UsersDAO usersDAO = new UsersDAO();

        // Insert user example
//        Users newUser = new Users(0, "5678901234", "Charlie Davis", "abc123", "1234567890", "charlie@example.com");
//        usersDAO.insertUser(newUser);
        // Update user example
//        newUser.setUserName("Charlie Updated");
//        newUser.setPhoneNumber("0987654321");
//        usersDAO.updateUser(newUser);
        // Find user by account number
//        Users foundUser = usersDAO.findUser("5678901234");
//        if (foundUser != null) {
//            System.out.println("User found: " + foundUser.getUserName());
//        }
        // Find all users
//        List<Users> usersList = usersDAO.findAllUsers();
//        for (Users user : usersList) {
//            System.out.println("User: " + user.getUserName() + ", Account: " + user.getAccountNumber());
//        }
        String testAccountNumber = "1234567890";
        String testPin = "a1b2c3";

        Users user = usersDAO.validateLogin(testAccountNumber, testPin);

        if (user != null) {
            System.out.println("Login successful!");
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Account Number: " + user.getAccountNumber());
            System.out.println("User Name: " + user.getUserName());
            System.out.println("Phone Number: " + user.getPhoneNumber());
            System.out.println("Email: " + user.getEmail());
        } else {
            System.out.println("Invalid account number or PIN.");
        }

        // Delete user example
//        usersDAO.deleteUser("5678901234");
    }
}
