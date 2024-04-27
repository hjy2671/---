package me.zhengjie.modules.system.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hjy
 * @date 2024/4/5 12:39
 */
@Data
public class RepairAssignBo {

    private Long repairId;

    private Long servicemanId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date expectedProcessingDateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date expectedProcessingDateTo;

}
