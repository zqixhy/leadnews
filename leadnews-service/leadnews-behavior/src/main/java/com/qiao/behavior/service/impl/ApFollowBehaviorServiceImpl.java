package com.qiao.behavior.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiao.behavior.mapper.ApFollowBehaviorMapper;
import com.qiao.behavior.service.ApBehaviorEntryService;
import com.qiao.behavior.service.ApFollowBehaviorService;
import com.qiao.common.constants.SystemConstants;
import com.qiao.model.behavior.dtos.FollowBehaviorDto;
import com.qiao.model.behavior.pojos.ApBehaviorEntry;
import com.qiao.model.behavior.pojos.ApFollowBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ApFollowBehaviorServiceImpl extends ServiceImpl<ApFollowBehaviorMapper, ApFollowBehavior> implements ApFollowBehaviorService {

    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;


    @Override
    public void saveFollowBehavior(FollowBehaviorDto dto) {
        ApBehaviorEntry entry = apBehaviorEntryService.findByUserIdOrEquipmentId(dto.getUserId(), SystemConstants.TYPE_USER);
        if(entry != null){
            ApFollowBehavior apFollowBehavior = new ApFollowBehavior();
            apFollowBehavior.setFollowId(dto.getFollowId());
            apFollowBehavior.setArticleId(dto.getArticleId());
            apFollowBehavior.setEntryId(entry.getEntryId());
            apFollowBehavior.setCreatedTime(new Date());
            save(apFollowBehavior);
        }
    }
}
