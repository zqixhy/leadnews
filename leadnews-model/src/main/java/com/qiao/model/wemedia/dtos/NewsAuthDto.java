package com.qiao.model.wemedia.dtos;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;

import java.security.PrivateKey;

@Data
public class NewsAuthDto {
    private Integer id;
    private String msg;
    private Integer page;
    private Integer size;
    private Integer status;
    private String title;

    public void checkParam(){
        if(this.page == null || this.page < 0){
            setPage(1);
        }
        if(this.size == null || this.size < 0 || this.size > 100){
            setSize(10);
        }
    }
}
