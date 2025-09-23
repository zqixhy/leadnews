package com.qiao.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.behavior.dtos.UnLikesBehaviorDto;
import com.qiao.model.behavior.pojos.ApFollowBehavior;
import com.qiao.model.behavior.pojos.ApUnlikesBehavior;
import com.qiao.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApUnlikesBehaviorService extends IService<ApUnlikesBehavior> {
    public ResponseResult unlike(UnLikesBehaviorDto dto);
}
