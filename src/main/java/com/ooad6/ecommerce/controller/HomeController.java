package com.ooad6.ecommerce.controller;

import com.ooad6.ecommerce.model.Cart;
import com.ooad6.ecommerce.repository.CartRepository;
import org.springframework.ui.Model;
import com.ooad6.ecommerce.model.Items;
import com.ooad6.ecommerce.repository.ItemsSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ItemsSearch itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @RequestMapping("/homepage")
    public String homepage(Model model) {
        List<Items> itemsList = itemRepository.findAll();
        model.addAttribute("items", itemsList);
        return "home.jsp";
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public String addToCart(@RequestParam String itemId, @RequestParam String itemName,
                            @RequestParam int cost, @RequestParam int qty,
                            @RequestParam String description) {
        Cart cartItem = new Cart(itemId, itemName, cost, qty, description);
        cartRepository.save(cartItem);
        return itemName+" added to cart!";
    }
}
