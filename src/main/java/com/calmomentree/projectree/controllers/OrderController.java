package com.calmomentree.projectree.controllers;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.Basket;
import com.calmomentree.projectree.models.Member;

import com.calmomentree.projectree.models.OrderItem;
import com.calmomentree.projectree.services.BasketService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private BasketService basketService;

    @Autowired
    private WebHelper webHelper;

    /**
     * 장바구니 첫화면 불러오기
     * 
     * @param memberInfo - 회원 번호
     * @return
     */
    @GetMapping("/order/basket")
    public String orderBasket(Model model,
            @SessionAttribute("memberInfo") Member memberInfo) {
        // @RequestParam(value = "memberId", defaultValue = "1") int memberId
        Basket input = new Basket();
        input.setMemberId(memberInfo.getMemberId());

        // 주문상품 카운트
        Basket input2 = new Basket();
        input2.setMemberId(memberInfo.getMemberId());

        List<Basket> output = null;

        int count = 0;

        try {
            output = basketService.getList(input);

            count = basketService.getCount(input2);

            // 사진 경로에 files 추가하기.
            for (Basket item : output) {
                item.setImgUrl(fileHelper.getUrl(item.getImgUrl()));
            }

        } catch (Exception e) {
            webHelper.serverError(e);
        }

        model.addAttribute("orderBasket", output);
        model.addAttribute("counts", count);

        return "order/basket";
    }

    @PostMapping("/order/order_form/add")
    public String orderForm(Model model,
            @SessionAttribute("memberInfo") Member memberInfo, // 현재 세션 정보 확인용
            @RequestParam("prodIdTmp") String prodIdTmp,
            @RequestParam("quantityTmp") String quantityTmp,
            @RequestParam("orderPriceTmp") String orderPriceTmp,
            @RequestParam("prodNameKor") String prodNameKor,
            @RequestParam("imgUrl") String imgUrl) {

        int prodId = Integer.parseInt(prodIdTmp);
        int quantity = Integer.parseInt(quantityTmp);
        int orderPrice = Integer.parseInt(orderPriceTmp);

        // // 주문서 중복 여부 확인
        // Order check = new Order();
        // check.setMemberId(memberInfo.getMemberId());

        // int num = 0;
        // try {
        // num = orderService.overCount(check);

        // } catch (Exception e) {
        // return restHelper.serverError(e);
        // }

        // Order input = new Order();
        // // input.setOrderNo();
        // input.set

        // Order output = null;
        // // 중복된 주문서가 없다면?
        // if(num == 0) {

        // output = orderService.addItem(output);
        // }

        OrderItem output = new OrderItem();
        output.setProdId(prodId);
        output.setOrderQuantity(quantity);
        output.setOrderPrice(orderPrice);
        output.setProdName(prodNameKor);
        output.setImgUrl(imgUrl);

        model.addAttribute("orders", output);

        return "order/order_form";
    }

    // @PostMapping("/order/order_form")
    // public String orderForm(Model model,
    // @SessionAttribute("memberInfo") Member memberInfo, // 현재 세션 정보 확인용
    // @RequestParam("prodId") List<String> prodIdTmp,
    // @RequestParam("quantity") List<String> quantityTmp,
    // @RequestParam("orderPrice") List<String> orderPriceTmp,
    // @RequestParam("prodNameKor") List<String> prodNameKor,
    // @RequestParam("imgUrl") List<String> imgUrl) {

    // List<OrderItem> list = new ArrayList<>();

    // for (int i=0; i<prodIdTmp.size(); i++) {
    // int prodId = Integer.parseInt(prodIdTmp.get(i));
    // int quantity = Integer.parseInt(quantityTmp.get(i));
    // int orderPrice = Integer.parseInt(orderPriceTmp.get(i));
    // String name = prodNameKor.get(i);
    // String url = imgUrl.get(i);

    // OrderItem output = new OrderItem();
    // output.setProdId(prodId);
    // output.setOrderQuantity(quantity);
    // output.setOrderPrice(orderPrice);
    // output.setProdName(name);
    // output.setImgUrl(url);

    // list.add(output);
    // }

    // model.addAttribute("list", list);

    // return "order/order_form";
    // }
}
