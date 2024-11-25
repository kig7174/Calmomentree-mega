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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProductController {
    
    @Autowired
    private WebHelper webHelper;

    @Autowired
    private ProductService productService;

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

    @GetMapping("/product/detail/{prodId}")
    public String prodDetail(
        Model model,
        @PathVariable("prodId") int prodId
    ) {

        Product input = new Product();
        input.setProdId(prodId);

        Product output = null;

        try {
            output = productService.getItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        model.addAttribute("product", output);
        
        return "product/detail";
    }
}
