package com.atm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.atm.bean.Users;
import com.atm.bean.Accounts;
import com.atm.bean.Transactions;
import com.atm.dao.AccountsDAO;
import com.atm.dao.TransactionsDAO;

public class DepositServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Retrieve session and user information
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");

            // Check if the user is logged in
            if (user == null) {
                response.sendRedirect("login.jsp"); // Redirect to login if session expired
                return;
            }

            // Parse deposit amount from request
            String amountStr = request.getParameter("amount");
            if (amountStr == null || amountStr.isEmpty()) {
                request.setAttribute("errorMessage", "Amount is required.");
                request.getRequestDispatcher("deposit.jsp").forward(request, response);
                return;
            }

            double depositAmount = Double.parseDouble(amountStr);
            if (depositAmount <= 0) {
                request.setAttribute("errorMessage", "Please enter a positive amount.");
                request.getRequestDispatcher("deposit.jsp").forward(request, response);
                return;
            }

            // Access DAOs
            AccountsDAO accountsDAO = new AccountsDAO();
            TransactionsDAO transactionsDAO = new TransactionsDAO();

            // Get the user's account information
            Accounts account = accountsDAO.getAccountByUserId(user.getUserId());

            // Perform the deposit operation
            double newBalance = account.getBalance() + depositAmount;

            // Update the balance in the accounts table
            try {
                boolean success = accountsDAO.updateBalance(account.getAccountId(), newBalance);
                if (success) {
                    // Format the current date to a string
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String transactionDateStr = sdf.format(new Date());

                    // Add a new transaction to the transactions table
                    Transactions transaction = new Transactions(
                        account.getAccountId(),
                        account.getAccountNumber(),  // Include the account_number
                        "deposit",                   // Transaction type
                        depositAmount,               // Deposit amount
                        transactionDateStr,          // Transaction date as string
                        newBalance                   // New balance after deposit
                    );
                    transactionsDAO.addTransaction(transaction);

                    // Redirect to the dashboard after a successful deposit
                    response.sendRedirect("dashboard.jsp");
                } else {
                    throw new Exception("Balance update failed.");
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log the error
                request.setAttribute("errorMessage", "Error updating balance. Please try again.");
                request.getRequestDispatcher("deposit.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles deposit requests";
    }
}
