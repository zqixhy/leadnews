package com.qiao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.user.dtos.AuthDto;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.user.pojos.ApUserRealname;

public interface ApUserRealnameService extends IService<ApUserRealname> {
    public ResponseResult getList(AuthDto dto);
    public ResponseResult pass(AuthDto dto);

    public ResponseResult fail(AuthDto dto);

}
