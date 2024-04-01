package me.zhengjie.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.FileInfo;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.config.FileProperties;
import me.zhengjie.modules.system.domain.*;
import me.zhengjie.modules.system.domain.vo.RepairApplicationVo;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.modules.system.service.LikeOrNotService;
import me.zhengjie.modules.system.service.RepairServicemanService;
import me.zhengjie.modules.system.service.dto.*;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import me.zhengjie.modules.system.service.mapper.RepairAndOssMapper;
import me.zhengjie.modules.system.service.mapper.RepairApplicationMapper;
import me.zhengjie.modules.system.service.RepairApplicationService;
import me.zhengjie.modules.system.service.mapper.RepairServicemanMapper;
import me.zhengjie.utils.*;
import me.zhengjie.utils.enums.RepairAndOssEnum;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RepairApplicationServiceImpl extends CommonServiceImpl<RepairApplicationMapper, RepairApplication> implements RepairApplicationService {

    private final RepairApplicationMapper repairApplicationMapper;
    private final RepairServicemanMapper repairServicemanMapper;
    private final LikeOrNotService likeOrNotService;
    private final RedisUtils redisUtils;
    private final RepairServicemanService repairServicemanService;
    private final FileProperties properties;
    private final FileService fileService;
    private final RepairAndOssMapper repairAndOssMapper;

    @Override
    public PageInfo<RepairApplicationDetailsDto> queryAll(RepairApplicationCriteria criteria, Pageable pageable) {

        if (criteria.getDate() != null){
            criteria.setFrom(DateUtil.format(DateUtil.getFirstDayOfMonthDate(criteria.getDate())));
            criteria.setTo(DateUtil.format(DateUtil.getLastDayOfMonthDate(criteria.getDate())));
        }
        final IPage<RepairApplicationDetailsDto> page = repairApplicationMapper.queryAll(QueryHelpMybatisPlus.getPredicate(criteria), PageUtil.toMybatisPage(pageable));

        return ConvertUtil.convertPage(page);
    }

    @Override
    public PageInfo<RepairApplicationVo> getProvideByMe(Long currentUserId, Pageable pageable) {
        PageInfo<RepairApplicationVo> repairApplicationVoPageInfo = ConvertUtil.convertPage(repairApplicationMapper.getProvideByMe(currentUserId, PageUtil.toMybatisPage(pageable)));
        return repairApplicationVoPageInfo;
    }

    @Override
    public List<FileInfo> getSitePhotos(Long repairId) {
        return repairApplicationMapper.getSitePhotosByRepairId(repairId, RepairAndOssEnum.SCENE.code);
    }

    @Override
    public List<RepairApplicationDetailsDto> queryProvideByUserId(Long id) {
        final List<RepairApplicationDetailsDto> list = repairApplicationMapper.queryProvideByUserId(id);
        list.forEach(r -> {
            if (r.getAcceptStatus() == null) {
                r.setAcceptStatus("-1");
            }
        });
        return list;
    }

    @Override
    public void like(String repairId, String type) {
        final Long userId = SecurityUtils.getCurrentUserId();

        final LikeOrNot like = likeOrNotService.getOne(new QueryWrapper<LikeOrNot>()
                .eq("repair_id", repairId)
                .eq("user_id", userId));

        if (like != null){
            if (!like.getType().equals(type)){
                like.setType(type);
                likeOrNotService.updateById(like);
            }else {
                likeOrNotService.removeById(like);
            }

        }else {
            likeOrNotService.save(createLike(userId, repairId, type));
        }
    }

    private LikeOrNot createLike(Long userId, String repairId, String type){
        final LikeOrNot likeOrNot = new LikeOrNot();
        likeOrNot.setUserId(userId);
        likeOrNot.setRepairId(repairId);
        likeOrNot.setType(type);
        return likeOrNot;
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

    @Transactional
    @Override
    public boolean assign(RepairServiceman resource) {
        final RepairApplication application = new RepairApplication();
        application.setId(resource.getRepairId());
        updateById(application);
        resource.setAppointerId(SecurityUtils.getCurrentUserId());
        return repairServicemanService.save(resource);
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
    public List<RepairApplicationAssignToMeDto> findAssignByMe() {
        return repairApplicationMapper.findAsassignByMe(SecurityUtils.getCurrentUserId());
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
