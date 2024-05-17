package me.zhengjie.modules.system.service.dto.criteria;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import me.zhengjie.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RepairApplicationCriteria {

    @ApiParam("选择时间-年月")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ApiParam(value = "起始时间",hidden = true)
    @Query(type = Query.Type.GREATER_THAN, propName = "finishTime")
    private String from;

    @ApiParam(value = "结束时间",hidden = true)
    @Query(type = Query.Type.LESS_THAN, propName = "finishTime")
    private String to;

    @ApiParam(value = "状态")
    @Query(propName = "ra.status")
    private String status;

    @ApiParam(value = "状态不等于")
    @Query(propName = "ra.status", type = Query.Type.NOT_EQUAL )
    private String raStatus;

    @ApiParam(value = "接受状态")
    @Query(propName = "rs.status", type = Query.Type.NOT_EQUAL)
    private String rsStatus = "2";

}
