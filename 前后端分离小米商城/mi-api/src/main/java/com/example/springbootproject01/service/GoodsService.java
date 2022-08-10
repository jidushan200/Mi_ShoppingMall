package com.example.springbootproject01.service;

import com.example.springbootproject01.bean.Goods;
import com.example.springbootproject01.bean.GoodsDetailExample;

import java.util.List;

public interface GoodsService {
    Integer inset(Goods goods);
    Integer insertConfigs(Integer goodsID,List<String> config);
    Integer insertColors(Integer goodsID,List<String> color);
    Integer updateById(Goods goods);
    List<GoodsDetailExample> getAll();
    List<GoodsDetailExample> search(String like);
    Integer delConfigs(Integer goodsId);
    Integer delColors(Integer goodsId);
    Integer deleteById(Integer id);
}
