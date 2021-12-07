package me.zhengjie.modules.system.service.dto;

import lombok.Data;

@Data
public class RepairStatistics {

    private Integer completeNum;

    private Integer repairingNum;

    private String topOneLikesServicemanNickname;

    private String topOneLikesProviderNickname;

}
