package com.markerhub.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Browse;
import com.markerhub.entity.User;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.mapper.BrowseMapper;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.BlogService;
import com.markerhub.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
@RestController
public class BlogController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    BlogService blogService;
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    BrowseMapper browseMapper;
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 5);
        IPage pageData = blogMapper.selectPage(page, new QueryWrapper<Blog>().orderByDesc("recent").orderByDesc("created"));
        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id , HttpServletRequest request) {
        String ip=request.getRemoteAddr();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        QueryWrapper<Browse> browseQueryWrapper=new QueryWrapper<>();
        browseQueryWrapper.select("blogid")
                .eq("date_format(time, '%Y-%m-%d')",ft.format(new Date())+"")
                .eq("ip",ip);

       List<Object> res= browseMapper.selectObjs(browseQueryWrapper);
       System.out.println(res);
        Blog blog = blogService.getById(id);
        boolean flag=false;
       for(Object a:res){
           if(id.longValue()==(((Integer) a).longValue())){
               flag=true;
               break;
           }
       }
       if(!flag){
           blog.setBrowse(blog.getBrowse()+1);
           Browse browse=new Browse();
           browse.setIp(ip)
                   .setBlogid(id)
                   .setTime(LocalDateTime.now());
           browseMapper.insert(browse);
       }

        Assert.notNull(blog, "该博客不存在或已被删除");
        blogMapper.updateById(blog);
        return Result.succ(blog);
    }
//    @PostMapping("/blog/star")
//    public Result star(@Validated @RequestBody Blog blog) {
//        blog.setStar(blog.getStar()+1);
//        return Result.succ(null);
//    }
//    @PostMapping("/blog/like")
//    public Result like(@Validated @RequestBody Blog blog) {
//        blog.setLike(blog.getLike()+1);
//        return Result.succ(null);
//    }
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {

//        Assert.isTrue(false, "公开版不能任意编辑！");
        User user=userMapper.selectById(ShiroUtil.getProfile().getId());
        Blog temp = null;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章

            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");
        } else {
            temp = new Blog();

            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);



        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        temp.setRecent(LocalDateTime.now());
        temp.setAuthor(user.getNickname());
        blogService.saveOrUpdate(temp);

        return Result.succ(null);
    }
    @GetMapping(value = "/blog/search")
    public  Result searchBlog(@RequestParam String keywords){
        System.out.println(keywords);
        QueryWrapper<Blog> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(isNotBlank(keywords),"title",keywords);
        queryWrapper.like(isNotBlank(keywords),"content",keywords);
        ArrayList<Blog> list= (ArrayList<Blog>) blogMapper.selectList(queryWrapper);
        return Result.succ(list);
    }
    public boolean isNotBlank(String s){
        return !(s==null||s.equals(""));
    }


}
