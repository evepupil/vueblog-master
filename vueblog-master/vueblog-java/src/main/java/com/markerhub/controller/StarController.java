package com.markerhub.controller;

import com.markerhub.common.lang.Result;
import com.markerhub.service.StarService;
import com.markerhub.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : StarController
 * @Author : Administrator
 * @Date: 2021/8/19 9:49
 * @Description : 收藏
 */
@RestController
public class StarController {
    @Autowired
    StarService starService;
    @Autowired
    UserService userService;
    @RequiresAuthentication
    @GetMapping("/starblog")
    public Result starBlog(@RequestParam Long blogid){
        Long userid=userService.getUser().getId();
        Assert.notNull(userid,"请先登录");
        if(userid==null)
            return Result.fail("请先登录");
        int res=starService.starBlog(blogid,userid);
        if(res==2){
            return Result.fail("取消收藏");

        }
        if(res==1){
            return Result.succ("收藏成功");

        }
        return Result.fail("收藏失败");


    }
}
