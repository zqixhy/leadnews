package com.qiao.user.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiao.apis.article.IArticleClient;
import com.qiao.apis.wemedia.IWemediaClient;
import com.qiao.common.constants.ApUserConstants;
import com.qiao.common.constants.WemediaConstants;
import com.qiao.model.article.pojos.ApAuthor;
import com.qiao.model.common.dtos.PageResponseResult;
import com.qiao.model.common.enums.AppHttpCodeEnum;
import com.qiao.model.user.dtos.AuthDto;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.user.pojos.ApUser;
import com.qiao.model.user.pojos.ApUserRealname;
import com.qiao.model.wemedia.pojos.WmUser;
import com.qiao.user.mapper.ApUserMapper;
import com.qiao.user.mapper.ApUserRealnameMapper;
import com.qiao.user.service.ApUserRealnameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Slf4j
public class ApApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper,ApUserRealname> implements ApUserRealnameService {

    @Autowired
    private ApUserMapper apUserMapper;

    @Autowired
    private IWemediaClient iWemediaClient;

    @Autowired
    private IArticleClient iArticleClient;

    @Override
    public ResponseResult getList(AuthDto dto) {
        dto.checkParam();
        IPage page = new Page(dto.getPage(),dto.getSize());

        LambdaQueryWrapper<ApUserRealname> lqw = new LambdaQueryWrapper<>();
        if(dto.getId() != null){
            lqw.eq(ApUserRealname::getId, dto.getId());
        }
        if(dto.getStatus() !=null){
            lqw.eq(ApUserRealname::getStatus, dto.getStatus());
        }
        lqw.orderByDesc(ApUserRealname::getCreatedTime);

        page = page(page,lqw);
        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    @Override
    public ResponseResult pass(AuthDto dto) {
        update(Wrappers.<ApUserRealname>lambdaUpdate().eq(ApUserRealname::getId, dto.getId())
                .set(ApUserRealname::getStatus,ApUserConstants.AUTHORIZATION_PASS)
                .set(ApUserRealname::getUpdatedTime,new Date()));

        ApUserRealname apUserRealname = getById(dto.getId());
        ApUser apUser = apUserMapper.selectById(apUserRealname.getUserId());
        WmUser wmUser = iWemediaClient.getByUserId(apUser.getId());
        if(wmUser == null){
            wmUser = new WmUser();
            BeanUtils.copyProperties(apUser, wmUser);
            wmUser.setApUserId(apUser.getId());
            wmUser.setStatus(WemediaConstants.WM_USER_OK);
            wmUser.setCreatedTime(new Date());
            ResponseResult responseResult = iWemediaClient.saveWmUser(wmUser);
            if(responseResult.getCode() == AppHttpCodeEnum.SUCCESS.getCode()){
                log.info("wmUser account creat success");
            }
        }

        ApAuthor apAuthor = iArticleClient.getByUserId(apUser.getId());
        if(apAuthor == null){
            apAuthor = new ApAuthor();
            apAuthor.setUserId(apUser.getId());
            apAuthor.setName(apUser.getName());
            apAuthor.setType((short)2);
            apAuthor.setCreatedTime(new Date());
            apAuthor.setWmUserId(wmUser.getId());
            iArticleClient.saveApAuthor(apAuthor);
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult fail(AuthDto dto) {
        ApUserRealname apUserRealname = new ApUserRealname();
        apUserRealname.setId(dto.getId());
        apUserRealname.setReason(dto.getMsg());
        apUserRealname.setUpdatedTime(new Date());
        apUserRealname.setStatus(ApUserConstants.AUTHORIZATION_FAILED);
        updateById(apUserRealname);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
