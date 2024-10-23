package com.atm.servlet;

import com.atm.bean.Accounts;
import com.atm.bean.Transactions;
import com.atm.bean.Users;
import com.atm.dao.AccountsDAO;
import com.atm.dao.TransactionsDAO;
import com.atm.dao.UsersDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class registerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Get parameters from the request
        String accountNumber = request.getParameter("accountNumber");
        String userName = request.getParameter("userName");
        String pin = request.getParameter("pin");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        int amount = Integer.parseInt(request.getParameter("amount"));

        // Create a Users object and insert it into the database
        Users u = new Users();
        u.setUserName(userName);
        u.setAccountNumber(accountNumber);
        u.setPin(pin);
        u.setPhoneNumber(phoneNumber);
        u.setEmail(email);

        UsersDAO ud = new UsersDAO();
        int x = ud.insertUser(u); // Insert user into database

        // Retrieve the user ID based on the account number
        Users u1 = ud.findUser(accountNumber);
        int id = u1.getUserId();

        // If the user is successfully inserted and the ID is valid
        if (id > 0) {
            // Insert data into the account table
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = dateFormat.format(date);

            Accounts ac = new Accounts();
            ac.setUserId(id);
            ac.setAccountNumber(accountNumber);
            ac.setBalance(amount);
            ac.setLastTransaction(date1);
            AccountsDAO acdao = new AccountsDAO();
            int z = acdao.insertAccountToRegister(ac); // Insert account into database

            // Fetch the account ID from the account table
            Accounts acc = acdao.getAccountByAccountNumber(accountNumber);
            int accountId = acc.getAccountId();

            // Insert data into the transactions table
            Transactions tx = new Transactions();
            tx.setAccountId(accountId);
            tx.setTransactionType("Initial Deposit");
            tx.setAmount(amount);
            tx.setTransactionDate(date1);
            tx.setBalanceAfter(amount);
            tx.setAccountNumber(accountNumber);
            TransactionsDAO txd = new TransactionsDAO();
            int y = txd.insertTransactionforRegister(tx); // Insert transaction into database

            // Forward to the login page if the transaction is successful
            if (y > 0) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);  // Forward to login page
            } else {
                // Handle failure case if necessary
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error registering user");
            }
        } else {
            // Handle failure case if user registration fails
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User registration failed");
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
        return "Short description";
    }
}
