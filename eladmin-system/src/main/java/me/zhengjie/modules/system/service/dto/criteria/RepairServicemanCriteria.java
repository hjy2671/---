package me.zhengjie.modules.system.service.dto.criteria;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import me.zhengjie.annotation.Query;

@Data
public class RepairServicemanCriteria {

    @ApiParam("状态")
    @Query
    private String status;

}
