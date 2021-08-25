package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Star;
import com.markerhub.entity.User;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.mapper.StarMapper;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName : StarServiceImpl
 * @Author : Administrator
 * @Date: 2021/8/16 10:57
 * @Description :
 */
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements StarService {
@Autowired
StarMapper starMapper;
@Autowired
BlogMapper blogMapper;
@Override
public int starBlog(Long blogid,Long userid){
    Blog blog=blogMapper.selectById(blogid);
    QueryWrapper<Star> starQueryWrapper=new QueryWrapper<>();
    starQueryWrapper.eq("blogid",blogid).eq("userid",userid);
    Star old=starMapper.selectOne(starQueryWrapper);
    if(old!=null){
        blog.setStar(blog.getStar()-1);
        blogMapper.updateById(blog);
        starMapper.deleteById(old.getId());
        return 2;
    }
    Star neww=new Star()
            .setBlogid(blogid)
            .setTime(LocalDateTime.now())
            .setUserid(userid);
    blog.setStar(blog.getStar()+1);
    blogMapper.updateById(blog);
    if(starMapper.insert(neww)==1)
        return 1;
    return 0;
}}
