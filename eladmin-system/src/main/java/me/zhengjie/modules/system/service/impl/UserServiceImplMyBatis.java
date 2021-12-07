package me.zhengjie.modules.system.service.impl;

import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.system.domain.UserMyBatis;
import me.zhengjie.modules.system.service.UserServiceMyBatis;
import me.zhengjie.modules.system.service.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImplMyBatis extends CommonServiceImpl<UserMapper, UserMyBatis> implements UserServiceMyBatis {


}
