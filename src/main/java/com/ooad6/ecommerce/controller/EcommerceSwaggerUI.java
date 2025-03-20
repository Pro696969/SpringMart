package com.ooad6.ecommerce.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class EcommerceSwaggerUI {
    @RequestMapping("/")
    public String redirect()  {
        return "index.jsp";
    }
}


