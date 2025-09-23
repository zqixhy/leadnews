package com.qiao.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiao.apis.schedule.IScheduleClient;
import com.qiao.model.common.dtos.ResponseResult;
import com.qiao.model.common.enums.TaskTypeEnum;
import com.qiao.model.schedule.dtos.Task;
import com.qiao.model.wemedia.pojos.WmNews;
import com.qiao.utils.common.ProtostuffUtil;
import com.qiao.wemedia.service.WmNewsAutoScanService;
import com.qiao.wemedia.service.WmNewsTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class WmNewsTaskServiceImpl implements WmNewsTaskService {

    @Autowired
    private IScheduleClient iScheduleClient;

    @Autowired
    private WmNewsAutoScanService wmNewsAutoScanService;

    @Override
    @Async
    public void addNewsToTask(Integer id, Date publishTime) {
        log.info("begin to add news to task........");
        WmNews wmNews = new WmNews();
        wmNews.setId(id);
        Task task = new Task();
        task.setExecuteTime(publishTime.getTime());
        task.setTaskType(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType());
        task.setPriority(TaskTypeEnum.NEWS_SCAN_TIME.getPriority());

        task.setParameters(ProtostuffUtil.serialize(wmNews));

        iScheduleClient.addTask(task);

        log.info("end adding news to task................");

    }

    @Scheduled(fixedRate = 1000)
    @Override
    public void scanNewsByTask() {
        log.info("scanning task....");
        ResponseResult poll = iScheduleClient.poll(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType(), TaskTypeEnum.NEWS_SCAN_TIME.getPriority());
        if(poll.getCode().equals(200) && poll.getData() != null){
            Task task = JSON.parseObject(JSON.toJSONString(poll.getData()), Task.class);
            WmNews wmNews = ProtostuffUtil.deserialize(task.getParameters(), WmNews.class);
            wmNewsAutoScanService.autoScanWmNews(wmNews.getId());
        }
    }

}
