package com.example.springbootproject01.controller.admin;

import com.example.springbootproject01.bean.Admin;
import com.example.springbootproject01.service.AdminService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
@Transactional
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Resource
    private AdminService adminService;

//    后台登录
    @GetMapping("/login")
    public Map<String,String> login(Admin admin){
//        System.out.println(admin);
        Admin login = adminService.login(admin.getUsername(), admin.getPassword(), admin.getAdmin());
        HashMap<String, String> map = new HashMap<>();
        map.put("username",login.getUsername());
        map.put("id", String.valueOf(login.getId()));
        return map;
    }
}
