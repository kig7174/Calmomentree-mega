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
     * 리뷰 게시판 목록 조회 컨트롤러
     * 페이징 처리와 검색 기능을 포함한 리뷰 목록을 조회합니다.
     * 
     * @param model 뷰에 전달할 데이터를 저장하는 Model 객체
     * @param keyword 검색어 (선택적 파라미터)
     * @param nowPage 현재 페이지 번호 (기본값: 1)
     * @return 리뷰 목록 페이지의 뷰 이름
     */
    @GetMapping("/board/product/list")
    private String reviewList (Model model,
        // 검색어 파라미터
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int nowPage) {

         // 페이징 처리를 위한 설정값
        int totalCount = 0;    // 전체 게시글 수
        int listCount = 10;    // 한 페이지당 표시할 목록 수
        int pageCount = 5;     // 한 그룹당 표시할 페이지 번호 수

        // 페이지 번호를 계산한 결과가 저장될 객체
        Pagination pagination = null;

        // 조회 조건에 사용할 객체
        ReviewBoard input = new ReviewBoard();
        input.setReviewTitle(keyword);

        // 조회 결과를 담을 객체
        List<ReviewBoard> output = null;

        try {
            // 전체 게시글 수 조회
            totalCount = reviewBoardService.getCount(input);
            // 페이지네이션 객체 생성 (페이지 번호 계산)
            pagination = new Pagination(nowPage, totalCount, listCount, pageCount);

            // SQL의 Limit절에서 사용될 값을 Beans의 static 변수에 저장
            ReviewBoard.setOffset(pagination.getOffset());
            ReviewBoard.setListCount(pagination.getListCount());
            
            // 리뷰 목록 조회
            output = reviewBoardService.getList(input);
            
            // 각 리뷰의 이미지 URL 설정
            for(ReviewBoard item : output) {
                item.setImgUrl(fileHelper.getUrl(item.getImgUrl()));
            }

        } catch (Exception e) {
            webHelper.serverError(e);

        }

        // 뷰에 데이터 전달
        model.addAttribute("review", output);        // 리뷰 목록
        model.addAttribute("keyword", keyword);      // 검색어
        model.addAttribute("pagination", pagination); // 페이지네이션 정보

        // 리뷰 목록 페이지로 이동
        return "board/product/list";
     }
    
    /**
    * 리뷰 상세보기 페이지를 위한 컨트롤러
    * 
    * @param model 뷰에 전달할 데이터를 저장하는 Model 객체
    * @param reviewBoardId 조회할 리뷰의 ID
    * @return 리뷰 상세 페이지의 뷰 이름
    */
    @SuppressWarnings("null")
    @GetMapping("/board/product/read/{reviewBoardId}")
    public String reviewRead(Model model,
            @PathVariable("reviewBoardId") int reviewBoardId){
        
        // 리뷰 게시글 정보를 담을 객체 생성
        ReviewBoard input = new ReviewBoard();
        input.setReviewBoardId(reviewBoardId);
        
        // 리뷰 이미지 정보를 담을 객체 생성
        ReviewImg reviewImg =new ReviewImg();
        reviewImg.setReviewBoardId(reviewBoardId);

        ReviewBoard output = null;
        
        // 리뷰 게시글 조회
        try {
            output = reviewBoardService.getItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }

        // 상품 이미지 URL 설정
        output.setImgUrl(fileHelper.getUrl(output.getImgUrl()));

        // 리뷰에 첨부된 이미지들 조회
        List<ReviewImg> reviewImgOutput = null;
        try {
            reviewImgOutput = reviewImgService.getList(reviewImg);
        } catch (Exception e) {
            webHelper.serverError(e);
        }       
        
        // 각 리뷰 이미지의 URL 설정
        for(int i=0;i<reviewImgOutput.size();i++) {
            ReviewImg r = reviewImgOutput.get(i);
            r.setImgUrl(fileHelper.getUrl(reviewImgOutput.get(i).getImgUrl()));
        }
        
        // 뷰에 데이터 전달
        model.addAttribute("reviewInfo", output);            // 리뷰 게시글 정보
        model.addAttribute("reviewImgs", reviewImgOutput);   // 리뷰 이미지 목록

        // 리뷰 상세 페이지로 이동
        return "board/product/read";
    }
    
    /**
     * 리뷰 작성을 위한 주문 목록 조회 컨트롤러
     * 
     * @param model 뷰에 전달할 데이터를 저장하는 Model 객체
     * @param memberInfo 세션에 저장된 회원 정보
     * @return 주문 목록 페이지의 뷰 이름
     */
    @SuppressWarnings("null")
    @GetMapping("/review/search_board_list")
    public String reviewSelectOrder(Model model,
        @SessionAttribute("memberInfo") Member memberInfo) {

        // 회원의 주문 목록을 조회하기 위한 객체 생성
        Order input = new Order();
        input.setMemberId(memberInfo.getMemberId());

        // 회원의 전체 주문 목록 조회
        List<Order> orderId = null;
        try {
            orderId = orderService.getList(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }
        
        // 주문에 포함된 상품 목록을 저장할 리스트
        List<OrderItem> order = new ArrayList<>();

        // 각 주문별로 주문상품 정보 조회
        for(Order item : orderId) {
            OrderItem inputTmp = new OrderItem();
            inputTmp.setOrderId(item.getOrderId());

            // 주문에 포함된 상품 목록 조회
            List<OrderItem> output = null;
            try {
              output = orderItemService.getList(inputTmp);
            } catch (Exception e) {
                webHelper.serverError(e);
            }

            // 각 상품의 이미지 URL 설정
            for(int i=0;i<output.size();i++) {
                output.get(i).setImgUrl(fileHelper.getUrl(output.get(i).getImgUrl()));
            }

            // 조회된 주문상품들을 전체 목록에 추가
            order.addAll(output);
        }
        
        // 뷰에 데이터 전달
        model.addAttribute("items", order);
        // 주문 목록 페이지로 이동
        return "order/search_board_list";
    }  
   
}
