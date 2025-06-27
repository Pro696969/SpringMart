package com.ooad6.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {
    @GetMapping("/payment")
    public String showPaymentPage() {
        return "payment"; // refers to payment.jsp in /WEB-INF/views
    }
}
