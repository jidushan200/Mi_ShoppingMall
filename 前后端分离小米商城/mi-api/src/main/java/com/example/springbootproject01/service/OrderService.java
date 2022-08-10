package com.example.springbootproject01.service;

import com.example.springbootproject01.bean.Order;
import com.example.springbootproject01.bean.OrderAllExample;

import java.util.List;

public interface OrderService {
    Integer insert(Order order);
    List<OrderAllExample> getOrderAllExampleById(Integer userId);
    List<OrderAllExample> getOrderAllExample();
    Boolean update(Order order);
    Integer deleteById(Integer id);
}
