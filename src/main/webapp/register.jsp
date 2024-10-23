<%-- 
    Document   : register
    Created on : 20 Oct, 2024
    Author     : Anand Mewade
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ATM Registration</title>
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
                width: 400px;
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
            .submit-button {
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
            .submit-button:hover {
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
            <h2>ATM Registration</h2>
            <form action="registerServlet" method="POST" onsubmit="return validateForm()">
                <input type="text" id="accountNumber" name="accountNumber" class="input-field" placeholder="Enter Account Number">
                <input type="text" id="userName" name="userName" class="input-field" placeholder="Enter User Name">
                <input type="password" id="pin" name="pin" class="input-field" placeholder="Enter PIN">
                <input type="text" id="phoneNumber" name="phoneNumber" class="input-field" placeholder="Enter Phone Number">
                <input type="email" id="email" name="email" class="input-field" placeholder="Enter Email">
                <input type="text" id="amount" name="amount" class="input-field" placeholder="Enter Initial Amount">
                <button type="submit" class="submit-button">Register</button>
                
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
                const userName = document.getElementById('userName').value;
                const pin = document.getElementById('pin').value;
                const phoneNumber = document.getElementById('phoneNumber').value;
                const email = document.getElementById('email').value;
                const amount = document.getElementById('amount').value;
                const message = document.getElementById('message');
                
                if (accountNumber === '' || userName === '' || pin === '' || phoneNumber === '' || email === '' || amount === '') {
                    message.innerHTML = '<span class="error">All fields are required.</span>';
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
                if (phoneNumber.length !== 10 || isNaN(phoneNumber)) {
                    message.innerHTML = '<span class="error">Invalid phone number. Must be 10 digits.</span>';
                    return false;
                }
                if (amount <= 0 || isNaN(amount)) {
                    message.innerHTML = '<span class="error">Amount must be a positive number.</span>';
                    return false;
                }
                return true;
            }
        </script>
    </body>
</html>
