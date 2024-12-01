package com.calmomentree.projectree.controllers.apis;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.Basket;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.services.BasketService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
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
        @SessionAttribute("memberInfo") Member memberInfo) {
        // @SessionAttribute("memberInfo") Member memberInfo
        // @RequestParam("memberId") int memberId 
        Basket input = new Basket();
        input.setBasketId(basketId);
        input.setQuantity(quantity);
        input.setMemberId(memberInfo.getMemberId());

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

    @DeleteMapping("/api/basket/delete")
    public Map<String, Object> basketDelete( // HttpServletRequest request,
        @RequestParam("basketId") int basketId,
        @SessionAttribute("memberInfo") Member memberInfo) {
        // @SessionAttribute("memberInfo") Member memberInfo
        // @RequestParam("memberId") int memberId
        Basket input = new Basket();
        input.setBasketId(basketId);
        input.setMemberId(memberInfo.getMemberId());

        try {
            basketService.deleteItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        return restHelper.sendJson();
    }
}
