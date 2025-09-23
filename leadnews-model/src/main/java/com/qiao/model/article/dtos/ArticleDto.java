package com.qiao.model.article.dtos;

import com.qiao.model.article.pojos.ApArticle;
import lombok.Data;

@Data
public class ArticleDto extends ApArticle {
    private String content;
}
