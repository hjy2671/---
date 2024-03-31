package me.zhengjie.modules.system.service.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Data
public class RepairApplicationAssignToMeDto {

    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @ApiModelProperty(value = "servicemanId", hidden = true)
    private Long repairServicemanId;

    @ApiModelProperty(value = "故障详情")
    private String faultDetails;

    @ApiModelProperty(value = "故障位置")
    private String faultLocation;

    @ApiModelProperty(value = "拒绝原因")
    private String refuseReason;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "紧急程度码")
    private String emergencyDegree;

    @ApiModelProperty(value = "维修状态码")
    private String repairStatus;

    @ApiModelProperty(value = "接受状态码")
    private String acceptStatus;

    @ApiModelProperty(value = "发现时间")
    private Date foundTime;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

}
