<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Orders</title>
</head>
<body>
<h1>Your Cart</h1>

<c:if test="${empty cartItems}">
    <p>Your cart is empty. <a href="/">Shop Now</a></p>
</c:if>

<c:if test="${not empty cartItems}">
    <table border="1">
        <thead>
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="totalCost" value="0" />
        <c:forEach var="item" items="${cartItems}">
            <tr>
                <td>${item.name}</td>
                <td>Rs. ${item.cost}</td>
                <td>${item.qty}</td>
                <td>${item.description}</td>
            </tr>
            <c:set var="totalCost" value="${totalCost + (item.cost * item.qty)}" />
        </c:forEach>
        </tbody>
    </table>

    <h3>Total Cost: Rs. <span id="totalCost">${totalCost}</span></h3>
</c:if>
    <a href="views/payment.jsp"><button>Proceed to Payment</button></a>
<%--        <button onclick="location.href='http://localhost:8080/payment.jsp'">Proceed to Payment</button>--%>
    <a href="views/cart.jsp"><button>Back to Cart</button></a>
<br>
</body>
</html>
