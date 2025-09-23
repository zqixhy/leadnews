package com.qiao.article.controller;

import com.alibaba.fastjson.JSON;
import com.qiao.article.service.ApArticleService;
import com.qiao.common.constants.ArticleConstants;
import com.qiao.model.article.dtos.ArticleDto;
import com.qiao.model.article.dtos.ArticleHomeDto;
import com.qiao.model.article.dtos.ArticleInfoDto;
import com.qiao.model.common.dtos.ResponseResult;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController {

    @Autowired
    private ApArticleService apArticleService;

    @PostMapping("/load")
    public ResponseResult load(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    @PostMapping("/loadmore")
    public ResponseResult loadMore(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,ArticleConstants.LOADTYPE_LOAD_MORE);

    }

    @PostMapping("/loadnew")
    public ResponseResult loadnew(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,ArticleConstants.LOADTYPE_LOAD_NEW);
    }

    @PostMapping("/load_article_behavior")
    public ResponseResult loadArticleBehavior(@RequestBody ArticleInfoDto dto){
        return apArticleService.loadArticleBehavior(dto);
    }

    @PostMapping("/load_article_info")
    public ResponseResult loadArticleInfo(){
        return null;
    }




}
