package com.qiao.model.wemedia.dtos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.qiao.model.wemedia.pojos.WmNews;
import lombok.Data;

import java.util.Date;

@Data
public class NewsDto extends WmNews {
    private String authorName;

}
