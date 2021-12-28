package me.zhengjie.modules.system.service.impl;

import lombok.AllArgsConstructor;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.system.domain.RolePath;
import me.zhengjie.modules.system.service.mapper.RolePathMapper;
import me.zhengjie.modules.system.service.RolePathService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RolePathServiceImpl extends CommonServiceImpl<RolePathMapper, RolePath> implements RolePathService {


}
