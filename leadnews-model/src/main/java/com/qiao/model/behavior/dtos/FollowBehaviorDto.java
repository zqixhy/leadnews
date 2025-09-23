package com.qiao.model.behavior.dtos;

import lombok.Data;

@Data
public class FollowBehaviorDto {
    private Long articleId;
    private Integer followId;
    private Integer userId;
    private Integer equipmentId;
}
