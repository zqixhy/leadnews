package com.qiao.schedule.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qiao.common.constants.ScheduleConstants;
import com.qiao.common.redis.CacheService;
import com.qiao.model.schedule.dtos.Task;
import com.qiao.model.schedule.pojos.Taskinfo;
import com.qiao.model.schedule.pojos.TaskinfoLogs;
import com.qiao.schedule.mapper.TaskinfoLogsMapper;
import com.qiao.schedule.mapper.TaskinfoMapper;
import com.qiao.schedule.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Override
    public long addTask(Task task) {

        boolean success = addTaskToDb(task);
        if(success){
            addTaskToCache(task);
        }

        return task.getTaskId();
    }

    @Override
    public boolean cancelTask(long taskId) {
        boolean flag = false;
        Task task = updateDb(taskId,ScheduleConstants.CANCELLED);

        if(task != null){
            removeTaskFromCache(task);
            flag = true;
        }
        return flag;
    }

    @Override
    public Task poll(int type, int priority) {
        String key = type +"_"+priority;
        String task_json = cacheService.lRightPop(ScheduleConstants.TOPIC+key);

        Task task = null;
        if(StringUtils.isNotBlank(task_json)){
            task = JSON.parseObject(task_json, Task.class);
            updateDb(task.getTaskId(),ScheduleConstants.EXECUTED);
        }

        return task;
    }

    private void removeTaskFromCache(Task task) {
        String key = task.getTaskType() + "_" + task.getPriority();
        if(task.getExecuteTime()<=System.currentTimeMillis()){
            cacheService.lRemove(ScheduleConstants.TOPIC+key,0,JSON.toJSONString(task));
        } else {
            cacheService.zRemove(ScheduleConstants.FUTURE+key,0,JSON.toJSONString(task));
        }
    }

    private Task updateDb(long taskId, int status) {
        taskinfoMapper.deleteById(taskId);
        TaskinfoLogs taskinfoLogs = taskinfoLogsMapper.selectById(taskId);
        taskinfoLogs.setStatus(status);
        taskinfoLogsMapper.updateById(taskinfoLogs);

        Task task = new Task();
        BeanUtils.copyProperties(taskinfoLogs,task);
        task.setExecuteTime(taskinfoLogs.getExecuteTime().getTime());

        return task;
    }

    @Autowired
    private CacheService cacheService;

    private void addTaskToCache(Task task) {
        String key = task.getTaskType()+"_"+task.getPriority();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,5);
        long nextScheduleTime = calendar.getTimeInMillis();

        if(task.getExecuteTime()<=System.currentTimeMillis()){
            cacheService.lLeftPush(ScheduleConstants.TOPIC+key, JSON.toJSONString(task));
        }else if(task.getExecuteTime() <= nextScheduleTime){
            cacheService.zAdd(ScheduleConstants.FUTURE+key,JSON.toJSONString(task),task.getExecuteTime());
        }

    }

    @Autowired
    private TaskinfoMapper taskinfoMapper;

    @Autowired
    private TaskinfoLogsMapper taskinfoLogsMapper;

    private boolean addTaskToDb(Task task){
        boolean flag = false;
        Taskinfo taskinfo = new Taskinfo();
        BeanUtils.copyProperties(task,taskinfo);

        taskinfo.setExecuteTime(new Date(task.getExecuteTime()));
        taskinfoMapper.insert(taskinfo);

        task.setTaskId(taskinfo.getTaskId());

        TaskinfoLogs taskinfoLogs = new TaskinfoLogs();
        BeanUtils.copyProperties(taskinfo,taskinfoLogs);
        taskinfoLogs.setVersion(1);
        taskinfoLogs.setStatus(ScheduleConstants.SCHEDULED);
        taskinfoLogsMapper.insert(taskinfoLogs);
        flag = true;
        return flag;
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void refresh(){

        String token = cacheService.tryLock("FUTURE_TASK_SYNC", 1000 * 30);
        if(StringUtils.isNotBlank(token)){
            Set<String> futureKeys = cacheService.scan(ScheduleConstants.FUTURE + "*");
            for (String futureKey : futureKeys) {
                String topicKey = ScheduleConstants.TOPIC+futureKey.split(ScheduleConstants.FUTURE)[1];
                Set<String> tasks = cacheService.zRangeByScore(futureKey, 0, System.currentTimeMillis());

                if(!tasks.isEmpty()){
                    cacheService.refreshWithPipeline(futureKey,topicKey,tasks);
                }
            }
        }
    }

    @PostConstruct
    @Scheduled(cron = "0 */5 * * * ?")
    public void reloadData(){
        Set<String> topicKeys = cacheService.scan(ScheduleConstants.TOPIC + "*");
        Set<String> futureKeys = cacheService.scan(ScheduleConstants.FUTURE + "*");
        cacheService.delete(topicKeys);
        cacheService.delete(futureKeys);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,5);
        List<Taskinfo> taskinfos = taskinfoMapper.selectList(Wrappers.<Taskinfo>lambdaQuery().lt(Taskinfo::getExecuteTime, calendar.getTime()));

        if(taskinfos != null && taskinfos.size() != 0){
            for (Taskinfo taskinfo : taskinfos) {
                Task task = new Task();
                BeanUtils.copyProperties(taskinfo,task);
                task.setExecuteTime(taskinfo.getExecuteTime().getTime());
                addTaskToCache(task);
            }
        }
    }
}
