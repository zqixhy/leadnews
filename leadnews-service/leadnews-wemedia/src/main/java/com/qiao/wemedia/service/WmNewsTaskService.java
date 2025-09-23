package com.qiao.wemedia.service;

import java.util.Date;

public interface WmNewsTaskService {
    public void addNewsToTask(Integer id, Date publishTime);

    public void scanNewsByTask();
}
