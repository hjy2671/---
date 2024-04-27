package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("repair_and_oss")
public class RepairAndOss {

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    private Long ossId;
    private Long repairApplicationId;
    private String type;

}
