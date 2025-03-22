<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Sign Up Page</title>
</head>
<body>
<h1>Hello World! This is the signup page</h1>
<form action="registered" method="post" >
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
    <input type="submit" value="Submit">
</form>
<a href="login.jsp">Login</a>

<c:if test="${not empty error}">
    <script>
        alert("${error}");
    </script>
</c:if>
</body>
</html>
