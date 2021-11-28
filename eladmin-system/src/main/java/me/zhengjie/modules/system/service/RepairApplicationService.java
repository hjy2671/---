package me.zhengjie.modules.system.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.service.dto.RepairApplicationDetailsDto;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RepairApplicationService extends CommonService<RepairApplication> {

    String CACHE_KEY = "RepairApplication";

    /**
     * 查询故障信息
     * @param criteria 条件
     * @param pageable 分页插件
     * @return PageInfo<RepairApplicationDetailsDto>
     */
    PageInfo<RepairApplicationDetailsDto> queryAll(RepairApplicationCriteria criteria, Pageable pageable);

    void like(Long repairId, String type);

    /**
     * 在提交报修故障前进行重复性判断，达到一定阈值则返回对应重复的列表，前端确认无误后，再次提交
     * @param resource RepairApplication
     * @return List<RepairApplication>
     */
    List<RepairApplication> commit(RepairApplication resource);

    boolean revoke(Long repairId);

    boolean assign(RepairServiceman resource);
}
