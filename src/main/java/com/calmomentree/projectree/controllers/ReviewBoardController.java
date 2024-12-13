package com.calmomentree.projectree.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.Pagination;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.Board;
import com.calmomentree.projectree.models.Product;
import com.calmomentree.projectree.models.ReviewBoard;
import com.calmomentree.projectree.models.ReviewImg;
import com.calmomentree.projectree.services.ProductService;
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
    private ProductService productService;
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
            Board.setOffset(pagination.getOffset());
            Board.setListCount(pagination.getListCount());

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
     
    @GetMapping("/board/product/read/{reviewBoardId}/{prodId}")
    public String reviewRead(Model model,
            @PathVariable("reviewBoardId") int reviewBoardId,
            @PathVariable("prodId") int prodId) {
        
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

        // 상품
        Product product = new Product();
        product.setProdId(prodId);

        Product prodOutput = null;

        try {
            prodOutput = productService.getItem(product);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // 리뷰 이미지
        List<ReviewImg> reviewImgOutput = null;

        try {
            reviewImgOutput = reviewImgService.getList(reviewImg);
        } catch (Exception e) {
            webHelper.serverError(e);
        }
        // log.info("=================================================");
        // log.info("리뷰이미지 : " + reviewImgOutput);
        // log.info("=================================================");
       
        
        for(int i=0;i<reviewImgOutput.size();i++) {
            ReviewImg r = reviewImgOutput.get(i);
            r.setImgUrl(fileHelper.getUrl(reviewImgOutput.get(i).getImgUrl()));
            
            // log.info("=================================================");
            // log.info("리뷰이미지(2) : " + reviewImgOutput);
            // log.info("=================================================");
        }
           
        model.addAttribute("reviewInfo", output);
        model.addAttribute("prodInfo", prodOutput);
        model.addAttribute("reviewImgs", reviewImgOutput);

        return "board/product/read";
    }
 
}
