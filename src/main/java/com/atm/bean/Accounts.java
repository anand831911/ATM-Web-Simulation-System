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
public class Accounts {
    private int accountId;
    private int userId;
    private String accountNumber;
    private double balance;
    private String lastTransaction;

    // Default Constructor
    public Accounts() {
    }

    // Parameterized Constructor
    public Accounts(int accountId, int userId, String accountNumber, double balance, String lastTransaction) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.lastTransaction = lastTransaction;
    }
     public Accounts(int accountId, int userId, String accountNumber, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getters and Setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(String lastTransaction) {
        this.lastTransaction = lastTransaction;
    }
}
