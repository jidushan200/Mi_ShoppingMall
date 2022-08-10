package com.example.springbootproject01.service.impl;

import com.example.springbootproject01.bean.Cart;
import com.example.springbootproject01.bean.CartEx;
import com.example.springbootproject01.bean.CartExample;
import com.example.springbootproject01.mapper.CartMapper;
import com.example.springbootproject01.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;

    @Override
    public Boolean insert(Cart cart) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andGoodsIdEqualTo(cart.getGoodsId());
        criteria.andNoteEqualTo(cart.getNote());
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        if (carts.size()>0){
            Cart cart1 = carts.get(0);
            cart1.setGoodsNum(cart1.getGoodsNum()+1);
            return cartMapper.updateByPrimaryKey(cart1)!=0;
        }
        return cartMapper.insertSelective(cart)!=0;
    }

    @Override
    public List<Cart> getByUserId(Integer userId) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return cartMapper.selectByExample(cartExample);
    }

    @Override
    public Integer update(Cart cart) {
        return cartMapper.updateByPrimaryKey(cart);
    }

    @Override
    public Integer delete(Integer id) {
        return cartMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delAll(Integer userId) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        cartMapper.deleteByExample(cartExample);
    }

    @Override
    public List<CartEx> getAll() {
        return cartMapper.getAll();
    }
}
