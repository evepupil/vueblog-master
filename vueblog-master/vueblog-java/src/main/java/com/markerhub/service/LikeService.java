package com.markerhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.markerhub.entity.Like;

import java.util.ArrayList;

/**
 * @ClassName : LikeService
 * @Author : Administrator
 * @Date: 2021/8/18 16:02
 * @Description :
 */
public interface LikeService {
    int likeBlog(Long blogid,Long userid);
    ArrayList<Like> likeMe(Long userid);
}
