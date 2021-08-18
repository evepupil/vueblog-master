package com.markerhub.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Comment;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.impl.OtherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @ClassName : OtherController
 * @Author : Administrator
 * @Date: 2021/8/16 9:39
 * @Description :其他的控制层，例如每日热点
 */
@RestController
public class OtherController {
    @Autowired
    OtherServiceImpl otherService;
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @RequestMapping(value = "/daylyhot")
    public Result daylyHot (){
        QueryWrapper<Blog> blogqueryWrapper=new QueryWrapper<>();
        QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        commentQueryWrapper.select("blogid","count(blogid) as amount")
                .eq("date_format(time, '%Y-%m-%d')",ft.format(new Date())+"")
                .groupBy("blogid")
                .orderByDesc("time");
        List<Map<String, Object>> commentHot =commentMapper.selectMaps(commentQueryWrapper); //comment指数
        commentQueryWrapper.select("id as blogid","count(blogid) as amount")
                .eq("date_format(time, '%Y-%m-%d')",ft.format(new Date())+"")
                .groupBy("blogid")
                .orderByDesc("time");
        return  Result.succ(commentHot);
    }
    @RequestMapping("/recentComment")
    public Result recentComment(){
        return otherService.recentComment();
    }

}
