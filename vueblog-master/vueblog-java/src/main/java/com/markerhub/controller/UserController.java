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
import com.markerhub.util.EmailCode;
import com.markerhub.util.RedisUtil;
import com.markerhub.util.ShiroUtil;
import org.apache.commons.mail.EmailException;
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
    RedisUtil redisUtil;
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
        return Result.fail("没有权限");
    }

    @RequiresAuthentication
    @GetMapping("/mystar")
    public Result mystar(@RequestParam Long userid){
        User user = userService.getById(userid);
        if(userid==user.getId()){
            return Result.succ(userService.getMystar(userid));
        }
        return Result.fail("没有权限");
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
            return Result.fail("没有权限");
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
        userService.updateHeadImg();
        return Result.succ(cosClientUtil.getUrl("/head/") + userid + "/" + fileName);
    }
    @RequiresAuthentication
    @GetMapping("/likeme")
    public  Result likeMe(@RequestParam Long userid){
        if(userService.getUser().getId()!=userid)
            return Result.fail("没有权限");
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
    @RequiresAuthentication
    @RequestMapping("/usercenter/updatebasic")
    public Result updateNickname(@RequestParam String nickname,
                                 @RequestParam String sign,
                        @RequestParam Long userid) {
        if(userService.getUser().getId()==userid){
            User user=userService.getById(userid);
            user.setSign(sign);
            user.setNickname(nickname);
            userService.updateById(user);
            return Result.succ("Ok");
        }
        return Result.fail("没有权限");
    }

    @RequiresAuthentication
    @RequestMapping("/usercenter/updateemail")
    public Result updateEmail(@RequestParam String email,
                             @RequestParam Long userid) {
        if (userService.getUser().getId() == userid) {
            QueryWrapper<User> queryWrapper=new QueryWrapper();
            queryWrapper.eq("email",email);
            User user=userService.getOne(queryWrapper);
            if(user!=null){
                return Result.fail("邮箱已被绑定");
            }
            user = userService.getById(userid);
            user.setEmail(email);
            userService.updateById(user);
            return Result.succ("Ok");
        }
        return Result.fail("没有权限");
    }
    @PostMapping("/changePw")
    public Result changePw(@RequestParam String email,
                           @RequestParam String code,@RequestParam String newPw){
        if(email.equals("")){
            return Result.fail("请输入验证码");
        }
        String rightCode= (String) redisUtil.get(email);
        System.out.println("zhengque:"+rightCode);
        if(!code.equals(rightCode)){
            return Result.fail("验证码错误");
        }
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        queryWrapper.eq("email",email);
        User user=userService.getOne(queryWrapper);
        if(user==null){
            return  Result.fail("邮箱绑定的用户不存在");
        }
        userService.changePw(user.getId(),newPw);
        return Result.succ("Ok");
    }
    @PostMapping("/code")
    public  Result code(@RequestParam String email,@RequestParam int state) throws EmailException {
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        queryWrapper.eq("email",email);
        User user=userService.getOne(queryWrapper);
        if(state==1){
            if(user==null){
                return  Result.fail("邮箱绑定的用户不存在");
            }
        }else if(state==0)
        {
            if(user!=null){
                return  Result.fail("邮箱已被注册");
            }
        }
        System.out.println(email);
        String code=EmailCode.sendMail(email,1,true);
        if(code!=null) {
            redisUtil.setWithTime(email, code, 30 * 60);
            return Result.succ("Ok");
        }
        return Result.fail("出错了");

    }
}
