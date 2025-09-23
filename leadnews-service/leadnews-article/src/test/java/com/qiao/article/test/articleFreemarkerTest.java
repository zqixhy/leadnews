package com.qiao.article.test;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qiao.article.ArticleApplication;
import com.qiao.article.mapper.ApArticleContentMapper;
import com.qiao.article.service.ApArticleService;
import com.qiao.file.service.FileStorageService;
import com.qiao.model.article.pojos.ApArticle;
import com.qiao.model.article.pojos.ApArticleContent;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = ArticleApplication.class)
@ExtendWith(SpringExtension.class)
public class  articleFreemarkerTest {

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    Configuration configuration;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    private ApArticleService apArticleService;

    @Test
    public void createStaticUrlTest() throws IOException, TemplateException {
        LambdaQueryWrapper<ApArticleContent> lqw = new LambdaQueryWrapper<>();
        ApArticleContent apArticleContent = apArticleContentMapper.selectOne(lqw.eq(ApArticleContent::getArticleId, 1303156149041758210L));
        if(apArticleContent != null && StringUtils.isNotBlank(apArticleContent.getContent())){
            Template template = configuration.getTemplate("article.ftl");
            Map<String,Object> content = new HashMap<>();
            content.put("content", JSONArray.parseArray(apArticleContent.getContent()));
            StringWriter out = new StringWriter();
            template.process(content,out);

            InputStream is = new ByteArrayInputStream(out.toString().getBytes());

            String path = fileStorageService.uploadHtmlFile("", apArticleContent.getArticleId() + ".html", is);

            apArticleService.update(Wrappers.<ApArticle>lambdaUpdate().eq(ApArticle::getId,apArticleContent.getArticleId()).set(ApArticle::getStaticUrl,path));

        }


    }
}
