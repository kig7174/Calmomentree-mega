package com.calmomentree.projectree.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.calmomentree.projectree.helpers.Pagination;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.Product;
import com.calmomentree.projectree.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class ProductController {
    
    @Autowired
    private WebHelper webHelper;

    @Autowired
    private ProductService productService;

    @ResponseBody
    @GetMapping("/product/search_ok")
    public void searchList(Model model,
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "order_by", defaultValue = "recent") String orderBy,
        @RequestParam(value = "page", defaultValue = "1") int nowPage) {

        if (keyword == null || keyword.equals("")) {
            return;
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

            if (orderBy.equals("recent")) {
                orderBy = "relase_date DESC";
            } else if (orderBy.equals("name")) {
                orderBy = "prod_name_kor ASC";
            } else if (orderBy.equals("priceasc")) {
                orderBy = "price ASC";
            } else if (orderBy.equals("pricedesc")) {
                orderBy = "price DESC";
            } else if (orderBy.equals("manu_name")) {
                orderBy = "manufacturer ASC";
            } else if (orderBy.equals("review")) {
                orderBy = "review ASC";
            } else {
                orderBy = "prod_id DESC";
            }

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

        webHelper.redirect("/product/search");;
    }
}
