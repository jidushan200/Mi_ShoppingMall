package com.example.springbootproject01.service.impl;

import com.example.springbootproject01.bean.Admin;
import com.example.springbootproject01.bean.AdminExample;
import com.example.springbootproject01.bean.User;
import com.example.springbootproject01.mapper.AdminMapper;
import com.example.springbootproject01.mapper.UserMapper;
import com.example.springbootproject01.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private UserMapper userMapper;


    @Override
    public boolean checkAgain(String username) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return admins.size() == 0;
    }

    @Override
    public boolean enroll(Admin admin) {
        int id = adminMapper.insertSelective(admin);
        if (id != 0) {
            User user = new User();
            user.setAdminId(admin.getId());
            userMapper.insertSelective(user);
        }
        return id != 0;
    }

    @Override
    public Admin login(String username, String password,String admin) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        criteria.andAdminEqualTo(admin);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        Admin admin1 = new Admin();
        if (admins.size()>0){
            admin1=admins.get(0);
        }
        return admin1;
    }

    @Override
    public Boolean update(String oldPwd,Admin admin) {
        Admin admin1 = adminMapper.selectByPrimaryKey(admin.getId());
        if (Objects.equals(admin1.getPassword(), oldPwd)){
            return adminMapper.updateByPrimaryKeySelective(admin)!=0;
        }
        return false;
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(null);
    }

    @Override
    public Boolean insertAdmin(Admin admin) {
        return adminMapper.insertSelective(admin)>0;
    }

    @Override
    public Boolean updateAdmin(Admin admin) {
        return adminMapper.updateByPrimaryKey(admin)>0;
    }

    @Override
    public Boolean deleteAdmin(Integer id) {
        return adminMapper.deleteByPrimaryKey(id)>0;
    }


}
