<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Home page</title>
</head>
<body>
<form>
    Enter: <input type="text" name="typing">
</form>
<button>Search</button>
<button>Cart</button>
<h2>Items Available</h2>

<h2>Available Items</h2>

<c:if test="${empty items}">
    <p class="no-items">No items found!</p>
</c:if>

<c:if test="${not empty items}">
    <table>
        <c:forEach var="item" items="${items}">
            <c:forEach var="lini" items="${item}">
                <tr>
                    <td>${lini.name}</td>
                    <td>${description}</td>
                    <td>${cost}</td>
                    <td>${stock}</td>
                    <td>${category}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</c:if>
</body>
</html>

