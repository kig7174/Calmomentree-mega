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

        for (int i=0; i<products.size(); i++) {
            Product p = products.get(i);
            p.setListImgUrl(fileHelper.getUrl(p.getListImgUrl()));
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

        try {
            output = productService.getItem(input);

            detail = prodImgService.getList(inputDetail);
            list = prodImgService.getList(inputList);
            info = prodImgService.getList(inputInfo);
        } catch (Exception e) {
            webHelper.serverError(e);
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

        model.addAttribute("product", output);
        model.addAttribute("detailImg", detail);
        model.addAttribute("list", list);
        model.addAttribute("infoImg", info);
        
        return "product/detail";
    }
}
