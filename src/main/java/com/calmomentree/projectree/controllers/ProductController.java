package com.calmomentree.projectree.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.Pagination;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.ProdImg;
import com.calmomentree.projectree.models.Product;
import com.calmomentree.projectree.services.ProdImgService;
import com.calmomentree.projectree.services.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


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
        input.setProdNameKor(keyword);
        
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
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagination", pagination);
        model.addAttribute("orderBySelected", orderBy);

        return "product/search";
    }

    
    @SuppressWarnings("null")
    @GetMapping("/product/detail/{prodId}")
    public String prodDetail(
        Model model,
        @PathVariable("prodId") int prodId
    ) {

        // 상품 객체 
        Product input = new Product();
        input.setProdId(prodId);

        Product output = null;

        // 상품 이미지 객체
        ProdImg inputDetail = new ProdImg();
        inputDetail.setImgType("detail");
        inputDetail.setProdId(prodId);

        ProdImg inputInfo = new ProdImg();
        inputInfo.setImgType("info");
        inputInfo.setProdId(prodId);

        List<ProdImg> detailList = null;
        List<ProdImg> infoList = null;

        try {
            output = productService.getItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        try {
            detailList = prodImgService.getList(inputDetail);
            infoList = prodImgService.getList(inputInfo);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        List<ProdImg> detail = new ArrayList<>();
        List<ProdImg> info = new ArrayList<>();

        // 이미지 변환
        for (int i=0; i<detailList.size(); i++) {
            ProdImg detailImg = detailList.get(i);

            detailImg.setImgUrl(fileHelper.getUrl(detailImg.getImgUrl()));

            detail.add(detailImg);
        }

        for (int i=0; i<infoList.size(); i++) {
            ProdImg infoImg = infoList.get(i);

            infoImg.setImgUrl(fileHelper.getUrl(infoImg.getImgUrl()));

            info.add(infoImg);
        }

        model.addAttribute("product", output);
        model.addAttribute("detailImg", detail);
        model.addAttribute("infoImg", info);
        
        return "product/detail";
    }
}
