package com.markerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @ClassName : browse
 * @Author : Administrator
 * @Date: 2021/8/16 10:49
 * @Description : 浏览
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("browse")
public class Browse {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long blogid;
    private Long userid;
    private LocalDateTime time;
    private Integer state;
    private String ip;

}
