package me.zhengjie.modules.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hjy
 * @date 2024/3/31 21:44
 */
@Data
public class RepairApplicationVo {

    private Long id;

    private String lng;

    private String lat;

    private String faultDetails;

    private String faultLocation;

    private String status;

    private Date expectedProcessingDateFrom;

    private Date expectedProcessingDateTo;

    private Date startTime;

    private Date finishTime;

    private Long providerId;

    private String servicemanNick;

    private List<String> pictures;
}
