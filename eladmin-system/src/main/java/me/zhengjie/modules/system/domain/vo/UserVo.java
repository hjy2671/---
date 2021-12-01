package me.zhengjie.modules.system.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserVo {

    @NotBlank
    private String username;

    @NotBlank
    private String nickName;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    @NotBlank
    private String code;

    @NotBlank
    private String rePassword;

}
