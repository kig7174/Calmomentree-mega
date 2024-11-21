package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.Product;

public interface ProductService {
    public Product addItem(Product params) throws Exception;

    public Product editItem(Product params) throws Exception;

    public int deleteItem(Product params) throws Exception;

    public Product getItem(Product params) throws Exception;

    public List<Product> getList(Product params) throws Exception;

    public int getCount(Product params) throws Exception;
}
