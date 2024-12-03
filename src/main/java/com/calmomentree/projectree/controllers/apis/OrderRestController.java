package com.calmomentree.projectree.controllers.apis;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.Basket;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.services.BasketService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
public class OrderRestController {

    @Autowired
    private RestHelper restHelper;

    @Autowired
    private BasketService basketService;

    @GetMapping("/api/basket/count")
    public Map<String, Object> basketCount(
        @RequestParam("memberId") int memberId
    ) {
        Basket input = new Basket();
        input.setMemberId(memberId);

        int basketCount = 0;

        try {
            basketCount = basketService.getCount(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("basketCount", basketCount);

        return restHelper.sendJson(data);
    }

    @GetMapping("/api/basket/unique_count")
    public Map<String, Object> uniqueBasket(
        HttpServletRequest request,
        @SessionAttribute("memberInfo") Member memberInfo,        
        @RequestParam("prodId") int prodId
    ) {

        Basket input = new Basket();
        input.setMemberId(memberInfo.getMemberId());
        input.setProdId(prodId);

        Basket uniqueBasket = null;

        try {
            uniqueBasket = basketService.uniqueBasketCount(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("uniqueBasket", uniqueBasket);

        return restHelper.sendJson(data);
    }
    
    @PutMapping("/api/basket/unique_edit")
    public Map<String, Object> uniqueBasketEdit(
        @RequestParam("quantity") String quantity,
        @RequestParam("basketId") String basketIdTmp,
        @SessionAttribute("memberInfo") Member memberInfo
    ) {
        int qty = Integer.parseInt(quantity);
        int basketId = Integer.parseInt(basketIdTmp);

        Basket input = new Basket();
        input.setBasketId(basketId);
        input.setQuantity(qty);
        input.setMemberId(memberInfo.getMemberId());

        try {
            basketService.editUniqueBasket(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        return restHelper.sendJson();
    }


    /**
     * 장바구니 수량 수정
     * @param quantity - 장바구니 수량
     * @param basketId - 장바구니 일련번호
     * @param memberInfo - 회원 번호
     * @return
     */
    @PutMapping("/api/basket/edit")
    public Map<String, Object> basketEdit(
            @RequestParam("quantity") int quantity,
            @RequestParam("basketId") int basketId,
            @SessionAttribute("memberInfo") Member memberInfo) {
        
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

    /**
     * 장바구니 삭제
     * @param basketId  - 장바구니 번호
     * @param memberInfo - 회원 번호
     * @return
     */
    @DeleteMapping("/api/basket/delete")
    public Map<String, Object> basketDelete(
            @RequestParam("basketId") int basketId,
            @SessionAttribute("memberInfo") Member memberInfo) {
        
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

    /**
     * 장바구니 추가하기 (중복 처리 수정필요)
     * @param memberInfo - 회원 번호
     * @param quantity   - 장바구니 수량
     * @param prodId    - 상품 번호
     * @return
     */
    @GetMapping("/api/basket/add")
    public Map<String, Object> basketAdd(
            @SessionAttribute("memberInfo") Member memberInfo,
            @RequestParam("quantity") int quantity,
            @RequestParam("prodId") int prodId) {

        Basket input = new Basket();
        input.setMemberId(memberInfo.getMemberId());
        input.setQuantity(quantity);
        input.setProdId(prodId);

        // 중복을 어떻게 처리해야 되는거지 ???????
        Basket check = new Basket();
        check.setMemberId(memberInfo.getMemberId());
        check.setProdId(prodId);

        
        try {
            basketService.addItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        
        return restHelper.sendJson();
    }

}
