<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function addToCart(itemId, itemName, cost, description) {
            let qty = 1; // Default quantity set to 1
            $.ajax({
                type: "POST",
                url: "/addToCart",
                data: { itemId: itemId, itemName: itemName, cost: cost, description: description, qty: qty },
                success: function(response) {
                    alert(response);
                },
                error: function() {
                    alert("Failed to add item to cart.");
                }
            });
        }
        function searchItems() {
            var query = document.getElementById("searchBox").value.trim();
            if (query === "") return;

            fetch("/search/" + encodeURIComponent(query))
                .then(response => response.json())
                .then(data => {
                    let resultsDiv = document.getElementById("searchResults");
                    resultsDiv.innerHTML = "";

                    data.forEach(item => {
                        let highlightedName = item.name.replace(
                            new RegExp(query, "gi"),
                            match => `<span style="background-color: yellow; font-weight: bold;">${match}</span>`
                        );

                        resultsDiv.innerHTML += `<p>${highlightedName} - ${item.description} ($${item.price})</p>`;
                    });
                })
                .catch(error => console.error("Search failed", error));
        }
    </script>
</head>
<body>
<h1>Welcome to the Homepage</h1>
<h1> User Id: ${userid}</h1>
<form>
    <label><input type="text" id = "searchBox" name="Search-bar" placeholder="Search here"></label>
    <button onclick="searchItems()">Search</button>
    <div id="searchResults"></div>
    <a href="/Cart">Cart</a>
</form>
<form action="/logout" method="post">
    <button type="submit">Logout</button>
</form>
<c:if test="${not empty items}">
    <h2>Items List</h2>
    <table border="1">
        <thead>
        <tr>
            <th>Name</th>
            <th>Price(in Rs.)</th>
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
                    <button onclick="addToCart('${item.id}', '${item.name}', ${item.cost}, '${item.description}')">
                        Add to Cart
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty items}">
    <p>No items found.</p>
</c:if>

<script>
    $(document).ready(function () {
        var userId = "${userid}";
        if (!userId) {
            window.location.href = "/signup";
        }
    });
</script>
</body>
</html>