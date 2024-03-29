package me.zhengjie.utils.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RepairAndOssEnum {
    SCENE("0", "现场照片"),
    RECEIPT("1", "回执照片");
    public final String code;
    public final String desc;
}
