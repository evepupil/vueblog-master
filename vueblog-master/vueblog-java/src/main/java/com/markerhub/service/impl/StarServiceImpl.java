package com.markerhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.entity.Star;
import com.markerhub.entity.User;
import com.markerhub.mapper.StarMapper;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.StarService;
import org.springframework.stereotype.Service;

/**
 * @ClassName : StarServiceImpl
 * @Author : Administrator
 * @Date: 2021/8/16 10:57
 * @Description :
 */
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements StarService {
}
