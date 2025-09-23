package com.qiao.search.service;

import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.search.dtos.UserSearchDto;

public interface ApAssociateWordsService {
    public ResponseResult  search(UserSearchDto dto);
}
