package com.example.springbootproject01.controller.user;

import com.example.springbootproject01.bean.AddressExample;
import com.example.springbootproject01.bean.Admin;
import com.example.springbootproject01.service.AdminService;
import com.example.springbootproject01.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Transactional
@RestController
public class AdminController {
    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @DeleteMapping("/admin/delete")
    public Boolean delete(Integer id){
        return adminService.deleteAdmin(id);
    }

    @PutMapping("/admin/put")
    public boolean put(Admin admin) {
//        System.out.println(admin);
        return adminService.updateAdmin(admin);

    }

    @GetMapping("/all")
    public List<Admin> all() {
        return adminService.getAll();
    }

    @GetMapping("/checkAgain")
    public boolean checkAgain(@RequestParam(value = "username", required = false, defaultValue = "") String username) {
        return adminService.checkAgain(username);
    }

    @PostMapping("/enroll")
    public boolean enroll(Admin admin) {
        if (admin.getAdmin() == null) {
            admin.setAdmin("0");
            return adminService.enroll(admin);
        }
        if (Objects.equals(admin.getAdmin(), "0")) {
            return adminService.enroll(admin);
        }
        if (Objects.equals(admin.getAdmin(), "1")) {
            return adminService.insertAdmin(admin);
        }
        return false;
    }

    @GetMapping("/login")
    public Map<String, Object> login(String username, String password) {

        Admin login = adminService.login(username, password, "0");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userService.getIdByAdminId(login.getId()));
        map.put("userName", login.getUsername());
        return map;
    }

    @PutMapping("/updatePwd")
    public Boolean updatePwd(Integer userId, String oldPwd, String password) {
        Integer adminId = userService.getAdminId(userId);
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setPassword(password);
        return adminService.update(oldPwd, admin);
    }
}
