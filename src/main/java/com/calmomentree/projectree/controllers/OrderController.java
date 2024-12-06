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
import com.calmomentree.projectree.models.Order;
import com.calmomentree.projectree.models.OrderItem;
import com.calmomentree.projectree.services.BasketService;
import com.calmomentree.projectree.services.OrderItemService;
import com.calmomentree.projectree.services.OrderService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

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
            @RequestParam("prodId") List<String> prodIdTmp,
            @RequestParam("quantity") List<String> quantityTmp,
            @RequestParam("orderPrice") List<String> orderPriceTmp,
            @RequestParam("prodNameKor") List<String> prodNameKor,
            @RequestParam("imgUrl") List<String> imgUrl) {

        int postCode = Integer.parseInt(memberInfo.getPostcode());

        int orderPrice = 0;
        for (int i=0; i<orderPriceTmp.size(); i++) {
            orderPrice += Integer.parseInt(orderPriceTmp.get(i));
        }

        Order order = new Order();
        order.setMemberName(memberInfo.getUserName());
        order.setMemberEmail(memberInfo.getEmail());
        order.setMemberPostcode(postCode);
        order.setMemberAddr1(memberInfo.getAddr1());
        order.setMemberAddr2(memberInfo.getAddr2());
        order.setMemberTel(memberInfo.getTel());
        order.setReceiverName(memberInfo.getUserName());
        order.setReceiverPostcode(postCode);
        order.setReceiverAddr1(memberInfo.getAddr1());
        order.setReceiverAddr2(memberInfo.getAddr2());
        order.setReceiverTel(memberInfo.getTel());
        order.setTotalPrice(orderPrice);
        order.setReq(".....");
        order.setMemberId(memberInfo.getMemberId());

        Order id = null;

        try {
            id = orderService.addItem(order);
        } catch (Exception e) {
            webHelper.serverError(e);
        }



        List<OrderItem> outputItem = new ArrayList<>();
        
        for (int i=0; i<prodIdTmp.size(); i++) {
            int prodId = Integer.parseInt(prodIdTmp.get(i));
            int quantity = Integer.parseInt(quantityTmp.get(i));
            int price = Integer.parseInt(orderPriceTmp.get(i));

            OrderItem o = new OrderItem();

            o.setProdName(prodNameKor.get(i));
            o.setOrderPrice(price);
            o.setOrderQuantity(quantity);
            o.setProdId(prodId);
            o.setOrderId(id.getOrderId());
            o.setImgUrl(imgUrl.get(i));

            outputItem.add(o);
        }
        
        // // 주문서 중복 여부 확인
        // Order check = new Order();
        // check.setMemberId(memberInfo.getMemberId());

        // int num = 0;
        // try {
        // num = orderService.overCount(check);
        // } catch (Exception e) {
        // webHelper.serverError(e);
        // }
      
        try {
            for (OrderItem item : outputItem) {
               item = orderItemService.addItem(item);
               
            }       
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }

        model.addAttribute("orderItems", outputItem);
        model.addAttribute("order", order);

        return "order/order_form";
    }
}
