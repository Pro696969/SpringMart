package com.ooad6.ecommerce.controller;

import org.springframework.ui.Model;
import com.ooad6.ecommerce.model.Items;
import com.ooad6.ecommerce.repository.ItemsSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class HomeController {
    @Autowired
    private ItemsSearch itemRepository;

    @RequestMapping("/Homepage")
    public String homepage(Model model) {
        // Retrieve all items from MongoDB

        List<Items> itemsList = itemRepository.findAll();

        // Debugging log to check if items are fetched
        System.out.println("Fetched items from DB: " + itemsList);

        // Add the list to the model to be used in the JSP view
        model.addAttribute("items", itemsList);

        return "home.jsp"; // Ensure JSP is named home.jsp
    }

}
