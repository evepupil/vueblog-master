package com.markerhub.common.dto;

import com.markerhub.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName : CommentAddDto
 * @Author : Administrator
 * @Date: 2021/8/12 17:49
 * @Description :
 */
@Data
public class CommentAddDto {
    @NotNull(message = "帖子id不能为空")
     int blogId;
    @NotBlank(message = "评论不能为空")
    String yourcomment;
    User user;
}
