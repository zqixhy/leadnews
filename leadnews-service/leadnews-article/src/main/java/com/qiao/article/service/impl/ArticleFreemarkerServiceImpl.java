package com.qiao.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qiao.article.mapper.ApArticleMapper;
import com.qiao.article.service.ApArticleService;
import com.qiao.article.service.ArticleFreemarkerService;
import com.qiao.common.constants.ArticleConstants;
import com.qiao.file.service.FileStorageService;
import com.qiao.model.article.pojos.ApArticle;
import com.qiao.model.search.vos.SearchArticleVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;

@Service
@Slf4j
@Transactional
public class ArticleFreemarkerServiceImpl implements ArticleFreemarkerService {

    @Autowired
    private Configuration configuration;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    @Async
    public void buildArticleToMinIO(ApArticle apArticle, String content) {

        if(StringUtils.isNotBlank(content)){
            Template template = null;
            StringWriter out = new StringWriter();
            try {
                template = configuration.getTemplate("article.ftl");
                HashMap<String, Object> contentDataModel = new HashMap<>();
                contentDataModel.put("content", JSONArray.parseArray(content));
                template.process(contentDataModel,out);
            } catch (Exception e) {
                e.printStackTrace();
            }

            InputStream in = new ByteArrayInputStream(out.toString().getBytes());
            String path = fileStorageService.uploadHtmlFile("", apArticle.getId() + ".html", in);

            apArticleMapper.update(apArticle,Wrappers.<ApArticle>lambdaUpdate().eq(ApArticle::getId,apArticle.getId()).set(ApArticle::getStaticUrl,path));

            createArticleESIndex(apArticle,content,path);

        }

    }

    private void createArticleESIndex(ApArticle apArticle, String content, String path) {
        SearchArticleVo searchArticleVo = new SearchArticleVo();
        BeanUtils.copyProperties(apArticle, searchArticleVo);
        searchArticleVo.setContent(content);
        searchArticleVo.setStaticUrl(path);

        kafkaTemplate.send(ArticleConstants.ARTICLE_ES_SYNC_TOPIC, JSON.toJSONString(searchArticleVo));



    }
}
