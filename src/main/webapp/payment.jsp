<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Page</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

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
    </script>
</head>
<body>

<h2>Complete Your Payment</h2>

<!-- Display Total Cost -->
<h3>Total Amount: Rs. <span id="displayTotalCost">${totalCost}</span></h3>
<input type="hidden" id="totalCost" value="${totalCost}">

<!-- Discount Coupon Section -->
<label for="couponName">Enter Discount Coupon ID:</label>
<input type="text" id="couponName" name="couponName">
<button type="button" onclick="applyDiscount()">Apply Discount</button>

<h3 id="discountAmount" style="color: green;"></h3>
<h3>Final Amount: <span id="finalAmount">Rs. ${totalCost}</span></h3>

<!-- Payment Form -->
<form action="/confirmOrder" method="post">
    <input type="hidden" name="totalCost" value="${totalCost}">
    <input type="hidden" name="finalAmount" id="finalAmountInput" value="${totalCost}">

    <label>Select Payment Method:</label><br>
    <input type="radio" name="paymentMethod" value="Credit Card" required> Credit Card<br>
    <input type="radio" name="paymentMethod" value="Cash on Delivery" required> Cash on Delivery<br>
    <input type="radio" name="paymentMethod" value="UPI" required> UPI<br>

    <div id="upiSection" style="display:none;">
        <label for="upiId">Enter UPI ID:</label>
        <input type="text" id="upiId" name="upiId">
    </div>

    <br>
    <button type="submit">Confirm Order</button>
</form>

<script>
    document.querySelectorAll('input[name="paymentMethod"]').forEach((elem) => {
        elem.addEventListener("change", function() {
            let upiSection = document.getElementById("upiSection");
            upiSection.style.display = this.value === "UPI" ? "block" : "none";
        });
    });
</script>

<br>
<a href="/orders"><button> Back to Orders </button></a>
</body>
</html>
