package com.calmomentree.projectree.controllers.apis;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.Sale;
import com.calmomentree.projectree.services.SaleService;

@RestController
public class SaleRestController {
    @Autowired
    private RestHelper restHelper;

    @Autowired
    private SaleService saleService;

    @GetMapping("/api/sales")
    public Map<String, Object> salesMonth() {
        List<Sale> month = null;

        try {
            month = saleService.getList(null);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", month);

        return restHelper.sendJson(data);
    }
}
