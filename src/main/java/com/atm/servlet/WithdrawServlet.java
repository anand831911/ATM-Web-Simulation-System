package com.atm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import com.atm.bean.Users;
import com.atm.bean.Accounts;
import com.atm.bean.Transactions;
import com.atm.dao.AccountsDAO;
import com.atm.dao.TransactionsDAO;

public class WithdrawServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Session check for logged-in user
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("login.jsp"); // Redirect to login if session expired or not set
                return;
            }

            // Retrieve the amount from the request and validate
            String amountStr = request.getParameter("amount");
            if (amountStr == null || amountStr.isEmpty()) {
                request.setAttribute("errorMessage", "Amount is required.");
                request.getRequestDispatcher("withdraw.jsp").forward(request, response);
                return;
            }

            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                request.setAttribute("errorMessage", "Invalid amount. Please enter a positive value.");
                request.getRequestDispatcher("withdraw.jsp").forward(request, response);
                return;
            }

            // Access DAOs
            AccountsDAO accountsDAO = new AccountsDAO();
            TransactionsDAO transactionsDAO = new TransactionsDAO();

            // Get user account information
            Accounts account = accountsDAO.getAccountByUserId(user.getUserId());

            // Check for sufficient balance
            if (account.getBalance() < amount) {
                request.setAttribute("errorMessage", "Insufficient balance.");
                request.getRequestDispatcher("withdraw.jsp").forward(request, response);
                return;
            }

            // Perform withdrawal
            double newBalance = account.getBalance() - amount;
            boolean success = accountsDAO.updateBalance(account.getAccountId(), newBalance);

            if (success) {
                // Log the withdrawal transaction
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String transactionDateStr = sdf.format(new Date());

                Transactions transaction = new Transactions(
                    account.getAccountId(),
                    account.getAccountNumber(),  // Include the account_number
                    "withdraw",
                    amount,
                    transactionDateStr,
                    newBalance
                );
                transactionsDAO.addTransaction(transaction);

                // Update session and redirect to dashboard
                session.setAttribute("balance", newBalance);
                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Withdrawal failed. Please try again.");
                request.getRequestDispatcher("withdraw.jsp").forward(request, response);
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
        return "Handles withdrawal requests.";
    }
}
