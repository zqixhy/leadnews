package com.qiao.search.service.impl;

import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.common.enums.AppHttpCodeEnum;
import com.qiao.model.search.dtos.UserSearchDto;
import com.qiao.search.pojos.ApAssociateWords;
import com.qiao.search.service.ApAssociateWordsService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApAssociateWordsServiceImpl implements ApAssociateWordsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResponseResult search(UserSearchDto dto) {

        if(StringUtils.isBlank(dto.getSearchWords())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        if(dto.getPageSize() >20){
            dto.setPageSize(20);
        }

        List<ApAssociateWords> wordsList = mongoTemplate.find(Query
                .query(Criteria.where("associateWords").regex(".*?\\" + dto.getSearchWords() + ".*"))
                .limit(dto.getPageSize()), ApAssociateWords.class);
        return ResponseResult.okResult(wordsList);
    }
}
