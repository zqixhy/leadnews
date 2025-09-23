package com.qiao.wemedia.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qiao.apis.wemedia.IWemediaClient;
import com.qiao.model.article.pojos.ApArticle;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.common.enums.AppHttpCodeEnum;
import com.qiao.model.wemedia.pojos.WmUser;
import com.qiao.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WemediaClient implements IWemediaClient {

    @Autowired
    private WmUserService wmUserService;

    @GetMapping("/getOne/{id}")
    public WmUser getByUserId(@PathVariable Integer id){
        return wmUserService.getOne(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getApUserId,id));
    };

    @PostMapping("/save")
    public ResponseResult saveWmUser(@RequestBody WmUser wmUser){
        wmUserService.save(wmUser);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    };
}
