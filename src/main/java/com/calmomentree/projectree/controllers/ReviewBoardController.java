package com.calmomentree.projectree.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.Pagination;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.models.Order;
import com.calmomentree.projectree.models.OrderItem;
import com.calmomentree.projectree.models.ReviewBoard;
import com.calmomentree.projectree.models.ReviewImg;
import com.calmomentree.projectree.services.OrderItemService;
import com.calmomentree.projectree.services.OrderService;
import com.calmomentree.projectree.services.ReviewBoardService;
import com.calmomentree.projectree.services.ReviewImgService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReviewBoardController {

    @Autowired
    private ReviewBoardService reviewBoardService;

    @Autowired
    private ReviewImgService reviewImgService;

    @Autowired
    private WebHelper webHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handle(Exception ex) {
        return "error/404";
    }

    /**
     * 리뷰 게시판 목록 조회하기
     * 
     * @param model
     * @param keyword
     * @param nowPage
     * @return
     */
    @GetMapping("/board/product/list")
    private String reviewList (Model model,
        // 검색어 파라미터
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int nowPage) {

        int totalCount = 0; // 전체 게시글 수
        int listCount = 10; // 한 페이지당 표시할 목록 수
        int pageCount = 5; // 한 그룹당 표시할 페이지 번호 수

        // 페이지 번호를 계산한 결과가 저장될 객체
        Pagination pagination = null;

        // 조회 조건에 사용할 객체
        ReviewBoard input = new ReviewBoard();
        input.setReviewTitle(keyword);

        List<ReviewBoard> output = null;

        try {
            // 전체 게시글 수 조회
            totalCount = reviewBoardService.getCount(input);
            // 페이지 번호 계산 ---> 계산결과를 로그로 출력될 것이다.
            pagination = new Pagination(nowPage, totalCount, listCount, pageCount);

            // SQL의 Limit절에서 사용될 값을 Beans의 static 변수에 저장
            ReviewBoard.setOffset(pagination.getOffset());
            ReviewBoard.setListCount(pagination.getListCount());

            output = reviewBoardService.getList(input);

            for(ReviewBoard item : output) {
                item.setImgUrl(fileHelper.getUrl(item.getImgUrl()));
            }

        } catch (Exception e) {
            webHelper.serverError(e);

        }

        // view에 데이터 전달
        model.addAttribute("review", output);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagination", pagination);

        return "board/product/list";
     }
     
    @SuppressWarnings("null")
    @GetMapping("/board/product/read/{reviewBoardId}")
    public String reviewRead(Model model,
            @PathVariable("reviewBoardId") int reviewBoardId){
            // @PathVariable("prodId") int prodId) {
        
        // 리뷰 
        ReviewBoard input = new ReviewBoard();
        input.setReviewBoardId(reviewBoardId);
        
        // 리뷰 이미지
        ReviewImg reviewImg =new ReviewImg();
        reviewImg.setReviewBoardId(reviewBoardId);

        ReviewBoard output = null;

        try {
            output = reviewBoardService.getItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }

        // 상품정보 이미지
        output.setImgUrl(fileHelper.getUrl(output.getImgUrl()));

        // 리뷰 이미지
        List<ReviewImg> reviewImgOutput = null;

        try {
            reviewImgOutput = reviewImgService.getList(reviewImg);
        } catch (Exception e) {
            webHelper.serverError(e);
        }       
        
        for(int i=0;i<reviewImgOutput.size();i++) {
            ReviewImg r = reviewImgOutput.get(i);
            r.setImgUrl(fileHelper.getUrl(reviewImgOutput.get(i).getImgUrl()));
        }
           
        model.addAttribute("reviewInfo", output);
        model.addAttribute("reviewImgs", reviewImgOutput);

        return "board/product/read";
    }
 
    @SuppressWarnings("null")
    @GetMapping("/review/search_board_list")
    public String reviewSelectOrder(Model model,
        @SessionAttribute("memberInfo") Member memberInfo) {

        // 회원 결제 페이지 일련 번호
        Order input = new Order();
        input.setMemberId(memberInfo.getMemberId());

        List<Order> orderId = null;

        try {
            orderId = orderService.getList(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }
        log.error("회원결제페이지 일련번호: " + orderId);

        List<OrderItem> order = new ArrayList<>();

        for(Order item : orderId) {
            OrderItem inputTmp = new OrderItem();
            inputTmp.setOrderId(item.getOrderId());

            List<OrderItem> output = null;
            try {
              output = orderItemService.getList(inputTmp);
            } catch (Exception e) {
                webHelper.serverError(e);
            }
            for(int i=0;i<output.size();i++) {
                output.get(i).setImgUrl(fileHelper.getUrl(output.get(i).getImgUrl()));
            }

            order.addAll(output);
        }
        
        model.addAttribute("items", order);

        return "order/search_board_list";
    }  
   
}
