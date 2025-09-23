package com.qiao.behavior.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiao.behavior.mapper.ApBehaviorEntryMapper;
import com.qiao.behavior.service.ApBehaviorEntryService;
import com.qiao.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.stereotype.Service;

@Service
public class ApBehaviorEntryImpl extends ServiceImpl<ApBehaviorEntryMapper, ApBehaviorEntry> implements ApBehaviorEntryService {
    @Override
    public ApBehaviorEntry findByUserIdOrEquipmentId(Integer userId, Integer type) {
        ApBehaviorEntry entry = getOne(Wrappers.<ApBehaviorEntry>lambdaQuery().eq(ApBehaviorEntry::getEntryId, userId).eq(ApBehaviorEntry::getType, type));
        return entry;
    }
}
