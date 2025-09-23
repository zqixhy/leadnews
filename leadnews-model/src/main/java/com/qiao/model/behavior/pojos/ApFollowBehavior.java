package com.qiao.model.behavior.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ApFollowBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("entry_id")
    private Integer entryId;

    @TableField("article_id")
    private Long articleId;

    @TableField("follow_id")
    private Integer followId;

    @TableField("created_time")
    private Date createdTime;
}
