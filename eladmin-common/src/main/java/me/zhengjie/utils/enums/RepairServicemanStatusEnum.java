package me.zhengjie.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RepairServicemanStatusEnum {

    val1("0", "待接收"),
    val2("1", "已接收"),
    val3("2", "已拒绝"),
    other("", "其他");

    private String code;
    private String desc;

    public static RepairServicemanStatusEnum find(String code){
        for (RepairServicemanStatusEnum value : RepairServicemanStatusEnum.values()) {
            if (value.code.equals(code)){
                return value;
            }
        }
        return other;
    }

}
