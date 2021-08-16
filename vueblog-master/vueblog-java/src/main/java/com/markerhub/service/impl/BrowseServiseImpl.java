package com.markerhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.entity.Star;
import com.markerhub.entity.User;
import com.markerhub.mapper.BrowseMapper;
import com.markerhub.mapper.StarMapper;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : BrowseServiseImpl
 * @Author : Administrator
 * @Date: 2021/8/16 10:57
 * @Description :
 */
@Service
public class BrowseServiseImpl extends ServiceImpl<StarMapper, Star> implements BrowseService {
    @Autowired
    BrowseMapper browseMapper;

}
