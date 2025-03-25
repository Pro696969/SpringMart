package com.ooad6.ecommerce.controller;

import com.ooad6.ecommerce.model.Cart;
import com.ooad6.ecommerce.repository.CartRepository;
import org.springframework.ui.Model;
import com.ooad6.ecommerce.model.Items;
import com.ooad6.ecommerce.repository.ItemsSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Random;

@Controller
public class HomeController {
    @Autowired
    private ItemsSearch itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @RequestMapping("/Homepage")
    public String homepage(Model model) {
        // Retrieve all items from MongoDB

        List<Items> itemsList = itemRepository.findAll();

        // Debugging log to check if items are fetched
        System.out.println("Fetched items from DB: " + itemsList);
        if (itemsList.isEmpty()) {
            System.out.println("No items found in database!");
        }


        // Add the list to the model to be used in the JSP view
        model.addAttribute("items", itemsList);

        return "home.jsp"; // Ensure JSP is named home.jsp
    }
    @PostMapping("/addToCart")
    @ResponseBody
    public String addToCart(@RequestParam("itemId") String itemId) {
        Optional<Items> itemOpt = itemRepository.findById(itemId);

        if (itemOpt.isPresent()) {
            Items item = itemOpt.get();

            // Create a new Cart object
            Cart cartItem = new Cart(item.getId(), item.getName(), item.getCost(), 1);
            cartRepository.save(cartItem); // Save to MongoDB

            System.out.println("Added to Cart: " + item.getName());
            return "Item added to cart successfully!";
        } else {
            System.out.println("Item not found for ID: " + itemId);
            return "Item not found!";
        }
    }

}
