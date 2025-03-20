package com.ooad6.ecommerce;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignupController {

    @RequestMapping("/Signup")
    public String signup() {
        return "signup.jsp";
    }

}
