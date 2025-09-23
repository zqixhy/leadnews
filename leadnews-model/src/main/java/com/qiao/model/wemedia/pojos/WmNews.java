package com.qiao.model.wemedia.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("wm_news")
public class WmNews implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("title")
    private String title;
    @TableField("content")
    private String content;
    @TableField("channel_id")
    private Integer channelId;
    @TableField("labels")
    private String labels;
    @TableField("created_time")
    private Date createdTime;
    @TableField("submited_time")
    private Date submitedTime;
    @TableField("publish_time")
    private Date publishTime;
    @TableField("article_id")
    private Long articleId;
    @TableField("images")
    private String images;
    @TableField("enable")
    private Short enable;

    /**
     * 文章布局
            0 无图文章
            1 单图文章
            3 多图文章
     */
    @TableField("type")
    private Short type;

    /**
     * 当前状态
            0 草稿
            1 提交（待审核）
            2 审核失败
            3 人工审核
            4 人工审核通过
            8 审核通过（待发布）
            9 已发布
     */
    @TableField("status")
    private Short status;
    /**
     * 拒绝理由
     */
    @TableField("reason")
    private String reason;

     //状态枚举类
     public enum Status{
        NORMAL((short)0),SUBMIT((short)1),FAIL((short)2),ADMIN_AUTH((short)3),ADMIN_SUCCESS((short)4),SUCCESS((short)8),PUBLISHED((short)9);
         short code;
         Status(short code){
             this.code = code;
         }
         public short getCode(){
             return this.code;
         }
    }

}