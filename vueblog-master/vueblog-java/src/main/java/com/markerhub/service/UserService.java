package com.markerhub.service;

import com.markerhub.entity.Blog;
import com.markerhub.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
public interface UserService extends IService<User> {
    User getUser ();
    int  saveUser(User user);
    ArrayList<Blog> getMystar(Long userid);
    void updateHeadImg();
    void changePw(Long userid,String newPw);
}
