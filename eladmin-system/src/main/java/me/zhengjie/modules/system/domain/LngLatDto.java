package me.zhengjie.modules.system.domain;

import lombok.Data;

/**
 * @author hjy
 * @date 2024/5/9 16:02
 * @description:
 **/
@Data
public class LngLatDto {
    private double lng;
    private double lat;
    private String status;
}
