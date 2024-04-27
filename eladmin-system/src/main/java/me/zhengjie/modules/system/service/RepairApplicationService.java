package me.zhengjie.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.CommonService;
import me.zhengjie.base.FileInfo;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.Evaluation;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.bo.RepairAssignBo;
import me.zhengjie.modules.system.domain.vo.RepairApplicationVo;
import me.zhengjie.modules.system.domain.vo.RepairSolvedVo;
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

    /**
     * 在提交报修故障前进行重复性判断，达到一定阈值则返回对应重复的列表，前端确认无误后，再次提交
     * @param resource RepairApplication
     * @return List<RepairApplication>
     */
    boolean commit(MultipartFile[] files, RepairApplication resource);

    boolean revoke(String repairId);

    boolean assign(RepairAssignBo bo);

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
    List<RepairApplicationVo> findAssignByMe();

    boolean deleteAll(Set<Long> ids);

    EvaluationStatisticDto getEvaluationStatistics();

    PageInfo<RepairApplicationVo> getProvideByMe(Long currentUserId, Pageable pageable);

    List<FileInfo> getSitePhotos(Long repairId);

    List<FileInfo> getResultPhotos(Long repairId);


    PageInfo<RepairApplicationVo> pendingList(Long currentUserId, Pageable pageable);

    boolean rollback(RepairApplication application);

    boolean publish(RepairApplication application);

    PageInfo<RepairSolvedVo> getResolveByMe(Long currentUserId, Pageable pageable);

    void setComment(Evaluation evaluation);
}
