package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import me.zhengjie.base.CommonModel;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("role_path")
public class RolePath extends CommonModel<RolePath> implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private String path;

}
