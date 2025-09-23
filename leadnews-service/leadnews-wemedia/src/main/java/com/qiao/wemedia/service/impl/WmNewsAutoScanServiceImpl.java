package com.qiao.wemedia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qiao.apis.article.IArticleClient;
import com.qiao.common.tess4j.Tess4jClient;
import com.qiao.file.service.FileStorageService;
import com.qiao.model.article.dtos.ArticleDto;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.wemedia.pojos.WmChannel;
import com.qiao.model.wemedia.pojos.WmNews;
import com.qiao.model.wemedia.pojos.WmSensitive;
import com.qiao.model.wemedia.pojos.WmUser;
import com.qiao.utils.common.SensitiveWordUtil;
import com.qiao.wemedia.mapper.WmChannelMapper;
import com.qiao.wemedia.mapper.WmNewsMapper;
import com.qiao.wemedia.mapper.WmSensitiveMapper;
import com.qiao.wemedia.mapper.WmUserMapper;
import com.qiao.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {

    @Autowired
    private WmNewsMapper wmNewsMapper;
    @Override
    @Async
    public void autoScanWmNews(Integer id){
        WmNews wmNews = wmNewsMapper.selectById(id);
        if(wmNews == null){
            throw new RuntimeException("article doesn't exist");
        }

        if(wmNews.getStatus().equals(WmNews.Status.SUBMIT.getCode())){
            Map<String, Object> textAndImages = handleTextAndImages(wmNews);
            if(!handleSensitiveScan((String) textAndImages.get("content"),wmNews)) return;
            if(!handleTextScan((String) textAndImages.get("content"))) return;
            if(!handleImageScan((List<String>) textAndImages.get("images"),wmNews)) return;
            ResponseResult responseResult = saveAppArticle(wmNews);
            if(!responseResult.getCode().equals(200)){
                throw new RuntimeException("save failed");
            }
            wmNews.setArticleId((Long) responseResult.getData());
            updateWmNews(wmNews,(short) 9,"processed");

        }

    }

    @Autowired
    private WmSensitiveMapper wmSensitiveMapper;
    private boolean handleSensitiveScan(String content,WmNews wmNews) {
        List<WmSensitive> wmSensitives = wmSensitiveMapper.selectList(Wrappers.<WmSensitive>lambdaQuery().select(WmSensitive::getSensitives));
        List<String> sensitiveList = wmSensitives.stream().map(WmSensitive::getSensitives).collect(Collectors.toList());
        SensitiveWordUtil.initMap(sensitiveList);

        Map<String, Integer> map = SensitiveWordUtil.matchWords(content);
        if(map.size()>0){
            updateWmNews(wmNews, (short) 2,"the content contains restrict words");
            return false;
        }

        return true;
    }

    @Resource
    private IArticleClient iArticleClient;

    @Autowired
    private WmChannelMapper wmChannelMapper;

    @Autowired
    private WmUserMapper wmUserMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private Tess4jClient tess4jClient;

    private ResponseResult saveAppArticle(WmNews wmNews) {
        WmUser wmUser = wmUserMapper.selectById(wmNews.getUserId());
        WmChannel wmChannel = wmChannelMapper.selectById(wmNews.getChannelId());

        ArticleDto dto = new ArticleDto();
        BeanUtils.copyProperties(wmNews,dto);
        dto.setLayout(wmNews.getType());
        dto.setAuthorId(wmNews.getUserId().longValue());
        if(wmUser != null){
            dto.setAuthorName(wmUser.getName());
        }
        if(wmChannel != null){
            dto.setChannelName(wmChannel.getName());
        }

        if(wmNews.getArticleId() != null){
            dto.setId(wmNews.getArticleId());
        }

        dto.setCreatedTime(new Date());

        ResponseResult responseResult = iArticleClient.saveArticle(dto);
        return responseResult;

    }

    private boolean handleTextScan(String content){
        Boolean flag = true;
        return flag;
    }

    private boolean handleImageScan(List<String> images, WmNews wmNews) {
        boolean flag = true;
        if(images == null || images.size() == 0){
            return flag;
        }
        images = images.stream().distinct().collect(Collectors.toList());


        try {
            for (String image : images) {
                byte[] bytes = fileStorageService.downLoadFile(image);

                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                BufferedImage bufferedImage = ImageIO.read(in);

                String result = tess4jClient.doOCR(bufferedImage);
                boolean isSensitive = handleSensitiveScan(result, wmNews);
                if(!isSensitive){
                    flag = false;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    private Map<String, Object> handleTextAndImages(WmNews wmNews){
        StringBuilder stringBuilder = new StringBuilder();
        List<String> images = new ArrayList<>();
        String content = wmNews.getContent();
        if(StringUtils.isNotBlank(content)){
            List<Map> maps = JSONArray.parseArray(content, Map.class);
            for (Map map : maps) {
                if(map.get("type").equals("text")){
                    stringBuilder.append(map.get("value"));
                }else if(map.get("type").equals("images")){
                    images.add((String) map.get("value"));
                }
            }
        }
        if(StringUtils.isNotBlank(wmNews.getImages())){
            String[] split = wmNews.getImages().split(",");
            images.addAll(Arrays.asList(split));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("content", stringBuilder.toString());
        result.put("images",images);
        return result;

    }

    private void updateWmNews(WmNews wmNews, short status, String reason){
        wmNews.setStatus(status);
        wmNews.setReason(reason);
        wmNewsMapper.updateById(wmNews);
    }

}
