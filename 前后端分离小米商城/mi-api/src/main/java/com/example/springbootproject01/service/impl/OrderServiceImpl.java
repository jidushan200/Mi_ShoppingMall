package com.example.springbootproject01.service.impl;

import com.example.springbootproject01.bean.Order;
import com.example.springbootproject01.bean.OrderAllExample;
import com.example.springbootproject01.mapper.OrderMapper;
import com.example.springbootproject01.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public Integer insert(Order order) {
        System.out.println(order);
        orderMapper.insertSelective(order);
        return order.getId();
    }

    @Override
    public List<OrderAllExample> getOrderAllExampleById(Integer userId) {
        return orderMapper.getOrderAllExampleById(userId);
    }

    @Override
    public List<OrderAllExample> getOrderAllExample() {
        return orderMapper.getOrderAllExample();
    }

    @Override
    public Boolean update(Order order) {
        return orderMapper.updateByPrimaryKeySelective(order)>0;
    }

    @Override
    public Integer deleteById(Integer id) {
        return orderMapper.deleteByPrimaryKey(id);
    }
}
