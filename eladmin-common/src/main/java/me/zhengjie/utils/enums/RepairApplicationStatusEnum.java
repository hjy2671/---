package me.zhengjie.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RepairApplicationStatusEnum {

    val1("0", "待处理"),
    val2("1", "已指派"),
    val3("2", "处理中"),
    val4("3", "已完成"),
    other("", "其他");

    private String code;
    private String desc;

    public static RepairApplicationStatusEnum find(String code){
        for (RepairApplicationStatusEnum value : RepairApplicationStatusEnum.values()) {
            if (value.code.equals(code)){
                return value;
            }
        }
        return other;
    }

}
