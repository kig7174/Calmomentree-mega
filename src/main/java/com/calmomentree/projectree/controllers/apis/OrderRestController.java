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
     * 회원별 장바구니에 담긴 총 상품 개수를 조회
     * 
     * @param memberId 회원의 고유 식별번호
     * @return 장바구니 상품 개수를 포함한 JSON 응답
     */
    @GetMapping("/api/basket/count")
    public Map<String, Object> basketCount(
            @RequestParam("memberId") int memberId) {
        // 장바구니 조회를 위한 객체 생성
        Basket input = new Basket();
        input.setMemberId(memberId);
        
        // 상품 개수 조회
        int basketCount = 0;
        try {
            basketCount = basketService.getCount(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        // 응답 데이터 구성
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("basketCount", basketCount);
        
        // JSON 형식으로 응답 데이터 반환
        return restHelper.sendJson(data);
    }
    
    /**
     * 특정 상품의 장바구니 중복 여부를 확인
     * 
     * @param request HTTP 요청 객체
     * @param memberInfo 세션에 저장된 회원 정보
     * @param prodId 중복 확인할 상품의 고유 번호
     * @return 장바구니 중복 정보를 포함한 JSON 응답
     */
    @GetMapping("/api/basket/unique_count")
    public Map<String, Object> uniqueBasket(
            HttpServletRequest request,
            @SessionAttribute("memberInfo") Member memberInfo,
            @RequestParam("prodId") Integer prodId) {
        
        // 중복 확인을 위한 객체 생성
        Basket input = new Basket();
        input.setMemberId(memberInfo.getMemberId());
        input.setProdId(prodId);
        
        // 특정 상품의 장바구니 중복 여부 확인
        Basket uniqueBasket = null;
        try {
            uniqueBasket = basketService.uniqueBasketCount(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        // 응답 데이터 구성
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("uniqueBasket", uniqueBasket);
        // JSON 형식으로 응답 데이터 반환
        return restHelper.sendJson(data);
    }

    /**
     * 상품 상세페이지에서 상품의 수량을 수정
     * 
     * @param quantity 수정할 상품 수량
     * @param basketIdTmp 수정할 장바구니 항목 ID
     * @param memberInfo 세션에 저장된 회원 정보
     * @return 수정 결과를 포함한 JSON 응답
    */
    @PutMapping("/api/basket/unique_edit")
    public Map<String, Object> uniqueBasketEdit(
            @RequestParam("quantity") String quantity,
            @RequestParam("basketId") String basketIdTmp,
            @SessionAttribute("memberInfo") Member memberInfo) {
        // 문자열 파라미터를 정수형으로 변환
        int qty = Integer.parseInt(quantity);
        int basketId = Integer.parseInt(basketIdTmp);
        
        // 장바구니 수정을 위한 객체 생성 및 데이터 설정
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
     * 장바구니 상품의 수량을 수정
     * 
     * @param basketIdTmp 수정할 장바구니 항목의 일련번호
     * @param quantityTmp 수정할 상품 수량
     * @param memberInfo 세션에 저장된 회원 정보
     * @return 수정된 장바구니 정보를 포함한 JSON 응답
     */
    @PutMapping("/api/basket/edit")
    public Map<String, Object> basketEdit(
            @RequestParam("basketIdTmp") String basketIdTmp,
            @RequestParam("quantityTmp") String quantityTmp,
            @SessionAttribute("memberInfo") Member memberInfo) {
        // 문자열 파라미터를 정수형으로 변환
        int basketId = Integer.parseInt(basketIdTmp);
        int quantity = Integer.parseInt(quantityTmp);
        
        // 장바구니 수정을 위한 객체 생성 및 데이터 설정
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
        // 수정된 장바구니 정보를 응답 데이터로 구성
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("orderBasket", output);
        
        // JSON 형식으로 응답 데이터 반환
        return restHelper.sendJson(data);
    }

    /**
     * 장바구니 항목을 삭제
     * 
     * @param basketIdTmp 삭제할 장바구니 항목의 일련번호
     * @param memberInfo 세션에 저장된 회원 정보
     * @return 삭제 결과를 포함한 JSON 응답
     */
    @DeleteMapping("/api/basket/delete")
    public Map<String, Object> basketDelete(
            @RequestParam("basketIdTmp") String basketIdTmp,
            @SessionAttribute("memberInfo") Member memberInfo) {
        // 문자열 파라미터를 정수형으로 변환
        int basketId = Integer.parseInt(basketIdTmp);
        
        // 장바구니 삭제를 위한 객체 생성 및 데이터 설정
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
     * 선택된 장바구니 항목들을 일괄 삭제
     * 
     * @param basketIdTmp 삭제할 장바구니 항목들의 ID 리스트
     * @param memberInfo 세션에 저장된 회원 정보
     * @return 삭제 결과를 포함한 JSON 응답
     */
    @DeleteMapping("/api/basket/delete_list")
    public Map<String, Object> basketListDelete(
            @RequestParam("basketIdTmp") List<Integer> basketIdTmp,
            @SessionAttribute("memberInfo") Member memberInfo) {
        
        // 각 장바구니 항목에 대해 삭제 처리
        for (int i = 0; i < basketIdTmp.size(); i++) {
            // 장바구니 삭제를 위한 객체 생성 및 데이터 설정
            Basket input = new Basket();
            input.setBasketId(basketIdTmp.get(i));
            input.setMemberId(memberInfo.getMemberId());

            try {
                basketService.deleteItem(input);
            } catch (Exception e) {
                return restHelper.serverError(e);
            }
        }

        return restHelper.sendJson();
    }

    /**
     * 장바구니에 상품을 추가
     * 
     * @param memberInfo 세션에 저장된 회원 정보
     * @param quantity 추가할 상품의 수량
     * @param prodId 추가할 상품의 고유 번호
     * @return 추가 결과를 포함한 JSON 응답
     */
    @GetMapping("/api/basket/add")
    public Map<String, Object> basketAdd(
            @SessionAttribute("memberInfo") Member memberInfo,
            @RequestParam("quantity") int quantity,
            @RequestParam("prodId") int prodId) {
        // 장바구니 추가를 위한 객체 생성 및 데이터 설정
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

    /**
     * 상품 ID로 상품 정보를 조회
     * 
     * @param prodIdTmp 조회할 상품의 고유 번호
     * @return 상품 정보를 포함한 JSON 응답
     */
    @GetMapping("/api/order/search")
    public Map<String, Object> orderSearch(
            @RequestParam("prodId") String prodIdTmp) {
        // 문자열 파라미터를 정수형으로 변환
        int prodId = Integer.parseInt(prodIdTmp);
        
        // 상품 조회를 위한 객체 생성 및 데이터 설정
        Product input = new Product();
        input.setProdId(prodId);
        
        Product output = null;

        try {
            output = productService.getItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        // 상품 이미지 URL 설정
        output.setListImgUrl1(fileHelper.getUrl(output.getListImgUrl1()));
        // 응답 데이터 구성
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("output",output);
        // JSON 형식으로 응답 데이터 반환
        return restHelper.sendJson(data);
    }
    /**
     * 회원의 주문 내역을 조회
     * 
     * @param memberInfo 세션에 저장된 회원 정보
     * @param orderState 주문 상태 필터 (선택)
     * @param startDate 조회 시작 날짜 (선택)
     * @param endDate 조회 종료 날짜 (선택)
     * @return 주문 목록과 검색 조건을 포함한 JSON 응답
     */
    @SuppressWarnings("static-access")
    @GetMapping("/api/myshop/order/list")
    public Map<String, Object> myshopOrder(
        @SessionAttribute("memberInfo") Member memberInfo,
        @RequestParam(value = "order_state", required = false) String orderState,
        @RequestParam(value = "start_date", required = false) String startDate,
        @RequestParam(value = "end_date", required = false) String endDate
    ) {
        // 주문 조회를 위한 객체 생성 및 검색 조건 설정
        Order input = new Order();
        input.setMemberId(memberInfo.getMemberId());
        input.setOrderState(orderState);
        input.setStartDate(startDate + " 00:00:00");
        input.setEndDate(endDate + " 23:59:59");

        List<Order> order = null;

        try {
            order = orderService.getList(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        // 각 주문의 이미지 URL 설정
        for (Order o : order) {
            o.setImgUrl(fileHelper.getUrl(o.getImgUrl()));
        }
        // 응답 데이터 구성
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("orders", order);
        data.put("orderState", orderState);
        data.put("startDate", startDate);
        data.put("endDate", endDate);
        
        // JSON 형식으로 응답 데이터 반환
        return restHelper.sendJson(data);
    }
}    