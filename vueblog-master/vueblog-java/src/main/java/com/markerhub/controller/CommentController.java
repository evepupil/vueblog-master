package com.markerhub.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Comment;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.service.CommentService;
import com.markerhub.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : CommentController
 * @Author : Administrator
 * @Date: 2021/8/12 11:33
 * @Description :
 */
@RestController
public class CommentController {

    @Autowired
    CommentMapper commentMapper;
@GetMapping(value = "/comments")
    public Result commentList(@RequestParam Long blogid){
     QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
     queryWrapper.eq("blogid",blogid);
     queryWrapper.orderByDesc("time");
    ArrayList<Comment> list= (ArrayList<Comment>) commentMapper.
            selectList(queryWrapper);
    return  Result.succ(list);
}
@GetMapping(value = "commentadd")
    public Result commentadd(@RequestParam Long blogid,@RequestParam String yourcomment){
    return new Result();
}
}
