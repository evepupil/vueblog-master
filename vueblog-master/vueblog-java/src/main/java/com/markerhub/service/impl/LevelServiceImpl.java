package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.entity.Level;
import com.markerhub.entity.User;
import com.markerhub.mapper.LevelMapper;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName : LevelServiceImpl
 * @Author : Administrator
 * @Date: 2021/8/19 10:56
 * @Description :
 */
public class LevelServiceImpl implements LevelService {
    //32x4x3x3x3x2
    int levelExp[]={32,128,384,1152,3456,6912};
    @Autowired
    LevelMapper levelMapper;
    @Autowired
    UserMapper userMapper;
    public int addExp(Long userid,int why,int obtainexp){
        QueryWrapper<Level> levelQueryWrapper=new QueryWrapper<>();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        levelQueryWrapper.eq("userid",userid)
                .eq("why",why)
                .eq("date_format(time, '%Y-%m-%d')",ft.format(new Date())+"");
        Level old=levelMapper.selectOne(levelQueryWrapper);
        if(old!=null){
            return 2;
        }
        User user=userMapper.selectById(userid);
        int userlevel=user.getLevel();
        int userexp=user.getExp()+obtainexp;
        if(userexp>levelExp[5]){
            userlevel=6;
        }
        else{
            for(int i=0;i<6;i++){
                if(levelExp[i]>userexp)
                    userlevel=i;
            }
        }
        user.setLevel(userlevel);
        user.setExp(userexp);
        Level neww=new Level()
                .setTime(LocalDateTime.now())
                .setObtainexp(obtainexp)
                .setUserid(userid)
                .setWhy(why)
                .setAllexp(userexp)
                .setLevel(userlevel)
                ;
        userMapper.updateById(user);
        if(levelMapper.insert(neww)==1)
        return 1;
        return 0;

    }
}
