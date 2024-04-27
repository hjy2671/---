package me.zhengjie.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.FileInfo;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.domain.RepairServiceman;
import me.zhengjie.modules.system.domain.vo.RepairApplicationVo;
import me.zhengjie.modules.system.domain.vo.ServicemanSimple;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.modules.system.service.RepairApplicationService;
import me.zhengjie.modules.system.service.RepairServicemanService;
import me.zhengjie.modules.system.service.dto.ServiceManTaskStatistics;
import me.zhengjie.modules.system.service.dto.SimpleUserDto;
import me.zhengjie.modules.system.service.dto.criteria.RepairServicemanCriteria;
import me.zhengjie.modules.system.service.mapper.RepairApplicationMapper;
import me.zhengjie.modules.system.service.mapper.RepairServicemanMapper;
import me.zhengjie.modules.system.util.status.RepairStatusFactory;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.enums.RepairApplicationStatusEnum;
import me.zhengjie.utils.enums.RepairServicemanStatusEnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RepairServicemanServiceImpl extends CommonServiceImpl<RepairServicemanMapper, RepairServiceman> implements RepairServicemanService {

    private final RepairServicemanMapper repairServicemanMapper;
    private final RepairApplicationService repairApplicationService;
    private final RepairApplicationMapper applicationMapper;
    private final FileService fileService;
    private final RepairAndOssService repairAndOssService;

    public RepairServicemanServiceImpl(RepairServicemanMapper repairServicemanMapper, @Lazy RepairApplicationService repairApplicationService, RepairApplicationMapper applicationMapper, FileService fileService, RepairAndOssService repairAndOssService) {
        this.repairServicemanMapper = repairServicemanMapper;
        this.repairApplicationService = repairApplicationService;
        this.applicationMapper = applicationMapper;
        this.fileService = fileService;
        this.repairAndOssService = repairAndOssService;
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

    @Override
    public boolean accept(Long repairId) {

        RepairApplication application = new RepairApplication();
        application.setId(repairId);
        application.setStatus(RepairApplicationStatusEnum.ASSIGNED.code);
        application.setStartTime(new Date());
        RepairStatusFactory.create(application).pass().save(applicationMapper);

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean finish(Long repairId, MultipartFile[] files) {
        RepairApplication application = new RepairApplication();
        application.setId(repairId);
        application.setStatus(RepairApplicationStatusEnum.PROCESSING.code);
        RepairStatusFactory.create(application).pass().save(applicationMapper);

        List<FileInfo> fileInfos = fileService.upload(files, "repair");

        if (repairAndOssService.save(fileInfos, repairId))
            return true;
        else
            log.error("文件保存失败");

        throw new RuntimeException("操作失败");
    }

    @Override
    public List<RepairApplicationVo> findAssignToMe(Long userId) {
        return repairServicemanMapper.findAssignToMe(userId);
    }

    @Override
    public List<SimpleUserDto> findUserByRole(Integer role) {
        return repairServicemanMapper.findUserByRole(role);
    }

    @Override
    public List<ServicemanSimple> simpleList() {
        return repairServicemanMapper.getSimpleServiceman(SecurityUtils.getCurrentUserId());
    }
}
