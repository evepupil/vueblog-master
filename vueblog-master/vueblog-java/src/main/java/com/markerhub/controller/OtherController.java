package com.markerhub.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.dto.Formdata;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Comment;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.impl.OtherServiceImpl;
import com.markerhub.util.COSClientUtil;
import com.markerhub.util.RedisUtil;
import com.markerhub.util.StringTransfer;
import net.sf.jsqlparser.schema.MultiPartName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @ClassName : OtherController
 * @Author : Administrator
 * @Date: 2021/8/16 9:39
 * @Description :其他的控制层，例如每日热点
 */
@RestController
public class OtherController {
    @Autowired
    OtherServiceImpl otherService;
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping(value = "/daylyhot")
    public Result daylyHot() {
        QueryWrapper<Blog> blogqueryWrapper = new QueryWrapper<>();
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        commentQueryWrapper.select("blogid", "count(blogid) as amount")
                .eq("date_format(time, '%Y-%m-%d')", ft.format(new Date()) + "")
                .groupBy("blogid")
                .orderByDesc("time");
        List<Map<String, Object>> commentHot = commentMapper.selectMaps(commentQueryWrapper); //comment指数

        return Result.succ(commentHot);
    }

    @RequestMapping("/recentComment")
    public Result recentComment() {
        return otherService.recentComment();
    }

    @RequestMapping("/upload/articleimg")
    public Result uploadArticleImg(@RequestParam("file") MultipartFile file,
                                   @RequestParam("blogid") Long blogid) {
        String fileName = file.getOriginalFilename();
        if (file.getSize() > 10 * 1024 * 1024)
            return Result.fail("0");
        COSClientUtil cosClientUtil = new COSClientUtil();
        try {
            COSClientUtil.uploadfile(file, "/article/" + blogid);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.succ("上传图片失败");
        }
        String uri = null;
        fileName = StringTransfer.stringToLegal(fileName);
        System.out.println(cosClientUtil.getUrl("/article/") + blogid + "/" + fileName);
        return Result.succ(cosClientUtil.getUrl("/article/") + blogid + "/" + fileName);


    }

    @RequestMapping("/upload/newarticleimg")
    public Result newUploadArticleImg(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Long blogid=otherService.getMaxBlogId()+1;
        if (file.getSize() > 10 * 1024 * 1024)
            return Result.fail("0");
        COSClientUtil cosClientUtil = new COSClientUtil();
        try {
            COSClientUtil.uploadfile(file, "/article/" + blogid);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.succ("上传图片失败");
        }

        String uri = null;

        System.out.println(cosClientUtil.getUrl("/article/") + blogid + "/" + fileName);
        return Result.succ(cosClientUtil.getUrl("/article/") + blogid + "/" + fileName);

    }
}
