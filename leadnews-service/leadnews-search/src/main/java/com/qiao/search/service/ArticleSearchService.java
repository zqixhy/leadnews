package com.qiao.search.service;

import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.search.dtos.UserSearchDto;

import java.io.IOException;

public interface ArticleSearchService {
    public ResponseResult search(UserSearchDto userSearchDto) throws IOException;
}
