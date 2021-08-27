package com.markerhub.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import com.markerhub.common.dto.LoginDto;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.UserService;
import com.markerhub.util.DateTransfer;
import com.markerhub.util.JwtUtils;
import com.markerhub.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@RestController
public class AccountController {
@Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserMapper userMapper;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request,
                        HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");

        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        user.setLastLogin(DateTransfer.date2LocalDateTime(new Date()));
        user.setIp(request.getRemoteAddr());
        userMapper.updateById(user);
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("sign",user.getSign())
                .put("nickname",user.getNickname())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    @PostMapping("/register")
    public Result register (@RequestParam String nickname,@RequestParam String email,
                            @RequestParam String code, @RequestParam String username,
                            @RequestParam String password,
                            HttpServletRequest request,
                            HttpServletResponse response){
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if(user!=null){
            return Result.fail("用户已存在");
        }
        user = userService.getOne(new QueryWrapper<User>().eq("email", email));
        if(user!=null){
            return Result.fail("该邮箱已被绑定");
        }
        user = userService.getOne(new QueryWrapper<User>().eq("nickname", nickname));
        if(user!=null){
            return Result.fail("该昵称已被注册");
        }
        String rightCode= (String) redisUtil.get(email);
        if(!code.equals(rightCode)){
            return Result.fail("验证码错误");
        }
        int userNum=userMapper.selectCount(new QueryWrapper<>())+1;
        User newuser=new User();
        newuser.setLastLogin(DateTransfer.date2LocalDateTime(new Date()));
        newuser.setIp(request.getRemoteAddr())
        .setUsername(username)
        .setCreated(new Date())
        .setPassword(SecureUtil.md5(password))
        .setStatus(0)
        .setLevel(0)
                .setNickname(nickname)
        .setEmail(email)
        ;
        userMapper.insert(newuser);
        String jwt = jwtUtils.generateToken(newuser.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        return Result.succ(user);
    }

}
