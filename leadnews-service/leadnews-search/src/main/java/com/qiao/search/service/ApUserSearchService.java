package com.qiao.search.service;

import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.search.dtos.HistorySearchDto;
import com.qiao.search.pojos.ApUserSearch;

public interface ApUserSearchService {
    public void insert(String keyword, Integer userId);

    public ResponseResult findUserSearch();

    public ResponseResult delete(HistorySearchDto dto);

}
