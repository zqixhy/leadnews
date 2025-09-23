package com.qiao.model.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @NotNull
    @Schema(description = "phone number",example = "13000000000")
    private String phone;
    @NotNull
    private String password;
}
