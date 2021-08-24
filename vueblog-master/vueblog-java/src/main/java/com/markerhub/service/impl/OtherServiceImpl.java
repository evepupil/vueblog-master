package com.markerhub.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Comment;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : OtherServiceImpl
 * @Author : Administrator
 * @Date: 2021/8/18 10:22
 * @Description :
 */
@Service
public class OtherServiceImpl implements OtherService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    BlogMapper blogMapper;
    @Override
    public Result recentComment() {
        QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
       // Long id= new Long(64);
        commentQueryWrapper.orderByDesc("id");
        ArrayList<Comment> comments=(ArrayList<Comment>) commentMapper.selectList(commentQueryWrapper);
        List<Comment> comments1=  comments.subList(0,5);
        ArrayList<Blog> blogs=new ArrayList<>();
        for(Comment comment:comments1){
            blogs.add(blogMapper.selectById(comment.getBlogid()));
        }

        return Result.succ(MapUtil.builder()
                .put("comments",comments1)
                .put("blogs",blogs)
                .map())
                ;
    }

    @Override
    public Long getMaxBlogId() {
        QueryWrapper<Blog > queryWrapper=new QueryWrapper<>();
        ArrayList<Blog> res= (ArrayList<Blog>) blogMapper.selectList(queryWrapper.orderByDesc("id"));
        return res.get(0).getId();
    }
}
