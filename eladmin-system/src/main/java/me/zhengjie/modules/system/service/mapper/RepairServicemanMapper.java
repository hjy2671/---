package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.vo.RepairApplicationVo;
import me.zhengjie.modules.system.domain.vo.ServicemanSimple;
import me.zhengjie.modules.system.service.dto.RepairApplicationAssignToMeDto;
import me.zhengjie.modules.system.service.dto.ServiceManTaskStatistics;
import me.zhengjie.modules.system.service.dto.SimpleUserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairServicemanMapper extends CommonMapper<RepairServicemanMapper, RepairServiceman> {

    ServiceManTaskStatistics statisticsTask(Long userId);

    String getTopOneLikesProviderNickname();

    String getTopOneLikesServicemanNickname();

    List<RepairApplicationVo> findAssignToMe(Long userId);

    List<SimpleUserDto> findUserByRole(Integer role);

    List<ServicemanSimple> getSimpleServiceman(@Param("userId") Long currentUserId);

    Long getOptimalMatchingServicemanId(@Param("userId") Long currentUserId);
}
