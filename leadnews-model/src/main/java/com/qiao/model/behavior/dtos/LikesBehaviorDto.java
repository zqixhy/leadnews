package com.qiao.model.behavior.dtos;

import lombok.Data;

@Data
public class LikesBehaviorDto {
    private Long articleId;
    private Short operation;
    private Short type;

}
