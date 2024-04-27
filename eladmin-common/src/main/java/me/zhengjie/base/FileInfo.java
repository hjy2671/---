package me.zhengjie.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hjy
 * @date 2024/3/3 21:04
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("oss")
public class FileInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long ossId;

    private String filename;//处理过后的名字，包括存储路径什么的

    private String originalName;//文件原本的名字

    private String url;//文件的url

    private Date createTime;

}
