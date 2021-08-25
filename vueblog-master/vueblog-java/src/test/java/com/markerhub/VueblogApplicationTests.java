package com.markerhub;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.entity.Blog;
import com.markerhub.entity.User;
import com.markerhub.service.*;
import com.markerhub.util.COSClientUtil;
import com.markerhub.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;


@SpringBootTest
class VueblogApplicationTests {
@Autowired
RedisUtil r;
@Autowired
LikeService likeService;
@Autowired
    StarService starService;
@Autowired
    BlogService blogService;
@Autowired
    UserService userService;
@Autowired
OtherService otherService;
    @Test
    void contextLoads() {
        ArrayList<Blog> blogArrayList= (ArrayList<Blog>) blogService.getBaseMapper().selectList(new QueryWrapper<>());
        for(Blog blog : blogArrayList){
            User user=userService.getById(blog.getUserId());
            blog.setAvatar(user.getAvatar());
            blogService.updateById(blog);
        }

    }

}
