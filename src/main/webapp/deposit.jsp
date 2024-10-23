<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Deposit Money</title>
        <style>
            body {
                background: linear-gradient(to right, #0f2027, #203a43, #2c5364); /* Gradient background */
                color: #fff;
                font-family: 'Roboto', sans-serif;
                margin: 0;
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                position: relative;
            }
            .atm-screen {
                width: 400px;
                background-color: #1c1e22;
                padding: 30px;
                border-radius: 20px;
                border: none;
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.8);
                text-align: center;
                transition: transform 0.3s ease;
            }
            .atm-screen:hover {
                transform: translateY(-5px);
            }
            h2 {
                color: #00ff6a;
                margin-bottom: 30px;
                font-size: 26px;
                font-weight: bold;
            }
            form {
                margin-bottom: 20px;
            }
            input[type="number"] {
                width: 80%;
                padding: 10px;
                margin-top: 15px;
                border-radius: 8px;
                border: 2px solid #00ff6a;
                background-color: #444;
                color: #fff;
            }
            button {
                padding: 10px 20px;
                background-color: #00ff6a;
                color: white;
                border: none;
                cursor: pointer;
                font-size: 16px;
                border-radius: 8px;
                transition: background-color 0.3s ease, transform 0.3s ease;
            }
            button:hover {
                background-color: #00d45a;
                transform: translateY(-2px);
            }
            .atm-options a {
                display: block;
                margin-top: 20px;
                padding: 15px;
                background-color: #2e3136;
                color: #fff;
                text-decoration: none;
                font-size: 18px;
                border-radius: 8px;
                border: 2px solid transparent;
                transition: background-color 0.3s ease, border 0.3s ease, transform 0.3s ease;
                text-align: center;
            }
            .atm-options a:hover {
                background-color: #00ff6a;
                color: #000;
                border-color: #00ff6a;
                transform: translateY(-2px);
            }
            .error-message {
                color: red;
                margin-top: 10px;
            }
            /* Footer Section */
            .footer {
                position: absolute;
                bottom: 10px;
                right: 20px;
                font-size: 14px;
                color: #ddd;
                font-family: 'Roboto', sans-serif;
            }
            .footer a {
                color: #00ff6a;
                text-decoration: none;
                transition: color 0.3s ease;
            }
            .footer a:hover {
                color: #00d45a;
            }
        </style>
    </head>
    <body>
        <div class="atm-screen">
            <h2>Deposit Money</h2>
            <form action="DepositServlet" method="POST">
                <label for="amount">Enter Amount to Deposit:</label>
                <input type="number" id="amount" name="amount" required>
                <button type="submit" style="margin-top:10px;">Deposit</button>
            </form>
            <% if (request.getAttribute("errorMessage") != null) { %>
                <p class="error-message"><%= request.getAttribute("errorMessage") %></p>
            <% } %>
            <div class="atm-options">
                <a href="dashboard.jsp">Back to Dashboard</a>
            </div>
        </div>
        
        <!-- Footer Section for Credit -->
        <div class="footer">
            Developed by <a href="#">Anand Mewade</a>
        </div>
    </body>
</html>
