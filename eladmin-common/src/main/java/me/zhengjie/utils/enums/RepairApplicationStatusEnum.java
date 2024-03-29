package me.zhengjie.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RepairApplicationStatusEnum {

    REFUSE("0", "审核拒绝"),
    BE_REVIEWED("1", "待审核"),
    PUBLISHED("2", "已公布"),
    ASSIGNED("3", "已指派"),
    PROCESSING("4", "处理中"),
    DONE("5", "已完成");
    public final String code;
    public final String desc;

    public static RepairApplicationStatusEnum find(String code){
        for (RepairApplicationStatusEnum value : RepairApplicationStatusEnum.values()) {
            if (value.code.equals(code)){
                return value;
            }
        }
        throw new RuntimeException("no status:" + code);
    }

}
