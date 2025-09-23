package com.qiao.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.wemedia.dtos.WmLoginDto;
import com.qiao.model.wemedia.pojos.WmUser;

public interface WmUserService extends IService<WmUser> {
    public ResponseResult login(WmLoginDto dto);

}
