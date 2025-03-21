<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Sign Up Page</title>
</head>
<body>
<h1>Hello World! This is the signup page</h1>
<form onsubmit="return validateForm(event)">
    Enter Your Name: <input type="text" name="name" required><br>
    Enter Your Email-ID: <input type="email" name="email" required><br>
    Enter Your Password: <input type="password" id="password" name="password" required><br>
    Confirm Password: <input type="password" id="checkpassw" name="checkpassw" required><br>
    Enter Your Address: <textarea name="address" required></textarea><br>
    Enter Your Phone Number: <input type="text" name="phno" required><br>
    Enter Country: <select name="country" id="country" required>
    <option value="">Select a country</option>
    <option value="USA">United States</option>
    <option value="China">China</option>
    <option value="India">India</option>
    <option value="Germany">Germany</option>
    <option value="UK">United Kingdom</option>
    <option value="France">France</option>
    <option value="Japan">Japan</option>
    <option value="Canada">Canada</option>
    <option value="Australia">Australia</option>
    <option value="Brazil">Brazil</option>
</select><br>
    Your UserId Is: <input type="text" id="userid" name="userid" readonly><br>
    <input type="submit" value="Submit">
</form>

<script type="text/javascript">
    function validateForm(event) {
        event.preventDefault(); // Prevent form submission

        var password = document.getElementById("password").value;
        var checkpassw = document.getElementById("checkpassw").value;

        if (password !== checkpassw) {
            alert("Passwords do not match! Please re-enter.");
            return false;
        }

        generateid();
        alert("User Registered Successfully");
    }

    function generateid() {
        var min = 100000;
        var max = 999999;
        var userid = Math.floor(Math.random() * (max - min + 1)) + min;
        document.getElementById("userid").value = userid;
    }
</script>
</body>
</html>
