package com.calmomentree.projectree.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.Pagination;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.Board;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.models.Order;
import com.calmomentree.projectree.models.OrderItem;
import com.calmomentree.projectree.services.BoardService;
import com.calmomentree.projectree.services.OrderItemService;
import com.calmomentree.projectree.services.OrderService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private WebHelper webHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handle(Exception ex) {
        return "error/404";
    }

    /**
     * 게시판 목록페이지
     */
    @GetMapping("/board/qna/list")
    public String qnaList(Model model,
            // 검색어 파라미터
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int nowPage) {

        int totalCount = 0; // 전체 게시글 수
        int listCount = 10; // 한 페이지당 표시할 목록 수
        int pageCount = 5; // 한 그룹당 표시할 페이지 번호 수

        // 페이지 번호를 계산한 결과가 저장될 객체
        Pagination pagination = null;

        // 조회 조건에 사용할 객체
        Board input = new Board();
        input.setBoardTitle(keyword);

        List<Board> output = null;

        try {
            // 전체 게시글 수 조회
            totalCount = boardService.getCount(input);
            // 페이지 번호 계산 ---> 계산결과를 로그로 출력될 것이다.
            pagination = new Pagination(nowPage, totalCount, listCount, pageCount);

            // SQL의 Limit절에서 사용될 값을 Beans의 static 변수에 저장
            Board.setOffset(pagination.getOffset());
            Board.setListCount(pagination.getListCount());

            output = boardService.getList(input);
        } catch (Exception e) {
            webHelper.serverError(e);

        }

        // view에 데이터 전달
        model.addAttribute("boards", output);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagination", pagination);

        return "board/qna/list";

    }

    /**
     * qna 게시판 상세페이지
     */
    @SuppressWarnings("null")
    @GetMapping("/board/qna/read/{boardId}")
    public String qnaRead(Model model,
            HttpServletResponse response,
            @PathVariable("boardId") int boardId) {

        // 조회 조건에 사용할 변수를 Beans에 저장
        Board input = new Board();
        input.setBoardId(boardId);

        Board output = null;

        try {
            output = boardService.getItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // 업로드 사진의 경로를 URL로 변환.
        output.setUploadImg(fileHelper.getUrl(output.getUploadImg()));

        // View에 데이터 전달
        model.addAttribute("board", output);

        return "board/qna/read";

    }

    /**
     * 게시글 수정 페이지
     */
    @GetMapping("/board/qna/modify/{boardId}")
    public String boardEdit(Model model,
            @PathVariable("boardId") int boardId
    // @PathVariable("memberId") int memberId
    ) {

        // 검색 조건으로 사용
        Board input = new Board();
        input.setBoardId(boardId);
        // input.setMemberId(memberId);

        // 수정할 데이터의 현재 값을 조회
        Board board = null;

        try {
            board = boardService.getItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // View에 데이터 전달
        model.addAttribute("board", board);

        return "board/qna/modify";
    }

    @GetMapping("/myshop/board_list")
    public String myshopBoard(Model model,
            @SessionAttribute("memberInfo") Member memberInfo,
            @RequestParam(value = "page", defaultValue = "1") int nowPage) {

        int totalCount = 0; // 전체 게시글 수
        int listCount = 10; // 한 페이지당 표시할 목록 수
        int pageCount = 5; // 한 그룹당 표시할 페이지 번호 수

        // 페이지 번호를 계산한 결과가 저장될 객체
        Pagination pagination = null;

        Board input = new Board();
        input.setMemberId(memberInfo.getMemberId());

        List<Board> output = null;

        try {
            // 전체 게시글 수 조회
            totalCount = boardService.getCount(input);
            // 페이지 번호 계산 ---> 계산결과를 로그로 출력될 것이다.
            pagination = new Pagination(nowPage, totalCount, listCount, pageCount);

            // SQL의 Limit절에서 사용될 값을 Beans의 static 변수에 저장
            Board.setOffset(pagination.getOffset());
            Board.setListCount(pagination.getListCount());

            output = boardService.getList(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        model.addAttribute("boards", output);
        model.addAttribute("pagination", pagination);

        return "/myshop/board_list";
    }

    @GetMapping("/order/search_board_list")
    public String reviewSelectOrder(Model model,
        @SessionAttribute("memberInfo") Member memberInfo) {

        // 회원 결제 페이지 일련 번호
        Order input = new Order();
        input.setMemberId(memberInfo.getMemberId());

        log.error("memberId: " +  memberInfo.getMemberId());
        List<Order> orderId = null;

        try {
            orderId = orderService.getList(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }
        log.error("회원결제페이지 일련번호: " + orderId);

        
        // OrderItem orderIdNum = null;
        // orderIdNum.setOrderId(orderId.getOrderId());
        

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

            order.addAll(output);
        }
        
        model.addAttribute("items", order);
        // try {
        //    order = orderItemService.getList(orderIdNum);
        // } catch (Exception e) {
        //     webHelper.serverError(e);
        // }

        return "order/search_board_list";
    }  
    
}
