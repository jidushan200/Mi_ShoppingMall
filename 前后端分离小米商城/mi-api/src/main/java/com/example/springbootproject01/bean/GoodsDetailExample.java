package com.example.springbootproject01.bean;

import lombok.Data;

import java.util.List;

@Data
public class GoodsDetailExample {
    private Goods goods;
    private List<GoodsConfig> goodsConfigList;
    private List<GoodsColor> goodsColorList;
}
