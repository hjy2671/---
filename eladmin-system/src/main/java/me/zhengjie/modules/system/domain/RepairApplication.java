package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import me.zhengjie.base.CommonEntity;
import me.zhengjie.modules.system.domain.bo.AddGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("sys_repair_application")
public class RepairApplication extends CommonEntity<RepairApplication> implements Serializable {

    @TableId(type= IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @NotNull(groups = {AddGroup.class}, message = "报修位置不能为空")
    private String lng;

    @NotNull(groups = {AddGroup.class}, message = "报修位置不能为空")
    private String lat;

    @ApiModelProperty(value = "故障详情")
    @NotNull(groups = {AddGroup.class}, message = "问题描述不能为空")
    private String faultDetails;

    @ApiModelProperty(value = "故障位置")
    @NotNull(groups = {AddGroup.class}, message = "详细地址不能为空")
    private String faultLocation;

    @ApiModelProperty(value = "状态(0审核拒绝，1待审核，2已公布，3已指派，4处理中，5已完成)")
    private String status;

    private Date expectedProcessingDateFrom;

    private Date expectedProcessingDateTo;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date finishTime;

    @ApiModelProperty(value = "提供者id")
    private Long providerId;

}
