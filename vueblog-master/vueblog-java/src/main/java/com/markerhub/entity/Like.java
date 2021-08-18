package com.markerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @ClassName : Like
 * @Author : Administrator
 * @Date: 2021/8/18 15:56
 * @Description :
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("like")
public class Like {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userid;
    private Long blogid;
    private Long commentid;
    private Long everycommentid;
    private LocalDateTime time;
    private int state;


}
