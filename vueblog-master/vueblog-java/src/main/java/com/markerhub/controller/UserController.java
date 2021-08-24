package com.markerhub.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Comment;
import com.markerhub.entity.Like;
import com.markerhub.entity.User;
import com.markerhub.service.BlogService;
import com.markerhub.service.CommentService;
import com.markerhub.service.LikeService;
import com.markerhub.service.UserService;
import com.markerhub.service.impl.BlogServiceImpl;
import com.markerhub.util.COSClientUtil;
import com.markerhub.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since
 */
@RestController
public class UserController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    LikeService likeService;
    //@RequiresAuthentication
    @GetMapping("/usercenter")
    public Result index(@RequestParam String id) {
        System.out.println(id);
        Long userid=Long.parseLong(id);
        User user = userService.getById(userid);
        //User visitUser = userService.getUser();
        //boolean isOwn=user.getId().equals(visitUser.getId());
        return Result.succ(user);
    }

    @RequiresAuthentication
    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user) {
        if(user.getId()!=userService.getUser().getId())
            return Result.fail("操作非法");
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
        ArrayList<Comment> res1=commentService.getReplay(userid);
        ArrayList<Blog> res2=new ArrayList<>();
        for(Comment c:res1){
            res2.add(blogService.getById(c.getBlogid()));
        }
        if(isOwn){
            return Result.succ(MapUtil.builder().put("comments",res1)
            .put("blogs",res2)
                    .map());
        }
        return Result.succ("No");
    }

    @RequiresAuthentication
    @GetMapping("/mystar")
    public Result mystar(@RequestParam Long userid){
        User user = userService.getById(userid);
        if(userid==user.getId()){
            return Result.succ(userService.getMystar(userid));
        }
        return Result.fail("No");
    }
    @GetMapping("/recentblog")
    public Result recentBlog(@RequestParam Long userid){
        return Result.succ(blogService.recentBlog(userid));
    }

    @RequiresAuthentication
    @RequestMapping ("/uploadheadimg")
    public Result uploadHeadImg(@RequestParam MultipartFile file,
                                 @RequestParam Long userid){
        User user=userService.getUser();
        if(user.getId()!=userid){
            return Result.fail("No");
        }
        String fileName=file.getOriginalFilename();
        COSClientUtil cosClientUtil=new COSClientUtil();
        try {
            COSClientUtil.uploadfile(file,"/head/" + userid);
        }
        catch (Exception e){
            return Result.fail("上传失败");
        }
        user.setAvatar(cosClientUtil.getUrl("/head/") + userid + "/" + fileName);
        userService.updateById(user);
        return Result.succ(cosClientUtil.getUrl("/head/") + userid + "/" + fileName);
    }
    @RequiresAuthentication
    @GetMapping("/likeme")
    public  Result likeMe(@RequestParam Long userid){
        if(userService.getUser().getId()!=userid)
            return Result.fail("No");
        ArrayList<Like> likeArrayList=likeService.likeMe(userid);
        ArrayList<HashMap<String,String>> bloginfo=new ArrayList<>();
        ArrayList<User> userArrayList=new ArrayList<>();
        //0文章 1评论 2评论中评论
        for(Like like:likeArrayList){
            User user=userService.getById(like.getUserid());
            userArrayList.add(user);
            if(like.getBlogid()!=null){
                Blog blog=blogService.getById(like.getBlogid());
                HashMap<String,String> t=new HashMap<>();
                t.put("type","帖子");
                t.put("title",blog.getTitle());
                t.put("blogid",blog.getId()+"");
                bloginfo.add(t);
            }
        }
        return Result.succ(MapUtil.builder()
        .put("like",likeArrayList)
                .put("blog",bloginfo)
                .put("user",userArrayList).map());
    }
}
