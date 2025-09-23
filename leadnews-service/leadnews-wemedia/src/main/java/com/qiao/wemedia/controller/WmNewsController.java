package com.qiao.wemedia.controller;

import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.wemedia.dtos.NewsAuthDto;
import com.qiao.model.wemedia.dtos.WmNewsDto;
import com.qiao.model.wemedia.dtos.WmNewsPageReqDto;
import com.qiao.wemedia.service.WmNewsService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {
    @Autowired
    private WmNewsService wmNewsService;

    @GetMapping("/del_news/{id}")
    public ResponseResult delNews(@PathVariable Integer id){
        return wmNewsService.delNews(id);
    }

    @PostMapping("/list")
    public ResponseResult getList(@RequestBody WmNewsPageReqDto dto){
        return wmNewsService.getList(dto);
    }

    @PostMapping("/submit")
    public ResponseResult submit(@RequestBody WmNewsDto dto){
        return wmNewsService.submit(dto);
    }

    @PostMapping("/down_or_up")
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto){return wmNewsService.downOrUp(dto);}

    @PostMapping("/list_vo")
    public ResponseResult getList_vo(@RequestBody NewsAuthDto dto){
        return wmNewsService.getList_vo(dto);
    }

    @GetMapping("/one_vo/{id}")
    public ResponseResult getDetail(@PathVariable Integer id){
        return wmNewsService.getDetail(id);
    }

    @PostMapping("/auth_fail")
    public ResponseResult authFail(@RequestBody NewsAuthDto dto){
        return wmNewsService.authFail(dto);
    }

    @PostMapping("/auth_pass")
    public ResponseResult authPass(@RequestBody NewsAuthDto dto){
        return wmNewsService.authPass(dto);
    }

    @GetMapping("/one/{id}")
    public ResponseResult getOne(@PathVariable Integer id){
        return wmNewsService.getOne(id);
    }

}
