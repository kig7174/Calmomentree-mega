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
<<<<<<< HEAD
import com.calmomentree.projectree.services.BasketService;
=======
import com.calmomentree.projectree.models.OrderItem;
import com.calmomentree.projectree.services.BasketService;
import com.calmomentree.projectree.services.OrderItemService;
>>>>>>> fb97eb08725b735ca4f9b1f7d31f8eb09d763047
import com.calmomentree.projectree.services.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.PostMapping;
>>>>>>> fb97eb08725b735ca4f9b1f7d31f8eb09d763047
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private WebHelper webHelper;

    @Autowired
    private BasketService basketService;

    @Autowired
    private OrderService orderService;

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

    @SuppressWarnings("null")
    @GetMapping("/order/order_form")
    public String orderAdd(
        Model model,
        HttpServletRequest request,
        @SessionAttribute("memberInfo") Member memberInfo,
        @RequestParam("basket_id") List<Integer> basketIdTmp
    ) {
        List<Basket> baskets = new ArrayList<>();

        int totalPrice = 0;

        for (int i=0; i<basketIdTmp.size(); i++) {
            Basket b = new Basket();
            b.setBasketId(basketIdTmp.get(i));
            b.setMemberId(memberInfo.getMemberId());

            Basket basket = null;
            
            try {
                basket = basketService.getItem(b);
            } catch (Exception e) {
                webHelper.serverError(e);
                return null;
            }

            basket.setImgUrl(fileHelper.getUrl(basket.getImgUrl()));

            int t = basket.getQuantity() * basket.getPrice();
            totalPrice += t;

            baskets.add(basket);
        }

        Order input = new Order();
        input.setOrderNo("1");
        input.setMemberName(memberInfo.getUserName());
        input.setMemberEmail(memberInfo.getEmail());
        input.setMemberPostcode(memberInfo.getPostcode());
        input.setMemberAddr1(memberInfo.getAddr1());
        input.setMemberAddr2(memberInfo.getAddr2());
        input.setMemberTel(memberInfo.getTel());
        input.setMemberId(memberInfo.getMemberId());
        input.setTotalPrice(totalPrice);

        Order order = null;

        try {
            order = orderService.addItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }

        // List<OrderItem> items = new ArrayList<>();

        // int totalPrice = 0;

        // for (int i=0; i<baskets.size(); i++) {
        //     OrderItem oi = new OrderItem();
        //     Basket b = baskets.get(i);
        //     oi.setProdName(b.getProdNameKor());
        //     oi.setOrderQuantity(b.getQuantity());
        //     oi.setProdId(b.getProdId());
        //     int p = b.getPrice() * b.getQuantity();
        //     oi.setOrderPrice(p);
        //     oi.setOrderId(order.getOrderId());

        //     OrderItem item = null;

        //     try {
        //         item = orderItemService.addItem(oi);
        //     } catch (Exception e) {
        //         webHelper.serverError(e);
        //         return null;
        //     }

        //     items.add(item);

        //     totalPrice += item.getOrderPrice();
        // }
        
        model.addAttribute("order", order);
        model.addAttribute("items", baskets);

        return "order/order_form";
    }
}
