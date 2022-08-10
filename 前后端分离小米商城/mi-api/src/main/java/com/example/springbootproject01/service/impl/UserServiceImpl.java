package com.example.springbootproject01.service.impl;

import com.example.springbootproject01.bean.User;
import com.example.springbootproject01.bean.UserExample;
import com.example.springbootproject01.mapper.UserMapper;
import com.example.springbootproject01.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Boolean updateUser(User user) {
        int i = userMapper.updateByPrimaryKeySelective(user);
        return i==0;
    }

    @Override
    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer getIdByAdminId(Integer adminId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAdminIdEqualTo(adminId);
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0).getId();
    }

    @Override
    public Integer getAdminId(Integer id) {
        return userMapper.selectByPrimaryKey(id).getAdminId();
    }
}
