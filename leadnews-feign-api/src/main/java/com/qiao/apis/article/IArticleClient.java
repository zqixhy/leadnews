package com.qiao.apis.article;

import com.qiao.apis.article.fallback.IArticleClientFallback;
import com.qiao.model.article.dtos.ArticleDto;
import com.qiao.model.article.pojos.ApArticle;
import com.qiao.model.article.pojos.ApAuthor;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.wemedia.pojos.WmUser;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "leadnews-article",fallback = IArticleClientFallback.class, path = "/api/v1/article")
public interface IArticleClient {
    @PostMapping("/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto dto);

    @GetMapping("/del/{id}")
    public ResponseResult delArticle(@PathVariable Long id);

    @GetMapping("/getOne/{id}")
    public ApArticle getOne(@PathVariable Long id);

    @PostMapping("/apAuthor/save")
    public ResponseResult saveApAuthor(@RequestBody ApAuthor apAuthor);

    @GetMapping("/apAuthor/getOne/{id}")
    public ApAuthor getByUserId(@PathVariable Integer id);





}
