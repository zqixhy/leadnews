package com.qiao.behavior.controller;

import com.qiao.behavior.service.ApLikesBehaviorService;
import com.qiao.behavior.service.ApUnlikesBehaviorService;
import com.qiao.model.behavior.dtos.LikesBehaviorDto;
import com.qiao.model.behavior.dtos.UnLikesBehaviorDto;
import com.qiao.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BehaviorController {
    @Autowired
    private ApLikesBehaviorService apLikesBehaviorService;

    @Autowired
    private ApUnlikesBehaviorService apUnlikesBehaviorService;

    @PostMapping("/likes_behavior")
    public ResponseResult like(@RequestBody LikesBehaviorDto dto){
        return apLikesBehaviorService.like(dto);
    }

    @PostMapping("/un_likes_behavior")
    public ResponseResult unlike(@RequestBody UnLikesBehaviorDto dto){
        return apUnlikesBehaviorService.unlike(dto);
    }

    @PostMapping("read_behavior")
    public ResponseResult read(){
        return null;
    }




}
