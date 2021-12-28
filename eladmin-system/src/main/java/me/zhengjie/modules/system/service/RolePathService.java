package me.zhengjie.modules.system.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.LikeOrNot;
import me.zhengjie.modules.system.domain.RolePath;
import me.zhengjie.modules.system.service.dto.criteria.LikeOrNotCriteria;
import org.springframework.data.domain.Pageable;

public interface RolePathService extends CommonService<RolePath> {

    String CACHE_KEY = "RolePath";


}
