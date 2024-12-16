package com.calmomentree.projectree.controllers.apis;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.Basket;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.models.Order;
import com.calmomentree.projectree.models.Product;
import com.calmomentree.projectree.services.BasketService;
import com.calmomentree.projectree.services.OrderService;
import com.calmomentree.projectree.services.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
public class OrderRestController {

    @Autowired
    private RestHelper restHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private BasketService basketService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    /**
     * 장바구니 갯수 표시
     */
    @GetMapping("/api/basket/count")
    public Map<String, Object> basketCount(
            @RequestParam("memberId") int memberId) {
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
            @RequestParam("prodId") Integer prodId) {

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
            @SessionAttribute("memberInfo") Member memberInfo) {
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
     * 
     * @param quantity   - 장바구니 수량
     * @param basketId   - 장바구니 일련번호
     * @param memberInfo - 회원 번호
     * @return
     */
    @PutMapping("/api/basket/edit")
    public Map<String, Object> basketEdit(
            @RequestParam("basketIdTmp") String basketIdTmp,
            @RequestParam("quantityTmp") String quantityTmp,
            @SessionAttribute("memberInfo") Member memberInfo) {

        int basketId = Integer.parseInt(basketIdTmp);
        int quantity = Integer.parseInt(quantityTmp);

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
     * 
     * @param basketId   - 장바구니 번호
     * @param memberInfo - 회원 번호
     * @return
     */
    @DeleteMapping("/api/basket/delete")
    public Map<String, Object> basketDelete(
            @RequestParam("basketIdTmp") String basketIdTmp,
            @SessionAttribute("memberInfo") Member memberInfo) {

        int basketId = Integer.parseInt(basketIdTmp);

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
     * 장바구니 리스트 삭제
     */
    @DeleteMapping("/api/basket/delete_list")
    public Map<String, Object> basketListDelete(
            @RequestParam("basketIdTmp") List<Integer> basketIdTmp,
            @SessionAttribute("memberInfo") Member memberInfo) {

        List<Basket> output = new ArrayList<>();
        for (int i = 0; i < basketIdTmp.size(); i++) {
            Basket input = new Basket();
            input.setBasketId(basketIdTmp.get(i));
            input.setMemberId(memberInfo.getMemberId());

            output.add(input);
            try {
                basketService.deleteItem(input);
            } catch (Exception e) {
                return restHelper.serverError(e);
            }
        }

        return restHelper.sendJson();
    }

    /**
     * 장바구니 추가하기 (중복 처리 수정필요)
     * 
     * @param memberInfo - 회원 번호
     * @param quantity   - 장바구니 수량
     * @param prodId     - 상품 번호
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

        try {
            basketService.addItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        return restHelper.sendJson();
    }

    @GetMapping("/api/order/search")
    public Map<String, Object> orderSearch(
            @RequestParam("prodId") String prodIdTmp) {
        
        int prodId = Integer.parseInt(prodIdTmp);

        Product input = new Product();
        input.setProdId(prodId);
        
        Product output = null;

        try {
            output = productService.getItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        output.setListImgUrl1(fileHelper.getUrl(output.getListImgUrl1()));

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("output",output);
        
        return restHelper.sendJson(data);
    }
    
    @SuppressWarnings("static-access")
    @GetMapping("/api/myshop/order/list")
    public Map<String, Object> myshopOrder(
        @SessionAttribute("memberInfo") Member memberInfo,
        @RequestParam(value = "order_state", required = false) String orderState,
        @RequestParam(value = "start_date", required = false) String startDate,
        @RequestParam(value = "end_date", required = false) String endDate
    ) {

        Order input = new Order();
        input.setMemberId(memberInfo.getMemberId());
        input.setOrderState(orderState);
        input.setStartDate(startDate);
        input.setEndDate(endDate);

        List<Order> order = null;

        try {
            order = orderService.getList(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        for (Order o : order) {
            o.setImgUrl(fileHelper.getUrl(o.getImgUrl()));
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("orders", order);
        data.put("orderState", orderState);
        data.put("startDate", startDate);
        data.put("endDate", endDate);

        return restHelper.sendJson(data);
    }
}    