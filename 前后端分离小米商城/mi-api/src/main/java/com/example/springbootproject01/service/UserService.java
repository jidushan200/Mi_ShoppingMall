package com.example.springbootproject01.service;

import com.example.springbootproject01.bean.User;

public interface UserService {
    Boolean updateUser(User user);
    User getUser(Integer userId);
    Integer getIdByAdminId(Integer adminId);
    Integer getAdminId(Integer id);
}
