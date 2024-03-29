package me.zhengjie.base;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import me.zhengjie.utils.SecurityUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @author hjy
 * @date 2024/3/3 21:04
 */
@Data
@Builder
@TableName("oss")
public class FileInfo {

    @TableId(type = IdType.ASSIGN_ID)
    private String ossId;

    private String filename;//处理过后的名字，包括存储路径什么的

    private String originalName;//文件原本的名字

    private String url;//文件的url

    private Date createTime;

}
