package me.zhengjie.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import me.zhengjie.base.FileInfo;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.config.FileProperties;
import me.zhengjie.modules.system.domain.*;
import me.zhengjie.modules.system.domain.bo.RepairAssignBo;
import me.zhengjie.modules.system.domain.vo.LngLat;
import me.zhengjie.modules.system.domain.vo.RepairApplicationVo;
import me.zhengjie.modules.system.domain.vo.RepairSolvedVo;
import me.zhengjie.modules.system.domain.vo.RepairStatisticVo;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.modules.system.service.LikeOrNotService;
import me.zhengjie.modules.system.service.RepairServicemanService;
import me.zhengjie.modules.system.service.dto.*;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import me.zhengjie.modules.system.service.mapper.EvaluationMapper;
import me.zhengjie.modules.system.service.mapper.RepairAndOssMapper;
import me.zhengjie.modules.system.service.mapper.RepairApplicationMapper;
import me.zhengjie.modules.system.service.RepairApplicationService;
import me.zhengjie.modules.system.service.mapper.RepairServicemanMapper;
import me.zhengjie.modules.system.util.status.RepairStatus;
import me.zhengjie.modules.system.util.status.RepairStatusFactory;
import me.zhengjie.utils.*;
import me.zhengjie.utils.enums.RepairAndOssEnum;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RepairApplicationServiceImpl extends CommonServiceImpl<RepairApplicationMapper, RepairApplication> implements RepairApplicationService {

    private final RepairServicemanService repairServicemanService;
    private final FileService fileService;

    private final RepairAndOssMapper repairAndOssMapper;
    private final EvaluationMapper evaluationMapper;
    private final RepairApplicationMapper repairApplicationMapper;
    private final RepairServicemanMapper repairServicemanMapper;

    @Override
    public PageInfo<RepairApplicationVo> queryAll(RepairApplicationCriteria criteria, Pageable pageable) {

        if (criteria.getDate() != null){
            criteria.setFrom(DateUtil.format(DateUtil.getFirstDayOfMonthDate(criteria.getDate())));
            criteria.setTo(DateUtil.format(DateUtil.getLastDayOfMonthDate(criteria.getDate())));
        }
        final IPage<RepairApplicationVo> page = repairApplicationMapper.queryAll(QueryHelpMybatisPlus.getPredicate(criteria), PageUtil.toMybatisPage(pageable));

        return ConvertUtil.convertPage(page);
    }

    @Override
    public PageInfo<RepairApplicationVo> getProvideByMe(Long currentUserId, Pageable pageable) {
        return ConvertUtil.convertPage(repairApplicationMapper.getProvideByMe(currentUserId, PageUtil.toMybatisPage(pageable)));
    }

    @Override
    public List<FileInfo> getSitePhotos(Long repairId) {
        return repairApplicationMapper.getSitePhotosByRepairId(repairId, RepairAndOssEnum.SCENE.code);
    }

    @Override
    public List<FileInfo> getResultPhotos(Long repairId) {
        return repairApplicationMapper.getSitePhotosByRepairId(repairId, RepairAndOssEnum.RECEIPT.code);
    }

    @Override
    public PageInfo<RepairApplicationVo> pendingList(Long currentUserId, Pageable pageable) {
        IPage<RepairApplicationVo> page = repairApplicationMapper.getPendingList(currentUserId, PageUtil.toMybatisPage(pageable));
        return PageInfo.of(page);
    }

    @Override
    public PageInfo<RepairApplicationVo> getPublish(Pageable page) {
        IPage<RepairApplicationVo> r = repairApplicationMapper.getPublishList(PageUtil.toMybatisPage(page));
        return PageInfo.of(r);
    }

    @Override
    public boolean rollback(RepairApplication application) {
        return RepairStatusFactory.create(application).rollback().save(repairApplicationMapper);
    }

    @Override
    public boolean publish(RepairApplication application) {
        return RepairStatusFactory.create(application).publish().save(repairApplicationMapper);
    }

    @Override
    public PageInfo<RepairSolvedVo> getResolveByMe(Long currentUserId, Pageable pageable) {
        return PageInfo.of(
                repairApplicationMapper.getResolveByMe(currentUserId, PageUtil.toMybatisPage(pageable))
        );
    }

    @Override
    public void setComment(Evaluation evaluation) {
        evaluationMapper.insert(evaluation);
    }

    @Override
    public RepairStatisticVo statistic() {
        List<LngLatDto> repairs = repairApplicationMapper.getLntLats();
        RepairStatisticVo result = new RepairStatisticVo();

        int processingCount = 0;
        int completedCount = 0;
        int pendingAuditCount = 0;
        int pendingProcessingCount = 0;

        double maxLng = 0;
        double maxLat = 0;

        double minLng = Double.MAX_VALUE;
        double minLat = Double.MAX_VALUE;

        List<LngLat> lngLats = new ArrayList<>();

        for (LngLatDto r : repairs) {
            if (RepairStatus.REFUSE.equals(r.getStatus()))
                continue;
            switch (r.getStatus()) {
                case RepairStatus.PENDING:
                    pendingAuditCount++;
                    break;
                case RepairStatus.PROCESSING:
                    processingCount++;
                    break;
                case RepairStatus.COMPLETED:
                    completedCount++;
                    break;
                case RepairStatus.ASSIGNED:
                    pendingProcessingCount++;
                    break;
            }

            if (r.getLng() > maxLng)
                maxLng = r.getLng();

            if (r.getLat() > maxLat)
                maxLat = r.getLat();

            if (r.getLng() < minLng)
                minLng = r.getLng();

            if (r.getLat() < minLat)
                minLat = r.getLat();

            lngLats.add(LngLat.build(r.getLng(), r.getLat()));
        }


        double[] center = new double[]{
                BigDecimal.valueOf(minLng).add(BigDecimal.valueOf(maxLng)).divide(BigDecimal.valueOf(2), 6, RoundingMode.HALF_UP).doubleValue(),
                BigDecimal.valueOf(minLat).add(BigDecimal.valueOf(maxLat)).divide(BigDecimal.valueOf(2), 6, RoundingMode.HALF_UP).doubleValue()
        };

        result.setCenter(center);
        result.setLngLats(lngLats);
        result.setCompletedCount(completedCount);
        result.setProcessingCount(processingCount);
        result.setPendingAuditCount(pendingAuditCount);
        result.setPendingProcessingCount(pendingProcessingCount);

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean commit(MultipartFile[] files, RepairApplication resource) {

        resource.setStatus(RepairApplicationStatusEnum.PENDING.code);
        resource.setProviderId(SecurityUtils.getCurrentUserId());
        if (repairApplicationMapper.insert(resource) == 0) {
            return false;
        }
        List<FileInfo> fileInfos = fileService.upload(files, "repair-application");

        List<RepairAndOss> list = fileInfos.stream().map(f -> {
            RepairAndOss repairAndOss = new RepairAndOss();
            repairAndOss.setRepairApplicationId(resource.getId());
            repairAndOss.setOssId(f.getOssId());
            repairAndOss.setType(RepairAndOssEnum.SCENE.code);
            return repairAndOss;
        }).collect(Collectors.toList());

        if (repairAndOssMapper.insertBatch(list)) {
            return true;
        }

        throw new RuntimeException("报修失败");
    }

    public List<RepairApplication> getSimilar(RepairApplication target){
        return list(new QueryWrapper<RepairApplication>().notIn("status", "3"))
                .stream()
                .filter(r -> StringUtils.getSimilarityRatio(r.getFaultDetails(), target.getFaultDetails()) > 60
                && StringUtils.getSimilarityRatio(r.getFaultLocation(), target.getFaultLocation()) > 60).collect(Collectors.toList());
    }

    @Override
    public boolean revoke(String repairId) {
        return remove(new QueryWrapper<RepairApplication>().eq("status", RepairApplicationStatusEnum.PENDING.getCode()).eq("repair_id", repairId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean assign(RepairAssignBo bo) {
        final RepairApplication application = new RepairApplication();
        application.setId(bo.getRepairId());
        application.setStatus(RepairApplicationStatusEnum.PENDING.getCode());
        application.setExpectedProcessingDateFrom(bo.getExpectedProcessingDateFrom());
        application.setExpectedProcessingDateTo(bo.getExpectedProcessingDateTo());

        RepairStatusFactory.create(application).pass().save(repairApplicationMapper);

        RepairServiceman serviceman = new RepairServiceman();
        serviceman.setAppointerId(SecurityUtils.getCurrentUserId());
        serviceman.setRepairId(bo.getRepairId());
        serviceman.setServicemanId(getOptimalMatchingServicemanId(bo.getServicemanId()));

        return repairServicemanService.save(serviceman);
    }

    /**
     * 获取最佳匹配的维修人员id
     */
    private Long getOptimalMatchingServicemanId(Long repairId) {
        if (repairId != null)
            return repairId;

        Long id =  repairServicemanMapper.getOptimalMatchingServicemanId(SecurityUtils.getCurrentUserId());

        if (id == null) {
            throw new RuntimeException("无维修人员可以指派，请先添加维修人员");
        }

        return id;
    }

    @Override
    public RepairStatistics getRepairStatistics() {
        final RepairStatistics result = repairApplicationMapper.getRepairStatistics();
        result.setTopOneLikesServicemanNickname(repairServicemanMapper.getTopOneLikesServicemanNickname());
        result.setTopOneLikesProviderNickname(repairServicemanMapper.getTopOneLikesProviderNickname());
        return result;
    }

    @Override
    public UserStatistics statistics() {
        return  repairApplicationMapper.getUserStatistics(SecurityUtils.getCurrentUserId());
    }

    @Override
    public List<RepairApplicationVo> findAssignByMe() {
        return repairApplicationMapper.findAssignByMe(SecurityUtils.getCurrentUserId());
    }

    @Override
    public boolean deleteAll(Set<Long> ids) {

        final List<RepairApplication> list =
                list(new LambdaQueryWrapper<RepairApplication>()
                        .in(RepairApplication::getId, ids));

        boolean result = deleteFilesByRepairIds(list.stream().map(RepairApplication::getId).collect(Collectors.toSet()));
        if (result && this.removeByIds(ids))
            return true;

        throw new RuntimeException("删除失败");
    }

    private boolean deleteFilesByRepairIds(Set<Long> ids) {
        return repairAndOssMapper.delete(new LambdaQueryWrapper<RepairAndOss>().in(RepairAndOss::getRepairApplicationId, ids)) > 0;
    }

    @Override
    public EvaluationStatisticDto getEvaluationStatistics() {
        return repairApplicationMapper.getEvaluationStatistics();
    }



}
