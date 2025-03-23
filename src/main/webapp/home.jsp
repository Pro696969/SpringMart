<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Home page</title>
</head>
<body>
<form>
    Enter: <input type="text" name="typing">
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
            <th>Cost </th>
            <th>Stock</th>
            <th>Category</th>
            <th>Review</th>
        </tr>
        <c:forEach var="item" items="${items}">
            <tr>
                <td>${item.Name}</td>
                <td>${item.Description}</td>
                <td>${item.Cost}</td>
                <td>${item.Stock}</td>
                <td>${item.Category}</td>
                <td>${item.Review}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>

