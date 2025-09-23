package com.qiao.model.wemedia.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WmNewsDto {
    
    private Integer id;
    private String title;
    private String content;
    private Integer channelId;
    private String labels;
    private Date publishTime;
    private Date submitedTime;
    private List<String> images;

    /**
     * 文章封面类型  0 无图 1 单图 3 多图 -1 自动
     */
    private Short type;
     /**
     * 状态 提交为1  草稿为0
     */
    private Short status;

    /**
     * 0down 1up
     */
    private Short enable;
}