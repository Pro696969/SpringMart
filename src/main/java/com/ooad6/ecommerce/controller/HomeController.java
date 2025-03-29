package com.ooad6.ecommerce.controller;

import com.ooad6.ecommerce.model.Cart;
import com.ooad6.ecommerce.repository.CartRepository;
import com.ooad6.ecommerce.repository.ItemsSearch;
import org.springframework.ui.Model;
import com.ooad6.ecommerce.model.Items;
import com.ooad6.ecommerce.repository.ItemsShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class HomeController {
    @Autowired
    private ItemsShow itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemsSearch itemsSearch;

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

    @GetMapping("/search/{text}")
    @ResponseBody
    public List<Items> search(@PathVariable String text) {
        System.out.println("Received search query: " + text); // ✅ Debugging
        List<Items> results = itemsSearch.findByText(text);
        System.out.println("Search results: " + results); // ✅ Debugging
        return results;
    }
}
