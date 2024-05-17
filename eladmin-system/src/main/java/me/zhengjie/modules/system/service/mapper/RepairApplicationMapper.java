package me.zhengjie.modules.system.service.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.base.FileInfo;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.LngLatDto;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.vo.RepairApplicationVo;
import me.zhengjie.modules.system.domain.vo.RepairSolvedVo;
import me.zhengjie.modules.system.service.dto.*;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairApplicationMapper extends CommonMapper<RepairApplicationMapper, RepairApplication> {

    /**
     * 查询故障信息
     * @param wrapper 条件
     * @param page 分页插件
     * @return IPage<RepairApplicationDetailsDto>
     */
    IPage<RepairApplicationVo> queryAll(@Param(Constants.WRAPPER) Wrapper<Object> wrapper, IPage<RepairApplicationDetailsDto> page);

    List<RepairApplicationDetailsDto> queryProvideByUserId(Long id);
    /**
     * 维修统计
     * @return 统计结果
     */
    RepairStatistics getRepairStatistics();

    UserStatistics getUserStatistics(Long userId);

    /**
     * 查询由我指派的
     * @return List<RepairApplicationAssignToMeDto>
     */
    List<RepairApplicationVo> findAssignByMe(@Param("userId") Long userId);

    EvaluationStatisticDto getEvaluationStatistics();

    IPage<RepairApplicationVo> getProvideByMe(@Param("userId") Long currentUserId, IPage<RepairApplicationVo> mybatisPage);

    List<FileInfo> getSitePhotosByRepairId(@Param("id") Long repairId, @Param("type") String type);

    IPage<RepairApplicationVo> getPendingList(
            @Param("userId") Long currentUserId,
            IPage<RepairApplicationVo> mybatisPage);

    IPage<RepairSolvedVo> getResolveByMe(@Param("userId") Long currentUserId, IPage<RepairApplicationVo> mybatisPage);

    IPage<RepairApplicationVo> getPublishList(IPage<RepairApplicationVo> mybatisPage);

    List<LngLatDto> getLntLats();

}
