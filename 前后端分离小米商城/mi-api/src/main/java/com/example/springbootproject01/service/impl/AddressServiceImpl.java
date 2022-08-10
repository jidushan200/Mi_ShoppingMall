package com.example.springbootproject01.service.impl;

import com.example.springbootproject01.bean.Address;
import com.example.springbootproject01.bean.AddressEx;
import com.example.springbootproject01.bean.AddressExample;
import com.example.springbootproject01.mapper.AddressMapper;
import com.example.springbootproject01.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;


    @Override
    public Boolean insert(Address address) {
        int i = addressMapper.insertSelective(address);
        if (i!=0){
            return true;
        }
        return false;
    }

    @Override
    public List<Address> get(Integer userId) {
        AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andUserIdEqualTo(userId);
        List<Address> list = new ArrayList<>();
        List<Address> addresses = addressMapper.selectByExample(addressExample);
        if (addresses.size()>0){
            list=addresses;
        }
        return list;
    }

    @Override
    public Boolean del(Integer id) {
        return addressMapper.deleteByPrimaryKey(id)!=0;
    }

    @Override
    public Boolean update(Address address) {
        return addressMapper.updateByPrimaryKey(address)!=0;
    }

    @Override
    public List<AddressEx> getAll() {
        return addressMapper.getAll();
    }

}
