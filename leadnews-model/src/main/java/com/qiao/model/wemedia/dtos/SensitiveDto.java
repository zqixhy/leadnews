package com.qiao.model.wemedia.dtos;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;

@Data
public class SensitiveDto {
    private String name;
    private Integer page;
    private Integer size;

    public void checkParam(){
        if(this.page == null || this.page < 0){
            setPage(1);
        }
        if(this.size == null || this.size < 0 || this.size > 100){
            setSize(10);
        }
    }
}
