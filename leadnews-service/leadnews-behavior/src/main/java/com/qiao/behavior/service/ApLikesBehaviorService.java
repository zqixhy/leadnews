package com.qiao.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.behavior.dtos.LikesBehaviorDto;
import com.qiao.model.behavior.pojos.ApFollowBehavior;
import com.qiao.model.behavior.pojos.ApLikesBehavior;
import com.qiao.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApLikesBehaviorService extends IService<ApLikesBehavior> {
    public ResponseResult like(LikesBehaviorDto dto);
}
