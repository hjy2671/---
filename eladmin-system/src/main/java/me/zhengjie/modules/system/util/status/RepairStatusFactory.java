package me.zhengjie.modules.system.util.status;

import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;

public class RepairStatusFactory {
    public static  RepairStatus create(RepairApplication repair) {
        switch (repair.getStatus()) {
            case RepairStatus.REFUSE:
                return new Refuse(repair);
            case RepairStatus.PENDING:
                return new Pending(repair);
            case RepairStatus.ASSIGNED:
                return new Assigned(repair);
            case RepairStatus.PUBLISHED:
                return new Published(repair);
            case RepairStatus.PROCESSING:
                return new Processing(repair);
            case RepairStatus.COMPLETED:
                return new Completed(repair);
        }

        throw new RuntimeException("no status:" + repair.getStatus());
    }

}
