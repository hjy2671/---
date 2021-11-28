package me.zhengjie.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.system.domain.LikeOrNot;
import me.zhengjie.modules.system.service.LikeOrNotService;
import me.zhengjie.modules.system.service.dto.criteria.LikeOrNotCriteria;
import me.zhengjie.modules.system.service.mapper.LikeOrNotMapper;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LikeOrNotServiceImpl extends CommonServiceImpl<LikeOrNotMapper, LikeOrNot> implements LikeOrNotService {

    private final LikeOrNotMapper likeOrNotMapper;

    @Override
    public PageInfo<LikeOrNot> queryAll(LikeOrNotCriteria criteria, Pageable pageable) {
        final IPage<LikeOrNot> page = likeOrNotMapper.selectPage(PageUtil.toMybatisPage(pageable), QueryHelpMybatisPlus.getPredicate(criteria));
        return ConvertUtil.convertPage(page);
    }
}
