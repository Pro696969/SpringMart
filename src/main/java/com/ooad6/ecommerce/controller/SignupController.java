package com.ooad6.ecommerce;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    @RequestMapping("/signup")
    public String signup() {
        return "signup.jsp";
    }
}
