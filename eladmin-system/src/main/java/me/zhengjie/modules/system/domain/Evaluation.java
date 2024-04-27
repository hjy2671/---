package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import me.zhengjie.base.CommonEntity;

import java.io.Serializable;

/**
 * @author hjy
 * @date 2024/4/27 14:22
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("evaluation")
public class Evaluation extends CommonEntity<RepairApplication> implements Serializable {

    @TableId(type= IdType.AUTO)
    private Long id;

    private Long applicationId;

    private Integer evaluation;

    private Integer effectGrade;

    private String qualityGrade;



}
