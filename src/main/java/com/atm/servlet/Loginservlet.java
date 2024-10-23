package com.atm.servlet;

import com.atm.bean.Users;
import com.atm.dao.UsersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Loginservlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String accountNumber = request.getParameter("accountNumber");
            String pin = request.getParameter("pin");

            UsersDAO usersDAO = new UsersDAO();
            Users user = usersDAO.validateLogin(accountNumber, pin);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userName", user.getUserName()); // Set username in session
                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid credentials");
                request.getRequestDispatcher("login.jsp").forward(request, response);
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
        return "Short description";
    }
}
