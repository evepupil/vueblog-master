package com.markerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @ClassName : Comment
 * @Author : Administrator
 * @Date: 2021/8/12 14:17
 * @Description :
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String nickname;
    private  Long userid;
    private String content;
    private Integer favorite;
    private String avatar;
    private Long blogid;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime time;
}
