package com.markerhub.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.dto.CommentAddDto;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Comment;
import com.markerhub.entity.User;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.service.CommentService;
import com.markerhub.service.impl.CommentServiceImpl;
import com.markerhub.util.DateTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    BlogMapper blogMapper;
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
@PostMapping(value = "commentadd")
    public Result commentadd(@Validated @RequestBody CommentAddDto commentAddDto, HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse){
    System.out.println(commentAddDto.getBlogId());
    int blogAddComment=commentAddDto.getBlogId();
    Comment newComment=new Comment();
    User commenter=commentAddDto.getUser();
    newComment.setUserid(commenter.getId())
            .setAvatar(commenter.getAvatar())
    .setContent(commentAddDto.getYourcomment())
    .setBlogid(new Long(commentAddDto.getBlogId()))
    .setNickname(commenter.getNickname())
    .setTime(LocalDateTime.now())
    ;
    Blog blog=blogMapper.selectById(commentAddDto.getBlogId());
    int newCommentNums=commentMapper.selectCount(new QueryWrapper<Comment>().eq("blogid",blogAddComment));
    blog.setRecent(DateTransfer.date2LocalDateTime(new Date()))
    .setCommentnums(newCommentNums+1);
    blogMapper.updateById(blog);
    commentMapper.insert(newComment);
    return  Result.succ(newComment);
}
}
