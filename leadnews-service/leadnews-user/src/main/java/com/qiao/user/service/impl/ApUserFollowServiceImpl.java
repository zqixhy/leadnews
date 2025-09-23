package com.qiao.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiao.apis.article.IArticleClient;
import com.qiao.common.exception.CustomException;
import com.qiao.model.article.pojos.ApArticle;
import com.qiao.model.behavior.dtos.FollowBehaviorDto;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.common.enums.AppHttpCodeEnum;
import com.qiao.model.user.dtos.UserRelationDto;
import com.qiao.model.user.pojos.ApUser;
import com.qiao.model.user.pojos.ApUserFan;
import com.qiao.model.user.pojos.ApUserFollow;
import com.qiao.model.wemedia.pojos.WmUser;
import com.qiao.user.mapper.ApUserFanMapper;
import com.qiao.user.mapper.ApUserFollowMapper;
import com.qiao.user.mapper.ApUserMapper;
import com.qiao.user.service.ApUserFollowService;
import com.qiao.user.service.ApUserService;
import com.qiao.utils.thread.AppThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper,ApUserFollow> implements ApUserFollowService {

    @Autowired
    private IArticleClient iArticleClient;

    @Autowired
    private ApUserFanMapper apUserFanMapper;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public ResponseResult followOrUnfollow(UserRelationDto dto) {
        ApUser user = AppThreadLocalUtil.getUser();
        if(user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        if(dto.getOperation() == 0){
            ApUserFollow one = getOne(Wrappers.<ApUserFollow>lambdaQuery()
                    .eq(ApUserFollow::getUserId, user.getId())
                    .eq(ApUserFollow::getFollowId, dto.getAuthorId()));
            if(one != null){
                return ResponseResult.errorResult(AppHttpCodeEnum.HAVE_FOLLOWED);
            }
            ApUserFollow apUserFollow = new ApUserFollow();
            apUserFollow.setUserId(user.getId());
            apUserFollow.setFollowId(dto.getAuthorId());
            ApArticle article = iArticleClient.getOne(dto.getArticleId());
            String authorName = article.getAuthorName();
            apUserFollow.setFollowName(authorName);
            apUserFollow.setIsNotice((short) 1);
            apUserFollow.setLevel((short)1);
            apUserFollow.setCreatedTime(new Date());
            save(apUserFollow);

            ApUserFan apUserFan = new ApUserFan();
            apUserFan.setUserId(dto.getAuthorId());
            apUserFan.setFansId(user.getId());
            apUserFan.setFansName(user.getName());
            apUserFan.setLevel((short)1);
            apUserFan.setIsDisplay((short)0);
            apUserFan.setIsShieldLetter((short)0);
            apUserFan.setIsShieldComment((short)0);
            apUserFan.setCreatedTime(new Date());
            apUserFanMapper.insert(apUserFan);

            FollowBehaviorDto followBehaviorDto = new FollowBehaviorDto();
            followBehaviorDto.setFollowId(dto.getAuthorId());
            followBehaviorDto.setArticleId(dto.getArticleId());
            followBehaviorDto.setUserId(user.getId());
            kafkaTemplate.send("follow.behavior.topic", JSON.toJSONString(dto));

        } else {
            remove(Wrappers.<ApUserFollow>lambdaQuery().eq(ApUserFollow::getUserId, user.getId())
                    .eq(ApUserFollow::getFollowId,dto.getAuthorId()));

            apUserFanMapper.delete(Wrappers.<ApUserFan>lambdaQuery().eq(ApUserFan::getFansId,user.getId())
                    .eq(ApUserFan::getUserId,dto.getAuthorId()));
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode());
    }
}
