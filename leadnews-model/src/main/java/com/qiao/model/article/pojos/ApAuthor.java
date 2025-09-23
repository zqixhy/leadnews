package com.qiao.model.article.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ap_author")
public class ApAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    /**
     * 账号类型
     0 爬虫数据
     1 签约合作商
     2 平台自媒体人
     */
    @TableField("type")
    private Short type;

    @TableField("user_id")
    private Integer userId;

    @TableField("wm_user_id")
    private Integer wmUserId;

    @TableField("created_time")
    private Date createdTime;

}
