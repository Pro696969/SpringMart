<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Home page</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
<form>
    Enter: <label>
    <input type="text" name="typing">
</label>
    <button>Search</button>
    <button>Cart</button>
</form>
<h2>Available Items</h2>

<c:if test="${empty items}">
    <p class="no-items">No items found!</p>
</c:if>

<c:if test="${not empty items}">
    <table>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Cost</th>
            <th>Stock</th>
            <th>Category</th>
            <th>Review</th>
            <th>Action</th>
        </tr>
        <c:forEach var="item" items="${items}">
            <tr>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td>$${item.cost}</td>
                <td>${item.stock}</td>
                <td>${item.category}</td>
                <td>${item.review eq 'null' ? 'No reviews yet' : item.review}</td>
                <td>
                    <form method="post">
                        <input type="hidden" name="itemId" value="${item.id}">
                        <button type="submit">Add to Cart</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>