package me.zhengjie.modules.system.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserResetVo {

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    @NotBlank
    private String code;

}
