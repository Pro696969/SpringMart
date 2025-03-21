package com.ooad6.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CartController {
    @RequestMapping("Cart")
    public String cart() {
        System.out.println("This is Cart");
        return "cart.jsp";
    }
}
