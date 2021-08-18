package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.entity.Comment;
import com.markerhub.entity.Like;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.mapper.LikeMapper;
import com.markerhub.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @ClassName : LikeServiceImpl
 * @Author : Administrator
 * @Date: 2021/8/18 16:06
 * @Description :
 */
@Service
public class LikeServiceImpl  implements LikeService {
    @Autowired
    LikeMapper likeMapper;
    @Override
    public int likeBlog(Long blogid, Long userid){
        QueryWrapper<Like> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userid",userid).eq("blogid",blogid);
        Like old=likeMapper.selectOne(queryWrapper);
        if(old!=null){
            likeMapper.delete(queryWrapper);
            return 2;
        }
        return likeMapper.insert(new Like().setBlogid(blogid)
        .setUserid(userid)
        .setTime(LocalDateTime.now()));
    };
}
