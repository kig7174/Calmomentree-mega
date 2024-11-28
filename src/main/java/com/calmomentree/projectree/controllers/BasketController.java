package com.calmomentree.projectree.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.Basket;
import com.calmomentree.projectree.services.BasketService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class BasketController {

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private BasketService basketService;

    @Autowired
    private WebHelper webHelper;

    @GetMapping("/order/basket")
    public String orderBasket(Model model,
            @RequestParam(value = "memberId", defaultValue = "1") int memberId) {

        Basket input = new Basket();
        input.setMemberId(memberId);
        
        List<Basket> output = null;
        List<Basket> li = new ArrayList<>();
        try {
            output = basketService.getList(input);
           
            
        } catch (Exception e) {
            webHelper.serverError(e);
        }
       
         
        for(int i=0 ;i< output.size(); i++) {
            Basket output2 = output.get(i);
            
            output2.setImgUrl(fileHelper.getUrl(output2.getImgUrl()));

            li.add(output2);
        }
        // for(int i=0 ;i< output.size(); i++) {
        //     Basket output2 = output.get(i);
            
        //     output2.setImgUrl(fileHelper.getUrl(output2.getImgUrl()));

        //     li.add(output2);
        // }

        model.addAttribute("orderBasket", output);
        model.addAttribute("li", li);

        return "order/basket";
    }

}
