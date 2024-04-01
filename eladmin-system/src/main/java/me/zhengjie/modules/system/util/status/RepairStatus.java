package me.zhengjie.modules.system.util.status;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface RepairStatus<T> {

    RepairStatus<T> pass();

    RepairStatus<T> rollback();

    RepairStatus<T> publish();


    boolean save(BaseMapper<T> mapper);

}
