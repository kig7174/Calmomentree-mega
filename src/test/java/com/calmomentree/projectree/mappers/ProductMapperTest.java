package com.calmomentree.projectree.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ProductMapperTest {
    @Autowired
    private ProductMapper productMapper;

    @Test
    @DisplayName("상품 등록 테스트")
    void addProd() {
        Product input = new Product();
        input.setProdNameKor("테스트 샴푸");
        input.setProdNameEng("test shampoo");
        input.setFuncTxt("기능 텍스트");
        input.setDescTxt("상세 텍스트");
        input.setPrice(50000);
        input.setIsDiscount("N");
        input.setCapacity("500ml");
        input.setSpecification("지성 두피로 인한 탈모 증상 완화에 도움을 주고, 약해진 모발을 케어하는 기능성 샴푸입니다. 두피의 유수분 밸런스를 조절해 피지 관리에 도움을 주고, 푸석한 모발을 윤기 있게 가꿔줍니다.");
        input.setUsePeriod("12개월");
        input.setUseMethod("미지근한 물로 두피와 모발을 충분히 적셔주세요. 적당량을 손에 덜어 젖은 머리에 거품을 내며 문지른 후, 미지근한 물로 깨끗이 헹궈주세요.");
        input.setManufacturer("유씨엘(주)");
        input.setReleaseDate("2024-11-22");
        input.setCategoryId(8);

        int output = productMapper.insert(input);

        log.debug("output : " + output);
    }

    @Test
    @DisplayName("상품 상세 테스트")
    void detailProd() {
        Product input = new Product();
        input.setProdId(5);

        productMapper.selectItem(input);
    }

    @Test
    @DisplayName("상품 목록 테스트")
    void listProdTest() {
        Product input = new Product();
        input.setCategoryId(4);

        List<Product> output = productMapper.selectListByCategory(input);

        log.debug("List : " + output);
    }

    @Test
    @DisplayName("상품 검색 테스트")
    void searchProdTest() {
        Product input = new Product();
        input.setProdNameKor("샴푸");
        Product.setOrderBy("recent");

        List<Product> output = productMapper.selectListBySearch(input);

        log.debug("List : " + output);
    }

    @Test
    @DisplayName("상품 검색 카운트 테스트")
    void searchProdCountTest() {
        Product input = new Product();
        input.setProdNameKor("샴푸");

        int output = productMapper.selectCount(input);

        log.debug("output : " + output);
    }
}
