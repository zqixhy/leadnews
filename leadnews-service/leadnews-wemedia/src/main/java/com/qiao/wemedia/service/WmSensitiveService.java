package com.qiao.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.wemedia.dtos.SensitiveDto;
import com.qiao.model.wemedia.pojos.WmSensitive;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface WmSensitiveService extends IService<WmSensitive> {

    public ResponseResult getList(SensitiveDto dto);

    public ResponseResult delete(Integer id);

    public ResponseResult saveOrUpdateSensitive(WmSensitive wmSensitive);

}
