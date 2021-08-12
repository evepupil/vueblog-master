package com.markerhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.entity.Comment;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * @ClassName : CommentServiceImpl
 * @Author : Administrator
 * @Date: 2021/8/12 14:16
 * @Description :
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment> implements CommentService {
}
