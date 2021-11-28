package me.zhengjie.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.system.domain.LikeOrNot;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.service.LikeOrNotService;
import me.zhengjie.modules.system.service.RepairServicemanService;
import me.zhengjie.modules.system.service.dto.RepairApplicationDetailsDto;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import me.zhengjie.modules.system.service.mapper.LikeOrNotMapper;
import me.zhengjie.modules.system.service.mapper.RepairApplicationMapper;
import me.zhengjie.modules.system.service.RepairApplicationService;
import me.zhengjie.utils.*;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;
import me.zhengjie.utils.enums.RepairServicemanStatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RepairApplicationServiceImpl extends CommonServiceImpl<RepairApplicationMapper, RepairApplication> implements RepairApplicationService {

    private final RepairApplicationMapper repairApplicationMapper;
    private final LikeOrNotService likeOrNotService;
    private final RedisUtils redisUtils;
    private final RepairServicemanService repairServicemanService;

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
    public void like(Long repairId, String type) {


        final LikeOrNot like = likeOrNotService.getOne(new QueryWrapper<LikeOrNot>()
                .eq("repair_id", repairId)
                .eq("user_id", SecurityUtils.getCurrentUserId()));

        if (like != null){
            likeOrNotService.removeById(like);
        }else {
            final LikeOrNot likeOrNot = new LikeOrNot();
            likeOrNot.setRepairId(repairId);
            likeOrNot.setType(type);
            likeOrNotService.save(likeOrNot);
        }

    }

    @Override
    public List<RepairApplication> commit(RepairApplication resource) {

        final List<RepairApplication> similar;

        if (redisUtils.get(resource.getFaultDetails()) == null && (similar = getSimilar(resource)).size() > 0 ){
            redisUtils.set(resource.getFaultDetails(), "1", Duration.ofMinutes(5).toMillis());
            return similar;
        }else {
            save(resource);
        }
        return null;
    }

    @Override
    public boolean revoke(Long repairId) {
        return remove(new QueryWrapper<RepairApplication>().eq("status", RepairApplicationStatusEnum.val1.getCode()).eq("repair_id", repairId));
    }

    @Override
    public boolean assign(RepairServiceman resource) {
        final RepairApplication application = new RepairApplication();
        application.setId(resource.getRepairId());
        application.setStatus(RepairApplicationStatusEnum.val2.getCode());
        updateById(application);
        return repairServicemanService.save(resource);
    }

    public List<RepairApplication> getSimilar(RepairApplication target){
        return list(new QueryWrapper<RepairApplication>().in("status", 0,1,2)).stream().filter(r -> StringUtils.getSimilarityRatio(r.getFaultDetails(), target.getFaultDetails()) > 70
                && StringUtils.getSimilarityRatio(r.getFaultLocation(), target.getFaultLocation()) > 85).collect(Collectors.toList());
    }


}
