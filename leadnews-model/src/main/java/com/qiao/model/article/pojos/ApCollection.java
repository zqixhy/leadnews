package com.qiao.model.article.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ApCollection {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("entry_id")
    private Long entryId;

    @TableField("article_id")
    private Long articleId;

    @TableField("type")
    private Short type;

    @TableField("published_time")
    private Date publishedTime;

    @TableField("collection_time")
    private Date collectionTime;
}
