package com.ooad6.ecommerce.controller;

import com.ooad6.ecommerce.factory.OrderFactory;
import com.ooad6.ecommerce.model.Cart;
import com.ooad6.ecommerce.model.Items;
import com.ooad6.ecommerce.model.Orders;
import com.ooad6.ecommerce.model.User;
import com.ooad6.ecommerce.observer.OrderObserver;
import com.ooad6.ecommerce.repository.CartRepository;
import com.ooad6.ecommerce.repository.ItemsShow;
import com.ooad6.ecommerce.repository.OrdersRepository;
import com.ooad6.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class OrdersController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ItemsShow itemRepository; // Added for stock management

    @Autowired
    private OrderFactory orderFactory;

    @Autowired
    private List<OrderObserver> observers;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/orders")
    public String showOrders(Model model, HttpSession session) {
        Object userIdObj = session.getAttribute("userid");

        if (userIdObj instanceof Integer) {
            int userId = (Integer) userIdObj;
            List<Cart> cartItems = cartRepository.findByUserid(userId);
            session.setAttribute("cartItems", cartItems);
            model.addAttribute("cartItems", cartItems);
            return "orders";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam("paymentMethod") String paymentMethod,
                               HttpSession session,
                               Model model) {
        Object userIdObj = session.getAttribute("userid");

        Optional<User> user = userRepository.findByuserId((Integer) userIdObj);
        model.addAttribute("username", user.get().getName());

        if (userIdObj instanceof Integer) {
            int userId = (Integer) userIdObj;
            List<Cart> cartItems = (List<Cart>) session.getAttribute("cartItems");

            if (cartItems == null || cartItems.isEmpty()) {
                session.setAttribute("orderMessage", "Your cart is empty. Add items before confirming the order.");
                return "redirect:/orders";
            }

            try {
                // Validate and update stock for each cart item
                boolean stockUpdateSuccess = updateItemStock(cartItems, model);

                if (!stockUpdateSuccess) {
                    // If stock update failed, return to orders page with error message
                    return "redirect:/orders";
                }

                // Factory to create the order (only if stock update succeeded)
                Orders newOrder = orderFactory.createOrder(userId, cartItems, paymentMethod);
                ordersRepository.save(newOrder);

                // Step 3: Notify observers
                for (OrderObserver observer : observers) {
                    observer.onOrderPlaced(newOrder);
                }

                // Step 4: Clear the cart from database and session
                cartRepository.deleteAll(cartItems);
                session.removeAttribute("cartItems");

                System.out.println("Order placed successfully! Order ID: " + newOrder.getOrderId());
                System.out.println("Stock updated for " + cartItems.size() + " items");

                session.setAttribute("orderMessage", "Order placed successfully! Order ID: " + newOrder.getOrderId());
                return "thankyou";

            } catch (Exception e) {
                System.err.println("Error processing order: " + e.getMessage());
                e.printStackTrace();
                session.setAttribute("orderMessage", "Error processing your order. Please try again.");
                return "redirect:/orders";
            }
        } else {
            return "redirect:/login";
        }
    }

    private boolean updateItemStock(List<Cart> cartItems, Model model) {
        for (Cart cartItem : cartItems) {
            // Find the corresponding item in inventory using the cart item's ID
            Optional<Items> itemOpt = itemRepository.findById(cartItem.getId());

            if (itemOpt.isPresent()) {
                Items item = itemOpt.get();

                // Check if sufficient stock is available
                if (item.getStock() >= cartItem.getQty()) {
                    // Update stock by reducing the ordered quantity
                    int oldStock = item.getStock();
                    int newStock = oldStock - cartItem.getQty();
                    item.setStock(newStock);
                    itemRepository.save(item);

                    System.out.println("Stock updated for item: " + item.getName() +
                            " | Old stock: " + oldStock +
                            " | Ordered: " + cartItem.getQty() +
                            " | New stock: " + newStock);
                } else {
                    // Insufficient stock
                    String errorMessage = "Insufficient stock for item: " + item.getName() +
                            ". Available: " + item.getStock() +
                            ", Requested: " + cartItem.getQty();
                    System.err.println(errorMessage);
                    model.addAttribute("orderMessage", errorMessage);
                    return false;
                }
            } else {
                // Item not found in inventory
                String errorMessage = "Item not found in inventory: " + cartItem.getName();
                System.err.println(errorMessage);
                model.addAttribute("orderMessage", errorMessage);
                return false;
            }
        }
        return true;
    }
}