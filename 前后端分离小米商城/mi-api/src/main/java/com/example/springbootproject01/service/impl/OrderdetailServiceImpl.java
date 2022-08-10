package com.example.springbootproject01.service.impl;

import com.example.springbootproject01.bean.Cart;
import com.example.springbootproject01.bean.Orderdetail;
import com.example.springbootproject01.bean.OrderdetailExample;
import com.example.springbootproject01.mapper.OrderdetailMapper;
import com.example.springbootproject01.service.OrderdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderdetailServiceImpl implements OrderdetailService {
    @Resource
    private OrderdetailMapper orderdetailMapper;

    @Override
    public Integer insert(Integer orderId, List<Cart> cartList) {
        int sum=0;
        Orderdetail orderdetail = new Orderdetail();
        orderdetail.setOrderId(orderId);
        for (Cart cart:cartList) {
            orderdetail.setGoodsId(cart.getGoodsId());
            orderdetail.setGoodsNum(cart.getGoodsNum());
            orderdetail.setNote(cart.getNote());
            sum+=orderdetailMapper.insertSelective(orderdetail);
        }
        return sum;
    }

    @Override
    public Integer delete(Integer id) {
        return orderdetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer countByOrderId(Integer orderId) {
        OrderdetailExample orderdetailExample = new OrderdetailExample();
        OrderdetailExample.Criteria criteria = orderdetailExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        return orderdetailMapper.countByExample(orderdetailExample);
    }
}
