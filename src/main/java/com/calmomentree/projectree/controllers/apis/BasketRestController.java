package com.calmomentree.projectree.controllers.apis;

import org.springframework.web.bind.annotation.RestController;

import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.Basket;
import com.calmomentree.projectree.services.BasketService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@RestController
public class BasketRestController {
    
    @Autowired
    private RestHelper restHelper;

    @Autowired
    private BasketService basketService;

    @PutMapping("/api/basket/edit")
    public Map<String, Object> basketEdit(
            @RequestParam("basketId") int basketId,
            @RequestParam("quantity") int quantity,
            @RequestParam("memberId") int memberId ) {
        
        Basket input = new Basket();
        input.setBasketId(basketId);
        input.setQuantity(quantity);
        

        try {
            basketService.editItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        Basket output = new Basket();
        output.setMemberId(memberId);

        try {
           output = basketService.getItem(output);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        
        return restHelper.sendJson(data);
    }
}
