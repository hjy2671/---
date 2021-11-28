package me.zhengjie.modules.system.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.LikeOrNot;
import me.zhengjie.modules.system.service.dto.criteria.LikeOrNotCriteria;
import org.springframework.data.domain.Pageable;

public interface LikeOrNotService extends CommonService<LikeOrNot> {

    String CACHE_KEY = "LikeOrNot";

    PageInfo<LikeOrNot> queryAll(LikeOrNotCriteria criteria, Pageable pageable);

}
