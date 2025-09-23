package com.qiao.behavior.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qiao.behavior.mapper.ApReadBehaviorMapper;
import com.qiao.behavior.service.ApReadBehaviorService;
import com.qiao.model.behavior.pojos.ApReadBehavior;
import org.springframework.stereotype.Service;

@Service
public class ApReadBehaviorServiceImpl extends ServiceImpl<ApReadBehaviorMapper, ApReadBehavior> implements ApReadBehaviorService {
}
