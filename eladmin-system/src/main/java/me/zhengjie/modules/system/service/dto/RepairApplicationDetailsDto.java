package me.zhengjie.modules.system.service.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.enums.RepairApplicationEmergencyDegreeEnum;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Data
public class RepairApplicationDetailsDto {

    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @ApiModelProperty(value = "故障详情")
    private String faultDetails;

    @ApiModelProperty(value = "故障位置")
    private String faultLocation;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "状态码")
    private String status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "发现时间")
    private Date foundTime;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

    @ApiModelProperty(value = "提供者昵称")
    private String providerNick;

    @ApiModelProperty(value = "维修者昵称")
    private String repairNick;

    @ApiModelProperty(value = "点赞数")
    private Long likes;

    @ApiModelProperty(value = "点踩数")
    private Long notLikes;

    @ApiModelProperty(value = "星级")
    private String grade = "0";

    @ApiModelProperty(value = "提供人员id")
    private Long providerId;

    @ApiModelProperty(value = "维修人员id")
    private Long servicemanId;

    @ApiModelProperty(value = "是否点过赞")
    private boolean hasLike = false;

    @ApiModelProperty(value = "是否点过踩")
    private boolean hasNotLike = false;

    @ApiModelProperty(value = "接受状态")
    private String acceptStatus;

    @ApiModelProperty(value = "评价")
    private String evaluation = "";

    public void setStatus(String status) {
        this.status = status;
        this.statusName = RepairApplicationStatusEnum.find(status).getDesc();
    }
}
