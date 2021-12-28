package me.zhengjie.modules.system.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.service.dto.*;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface RepairApplicationService extends CommonService<RepairApplication> {

    String CACHE_KEY = "RepairApplication";

    /**
     * 查询故障信息
     * @param criteria 条件
     * @param pageable 分页插件
     * @return PageInfo<RepairApplicationDetailsDto>
     */
    PageInfo<RepairApplicationDetailsDto> queryAll(RepairApplicationCriteria criteria, Pageable pageable);

    List<RepairApplicationDetailsDto> queryProvideByUserId(Long id);

    void like(String repairId, String type);

    /**
     * 在提交报修故障前进行重复性判断，达到一定阈值则返回对应重复的列表，前端确认无误后，再次提交
     * @param resource RepairApplication
     * @return List<RepairApplication>
     */
    List<RepairApplication> commit(List<MultipartFile> files, RepairApplication resource);

    boolean revoke(String repairId);

    boolean assign(RepairServiceman resource);

    RepairStatistics getRepairStatistics();

    /**
     * 统计用户报修相关信息
     * @return UserStatistics
     */
    UserStatistics statistics();

    /**
     * 查询由我指派的
     * @return List<RepairApplicationAssignToMeDto>
     */
    List<RepairApplicationAssignToMeDto> findAssignByMe();

    boolean deleteAll(Set<String> ids);

    EvaluationStatisticDto getEvaluationStatistics();

}
