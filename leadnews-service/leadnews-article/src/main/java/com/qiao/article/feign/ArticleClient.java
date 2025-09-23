package com.qiao.article.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qiao.apis.article.IArticleClient;
import com.qiao.article.mapper.ApArticleMapper;
import com.qiao.article.mapper.ApAuthorMapper;
import com.qiao.article.service.ApArticleService;
import com.qiao.model.article.dtos.ArticleDto;
import com.qiao.model.article.pojos.ApArticle;
import com.qiao.model.article.pojos.ApAuthor;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleClient implements IArticleClient {
    @Autowired
    private ApArticleService apArticleService;

    @Autowired
    private ApAuthorMapper apAuthorMapper;

    @PostMapping("/api/v1/article/save")
    @Override
    public ResponseResult saveArticle(ArticleDto dto) {
        return apArticleService.saveArticle(dto);
    }

    @Override
    @GetMapping("/api/v1/article/del/{id}")
    public ResponseResult delArticle(Long id) {
        return apArticleService.delArticle(id);
    }

    @GetMapping("/api/v1/article/getOne/{id}")
    public ApArticle getOne(Long id){

        return apArticleService.getById(id);
    }

    @PostMapping("/api/v1/article/apAuthor/save")
    public ResponseResult saveApAuthor(ApAuthor apAuthor) {
        apAuthorMapper.insert(apAuthor);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/article/apAuthor/getOne")
    public ApAuthor getByUserId(@PathVariable Integer id){
        return apAuthorMapper.selectOne(Wrappers.<ApAuthor>lambdaQuery().eq(ApAuthor::getUserId, id));

    }



}
