package me.zhengjie.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LikeOrNotTypeEnum {

    like("0", "赞"),
    not("1", "踩"),
    other("", "其他");

    private String code;
    private String desc;

    public static LikeOrNotTypeEnum find(String code){
        for (LikeOrNotTypeEnum value : LikeOrNotTypeEnum.values()) {
            if (value.code.equals(code)){
                return value;
            }
        }
        return other;
    }

}
