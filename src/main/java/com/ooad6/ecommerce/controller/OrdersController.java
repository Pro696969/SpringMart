package com.ooad6.ecommerce.controller;

import com.ooad6.ecommerce.model.Cart;
import com.ooad6.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrdersController {

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/orders")
    public String showOrders(Model model, HttpSession session) {
        // Retrieve user ID from session (assuming it is stored as an Integer)
        Object userIdObj = session.getAttribute("userid");

        // Ensure userid exists and is an integer
        if (userIdObj instanceof Integer) {
            int userId = (Integer) userIdObj;

            // Fetch all cart items for this user
            List<Cart> cartItems = cartRepository.findByUserid(userId);
            model.addAttribute("cartItems", cartItems);
            return "orders.jsp";  // Redirect to orders.jsp
        } else {
            return "redirect:/login"; // Redirect to login if userid is missing
        }
    }
}
