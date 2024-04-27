package me.zhengjie.modules.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhengjie.base.FileInfo;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date expectedProcessingDateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date expectedProcessingDateTo;

    private Date startTime;

    private Date finishTime;

    private Long providerId;

    private String servicemanNick;

    private String evaluation;

    private Integer effectGrade;

    private Integer qualityGrade;


    private List<FileInfo> pictures;
}
