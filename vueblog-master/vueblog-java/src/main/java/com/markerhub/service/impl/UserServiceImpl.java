package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Comment;
import com.markerhub.entity.Star;
import com.markerhub.entity.User;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.mapper.StarMapper;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StarMapper starMapper;
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    CommentMapper commentMapper;

    public User getUser() {
        User visituser = userMapper.selectById(ShiroUtil.getProfile().getId());
        return visituser;
    }

    @Override
    public int saveUser(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public ArrayList<Blog> getMystar(Long userid) {
        QueryWrapper<Star> starQueryWrapper= new QueryWrapper<>();
        starQueryWrapper.eq("userid",userid).orderByDesc("time");
        ArrayList<Star> res= (ArrayList<Star>) starMapper.selectList(starQueryWrapper);
        ArrayList<Blog> starlist=new ArrayList<>();
        for(Star s:res){
            Blog b=blogMapper.selectById(s.getBlogid());
            starlist.add(b);
        }
        return starlist;
    }
    @Override
    public void updateHeadImg() {
        ArrayList<Blog> blogArrayList= (ArrayList<Blog>) blogMapper.selectList(new QueryWrapper<>());
        for(Blog blog : blogArrayList){
            User user=userMapper.selectById(blog.getUserId());
            blog.setAvatar(user.getAvatar());
            blogMapper.updateById(blog);
        }
        ArrayList<Comment> commentArrayList= (ArrayList<Comment>) commentMapper.selectList(new QueryWrapper<>());
        for(Comment comment :commentArrayList){
            User user=userMapper.selectById(comment.getUserid());
            comment.setAvatar(user.getAvatar());
            commentMapper.updateById(comment);
        }


    }
}
