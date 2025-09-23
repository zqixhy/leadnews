package com.qiao.article.listener;

import com.alibaba.fastjson.JSON;
import com.qiao.article.service.ApArticleConfigService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ArticleUpDownListener {

    @Autowired
    private ApArticleConfigService apArticleConfigService;


    @KafkaListener(topics = "wm.news.topic.down.or.up")
    public void onMessage(String message){
        log.info("receive message:{}",message);
        if(StringUtils.isNotBlank(message)){
            Map map = JSON.parseObject(message, Map.class);
            apArticleConfigService.updateByMap(map);

        }
    }
}
