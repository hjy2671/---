package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("repair_and_oss")
public class RepairAndOss {

    @TableId(type= IdType.ASSIGN_ID)
    private String id;
    private String ossId;
    private String repairApplicationId;
    private String type;

}
