package com.qiao.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiao.model.wemedia.pojos.WmNews;
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;

public interface WmNewsAutoScanService{
    public void autoScanWmNews(Integer id);
}
