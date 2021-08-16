package com.markerhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.markerhub.entity.Comment;
import com.markerhub.entity.Star;
import org.springframework.stereotype.Repository;

/**
 * @ClassName : StarMapper
 * @Author : Administrator
 * @Date: 2021/8/16 10:55
 * @Description :
 */
@Repository
public interface StarMapper extends BaseMapper<Star> {
}
