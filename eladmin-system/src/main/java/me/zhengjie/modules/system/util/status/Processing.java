package me.zhengjie.modules.system.util.status;

import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;

public class Processing extends AbstractRepairStatus{
    public Processing(RepairApplication repair) {
        super(repair);
    }

    @Override
    public RepairStatus pass() {
        repair.setStatus(RepairApplicationStatusEnum.COMPLETED.code);
        return new Completed(repair);
    }
}
