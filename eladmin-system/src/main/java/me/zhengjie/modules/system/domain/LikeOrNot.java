package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import me.zhengjie.base.CommonEntity;
import me.zhengjie.utils.enums.LikeOrNotTypeEnum;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_like_or_not")
public class LikeOrNot extends CommonEntity<LikeOrNot> implements Serializable {

    @TableId(type= IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID", hidden = true)
    private String id;

    @ApiModelProperty(value = "报修id")
    private String repairId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "类型")
    private String type;

    @TableField(exist = false)
    @ApiModelProperty(value = "类型名称")
    private String typeName;

    public void setType(String type) {
        this.type = type;
        this.typeName = LikeOrNotTypeEnum.find(type).getDesc();
    }
}
