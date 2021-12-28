package me.zhengjie.modules.system.service.dto;

import lombok.Data;

@Data
public class SimpleUserDto {

    private Long userId;

    private String nickname = "";

    private Integer doingNum = 0;

    private Integer pendingNum = 0;

}
