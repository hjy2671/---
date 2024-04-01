package me.zhengjie.modules.system.util.status;

import me.zhengjie.utils.enums.RepairApplicationStatusEnum;

public class RepairStatusFactory {
    public static <T> RepairStatus<T> create(Class<T> type, String key) {
        if (RepairApplicationStatusEnum.REFUSE.code.equals(key))
            return null;


        throw new RuntimeException("no status:" + key);
    }

}
