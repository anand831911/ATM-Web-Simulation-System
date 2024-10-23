/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atm.bean;

/**
 *
 * @author HOME
 */
public class Users {
    private int userId;
    private String accountNumber;
    private String userName;
    private String pin;
    private String phoneNumber;
    private String email;

    // Default Constructor
    public Users() {
    }

    // Parameterized Constructor
    public Users(int userId, String accountNumber, String userName, String pin, String phoneNumber, String email) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.userName = userName;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}