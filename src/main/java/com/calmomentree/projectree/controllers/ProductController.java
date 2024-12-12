package com.calmomentree.projectree.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.Pagination;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.ProdImg;
import com.calmomentree.projectree.models.Product;
import com.calmomentree.projectree.models.ReviewBoard;
import com.calmomentree.projectree.models.ReviewImg;
import com.calmomentree.projectree.services.ProdImgService;
import com.calmomentree.projectree.services.ProductService;
import com.calmomentree.projectree.services.ReviewBoardService;
import com.calmomentree.projectree.services.ReviewImgService;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Controller
public class ProductController {
    
    @Autowired
    private WebHelper webHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProdImgService prodImgService;

    @Autowired
    private ReviewBoardService reviewBoardService;

    @Autowired
    private ReviewImgService reviewImgService;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handle(Exception ex) {
        return "error/404";
    }

    @SuppressWarnings("null")
    @GetMapping("/product/search")
    public String searchList(Model model,
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "order_by", defaultValue = "recent") String orderBy,
        @RequestParam(value = "page", defaultValue = "1") int nowPage) {

        if (keyword == null || keyword.equals("")) {
            return "product/search";
        }

        int totalCount = 0;
        int listCount = 8;
        int pageCount = 3;

        Pagination pagination = null;

        Product input = new Product();
        input.setProdNameKor(keyword.trim());
        
        List<Product> products = null;
        
        try {
            totalCount = productService.getCount(input);
            pagination = new Pagination(nowPage, totalCount, listCount, pageCount);

            Product.setOffset(pagination.getOffset());
            Product.setListCount(pagination.getListCount());
            Product.setOrderBy(orderBy);

            products = productService.getListBySearch(input);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }

        for (int i=0; i<products.size(); i++) {
            Product p = products.get(i);
            p.setListImgUrl1(fileHelper.getUrl(p.getListImgUrl1()));
            p.setListImgUrl2(fileHelper.getUrl(p.getListImgUrl2()));
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagination", pagination);
        model.addAttribute("orderBySelected", orderBy);

        return "product/search";
    }

    @GetMapping("product/list/{categoryId}")
    public String prodList(
        Model model,
        @PathVariable("categoryId") int categoryId
    ) {
        Product input = new Product();
        input.setCategoryId(categoryId);

        List<Product> products = null;

        try {
            products = productService.getListByCategory(input);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }

        for (int i=0; i<products.size(); i++) {
            Product p = products.get(i);
            p.setListImgUrl1(fileHelper.getUrl(p.getListImgUrl1()));
            p.setListImgUrl2(fileHelper.getUrl(p.getListImgUrl2()));
        }

        if (categoryId == 1) {
            for (int i=0; i<products.size(); i++) {
                Product p = products.get(i);
                p.setParentCategoryName("ALL");
            }
        }

        model.addAttribute("products", products);

        return "product/list";
    }
    
    @SuppressWarnings("null")
    @GetMapping("/product/detail/{prodId}")
    public String prodDetail(
        Model model,
        @PathVariable("prodId") int prodId,
        @RequestParam(value = "page", defaultValue = "1") int nowPage
    ) {

        // 상품 객체 
        Product input = new Product();
        input.setProdId(prodId);

        Product output = null;


        // 상품 이미지 객체
        ProdImg inputDetail = new ProdImg();
        inputDetail.setImgType("detail");
        inputDetail.setProdId(prodId);

        ProdImg inputList = new ProdImg();
        inputList.setImgType("list");
        inputList.setProdId(prodId);

        ProdImg inputInfo = new ProdImg();
        inputInfo.setImgType("info");
        inputInfo.setProdId(prodId);

        List<ProdImg> detail = null;
        List<ProdImg> list = null;
        List<ProdImg> info = null;

        // 리뷰 페이지 번호 계산
        int totalCount = 0;
        int listCount = 5;
        int pageCount = 5;

        Pagination pagination = null;

        // 상품에 대한 리뷰 객체
        ReviewBoard inputReview = new ReviewBoard();
        inputReview.setProdId(prodId);

        List<ReviewBoard> review = null;


        try {
            output = productService.getItem(input);

            detail = prodImgService.getList(inputDetail);
            list = prodImgService.getList(inputList);
            info = prodImgService.getList(inputInfo);

            totalCount = reviewBoardService.getCount(inputReview);
            pagination = new Pagination(nowPage, totalCount, listCount, pageCount);

            ReviewBoard.setOffset(pagination.getOffset());
            ReviewBoard.setListCount(pagination.getListCount());

            review = reviewBoardService.getList(inputReview);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }


        // 상품에 대한 리뷰 이미지 객체
        for (int i=0; i<review.size(); i++) {
            ReviewImg r = new ReviewImg();
            r.setReviewBoardId(review.get(i).getReviewBoardId());

            List<ReviewImg> reImg = null;

            try {
                reImg = reviewImgService.getList(r);
            } catch (Exception e) {
                webHelper.serverError(e);
                return null;
            }

            review.get(i).setReviewImgUrl(reImg);
        }
        

        // 이미지 변환
        for (int i=0; i<detail.size(); i++) {
            ProdImg img = detail.get(i);
            img.setImgUrl(fileHelper.getUrl(img.getImgUrl()));
        }

        for (int i=0; i<list.size(); i++) {
            ProdImg img = list.get(i);
            img.setImgUrl(fileHelper.getUrl(img.getImgUrl()));
        }

        for (int i=0; i<info.size(); i++) {
            ProdImg img = info.get(i);
            img.setImgUrl(fileHelper.getUrl(img.getImgUrl()));
        }

        for (int i=0; i<review.size(); i++) {
            List<ReviewImg> img = review.get(i).getReviewImgUrl();

            for (int j=0; j<img.size(); j++) {
                img.get(j).setImgUrl(fileHelper.getUrl(img.get(j).getImgUrl()));
            }
        }

        model.addAttribute("product", output);
        model.addAttribute("detailImg", detail);
        model.addAttribute("listImg", list);
        model.addAttribute("infoImg", info);
        model.addAttribute("reviews", review);
        model.addAttribute("pagination", pagination);
        
        return "product/detail";
    }
}
