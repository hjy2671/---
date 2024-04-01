package me.zhengjie.modules.system.util.status;

import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;

public class Pending extends AbstractRepairStatus{
    public Pending(RepairApplication repair) {
        super(repair);
    }

    @Override
    public RepairStatus pass() {
        repair.setStatus(RepairApplicationStatusEnum.ASSIGNED.code);
        return new Assigned(repair);
    }

    @Override
    public RepairStatus rollback() {
        repair.setStatus(RepairApplicationStatusEnum.REFUSE.code);
        return new Refuse(repair);
    }

    @Override
    public RepairStatus publish() {
        repair.setStatus(RepairApplicationStatusEnum.PUBLISHED.code);
        return new Published(repair);
    }
}
