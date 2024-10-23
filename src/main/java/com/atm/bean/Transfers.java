/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atm.bean;

import java.util.Date;

/**
 *
 * @author HOME
 */
public class Transfers {
    private int transferId;
    private int fromAccountId;
    private int toAccountId;
    private double amount;
    private String transferDate;

    // Default Constructor
    public Transfers() {
    }

    // Parameterized Constructor
    public Transfers(int transferId, int fromAccountId, int toAccountId, double amount, String transferDate) {
        this.transferId = transferId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.transferDate = transferDate;
    }

    public Transfers(int fromAccountId, int toAccountId, double amount, Date transferDate) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.transferDate = transferDate.toString();
    }

    // Getters and Setters
    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }
}