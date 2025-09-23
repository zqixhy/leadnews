package com.qiao.model.admin.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdUserDto {

    @NotNull
    @Schema(description = "name",example = "admin")
    private String name;
    @NotNull
    private String password;
}
