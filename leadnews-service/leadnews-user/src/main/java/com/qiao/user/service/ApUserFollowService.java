package com.qiao.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.user.dtos.UserRelationDto;
import com.qiao.model.user.pojos.ApUserFollow;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApUserFollowService extends IService<ApUserFollow> {
    public ResponseResult followOrUnfollow(UserRelationDto dto);
}
