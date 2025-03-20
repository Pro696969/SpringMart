package com.ooad6.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SignupController {
    @RequestMapping("signup")
    public String sign() {
        return "signup.jsp";
    }
}
