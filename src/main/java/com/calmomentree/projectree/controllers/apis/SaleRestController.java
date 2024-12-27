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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Sale API", description = "매출 Dashboard 관련 API")
public class SaleRestController {
    @Autowired
    private RestHelper restHelper;

    @Autowired
    private SaleService saleService;

    @GetMapping("/api/sales")
    @Operation(summary = "월 매출 조회 데이터", description = "매출 조회 데이터를 Json파일로 전송합니다.")
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
