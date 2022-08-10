package com.example.springbootproject01.controller.user;

import com.example.springbootproject01.bean.Address;
import com.example.springbootproject01.bean.AddressEx;
import com.example.springbootproject01.service.AddressService;
import com.example.springbootproject01.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@Transactional
@RestController
@RequestMapping("/address")
public class AddressController {
    @Resource
    private AddressService addressService;

    @Resource
    private UserService userService;

    @GetMapping("/getAll")
    public List<AddressEx> getAll(){
        return addressService.getAll();
    }

    @PostMapping("/insert")
    public Boolean insert(Address address){
        return addressService.insert(address);
    }

    @GetMapping("/get")
    public List<Address> get(Integer userId){
        return addressService.get(userId);
    }

    @DeleteMapping("/delete")
    public Boolean delete(Integer id){
        return addressService.del(id);
    }

    @PutMapping("/update")
    public Boolean update(Address address){
        return addressService.update(address);
    }
}
