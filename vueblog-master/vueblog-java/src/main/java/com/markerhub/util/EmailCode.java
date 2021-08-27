package com.markerhub.util;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class EmailCode {

    public static String sendMail(String address, int state, Boolean isSSL) throws EmailException {
        String htmlMsg = "您的验证码是：";
        String subject="8888";
        String code = "";
        if (state == 1) {
            subject = "您正在修改密码";
        } else if (state == 0) {
            subject = "您正在注册";
        }
        Random random=new Random();
        for(int i=0;i<4;i++){
           code+=random.nextInt(10);
        }
        htmlMsg+=code;
        if (StringUtils.isEmpty(address) || StringUtils.isEmpty(subject) || StringUtils.isEmpty(htmlMsg)) {
            throw new EmailException();
        }

        try {
            Email email = new HtmlEmail();
            List<String> list = new ArrayList<String>();
            list.add(address);
            String[] tos = list.toArray(new String[list.size()]);

            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
            email.setHostName("smtp.qq.com");
            if (isSSL) {
                email.setSSLOnConnect(true);
                email.setSmtpPort(465);
            }
            // 字符编码集的设置
            email.setCharset("UTF-8");
            // 收件人的邮箱
            email.addTo(address);
            // 发送人的邮箱以及发件人名称
            email.setFrom("1766468434@qq.com", "叶桐");
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication("1766468434@qq.com", "");
            // 要发送的邮件主题
            email.setSubject(subject);
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setMsg(htmlMsg);

            String result1 = email.send();
            System.out.println(code);
            return code;

        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailException();
        }
    }
}