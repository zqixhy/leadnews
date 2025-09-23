package com.qiao.behavior.listener;

import com.alibaba.fastjson.JSON;
import com.qiao.behavior.service.ApFollowBehaviorService;
import com.qiao.model.behavior.dtos.FollowBehaviorDto;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class FollowBehaviorListener {

    @Autowired
    private ApFollowBehaviorService apFollowBehaviorService;

    @KafkaListener(topics = "follow.behavior.topic")
    public void onMessage(String message){
        log.info("receive message:{}",message);
        if(StringUtils.isNotBlank(message)){
            FollowBehaviorDto dto = JSON.parseObject(message, FollowBehaviorDto.class);
            apFollowBehaviorService.saveFollowBehavior(dto);

        }
    }

}
