package com.markerhub.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.*;
/**
 * @ClassName : WebConfig
 * @Author : Administrator
 * @Date: 2021/8/18 9:43
 * @Description :在web的配置文件中，实例化登陆的拦截器，并添加规则
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{

}
