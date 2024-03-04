package me.zhengjie.base;

import lombok.Builder;
import lombok.Data;
import me.zhengjie.utils.SecurityUtils;

import java.util.UUID;

/**
 * @author hjy
 * @date 2024/3/3 21:04
 */
@Data
public class FileInfo {

    private String filename;//处理过后的名字，包括存储路径什么的

    private String originalName;//文件原本的名字

    private String url;//文件的类型

    private String type;




}
