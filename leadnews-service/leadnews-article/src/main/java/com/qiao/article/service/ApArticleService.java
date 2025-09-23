package com.qiao.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.article.dtos.ArticleDto;
import com.qiao.model.article.dtos.ArticleHomeDto;
import com.qiao.model.article.dtos.ArticleInfoDto;
import com.qiao.model.article.pojos.ApArticle;
import com.qiao.model.common.dtos.ResponseResult;

public interface ApArticleService extends IService<ApArticle> {

    public ResponseResult load(ArticleHomeDto dto,Short type);

    public ResponseResult saveArticle(ArticleDto dto);

    public ResponseResult delArticle(Long id);

    public ResponseResult loadArticleBehavior(ArticleInfoDto dto);
}
