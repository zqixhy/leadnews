package com.qiao.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiao.model.common.dtos.PageResponseResult;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.common.enums.AppHttpCodeEnum;
import com.qiao.model.wemedia.dtos.SensitiveDto;
import com.qiao.model.wemedia.pojos.WmNews;
import com.qiao.model.wemedia.pojos.WmSensitive;
import com.qiao.wemedia.mapper.WmSensitiveMapper;
import com.qiao.wemedia.service.WmSensitiveService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WmSensitiveServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements WmSensitiveService {
    @Override
    public ResponseResult getList(SensitiveDto dto) {
        dto.checkParam();
        IPage page = new Page(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<WmSensitive> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(WmSensitive::getCreatedTime);
        if(StringUtils.isNotBlank(dto.getName())){
            lqw.like(WmSensitive::getSensitives, dto.getName());
        }
        page = page(page,lqw);

        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(page.getRecords());

        return responseResult;
    }

    @Override
    public ResponseResult delete(Integer id) {
        if(id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        removeById(id);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult saveOrUpdateSensitive(WmSensitive wmSensitive) {
        wmSensitive.setCreatedTime(new Date());

        saveOrUpdate(wmSensitive);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
