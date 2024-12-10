package com.calmomentree.projectree.controllers;

import java.util.ArrayList;
import java.util.Calendar;
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

import jakarta.servlet.http.HttpServletRequest;
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
    private WebHelper webHelper;

    @Autowired
    private BasketService basketService;

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
        
        model.addAttribute("order", order);
        model.addAttribute("items", baskets);

        return "order/order_form";
    }

    @PostMapping("/order/order_ok")
    public String orderOk(
        Model model,
        @SessionAttribute("memberInfo") Member memberInfo,
        @RequestParam("order_id") int orderId,
        @RequestParam("member_name") String memberName,
        @RequestParam("email") String memberEmail,
        @RequestParam("tel") String memberTel,
        @RequestParam("postcode") String memberPostcode,
        @RequestParam("addr1") String memberAddr1,
        @RequestParam("addr2") String memberAddr2,
        @RequestParam("receiver_name") String receiverName,
        @RequestParam("receiver_postcode") String receiverPostcode,
        @RequestParam("receiver_addr1") String receiverAddr1,
        @RequestParam("receiver_addr2") String receiverAddr2,
        @RequestParam("receiver_tel") String receiverTel,
        @RequestParam("req") String req,
        @RequestParam("total_price") Integer totalPrice,
        @RequestParam("basket_id") List<Integer> basketIdTmp
    ) {

        Order input = new Order();
        input.setOrderId(orderId);
        input.setMemberId(memberInfo.getMemberId());
        input.setOrderNo("1");
        input.setOrderState("결제완료");
        input.setMemberName(memberName);
        input.setMemberEmail(memberEmail);
        input.setMemberPostcode(memberPostcode);
        input.setMemberAddr1(memberAddr1);
        input.setMemberAddr2(memberAddr2);
        input.setMemberTel(memberTel);
        input.setReceiverName(receiverName);
        input.setReceiverPostcode(receiverPostcode);
        input.setReceiverAddr1(receiverAddr1);
        input.setReceiverAddr2(receiverAddr2);
        input.setReceiverTel(receiverTel);
        input.setReq(req);

        Order.orderCount();
        int count = Order.getCount();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);

        input.setOrderNo(String.format("%04d%02d%02d%04d", year, month, date, count));

        Order order = null;

        try {
            order = orderService.editItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }
        
        List<Basket> baskets = new ArrayList<>();

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

            baskets.add(basket);
        }

        List<OrderItem> items = new ArrayList<>();

        for (int i=0; i<baskets.size(); i++) {
            OrderItem oi = new OrderItem();

            Basket b = baskets.get(i);
            oi.setProdName(b.getProdNameKor());
            oi.setOrderQuantity(b.getQuantity());
            oi.setProdId(b.getProdId());
            int p = b.getPrice() * b.getQuantity();
            oi.setOrderPrice(p);
            oi.setOrderId(orderId);

            OrderItem item = null;

            try {
                item = orderItemService.addItem(oi);
            } catch (Exception e) {
                webHelper.serverError(e);
                return null;
            }

            items.add(item);
        }

        for (int i=0; i<baskets.size(); i++) {
            Basket b = new Basket();
            b.setBasketId(basketIdTmp.get(i));
            b.setMemberId(memberInfo.getMemberId());

            try {
                basketService.deleteItem(b);
            } catch (Exception e) {
                webHelper.serverError(e);
                return null;
            }
        }

        

        model.addAttribute("order", order);
        model.addAttribute("orderItems", items);

        return "order/order_result";
    }   
}
