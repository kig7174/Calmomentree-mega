package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.Order;

public interface OrderService {
    public Order addItem(Order params) throws Exception;

    public Order editItem(Order params) throws Exception;

    public int deleteItem(Order params) throws Exception;

    public Order getItem(Order params) throws Exception;

    public List<Order> getList(Order params) throws Exception;

    public int getCount(Order params) throws Exception;

    public int overCount(Order params) throws Exception;
}
