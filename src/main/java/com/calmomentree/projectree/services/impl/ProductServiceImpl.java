package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.ProductMapper;
import com.calmomentree.projectree.models.Product;
import com.calmomentree.projectree.services.ProductService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product addItem(Product input) throws Exception {
        int rows = 0;

        try {
            rows = productMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return productMapper.selectItem(input);
    }

    @Override
    public Product editItem(Product input) throws Exception {
        int rows = 0;

        try {
            rows = productMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return productMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Product input) throws Exception {
        int rows = 0;

        try {
            rows = productMapper.delete(input);

            if (rows == 0) {
                throw new Exception("삭제된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 삭제에 실패했습니다.", e);
            throw e;
        }

        return rows;
    }

    @Override
    public Product getItem(Product input) throws Exception {
        Product output = null;

        try {
            output = productMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("교수 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<Product> getListByCategory(Product input) throws Exception {
        List<Product> output = null;

        try {
            output = productMapper.selectListByCategory(input);
        } catch (Exception e) {
            log.error("상품 목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<Product> getListBySearch(Product input) throws Exception {
        List<Product> output = null;

        try {
            output = productMapper.selectListBySearch(input);
        } catch (Exception e) {
            log.error("상품 목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int getCount(Product input) throws Exception {
        int output = 0;

        try {
            output = productMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    } 
}