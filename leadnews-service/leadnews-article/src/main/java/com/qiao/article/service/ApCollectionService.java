package com.qiao.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.behavior.dtos.CollectionBehaviorDto;
import com.qiao.model.article.pojos.ApCollection;
import com.qiao.model.common.dtos.ResponseResult;

public interface ApCollectionService extends IService<ApCollection> {
    public ResponseResult collect(CollectionBehaviorDto dto);
}
