package com.qiao.article.service;

import com.qiao.model.article.pojos.ApArticle;
import com.qiao.model.article.pojos.ApArticleContent;

public interface ArticleFreemarkerService {
    public void buildArticleToMinIO(ApArticle apArticle, String content);
}
