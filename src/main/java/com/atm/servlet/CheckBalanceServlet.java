package com.atm.servlet;

import com.atm.bean.Accounts;
import com.atm.bean.Users;
import com.atm.dao.AccountsDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckBalanceServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Get the session and user
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        // If user is not in session, redirect to login
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Fetch user's account balance from the database
        AccountsDAO accountsDAO = new AccountsDAO();
        Accounts account = accountsDAO.getAccountByUserId(user.getUserId());

        if (account != null) {
            // If account exists, get the balance
            Double balance = account.getBalance();
            if (balance == null) {
                balance = 0.0;  // Set balance to 0 if null
            }
            request.setAttribute("balance", balance);
        } else {
            request.setAttribute("balance", null);  // Handle account not found
        }

        // Forward to the checkBalance.jsp page
        request.getRequestDispatcher("checkBalance.jsp").forward(request, response);
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
        return "Check Balance Servlet";
    }
}
