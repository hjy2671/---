package me.zhengjie.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    val1("0", "空闲"),
    val2("1", "忙碌"),
    other("", "其他");

    private String code;
    private String desc;

    public static UserStatusEnum find(String code){
        for (UserStatusEnum value : UserStatusEnum.values()) {
            if (value.code.equals(code)){
                return value;
            }
        }
        return other;
    }

}
