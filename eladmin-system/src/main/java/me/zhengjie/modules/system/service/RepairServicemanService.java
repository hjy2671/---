package me.zhengjie.modules.system.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.vo.RepairApplicationVo;
import me.zhengjie.modules.system.domain.vo.ServicemanSimple;
import me.zhengjie.modules.system.service.dto.RepairApplicationAssignToMeDto;
import me.zhengjie.modules.system.service.dto.RepairApplicationDetailsDto;
import me.zhengjie.modules.system.service.dto.ServiceManTaskStatistics;
import me.zhengjie.modules.system.service.dto.SimpleUserDto;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import me.zhengjie.modules.system.service.dto.criteria.RepairServicemanCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RepairServicemanService extends CommonService<RepairServiceman> {

    String CACHE_KEY = "RepairServiceman";

    PageInfo<RepairServiceman> queryAll(RepairServicemanCriteria criteria, Pageable pageable);

    ServiceManTaskStatistics statisticsTask(Long userId);

    boolean accept(Long repairId);

    boolean refuse(RepairServiceman resource);

    Boolean finish(Long repairId, MultipartFile[] files);

    List<RepairApplicationVo> findAssignToMe(Long userId);

    List<SimpleUserDto> findUserByRole(Integer role);

    List<ServicemanSimple> simpleList();
}
