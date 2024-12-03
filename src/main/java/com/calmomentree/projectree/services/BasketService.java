package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.Basket;

public interface BasketService {
    public Basket addItem(Basket params) throws Exception;

    public Basket editItem(Basket params) throws Exception;

    public int deleteItem(Basket params) throws Exception;

    public Basket getItem(Basket params) throws Exception;

    public List<Basket> getList(Basket params) throws Exception;

    public int getCount(Basket params) throws Exception;

    public Basket uniqueBasketCount(Basket parmas) throws Exception;

    public Basket editUniqueBasket(Basket params) throws Exception;

    public int deleteByOverDays() throws Exception;
}
