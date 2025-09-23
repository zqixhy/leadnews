package com.qiao.behavior.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qiao.behavior.mapper.ApLikesBehaviorMapper;
import com.qiao.behavior.service.ApBehaviorEntryService;
import com.qiao.behavior.service.ApLikesBehaviorService;
import com.qiao.common.constants.SystemConstants;
import com.qiao.model.behavior.dtos.LikesBehaviorDto;
import com.qiao.model.behavior.pojos.ApBehaviorEntry;
import com.qiao.model.behavior.pojos.ApLikesBehavior;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.common.enums.AppHttpCodeEnum;
import com.qiao.model.user.pojos.ApUser;
import com.qiao.utils.thread.AppThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApLikesBehaviorServiceImpl extends ServiceImpl<ApLikesBehaviorMapper, ApLikesBehavior> implements ApLikesBehaviorService {
    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    @Override
    public ResponseResult like(LikesBehaviorDto dto) {
        if(dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApUser user = AppThreadLocalUtil.getUser();
        ApBehaviorEntry entry = null;
        if(user != null){
            entry = apBehaviorEntryService.findByUserIdOrEquipmentId(user.getId(), SystemConstants.TYPE_USER);
        }
        if(entry ==null){
            throw new RuntimeException("entry doesn't exist");
        }
        if(dto.getOperation() ==1){

        }
        return null;
    }
}
