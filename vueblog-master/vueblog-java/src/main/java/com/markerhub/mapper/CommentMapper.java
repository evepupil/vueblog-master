package com.markerhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.markerhub.entity.Comment;
import org.springframework.stereotype.Repository;

/**
 * @ClassName : CommentMapper
 * @Author : Administrator
 * @Date: 2021/8/12 14:19
 * @Description :
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
}
