package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.OrderItem;

public interface OrderItemService {
    public OrderItem addItem(OrderItem params) throws Exception;

    public OrderItem editItem(OrderItem params) throws Exception;

    public int deleteItem(OrderItem params) throws Exception;

    public OrderItem getItem(OrderItem params) throws Exception;

    public List<OrderItem> getList(OrderItem params) throws Exception;

    public int getCount(OrderItem params) throws Exception;
}
