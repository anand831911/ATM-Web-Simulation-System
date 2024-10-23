package com.atm.servlet;

import com.atm.bean.Accounts;
import com.atm.bean.Transfers;
import com.atm.bean.Users;
import com.atm.bean.Transactions;
import com.atm.dao.AccountsDAO;
import com.atm.dao.TransferDAO;
import com.atm.dao.TransactionsDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TransferServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        
        String toAccountNumber = request.getParameter("toAccountNumber");
        String amountParam = request.getParameter("amount");

        if (amountParam == null || amountParam.isEmpty()) {
            request.setAttribute("errorMessage", "Amount cannot be empty.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        try {
            double amount = Double.parseDouble(amountParam);

            // DAO Objects
            AccountsDAO accountsDAO = new AccountsDAO();
            TransferDAO transfersDAO = new TransferDAO();
            TransactionsDAO transactionsDAO = new TransactionsDAO();

            // Get sender's and recipient's accounts
            Accounts fromAccount = accountsDAO.getAccountByUserId(user.getUserId());
            Accounts toAccount = accountsDAO.getAccountByAccountNumber(toAccountNumber);

            if (toAccount == null) {
                request.setAttribute("errorMessage", "Recipient account not found.");
                request.getRequestDispatcher("transfer.jsp").forward(request, response);
                return;
            }

            if (fromAccount.getBalance() < amount) {
                request.setAttribute("errorMessage", "Insufficient balance.");
                request.getRequestDispatcher("transfer.jsp").forward(request, response);
                return;
            }

            // Deduct money from sender's account
            double newFromBalance = fromAccount.getBalance() - amount;
            boolean updateFromAccount = accountsDAO.updateBalance(fromAccount.getAccountId(), newFromBalance);

            // Add money to recipient's account
            double newToBalance = toAccount.getBalance() + amount;
            boolean updateToAccount = accountsDAO.updateBalance(toAccount.getAccountId(), newToBalance);

            if (updateFromAccount && updateToAccount) {
                // Log the transfer
                Transfers transfer = new Transfers(fromAccount.getAccountId(), toAccount.getAccountId(), amount, new Date());
                transfersDAO.addTransfer(transfer);

                // Log transactions for both accounts
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String transactionDateStr = sdf.format(new Date());

                // Log for sender
                Transactions senderTransaction = new Transactions(
                    fromAccount.getAccountId(),
                    fromAccount.getAccountNumber(),  // Sender's account number
                    "transfer",
                    amount,
                    transactionDateStr,
                    newFromBalance
                );
                transactionsDAO.addTransaction(senderTransaction);

                // Log for recipient
                Transactions recipientTransaction = new Transactions(
                    toAccount.getAccountId(),
                    toAccount.getAccountNumber(),  // Recipient's account number
                    "transfer",
                    amount,
                    transactionDateStr,
                    newToBalance
                );
                transactionsDAO.addTransaction(recipientTransaction);

                // Update session with new balance and show success message
                session.setAttribute("balance", newFromBalance);
                request.setAttribute("successMessage", "Transfer successful. New Balance: " + newFromBalance);
                request.getRequestDispatcher("transfer.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Transfer failed. Please try again.");
                request.getRequestDispatcher("transfer.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid amount format.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
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
        return "TransferServlet handles money transfers between accounts.";
    }
}
