package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.service.dto.RepairApplicationAssignToMeDto;
import me.zhengjie.modules.system.service.dto.ServiceManTaskStatistics;
import me.zhengjie.modules.system.service.dto.SimpleUserDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairServicemanMapper extends CommonMapper<RepairServiceman> {

    ServiceManTaskStatistics statisticsTask(Long userId);

    String getTopOneLikesProviderNickname();

    String getTopOneLikesServicemanNickname();

    List<RepairApplicationAssignToMeDto> findAssignToMe(Long userId);

    List<SimpleUserDto> findUserByRole(String role);

}
