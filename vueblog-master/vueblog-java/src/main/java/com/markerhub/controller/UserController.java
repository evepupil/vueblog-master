package com.markerhub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Comment;
import com.markerhub.entity.User;
import com.markerhub.service.BlogService;
import com.markerhub.service.CommentService;
import com.markerhub.service.UserService;
import com.markerhub.service.impl.BlogServiceImpl;
import com.markerhub.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
@RestController
public class UserController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @RequiresAuthentication
    @GetMapping("/usercenter")
    public Result index(@RequestParam Long id) {
        User user = userService.getById(id);
        User visitUser = userService.getUser();
        boolean isOwn=user.getId()==visitUser.getId();
        return Result.succ(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user) {
        int res=userService.saveUser(user);
        if(res==1)
            return Result.succ("修改成功");
        return Result.fail("修改失败");
    }
    @RequiresAuthentication
    @GetMapping("/replayme")
    public Result replay(@RequestParam Long userid){
        User user = userService.getById(userid);
        User visitUser = userService.getUser();
        boolean isOwn=user.getId()==visitUser.getId();
        if(isOwn){
            return Result.succ(commentService.getReplay(userid));
        }
        return Result.succ("No");
    }

    @RequiresAuthentication
    @GetMapping("/mystar")
    public Result mystar(@RequestParam Long userid){
        User user = userService.getById(userid);
        return Result.succ("No");
    }
    @GetMapping("/recentblog")
    public Result recentBlog(@RequestParam Long userid){
        return Result.succ(blogService.recentBlog(userid));
    }
}
