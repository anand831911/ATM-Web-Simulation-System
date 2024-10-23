<%-- 
    Document   : login
    Created on : 2 Oct, 2024, 12:20:30 PM
    Author     : Anand Mewade
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ATM Login</title>
        <style>
            body {
                background: linear-gradient(to right, #141e30, #243b55);
                font-family: 'Roboto', sans-serif;
                color: #fff;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .atm-screen {
                width: 360px;
                background-color: #1c1e22;
                border-radius: 20px;
                padding: 30px;
                text-align: center;
                box-shadow: 0 12px 24px rgba(0, 0, 0, 0.8);
                transition: transform 0.3s ease;
            }
            .atm-screen:hover {
                transform: translateY(-5px);
            }
            h2 {
                color: #00ff6a;
                font-size: 26px;
                font-weight: bold;
                margin-bottom: 25px;
            }
            .input-field {
                width: 90%;
                padding: 14px;
                margin: 12px 0;
                border-radius: 10px;
                border: 1px solid #555;
                background-color: #2e3136;
                color: #fff;
                font-size: 18px;
                outline: none;
                text-align: center;
                transition: border 0.3s ease;
            }
            .input-field:focus {
                border-color: #00ff6a;
            }
            .login-button {
                width: 100%;
                padding: 15px;
                background: linear-gradient(to right, #00c6ff, #0072ff);
                color: white;
                border: none;
                font-size: 20px;
                font-weight: bold;
                cursor: pointer;
                border-radius: 8px;
                transition: background-color 0.3s ease, transform 0.3s ease;
            }
            .login-button:hover {
                background-color: #0057e7;
                transform: translateY(-2px);
            }
            .message {
                margin-top: 20px;
                font-size: 16px;
            }
            .error {
                color: #ff4d4d;
            }
            .register-button {
                width: 100%;
                padding: 12px;
                background-color: #ff7b00;
                color: white;
                font-size: 18px;
                margin-top: 20px;
                border: none;
                cursor: pointer;
                border-radius: 8px;
                transition: background-color 0.3s ease, transform 0.3s ease;
            }
            .register-button:hover {
                background-color: #ff6200;
                transform: translateY(-2px);
            }
            .footer {
                margin-top: 30px;
                font-size: 12px;
                color: #aaa;
                text-align: center;
            }
            .credit {
                position: absolute;
                bottom: 10px;
                right: 10px;
                font-size: 12px;
                color: #ccc;
                font-style: italic;
            }
        </style>
    </head>
    <body>
        <div class="atm-screen">
            <h2>ATM Login</h2>
            <form action="Loginservlet" method="POST" onsubmit="return validateForm()">
                <input type="text" id="accountNumber" name="accountNumber" class="input-field" placeholder="Enter Account Number">
                <input type="password" id="pin" name="pin" class="input-field" placeholder="Enter PIN">
                <button type="submit" class="login-button">Login</button>
                <button type="button" class="register-button" onclick="window.location.href='register.jsp'">Register</button>
            </form>
            <div class="message" id="message"></div>
            <div class="footer">© 2024 ATM Services | All rights reserved</div>
        </div>
        
        <div class="credit">
            Developed by Anand Mewade
        </div>

        <script>
            function validateForm() {
                const accountNumber = document.getElementById('accountNumber').value;
                const pin = document.getElementById('pin').value;
                const message = document.getElementById('message');
                
                if (accountNumber === '' || pin === '') {
                    message.innerHTML = '<span class="error">Both fields are required.</span>';
                    return false;
                }
                if (accountNumber.length !== 10 || isNaN(accountNumber)) {
                    message.innerHTML = '<span class="error">Invalid account number. Must be 10 digits.</span>';
                    return false;
                }
                if (pin.length !== 4 || isNaN(pin)) {
                    message.innerHTML = '<span class="error">PIN must be 4 digits.</span>';
                    return false;
                }
                return true;
            }
        </script>
    </body>
</html>
