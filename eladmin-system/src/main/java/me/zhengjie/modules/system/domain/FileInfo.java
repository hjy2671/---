package me.zhengjie.modules.system.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author hjy
 * @date 2024/3/3 21:04
 */
@Data
@Builder
public class FileInfo {

    private String filename;//处理过后的名字，包括存储路径什么的

    private String originalName;//文件原本的名字

    private String url;//文件的类型



}
