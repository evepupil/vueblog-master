package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.entity.Blog;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
@Autowired
    BlogMapper blogMapper;
    @Override
    public ArrayList<Blog> recentBlog(Long userid){
        QueryWrapper<Blog> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userid",userid).orderByDesc("created");
        return (ArrayList<Blog>) blogMapper.selectList(queryWrapper);
}
}
