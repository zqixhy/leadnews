package com.qiao.search.listener;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.alibaba.fastjson.JSON;
import com.qiao.common.constants.ArticleConstants;
import com.qiao.model.search.vos.SearchArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SyncArticleListener {

    @Autowired
    private ElasticsearchClient esClient;

    @KafkaListener(topics = ArticleConstants.ARTICLE_ES_SYNC_TOPIC)
    public void onMessage(String message) throws IOException {
        if(StringUtils.isNotBlank(message)){

            log.info("MESSAGE={}",message);
            SearchArticleVo searchArticleVo = JSON.parseObject(message, SearchArticleVo.class);

            esClient.index(i -> i
                    .index("app_info_article")
                    .id(searchArticleVo.getId().toString())
                    .document(searchArticleVo));
        }

    }
}
