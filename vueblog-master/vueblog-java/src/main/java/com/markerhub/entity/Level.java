package com.markerhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @ClassName : Level
 * @Author : Administrator
 * @Date: 2021/8/19 10:52
 * @Description :
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("level")
public class Level {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userid;
    private Integer why;
    private LocalDateTime time;
    private Integer obtainexp;
    private Integer allexp;
    private Integer level;
    private Integer state;
}
