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

@Slf4j
@RestController
public class BasketRestController {
    
    @Autowired
    private RestHelper restHelper;

    @Autowired
    private BasketService basketService;

    @PutMapping("/api/basket/edit")
    public Map<String, Object> basketEdit(
        @RequestParam("quantity") int quantity,
        @RequestParam("basketId") int basketId,
        @RequestParam("memberId") int memberId ) {
        
        Basket input = new Basket();
        input.setBasketId(basketId);
        input.setQuantity(quantity);
        input.setMemberId(memberId);

        Basket output = null;

        try {
            output = basketService.editItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("orderBasket", output);
                
        return restHelper.sendJson(data);
    }
}
