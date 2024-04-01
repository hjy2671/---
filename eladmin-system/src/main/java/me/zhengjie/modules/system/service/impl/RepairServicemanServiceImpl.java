package me.zhengjie.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.service.RepairApplicationService;
import me.zhengjie.modules.system.service.RepairServicemanService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.RepairApplicationAssignToMeDto;
import me.zhengjie.modules.system.service.dto.ServiceManTaskStatistics;
import me.zhengjie.modules.system.service.dto.SimpleUserDto;
import me.zhengjie.modules.system.service.dto.criteria.RepairServicemanCriteria;
import me.zhengjie.modules.system.service.mapper.RepairServicemanMapper;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;
import me.zhengjie.utils.enums.RepairServicemanStatusEnum;
import me.zhengjie.utils.enums.UserStatusEnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RepairServicemanServiceImpl extends CommonServiceImpl<RepairServicemanMapper, RepairServiceman> implements RepairServicemanService {

    private final RepairServicemanMapper repairServicemanMapper;
    private final RepairApplicationService repairApplicationService;
    private final UserService userService;

    public RepairServicemanServiceImpl(RepairServicemanMapper repairServicemanMapper, @Lazy RepairApplicationService repairApplicationService, UserService userService) {
        this.repairServicemanMapper = repairServicemanMapper;
        this.repairApplicationService = repairApplicationService;
        this.userService = userService;
    }

    @Override
    public PageInfo<RepairServiceman> queryAll(RepairServicemanCriteria criteria, Pageable pageable) {
        final IPage<RepairServiceman> page = repairServicemanMapper.selectPage(PageUtil.toMybatisPage(pageable), QueryHelpMybatisPlus.getPredicate(criteria));
        return ConvertUtil.convertPage(page);
    }

    @Override
    public ServiceManTaskStatistics statisticsTask(Long userId) {
        return repairServicemanMapper.statisticsTask(userId);
    }

    @Transactional
    @Override
    public boolean accept(RepairServiceman resource) {

        try {
            // 设置状态为已接受
            updateById(new RepairServiceman(){{setId(resource.getId());setStatus(RepairServicemanStatusEnum.val2.getCode());}});

            // 设置状态为处理中
            repairApplicationService.updateById(new RepairApplication(){{setStartTime(new Date());setId(resource.getRepairId());setStatus(RepairApplicationStatusEnum.PROCESSING.getCode());}});

            // 设置用户状态
            userService.updateStatus(SecurityUtils.getCurrentUsername(), UserStatusEnum.val2.getCode());
        }catch (Exception e){
            throw new RuntimeException("接受任务失败，请刷新页面");
        }

        return true;
    }

    @Override
    public boolean refuse(RepairServiceman resource) {

        try {
            // 设置状态为已拒绝
            updateById(new RepairServiceman(){{setRefuseReason(resource.getRefuseReason());setId(resource.getId());setStatus(RepairServicemanStatusEnum.val3.getCode());}});

            // 设置状态为待处理
            repairApplicationService.updateById(new RepairApplication(){{setId(resource.getRepairId());setStatus(RepairApplicationStatusEnum.PENDING.getCode());}});
        }catch (Exception e){
            throw new RuntimeException("拒绝任务失败，请刷新页面");
        }

        return true;
    }

    @Override
    public boolean finish(RepairServiceman resource) {
        try {
            // 设置状态为待处理
            repairApplicationService.updateById(new RepairApplication(){{setFinishTime(new Date());setId(resource.getRepairId());setStatus(RepairApplicationStatusEnum.PENDING.getCode());}});
        }catch (Exception e){
            throw new RuntimeException("完成任务失败，请刷新页面");
        }

        return true;
    }

    @Override
    public List<RepairApplicationAssignToMeDto> findAssignToMe(Long userId) {
        return repairServicemanMapper.findAssignToMe(userId);
    }

    @Override
    public List<SimpleUserDto> findUserByRole(Integer role) {
        return repairServicemanMapper.findUserByRole(role);
    }


}
