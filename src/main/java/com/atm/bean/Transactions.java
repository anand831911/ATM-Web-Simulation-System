package com.atm.bean;

public class Transactions {
     private int transactionId;
    private int accountId;
    private String accountNumber;  // Change from int to String
    private String transactionType;
    private double amount;
    private String transactionDate;
    private double balanceAfter;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    // Constructor with parameters
   public Transactions(int accountId, String accountNumber, String transactionType, double amount, String transactionDate, double balanceAfter) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;  // Now it's a String
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.balanceAfter = balanceAfter;
    }
   public Transactions() {
    }

    // Full constructor
    public Transactions(int transactionId, int accountId, String transactionType, double amount, String transactionDate, String accountNumber, double balanceAfter) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.accountNumber = accountNumber;
        this.balanceAfter = balanceAfter;
    }

    // Getter and Setter methods
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
}
