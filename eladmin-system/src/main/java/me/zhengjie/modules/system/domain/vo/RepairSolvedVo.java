package me.zhengjie.modules.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.zhengjie.base.FileInfo;

import java.util.Date;
import java.util.List;

/**
 * @author hjy
 * @date 2024/3/31 21:44
 */
@Data
public class RepairSolvedVo {

    private Long id;

    private String faultDetails;

    private String faultLocation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date finishTime;

    private String evaluation;

    private Integer effectGrade;

    private Integer qualityGrade;

    private List<FileInfo> before;

    private List<FileInfo> after;
}
