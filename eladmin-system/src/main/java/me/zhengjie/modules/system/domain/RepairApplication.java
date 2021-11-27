package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import me.zhengjie.base.CommonEntity;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_repair_application")
public class RepairApplication extends CommonEntity<RepairApplication> implements Serializable {

    @TableId(type= IdType.AUTO)
    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @ApiModelProperty(value = "故障详情")
    private String faultDetails;

    @ApiModelProperty(value = "故障位置")
    private String faultLocation;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "紧急程度")
    private String emergencyDegree;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "发现时间")
    private Date foundTime;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

    @ApiModelProperty(value = "提供者id")
    private Long providerId;

}
