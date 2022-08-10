package com.example.springbootproject01.service;

import com.example.springbootproject01.bean.Admin;

import java.util.List;

public interface AdminService {
    boolean checkAgain(String username);
    boolean enroll(Admin admin);
    Admin login(String username,String password,String admin);
    Boolean update(String oldPwd,Admin admin);
    List<Admin> getAll();
    Boolean insertAdmin(Admin admin);
    Boolean updateAdmin(Admin admin);
    Boolean deleteAdmin(Integer id);
}
