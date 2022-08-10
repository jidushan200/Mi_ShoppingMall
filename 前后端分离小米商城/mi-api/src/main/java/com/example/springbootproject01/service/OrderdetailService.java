package com.example.springbootproject01.service;

import com.example.springbootproject01.bean.Cart;

import java.util.List;

public interface OrderdetailService {
    Integer insert(Integer orderId, List<Cart> cartList);
    Integer delete(Integer id);
    Integer countByOrderId(Integer orderId);
}
