package com.qiao.wemedia.controller;

import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.wemedia.dtos.SensitiveDto;
import com.qiao.model.wemedia.pojos.WmSensitive;
import com.qiao.wemedia.service.WmSensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensitive")
public class WmSensitiveController {

    @Autowired
    private WmSensitiveService wmSensitiveService;

    @PostMapping("/list")
    public ResponseResult getList(@RequestBody SensitiveDto dto){
        return wmSensitiveService.getList(dto);
    }

    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable Integer id){
        return wmSensitiveService.delete(id);
    }

    @PostMapping("/save")
    public ResponseResult save(@RequestBody WmSensitive wmSensitive){
        return wmSensitiveService.saveOrUpdateSensitive(wmSensitive);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody WmSensitive wmSensitive){
        return wmSensitiveService.saveOrUpdateSensitive(wmSensitive);
    }
}
