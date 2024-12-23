package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.Sale;

public interface SaleService {
    public void addItem() throws Exception;

    public Sale editItem(Sale params) throws Exception;

    public int deleteItem(Sale params) throws Exception;

    public Sale getItem(Sale params) throws Exception;

    public List<Sale> getList(Sale params) throws Exception;

    public int getCount(Sale params) throws Exception;
}

