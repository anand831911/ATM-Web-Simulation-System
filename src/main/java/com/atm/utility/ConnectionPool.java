/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atm.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HOME
 */
public class ConnectionPool {
    static Connection conn;
    public static Connection connectDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "root");
           // System.out.println("Database connection Sucess");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    public static void main(String[] args) {
        ConnectionPool.connectDB();
    }
}
