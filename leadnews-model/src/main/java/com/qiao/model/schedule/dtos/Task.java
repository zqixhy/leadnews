package com.qiao.model.schedule.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class Task implements Serializable {


    private Long taskId;

    private Integer taskType;

    private Integer priority;

    private long executeTime;

    private byte[] parameters;
    
}