package com.qiao.behavior.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiao.behavior.mapper.ApUnLikesBehaviorMapper;

import com.qiao.behavior.service.ApUnlikesBehaviorService;
import com.qiao.model.behavior.dtos.UnLikesBehaviorDto;
import com.qiao.model.behavior.pojos.ApUnlikesBehavior;
import com.qiao.model.common.dtos.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class ApUnLikesBehaviorServiceImpl extends ServiceImpl<ApUnLikesBehaviorMapper, ApUnlikesBehavior> implements ApUnlikesBehaviorService {
    @Override
    public ResponseResult unlike(UnLikesBehaviorDto dto) {
        return null;
    }
}
