package com.qiao.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.behavior.pojos.ApBehaviorEntry;

public interface ApBehaviorEntryService extends IService<ApBehaviorEntry> {
    public ApBehaviorEntry findByUserIdOrEquipmentId(Integer userId, Integer type);
}
