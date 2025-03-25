<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
<h1>Your Shopping Cart</h1>

<c:if test="${not empty sessionScope.cart}">
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Cost</th>
            <th>Category</th>
        </tr>
        <c:forEach items="${sessionScope.cart}" var="item">
            <tr>
                <td>${item.name}</td>
                <td>${item.cost}</td>
                <td>${item.category}</td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <form action="clearCart.jsp" method="post">
        <button type="submit">Clear Cart</button>
    </form>
</c:if>

<c:if test="${empty sessionScope.cart}">
    <p>Your cart is empty.</p>
</c:if>

<a href="/Homepage">Back to Home</a>
</body>
</html>
