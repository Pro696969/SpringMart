package com.ooad6.ecommerce.controller;

import com.ooad6.ecommerce.model.Cart;
import com.ooad6.ecommerce.model.User;
import com.ooad6.ecommerce.repository.CartRepository;
import com.ooad6.ecommerce.repository.ItemsSearch;
import com.ooad6.ecommerce.repository.UserRepository;
import org.springframework.ui.Model;
import com.ooad6.ecommerce.model.Items;
import com.ooad6.ecommerce.repository.ItemsShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class HomeController {
    @Autowired
    private ItemsShow itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemsSearch itemsSearch;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/homepage")
    public String homepage(Model model, HttpSession session) {
        List<Items> itemsList = itemRepository.findAll();
        model.addAttribute("items", itemsList);

        Object userIdObj = session.getAttribute("userid");
        int userId = (Integer) userIdObj;

        Optional<User> user = userRepository.findByuserId(userId);
        model.addAttribute("username", user.get().getName());
        return "home";
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public String addToCart(@RequestParam String itemId, @RequestParam int userid, @RequestParam String itemName,
                            @RequestParam int cost, @RequestParam int qty,
                            @RequestParam String description) {
        if (userid == 0) {
            return "User ID is missing!";
        }

        Cart cartItem = new Cart(itemId, userid, itemName, cost, qty, description);
        cartRepository.save(cartItem);
        return itemName+" added to cart! for user with ID " + userid;
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
