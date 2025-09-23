package com.qiao.model.user.dtos;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRelationDto  {
    private Long articleId;
    private Integer authorId;

    /*
    0 follow
    1 unfollow
     */
    private Short operation;

}
