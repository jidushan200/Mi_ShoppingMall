package com.example.springbootproject01.service;

import com.example.springbootproject01.bean.Address;
import com.example.springbootproject01.bean.AddressEx;

import java.util.List;

public interface AddressService {
    Boolean insert(Address address);
    List<Address> get(Integer userId);
    Boolean del(Integer id);
    Boolean update(Address address);
    List<AddressEx> getAll();
}
