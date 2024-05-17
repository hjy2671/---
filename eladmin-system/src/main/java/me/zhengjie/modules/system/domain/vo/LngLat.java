package me.zhengjie.modules.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LngLat {
    private double[] lnglat;

    public void setLnglat(double lng, double lat) {
        this.lnglat = new double[]{lng, lat};
    }

    public static LngLat build(double lng, double lat) {
        return new LngLat(new double[]{lng, lat});
    }
}
