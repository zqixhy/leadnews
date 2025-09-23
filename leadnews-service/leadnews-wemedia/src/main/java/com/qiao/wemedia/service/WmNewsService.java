package com.qiao.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.wemedia.dtos.NewsAuthDto;
import com.qiao.model.wemedia.dtos.WmNewsDto;
import com.qiao.model.wemedia.dtos.WmNewsPageReqDto;
import com.qiao.model.wemedia.pojos.WmNews;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface WmNewsService extends IService<WmNews> {
    public ResponseResult getList(WmNewsPageReqDto dto);

    public ResponseResult submit(WmNewsDto dto);

    public ResponseResult downOrUp(WmNewsDto dto);

    public ResponseResult getList_vo(NewsAuthDto dto);

    public ResponseResult getDetail(Integer id);

    public ResponseResult authFail(NewsAuthDto dto);

    public ResponseResult authPass(NewsAuthDto dto);

    public ResponseResult delNews(Integer id);

    public ResponseResult getOne(Integer id);

}
