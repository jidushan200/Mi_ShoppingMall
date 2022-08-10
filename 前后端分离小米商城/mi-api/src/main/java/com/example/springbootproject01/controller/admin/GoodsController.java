package com.example.springbootproject01.controller.admin;

import com.example.springbootproject01.bean.Goods;
import com.example.springbootproject01.bean.GoodsConfig;
import com.example.springbootproject01.bean.GoodsDetailExample;
import com.example.springbootproject01.bean.User;
import com.example.springbootproject01.service.GoodsService;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Transactional
@RestController
@RequestMapping("/admin/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @DeleteMapping("/del")
    public Boolean del(Integer id){
        return goodsService.deleteById(id)>0;
    }

    @PutMapping("/update")
    public Boolean update(@RequestBody String content){
//        System.out.println(content);
        Goods goods = new Goods();
        List<String> configList=new ArrayList<>();
        List<String> colorList=new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(content);
            goods.setId((Integer) jsonObject.get("id"));
            goods.setName((String) jsonObject.get("name"));
            goods.setMessage((String) jsonObject.get("message"));
            goods.setPrice(new BigDecimal(jsonObject.get("price").toString()));
            goods.setClassify((String) jsonObject.get("classify"));

//            System.out.println(goods);

            JSONArray jsonArray = new JSONArray(jsonObject.get("config").toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                configList.add(jsonArray.get(i).toString());
            }

            JSONArray jsonArray2 = new JSONArray(jsonObject.get("color").toString());
            for (int i = 0; i < jsonArray2.length(); i++) {
                colorList.add(jsonArray2.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        goodsService.delConfigs(goods.getId());
        goodsService.delColors(goods.getId());
        goodsService.insertConfigs(goods.getId(),configList);
        goodsService.insertColors(goods.getId(),colorList);

        return goodsService.updateById(goods)>0;
    }

    @GetMapping("/search")
    public List<GoodsDetailExample> search(String search){
//        System.out.println(search);
        return goodsService.search(search);
    }

    @GetMapping("/all")
    public List<GoodsDetailExample> all(){
        return goodsService.getAll();
    }

    //添加商品
    @PostMapping("/insert")
    public Integer insert(@RequestBody String content) {
//        System.out.println(content);
        Goods goods = new Goods();
        List<String> configList = new ArrayList<>();
        List<String> colorList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(content);
//            System.out.println(jsonObject);
            goods.setName((String) jsonObject.get("name"));
            goods.setMessage((String) jsonObject.get("message"));
            goods.setPrice(new BigDecimal(jsonObject.get("price").toString()));
            goods.setClassify((String) jsonObject.get("classify"));

            JSONArray config = new JSONArray(jsonObject.get("config").toString());
            for (int i=0;i<config.length();i++){
//                System.out.println(config.get(i));
                JSONObject jsonObject1 = new JSONObject(config.get(i).toString());
                configList.add(String.valueOf(jsonObject1.get("value")));
            }

            JSONArray color = new JSONArray(jsonObject.get("color").toString());
            for (int i=0;i<color.length();i++){
//                System.out.println(color.get(i));
                JSONObject jsonObject1 = new JSONObject(color.get(i).toString());
                colorList.add(String.valueOf(jsonObject1.get("value")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        System.out.println(goods);
//        System.out.println(configList);
//        System.out.println(colorList);
        Integer inset;
        inset = goodsService.inset(goods);
        if (configList.size()>0){
            goodsService.insertConfigs(inset, configList);
        }
        if (colorList.size()>0){
            goodsService.insertColors(inset,colorList);
        }
        return inset;
    }

    //商品图片上传
    @PostMapping("/indexImg")
    public Boolean indexImgUpload(@RequestPart("indexImg") MultipartFile indexImg, String goodsId) throws IOException {
        // 保存目录位置根据项目需求可随意更改
        String path="C:\\Users\\12828\\Desktop\\images\\goods";

        String originalFilename=null;
        if(!indexImg.isEmpty()){
            //保存到文件服务器，OSS服务器
            String[] split = Objects.requireNonNull(indexImg.getOriginalFilename()).split("\\.");
            originalFilename="indexImg"+goodsId+"."+split[split.length-1];
//            System.out.println(split[split.length-1]);
            indexImg.transferTo(new File(path+"\\"+originalFilename));
        }

        Goods goods = new Goods();
        goods.setId(Integer.valueOf(goodsId));
        goods.setIndexImg("/images/goods/"+originalFilename);
        return goodsService.updateById(goods)>0;
    }

    @PostMapping("/detailImg")
    public Boolean detailImgUpload(@RequestPart("detailImg") MultipartFile detailImg, String goodsId) throws IOException {
        // 保存目录位置根据项目需求可随意更改
        String path="C:\\Users\\12828\\Desktop\\images\\goods";

        String originalFilename=null;
        if(!detailImg.isEmpty()){
            //保存到文件服务器，OSS服务器
            String[] split = Objects.requireNonNull(detailImg.getOriginalFilename()).split("\\.");
            originalFilename="detailImg"+goodsId+"."+split[split.length-1];
            detailImg.transferTo(new File(path+"\\"+originalFilename));
        }

        Goods goods = new Goods();
        goods.setId(Integer.valueOf(goodsId));
        goods.setDetailImg("/images/goods/"+originalFilename);
        return goodsService.updateById(goods)>0;
    }
}
