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
public class ATMOperationsLog {
   private int logId;
    private int accountId;
    private String operationType;
    private String operationTime; // Change variable name to operationTime
    private String operationStatus;

    // Parameterized constructor
    public ATMOperationsLog(int logId, int accountId, String operationType, String operationTime, String operationStatus) {
        this.logId = logId;
        this.accountId = accountId;
        this.operationType = operationType;
        this.operationTime = operationTime; // Change this line
        this.operationStatus = operationStatus;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }
    
    
}