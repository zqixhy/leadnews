package com.qiao.model.schedule.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author itheima
 */
@Data
@TableName("taskinfo")
public class Taskinfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
    private Long taskId;

    @TableField("execute_time")
    private Date executeTime;

    @TableField("parameters")
    private byte[] parameters;

    @TableField("priority")
    private Integer priority;

    @TableField("task_type")
    private Integer taskType;


}
