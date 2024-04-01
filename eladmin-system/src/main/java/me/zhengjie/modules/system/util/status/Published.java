package me.zhengjie.modules.system.util.status;

import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;

public class Published extends AbstractRepairStatus{
    public Published(RepairApplication repair) {
        super(repair);
    }

    @Override
    public RepairStatus pass() {
        repair.setStatus(RepairApplicationStatusEnum.ASSIGNED.code);
        return new Assigned(repair);
    }

    @Override
    public RepairStatus rollback() {
        repair.setStatus(RepairApplicationStatusEnum.PENDING.code);
        return new Pending(repair);
    }
}
