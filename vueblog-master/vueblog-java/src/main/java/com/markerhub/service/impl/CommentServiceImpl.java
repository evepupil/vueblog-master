package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.entity.Comment;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
    @Override
    public ArrayList<Comment> getReplay (Long id){
    QueryWrapper<Comment> queryWrapper=new QueryWrapper<Comment>();
    queryWrapper.eq("userid",id)
            .orderByDesc("time");
    return  (ArrayList<Comment>) commentMapper.selectList(queryWrapper);
}
}
