package com.markerhub.controller;

import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import com.markerhub.service.impl.LikeServiceImpl;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : LikeController
 * @Author : Administrator
 * @Date: 2021/8/16 10:03
 * @Description : 点赞
 */
@RestController
public class LikeController {
    @Autowired
    UserService userService;
    @Autowired
    LikeServiceImpl likeService;

@RequiresAuthentication
    @GetMapping("/likeblog")
    public Result like(@RequestParam Long blogid){
    User visitUser = userService.getUser();
    int res=likeService.likeBlog(blogid,visitUser.getId());
    if(res==2)
        return Result.succ("取消点赞");
    if(res==1)
        return Result.succ("点赞成功");
    return Result.fail("点赞失败");
}
}
