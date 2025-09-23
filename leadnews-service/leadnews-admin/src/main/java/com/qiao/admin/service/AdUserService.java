package com.qiao.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.admin.dtos.AdUserDto;
import com.qiao.model.admin.pojos.AdUser;
import com.qiao.model.common.dtos.ResponseResult;

public interface AdUserService extends IService<AdUser> {
    public ResponseResult login(AdUserDto dto);

}
