package com.markerhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.markerhub.entity.Comment;
import com.markerhub.entity.Like;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : LikeMapper
 * @Author : Administrator
 * @Date: 2021/8/18 16:02
 * @Description :
 */
@Repository
public interface LikeMapper extends BaseMapper<Like> {
}
