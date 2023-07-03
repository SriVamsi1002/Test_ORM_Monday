<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Profile Page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }

        .container {
            margin-top: 50px;
            max-width: 600px;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            font-size: 24px;
            margin-bottom: 30px;
            text-align: center;
        }

        table {
            width: 100%;
            margin-bottom: 30px;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        th {
            text-align: left;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            font-weight: bold;
        }

        .form-group input[type="text"], .form-group input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }

        .form-group .form-check {
            margin-top: 10px;
        }

        .form-group .form-check label {
            margin-left: 5px;
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }

        .btn-primary:hover {
            background-color: #0069d9;
            border-color: #0062cc;
        }

        .btn-primary:focus, .btn-primary.focus {
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.5);
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Profile Details</h1>
        <table>
            <tr>
                <th>ID:</th>
                <td>${user.getUserId()}</td>
            </tr>
            <tr>
                <th>Name:</th>
                <td>${user.getUserDisplayName()}</td>
            </tr>
            <tr>
                <th>Password:</th>
                <td>
                    <input type="password" id="passwordField" value="${user.getUserPassword()}" readonly class="form-control" />
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="togglePassword" />
                        <label class="form-check-label" for="togglePassword">
                            Show Password
                        </label>
                    </div>
                </td>
            </tr>
            <tr>
                <th>Emp ID:</th>
                <td>${user.getUserEmployeeId()}</td>
            </tr>
            <tr>
                <th>User Role:</th>
                <td>${user.getUserRole().getRoleId()}</td>
            </tr>
        </table>
        <form action="edit" method="post">
            <input type="hidden" name="id" value="${user.getUserId()}" />
            <div class="form-group text-center">
                <input type="submit" value="Edit Profile" class="btn btn-primary" />
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
    const passwordField = document.getElementById('passwordField');
    const togglePassword = document.getElementById('togglePassword');

    togglePassword.addEventListener('change', function() {
        if (togglePassword.checked) {
            passwordField.type = 'text';
        } else {
            passwordField.type = 'password';
        }
    });
    </script>
</body>
</html>
