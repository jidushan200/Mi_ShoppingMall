package com.example.springbootproject01.controller.user;

import com.example.springbootproject01.bean.Cart;
import com.example.springbootproject01.bean.CartEx;
import com.example.springbootproject01.service.CartService;
import org.springframework.http.HttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;
@Transactional
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;

    @GetMapping("/getAll")
    public List<CartEx> getAll(){
        return cartService.getAll();
    }

    @DeleteMapping("/clear")
    public void clear(Integer userId){
        cartService.delAll(userId);
    }

    @DeleteMapping("/delete")
    public Integer delete(Integer id){
        return cartService.delete(id);
    }

    @PutMapping("/put")
    public Integer put(Cart cart){
        return cartService.update(cart);
    }

    @GetMapping("/get")
    public List<Cart> get(Integer userId){
        return cartService.getByUserId(userId);
    }

    @PostMapping("/add")
    public Boolean add(Cart cart){
        System.out.println(cart);
        return cartService.insert(cart);
    }
}
