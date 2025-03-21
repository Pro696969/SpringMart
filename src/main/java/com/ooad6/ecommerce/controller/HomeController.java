package com.ooad6.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    @RequestMapping("/Homepage")
    public String homepage() {
        System.out.println("This is Homepage");
        return "home.jsp";
    }
}
