package com.markerhub.service;
import com.markerhub.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * @ClassName : CommentService
 * @Author : Administrator
 * @Date: 2021/8/12 14:15
 * @Description :
 */
public interface CommentService extends IService<Comment> {
    ArrayList<Comment> getReplay(Long id);
}
