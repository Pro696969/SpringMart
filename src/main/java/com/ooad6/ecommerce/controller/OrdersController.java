package com.ooad6.ecommerce.controller;

import com.ooad6.ecommerce.model.Cart;
import com.ooad6.ecommerce.model.Orders;
import com.ooad6.ecommerce.repository.CartRepository;
import com.ooad6.ecommerce.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Controller
public class OrdersController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/orders")
    public String showOrders(Model model, HttpSession session) {
        // Retrieve user ID from session (assuming it is stored as an Integer)
        Object userIdObj = session.getAttribute("userid");

        // Ensure userid exists and is an integer
        if (userIdObj instanceof Integer) {
            int userId = (Integer) userIdObj;

            // Fetch all cart items for this user
            List<Cart> cartItems = cartRepository.findByUserid(userId);
            System.out.println(cartItems.toString());
            session.setAttribute("cartItems", cartItems);
            model.addAttribute("cartItems", cartItems);
            return "orders.jsp";  // Redirect to orders.jsp
        } else {
            return "redirect:/login"; // Redirect to login if userid is missing
        }
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam("paymentMethod") String paymentMethod, HttpSession session) {
        Object userIdObj = session.getAttribute("userid");

        if (userIdObj instanceof Integer) {
            int userId = (Integer) userIdObj;

            // Retrieve cart items from session
            List<Cart> cartItems = (List<Cart>) session.getAttribute("cartItems");

            if (cartItems == null || cartItems.isEmpty()) {
                session.setAttribute("orderMessage", "Your cart is empty. Add items before confirming the order.");
                return "redirect:/orders";
            }

            // Create new order
            Orders newOrder = new Orders();
            int orderId = new Random().nextInt(900000) + 100000; // hehe re-used logik
            newOrder.setOrderId(orderId);
            newOrder.setUserId(userId);
            newOrder.setProdList(cartItems);
            newOrder.setPaymentMethod(paymentMethod);
            newOrder.setTimestamp(LocalDateTime.now());
            System.out.println(newOrder.toString());

            // Save order to MongoDB
            ordersRepository.save(newOrder);

            // Clear the cart after order confirmation
            cartRepository.deleteAll(cartItems);
            session.removeAttribute("cartItems");

            session.setAttribute("orderMessage", "Order placed successfully! Order ID: " + newOrder.getOrderId());
            return "thankyou.jsp";
        } else {
            return "redirect:/login"; // Redirect to login if userid is missing
        }
    }
}
