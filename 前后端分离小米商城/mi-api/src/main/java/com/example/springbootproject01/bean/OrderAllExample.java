package com.example.springbootproject01.bean;

import lombok.Data;

import java.util.List;

@Data
public class OrderAllExample extends Order{
    private List<Ex1> ex1List;

    @Override
    public String toString() {
        return super.toString()+"OrderAllExample{" +
                "ex1List=" + ex1List +
                "} " ;
    }
}
