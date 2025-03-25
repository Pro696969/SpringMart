<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- Include jQuery -->
    <script>
        function addToCart(itemId, itemName) {
            $.ajax({
                type: "POST",
                url: "/addToCart",
                data: { itemId: itemId },
                success: function(response) {
                    alert(itemName + " has been added to your cart!"); // Popup alert
                },
                error: function() {
                    alert("Failed to add item to cart.");
                }
            });
        }
    </script>
</head>
<body>
<h1>Welcome to the Homepage</h1>
<form>
    <label><input type="text" name="Search-bar" placeholder="Search here"></label>
    <button>Search</button>
    <button><a href="/Cart">Cart</a></button>
</form>
<c:if test="${not empty items}">
    <h2>Items List</h2>
    <table border="1">
        <thead>
        <tr>
            <th>Name</th>
            <th>Cost</th>
            <th>Stock</th>
            <th>Description</th>
            <th>Category</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${items}" var="item">
            <tr>
                <td>${item.name}</td>
                <td>${item.cost}</td>
                <td>${item.stock}</td>
                <td>${item.description}</td>
                <td>${item.category}</td>
                <td>
                    <button onclick="addToCart('${item.id}', '${item.name}')">Add to Cart</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty items}">
    <p>No items found.</p>
</c:if>
</body>
</html>