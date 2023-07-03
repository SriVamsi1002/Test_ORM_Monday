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
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        
        h1 {
            color: #333;
            text-align: center;
            margin-top: 20px;
        }
        
        form {
            text-align: center;
            margin-top: 20px;
        }
        
        input[type="submit"] {
            padding: 10px 20px;
            background-color: #333;
            color: #fff;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }
        
        input[type="submit"]:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Change Password</h1>
        <form action="editsuccess" method="post">
        <div class="mb-3">
                <label for="newPassword" class="form-label">User ID:</label>
                <div class="input-group">
                    <input type="text" name="id" class="form-control" required readonly value="${id}">
                </div>
            </div>
            <div class="mb-3">
                <label for="oldPassword" class="form-label">Enter Old Password:</label>
                <div class="input-group">
                    <input type="password" id="oldPassword" name="oldPassword" class="form-control" required>
                    <button type="button" class="btn btn-outline-secondary" id="toggleOldPassword" onclick="togglePasswordVisibility('oldPassword')">
                        <i id="oldPasswordIcon" class="bi bi-eye"></i>
                    </button>
                </div>
            </div>
            <div class="mb-3">
                <label for="newPassword" class="form-label">Enter New Password:</label>
                <div class="input-group">
                    <input type="password" id="newPassword" name="newPassword" class="form-control" required>
                    <button type="button" class="btn btn-outline-secondary" id="toggleNewPassword" onclick="togglePasswordVisibility('newPassword')">
                        <i id="newPasswordIcon" class="bi bi-eye"></i>
                    </button>
                </div>
            </div>
            <div class="mb-3">
                <label for="confirmPassword" class="form-label">Confirm Password:</label>
                <div class="input-group">
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="showConfirmPassword"  onclick="togglePasswordVisibility('confirmPassword')" >
                        <label class="form-check-label" for="showConfirmPassword">
                            Show Password
                        </label>
                    </div>
                </div>
            </div>
            <input type="submit" value="Change Password" class="btn btn-primary" onclick="window.location.href='profile'">
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
    	var fieldId;
        function togglePasswordVisibility(fieldId) {
            const field = document.getElementById(fieldId);
            const icon = document.getElementById(fieldId + 'Icon');

            if (field.type === 'password') {
                field.type = 'text';
                icon.classList.remove('bi-eye');
                icon.classList.add('bi-eye-slash');
            } else {
                field.type = 'password';
                icon.classList.remove('bi-eye-slash');
                icon.classList.add('bi-eye');
            }
        }
    </script>
</body>
</html>
