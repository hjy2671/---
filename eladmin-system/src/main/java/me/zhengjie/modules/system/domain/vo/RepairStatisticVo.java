package me.zhengjie.modules.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hjy
 * @date 2024/5/9 15:47
 * @description:
 **/
@Data
public class RepairStatisticVo {

    private double[] center;

    private List<LngLat> lngLats;

    @ApiModelProperty(value = "处理中数量")
    private Integer processingCount;

    @ApiModelProperty(value = "已完成数量")
    private Integer completedCount;

    @ApiModelProperty(value = "待审核数量")
    private Integer pendingAuditCount;

    @ApiModelProperty(value = "待处理数量")
    private Integer pendingProcessingCount;

}

