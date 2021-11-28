package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.service.dto.ServiceManTaskStatistics;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairServicemanMapper extends CommonMapper<RepairServiceman> {

    ServiceManTaskStatistics statisticsTask(Long userId);

}
