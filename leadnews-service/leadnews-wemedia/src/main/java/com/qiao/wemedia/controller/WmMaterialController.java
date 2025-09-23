package com.qiao.wemedia.controller;

import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.wemedia.dtos.WmMaterialDto;
import com.qiao.wemedia.service.WmMaterialService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    @Autowired
    private WmMaterialService wmMaterialService;

    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile){
        return wmMaterialService.uploadPicture(multipartFile);

    }

    @PostMapping("/list")
    public ResponseResult list(@RequestBody WmMaterialDto wmMaterialDto){
        return wmMaterialService.list(wmMaterialDto);
    }

    @GetMapping("/del_picture/{id}")
    public ResponseResult delPicture(@PathVariable Integer id){
        return wmMaterialService.delPicture(id);
    }
}
