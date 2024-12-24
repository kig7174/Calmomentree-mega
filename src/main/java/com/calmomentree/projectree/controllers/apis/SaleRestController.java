package com.calmomentree.projectree.controllers.apis;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleRestController {
    
    @GetMapping("/api/sales")
    public Map<String, Object> sales() {

        return null;
    }
}
