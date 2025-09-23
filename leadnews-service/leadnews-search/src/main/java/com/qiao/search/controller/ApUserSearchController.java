package com.qiao.search.controller;

import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.search.dtos.HistorySearchDto;
import com.qiao.search.service.ApUserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/history")
public class ApUserSearchController {
    @Autowired
    private ApUserSearchService apUserSearchService;

    @PostMapping("/load")
    public ResponseResult findUserSearch(){
        return apUserSearchService.findUserSearch();
    }

    @PostMapping("/del")
    public ResponseResult delete(@RequestBody HistorySearchDto dto){
        return apUserSearchService.delete(dto);
    }
}
