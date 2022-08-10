package com.example.springbootproject01.service.impl;

import com.example.springbootproject01.bean.*;
import com.example.springbootproject01.mapper.GoodsColorMapper;
import com.example.springbootproject01.mapper.GoodsConfigMapper;
import com.example.springbootproject01.mapper.GoodsMapper;
import com.example.springbootproject01.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsConfigMapper goodsConfigMapper;

    @Resource
    private GoodsColorMapper goodsColorMapper;

    @Override
    public Integer inset(Goods goods) {
        goodsMapper.insertSelective(goods);
        return goods.getId();
    }

    @Override
    public Integer insertConfigs(Integer goodsID,List<String> config) {
        int sum=0;
        GoodsConfig goodsConfig = new GoodsConfig();
        goodsConfig.setGoodsId(goodsID);
        for (int i = 0; i < config.size(); i++) {
            goodsConfig.setConfig(config.get(i));
            sum+=goodsConfigMapper.insertSelective(goodsConfig);
        }
        return sum;
    }

    @Override
    public Integer insertColors(Integer goodsID, List<String> color) {
        int sum=0;
        GoodsColor goodsColor = new GoodsColor();
        goodsColor.setGoodsId(goodsID);
        for (int i = 0; i < color.size(); i++) {
            goodsColor.setColor(color.get(i));
            sum+=goodsColorMapper.insertSelective(goodsColor);
        }
        return sum;
    }

    @Override
    public Integer updateById(Goods goods) {
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public List<GoodsDetailExample> getAll() {
        return goodsMapper.getAll();
    }

    @Override
    public List<GoodsDetailExample> search(String like) {
        return goodsMapper.likeGetGoods(like);
    }

    @Override
    public Integer delConfigs(Integer goodsId) {
        GoodsConfigExample goodsConfigExample = new GoodsConfigExample();
        GoodsConfigExample.Criteria criteria = goodsConfigExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        return goodsConfigMapper.deleteByExample(goodsConfigExample);
    }

    @Override
    public Integer delColors(Integer goodsId) {
        GoodsColorExample goodsColorExample = new GoodsColorExample();
        goodsColorExample.createCriteria().andGoodsIdEqualTo(goodsId);
        return goodsColorMapper.deleteByExample(goodsColorExample);
    }

    @Override
    public Integer deleteById(Integer id) {
        return goodsMapper.deleteByPrimaryKey(id);
    }
}
