package com.qiao.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.behavior.dtos.FollowBehaviorDto;
import com.qiao.model.behavior.pojos.ApFollowBehavior;

public interface ApFollowBehaviorService extends IService<ApFollowBehavior> {
    void saveFollowBehavior(FollowBehaviorDto dto);
}
