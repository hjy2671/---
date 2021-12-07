package me.zhengjie.utils.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AcceptFileTypeEnum {

    jpg("jpg", "jpg"),
    png("png", "png"),
    jpeg("jpeg", "jpeg"),
    gif("gif", "gif"),
    bmp("bmp", "bmp");

    private String code;
    private String desc;

    public static boolean accept(String code){
        for (AcceptFileTypeEnum value : AcceptFileTypeEnum.values()) {
            if (value.code.equals(code)){
                return true;
            }
        }
        return false;
    }


}
