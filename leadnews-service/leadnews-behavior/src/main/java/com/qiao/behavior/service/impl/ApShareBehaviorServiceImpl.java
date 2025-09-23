package com.qiao.behavior.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qiao.behavior.mapper.ApShareBehaviorMapper;
import com.qiao.behavior.service.ApShareBehaviorService;
import com.qiao.model.behavior.pojos.ApShareBehavior;
import org.springframework.stereotype.Service;

@Service
public class ApShareBehaviorServiceImpl extends ServiceImpl<ApShareBehaviorMapper, ApShareBehavior> implements ApShareBehaviorService {
}
