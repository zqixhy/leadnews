package com.qiao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.user.dtos.LoginDto;
import com.qiao.model.user.pojos.ApUser;

public interface ApUserService extends IService<ApUser> {
    public ResponseResult login(LoginDto dto);
}
