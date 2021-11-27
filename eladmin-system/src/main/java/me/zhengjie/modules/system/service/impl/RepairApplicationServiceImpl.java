package me.zhengjie.modules.system.service.impl;

import lombok.AllArgsConstructor;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.service.mapper.RepairApplicationMapper;
import me.zhengjie.modules.system.service.RepairApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RepairApplicationServiceImpl extends CommonServiceImpl<RepairApplicationMapper, RepairApplication> implements RepairApplicationService {

}
