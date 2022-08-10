package com.example.springbootproject01.controller.user;

import com.example.springbootproject01.bean.User;
import com.example.springbootproject01.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
@Transactional
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${server.port}")
    private String port;

    @Resource
    private UserService userService;

    @GetMapping("/getUser")
    public User getUser(Integer id){
        User user = userService.getUser(id);
        user.setHeadImg("http://localhost:"+port+user.getHeadImg());
        return user;
    }

    //修改个人信息
    @PutMapping("/save")
    public void save(User user){
        userService.updateUser(user);
    }

    //头像上传
    @PostMapping("/headImgUpload")
    public void headImgUpload(@RequestPart("headerImg") MultipartFile headerImg,String userId) throws IOException {
        // 保存目录位置根据项目需求可随意更改
        String path="C:\\Users\\12828\\Desktop\\images\\avatar";

        String originalFilename=null;
        if(!headerImg.isEmpty()){
            //保存到文件服务器，OSS服务器
            String[] split = Objects.requireNonNull(headerImg.getOriginalFilename()).split("\\.");
            originalFilename="headImg"+userId+"."+split[split.length-1];
            headerImg.transferTo(new File(path+"\\"+originalFilename));
        }
        User user = new User();
        user.setId(Integer.valueOf(userId));
        user.setHeadImg("/images/avatar/"+originalFilename);
        userService.updateUser(user);
    }
}
