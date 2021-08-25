package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Comment;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.service.BlogService;
import com.markerhub.service.CommentService;
import io.lettuce.core.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @ClassName : CommentServiceImpl
 * @Author : Administrator
 * @Date: 2021/8/12 14:16
 * @Description :
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment> implements CommentService {
@Autowired
CommentMapper commentMapper;
@Autowired
BlogService blogService;
    @Override
    public ArrayList<Comment> getReplay (Long id){   //返回Comment   除自己以外
    QueryWrapper<Blog> blogQueryWrapper=new QueryWrapper<>();
    blogQueryWrapper.eq("userid",id);
    ArrayList<Blog> blogArrayList= (ArrayList<Blog>) blogService.getBaseMapper().selectList(blogQueryWrapper);
    QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
    for(Blog b:blogArrayList){
        commentQueryWrapper.eq("blogid",b.getId()).or();
    }
    commentQueryWrapper.orderByDesc("time");
    ArrayList<Comment> commentArrayList=(ArrayList<Comment>) commentMapper.selectList(commentQueryWrapper);

    ArrayList<Comment> res=new ArrayList<>();
    for(Comment c:commentArrayList){
        if(c.getUserid()!=id){
            res.add(c);
        }
    }
    return res;
}
}
