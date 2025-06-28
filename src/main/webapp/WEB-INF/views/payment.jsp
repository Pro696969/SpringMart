<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Page</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/payment.css">
    <style>
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 12px;
            border-radius: 5px;
            margin: 10px 0;
            border: 1px solid #f5c6cb;
        }
        .success-message {
            background-color: #d4edda;
            color: #155724;
            padding: 12px;
            border-radius: 5px;
            margin: 10px 0;
            border: 1px solid #c3e6cb;
        }
    </style>
    <script>
        function applyDiscount() {
            let totalCost = parseFloat(document.getElementById("totalCost").value);
            let couponName = document.getElementById("couponName").value;

            if (couponName.trim() === "") {
                alert("Please enter a coupon code.");
                return;
            }

            $.ajax({
                url: "/validateCoupon",
                type: "GET",
                data: { couponName: couponName, totalCost: totalCost },
                success: function(response) {
                    if (response.valid) {
                        document.getElementById("finalAmount").innerText = "Rs. " + response.finalAmount.toFixed(2);
                        document.getElementById("discountAmount").innerText = "Discount Applied: Rs. " + response.discountAmount.toFixed(2);
                        document.getElementById("finalAmountInput").value = response.finalAmount;
                    } else {
                        alert(response.message);
                    }
                },
                error: function() {
                    alert("Error validating coupon. Please try again.");
                }
            });
        }

        function validateForm() {
            let paymentMethod = document.querySelector('input[name="paymentMethod"]:checked');
            if (!paymentMethod) {
                alert("Please select a payment method.");
                return false;
            }

            if (paymentMethod.value === "UPI") {
                let upiId = document.getElementById("upiId").value.trim();
                if (upiId === "") {
                    alert("Please enter your UPI ID.");
                    return false;
                }
            }

            return true;
        }
    </script>
</head>
<body>

<div class="header">
    <h1>Complete Payment</h1>
    <a href="/orders"><button class="btn top-right-btn">Back to Orders</button></a>
</div>

<div class="container">
    <!-- Display error/success messages -->
    <c:if test="${not empty error}">
        <div class="error-message">
                ${error}
        </div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="success-message">
                ${success}
        </div>
    </c:if>

    <!-- Display Total Cost -->
    <h3>Total Amount: Rs. <span id="displayTotalCost">${totalCost}</span></h3>
    <input type="hidden" id="totalCost" value="${totalCost}">

    <!-- Discount Coupon Section -->
    <div class="coupon-section">
        <label for="couponName">Discount Coupon:</label>
        <input type="text" id="couponName" name="couponName">
        <button type="button" class="btn" onclick="applyDiscount()">Apply</button>
    </div>

    <h3 id="discountAmount" style="color: green;"></h3>
    <h3>Final Amount: <span id="finalAmount">Rs. ${totalCost}</span></h3>

    <!-- Payment Form -->
    <form action="/confirmOrder" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="totalCost" value="${totalCost}">
        <input type="hidden" name="finalAmount" id="finalAmountInput" value="${totalCost}">

        <div class="payment-methods">
            <label class="section-label">Select Payment Method:</label><br><br>

            <label class="payment-option">
                <input type="radio" name="paymentMethod" value="Credit Card" required>
                💳 Credit Card
            </label>

            <label class="payment-option">
                <input type="radio" name="paymentMethod" value="Cash on Delivery" required>
                💵 Cash on Delivery
            </label>

            <label class="payment-option">
                <input type="radio" name="paymentMethod" value="UPI" required onchange="toggleUPI(true)">
                📱 UPI
            </label>

            <div id="upiSection" style="display: none; margin-left: 25px; margin-top: 10px;">
                <label for="upiId">Enter UPI ID:</label>
                <input type="text" id="upiId" name="upiId" placeholder="example@upi">
            </div>
        </div>

        <br>
        <button type="submit" class="btn">Confirm Order</button>
    </form>

</div>

<script>
    document.querySelectorAll('input[name="paymentMethod"]').forEach((elem) => {
        elem.addEventListener("change", function() {
            let upiSection = document.getElementById("upiSection");
            upiSection.style.display = this.value === "UPI" ? "block" : "none";
        });
    });
</script>

</body>
</html>