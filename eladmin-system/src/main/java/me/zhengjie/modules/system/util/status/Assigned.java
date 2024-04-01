package me.zhengjie.modules.system.util.status;

import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;

public class Assigned extends AbstractRepairStatus{

    public Assigned(RepairApplication repair) {
        super(repair);
    }

    @Override
    public RepairStatus pass() {
        repair.setStatus(RepairApplicationStatusEnum.PROCESSING.code);
        return new Processing(repair);
    }

    @Override
    public RepairStatus rollback() {
        repair.setStatus(RepairApplicationStatusEnum.PENDING.code);
        return new Pending(repair);
    }
}
