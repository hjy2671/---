package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import me.zhengjie.base.CommonEntity;
import me.zhengjie.utils.enums.RepairServicemanStatusEnum;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_repair_serviceman")
public class RepairServiceman extends CommonEntity<RepairServiceman> implements Serializable {

    @TableId(type= IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID", hidden = true)
    private String id;

    @ApiModelProperty(value = "报修id")
    private Long repairId;

    @ApiModelProperty(value = "维修人员id")
    private Integer servicemanId;

    @ApiModelProperty(value = "状态")
    private String status;

    @TableField(exist = false)
    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "拒绝原因")
    private String refuseReason;

    public void setStatus(String status) {
        this.status = status;
        this.statusName = RepairServicemanStatusEnum.find(status).getDesc();
    }
}
