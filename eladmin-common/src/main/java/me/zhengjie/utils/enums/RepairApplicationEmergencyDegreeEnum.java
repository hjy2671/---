package me.zhengjie.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RepairApplicationEmergencyDegreeEnum {

    general("0", "一般"),
    medium("1", "中等"),
    urgent("2", "紧急"),
    other("", "其他");

    private String code;
    private String desc;

    public static RepairApplicationEmergencyDegreeEnum find(String code){
        for (RepairApplicationEmergencyDegreeEnum value : RepairApplicationEmergencyDegreeEnum.values()) {
            if (value.code.equals(code)){
                return value;
            }
        }
        return other;
    }


}
