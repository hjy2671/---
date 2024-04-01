package me.zhengjie.modules.system.util.status;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.system.domain.RepairApplication;

public abstract class AbstractRepairStatus implements RepairStatus{

    protected final RepairApplication repair;
    protected static final RepairStatusChangedException CHANGED_EXCEPTION = new RepairStatusChangedException("当前维修状态不可变");

    public AbstractRepairStatus(RepairApplication repair) {
        this.repair = repair;
    }

    @Override
    public RepairStatus pass() {
        throw CHANGED_EXCEPTION;
    }

    @Override
    public RepairStatus rollback() {
        throw CHANGED_EXCEPTION;
    }

    @Override
    public RepairStatus publish() {
        throw  CHANGED_EXCEPTION;
    }

    @Override
    public boolean save(BaseMapper mapper) {
        return mapper.updateById(repair) > 0;
    }
}
