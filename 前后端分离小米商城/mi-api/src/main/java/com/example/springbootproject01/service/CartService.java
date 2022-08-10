package com.example.springbootproject01.service;

import com.example.springbootproject01.bean.Cart;
import com.example.springbootproject01.bean.CartEx;

import java.util.List;

public interface CartService {
    Boolean insert(Cart cart);
    List<Cart> getByUserId(Integer userId);
    Integer update(Cart cart);
    Integer delete(Integer id);
    void delAll(Integer userId);
    List<CartEx> getAll();
}
