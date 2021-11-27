package me.zhengjie.modules.system.service.dto.criteria;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import me.zhengjie.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RepairApplicationCriteria {

    @ApiParam("选择时间-年月")
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date date;

    @ApiParam(value = "起始时间",hidden = true)
    @Query(type = Query.Type.GREATER_THAN, propName = "finishTime")
    private Date from;

    @ApiParam(value = "结束时间",hidden = true)
    @Query(type = Query.Type.LESS_THAN, propName = "finishTime")
    private Date to;

    @ApiParam(value = "提供者id")
    @Query
    private Long providerId;

}
