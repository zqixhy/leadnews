package com.qiao.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiao.model.common.enums.AppHttpCodeEnum;
import com.qiao.model.wemedia.dtos.ChannelDto;
import com.qiao.model.common.dtos.PageResponseResult;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.wemedia.pojos.WmChannel;
import com.qiao.model.wemedia.pojos.WmNews;
import com.qiao.wemedia.mapper.WmChannelMapper;
import com.qiao.wemedia.mapper.WmNewsMapper;
import com.qiao.wemedia.service.WmChannelService;
import com.qiao.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {

    @Autowired
    private WmNewsService wmNewsService;
    @Override
    public ResponseResult findAll() {

        return ResponseResult.okResult(list());
    }

    @Override
    public ResponseResult getList(ChannelDto dto) {
        dto.checkParam();
        IPage page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<WmChannel> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(WmChannel::getCreatedTime);
       /* if(dto.getStatus() != null){
            lqw.eq(WmChannel::getStatus, dto.getStatus());
        }*/
        if(StringUtils.isNotBlank(dto.getName())){
            lqw.like(WmChannel::getName,dto.getName());
        }

        page = page(page, lqw);

        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    @Override
    public ResponseResult saveOrUpdateChannel(WmChannel wmChannel) {
        wmChannel.setCreatedTime(new Date());

        saveOrUpdate(wmChannel);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult delete(Integer id) {
        if(id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        List<WmNews> list = wmNewsService.list(Wrappers.<WmNews>lambdaQuery().eq(WmNews::getChannelId, id));

        if(list != null || list.size() != 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"channel is used in article");
        }

        removeById(id);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
