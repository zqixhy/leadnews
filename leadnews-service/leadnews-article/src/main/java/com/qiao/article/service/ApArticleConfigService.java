package com.qiao.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.article.pojos.ApArticleConfig;

import java.util.Map;

public interface ApArticleConfigService extends IService<ApArticleConfig> {
    void updateByMap(Map map);

}
