package com.ooad6.ecommerce.controller;

import com.ooad6.ecommerce.model.Cart;
import com.ooad6.ecommerce.model.Items;
import com.ooad6.ecommerce.repository.CartRepository;
import com.ooad6.ecommerce.repository.ItemsShow;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemsShow itemRepository;

    @RequestMapping("/cart")
    public String cart(Model model, HttpSession session) {
        Object userIdObj = session.getAttribute("userid");
        int userId = (Integer) userIdObj;
        List<Cart> cartList = cartRepository.findByUserid(userId);
        model.addAttribute("cartItems", cartList);

        int totalCost = 0;
        for (Cart item : cartList) {
            totalCost += item.getCost() * item.getQty();
        }
        model.addAttribute("totalCost", totalCost);
        session.setAttribute("totalCost", totalCost);

        return "cart";
    }

    @PostMapping("/updateCartQty")
    @ResponseBody
    public int updateCartQty(@RequestParam String itemId, @RequestParam int change) {
        Optional<Cart> cartItemOpt = cartRepository.findById(itemId);
        Optional<Items> itemOpt = itemRepository.findById(itemId);

        if (cartItemOpt.isPresent() && itemOpt.isPresent()) {
            Cart cartItem = cartItemOpt.get();
            Items item = itemOpt.get();

            int newQty = cartItem.getQty() + change;

            if (newQty < 1) return getTotalCartCost();  // Prevent negative values

            if (newQty > item.getStock()) {
                System.out.println("Cannot increase quantity. Available stock: " + item.getStock() +
                        ", Requested quantity: " + newQty);
                return getTotalCartCost(); // Prevent exceeding available stock
            }

            cartItem.setQty(newQty);
            cartRepository.save(cartItem);
        }

        return getTotalCartCost();
    }

    @PostMapping("/clearCart")
    public String clearCart(HttpSession session) {
        Object userIdObj = session.getAttribute("userid");
        int userId = (Integer) userIdObj;

        // Only clear cart items for the current user
        List<Cart> userCartItems = cartRepository.findByUserid(userId);

        // This logic is further handled in Orders Controller as we decrease the item stock when payment is done.

        // Since we're not reserving stock in cart, no need to restore stock
        // Just clear the user's cart
        cartRepository.deleteAll(userCartItems);
        // Clear the cart
        cartRepository.deleteAll(userCartItems);

        return "redirect:/cart";
    }

    private int getTotalCartCost() {
        List<Cart> cartList = cartRepository.findAll();
        return cartList.stream().mapToInt(item -> item.getCost() * item.getQty()).sum();
    }

}
