package com.calmomentree.projectree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public String Dashboard() {
        return "dashboard";
    }
}
