package me.zhengjie.modules.system.util.status;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.system.domain.RepairApplication;

public interface RepairStatus {

    String REFUSE = "0";
    String PENDING = "1";
    String PUBLISHED = "2";
    String ASSIGNED = "3";
    String PROCESSING = "4";
    String COMPLETED = "5";

    RepairStatus pass();

    RepairStatus rollback();

    RepairStatus publish();


    boolean save(BaseMapper<RepairApplication> mapper);

}
