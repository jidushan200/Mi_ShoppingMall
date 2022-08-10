package com.example.springbootproject01.controller.user;

import com.example.springbootproject01.bean.*;
import com.example.springbootproject01.service.CartService;
import com.example.springbootproject01.service.OrderService;
import com.example.springbootproject01.service.OrderdetailService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;
    @Resource
    private OrderdetailService orderdetailService;

    @PutMapping("/put")
    public Boolean put(Order order){
        return orderService.update(order);
    }

    @DeleteMapping("/del")
    public void del(Orderdetail orderdetail){
        orderdetailService.delete(orderdetail.getId());
        if (orderdetailService.countByOrderId(orderdetail.getOrderId())==0){
            orderService.deleteById(orderdetail.getOrderId());
        }
    }

    @GetMapping("/admin/getAll")
    public List<OrderAllExample> getAll(){
        return orderService.getOrderAllExample();
    }

    @GetMapping("/get")
    public List<OrderAllExample> get(Integer userId){
        return orderService.getOrderAllExampleById(userId);
    }

    @PostMapping("/add")
    public Integer add(Address address) {
        System.out.println(address);
        Order order = new Order();
        order.setUserId(address.getUserId());
        order.setConsignee(address.getConsignee());
        order.setAddress(address.getRegion()+address.getDetailAddress());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String parse = simpleDateFormat.format(date);
        try {
            Date parse1 = simpleDateFormat.parse(parse);
            order.setCreationTime(parse1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Integer orderId = orderService.insert(order);
        List<Cart> all = cartService.getByUserId(address.getUserId());
        if (orderId != null) {
            Integer insert = orderdetailService.insert(orderId, all);
            if (insert>0){
                cartService.delAll(address.getUserId());
            }
            return insert;
        }
        return 0;
    }
}
