package com.atm.servlet;

import com.atm.bean.Transactions;
import com.atm.bean.Users;
import com.atm.dao.TransactionsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TransactionHistoryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user != null) {
            String accountNumber = user.getAccountNumber();  // Get the logged-in user's account number

            TransactionsDAO transactionsDAO = new TransactionsDAO();
            // Fetch transactions for the logged-in user by account number
            List<Transactions> transactionList = transactionsDAO.getTransactionsByAccountNumber(accountNumber);

            // Start HTML response
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Transaction History</title>");
            out.println("<style>");
            out.println("body { background: linear-gradient(to right, #0f2027, #203a43, #2c5364); color: #fff; font-family: 'Roboto', sans-serif; margin: 0; padding: 20px; }");
            out.println(".atm-screen { width: 80%; margin: 0 auto; background-color: #1c1e22; padding: 30px; border-radius: 20px; box-shadow: 0 8px 20px rgba(0, 0, 0, 0.8); }");
            out.println("h1 { text-align: center; color: #00ff6a; font-size: 32px; font-weight: bold; }");
            out.println("table { width: 100%; margin-top: 20px; border-collapse: collapse; }");
            out.println("table, th, td { border: 1px solid #00ff6a; color: #fff; }");
            out.println("th, td { padding: 15px; text-align: center; }");
            out.println("th { background-color: #00ff6a; color: #fff; }");
            out.println("td { background-color: #444; }");
            out.println(".no-transactions { text-align: center; color: #ff6666; }");
            out.println(".back-link { display: block; margin: 20px auto; width: fit-content; padding: 10px 20px; background-color: #00ff6a; text-align: center; color: #fff; text-decoration: none; border-radius: 8px; transition: background-color 0.3s ease; }");
            out.println(".back-link:hover { background-color: #00d45a; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='atm-screen'>");
            out.println("<h1>Transaction History</h1>");

            // Check if the transaction list is empty
            if (transactionList == null || transactionList.isEmpty()) {
                out.println("<p class='no-transactions'>No transactions found for this account.</p>");
            } else {
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Transaction Type</th>");
                out.println("<th>Amount</th>");
                out.println("<th>Date</th>");
                out.println("<th>Balance After</th>");
                out.println("</tr>");

                // Loop through transactions and display in table
                for (Transactions transaction : transactionList) {
                    out.println("<tr>");
                    out.println("<td>" + transaction.getTransactionType() + "</td>");
                    out.println("<td>" + transaction.getAmount() + "</td>");
                    out.println("<td>" + transaction.getTransactionDate() + "</td>");
                    out.println("<td>" + transaction.getBalanceAfter() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

            // Add a link back to the dashboard
            out.println("<a href='dashboard.jsp' class='back-link'>Back to Dashboard</a>");

            out.println("</div>"); // End of atm-screen
            out.println("</body>");
            out.println("</html>");
        } else {
            // Redirect to login if the user is not logged in
            response.sendRedirect("login.jsp");
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
        return "Transaction History Servlet";
    }
}
