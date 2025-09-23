package com.qiao.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.wemedia.dtos.WmMaterialDto;
import com.qiao.model.wemedia.pojos.WmMaterial;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {
    public ResponseResult uploadPicture(MultipartFile multipartFile);

    public ResponseResult list(WmMaterialDto wmMaterialDto);

    public ResponseResult delPicture(Integer id);
}
