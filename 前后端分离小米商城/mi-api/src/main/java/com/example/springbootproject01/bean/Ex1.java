package com.example.springbootproject01.bean;

import lombok.Data;

@Data
public class Ex1 extends Orderdetail{
    private Goods goods;

    @Override
    public String toString() {
        return super.toString()+"Ex1{" +
                "goods=" + goods +
                "} ";
    }
}
