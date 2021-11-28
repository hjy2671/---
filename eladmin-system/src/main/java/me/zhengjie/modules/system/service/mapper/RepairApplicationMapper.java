package me.zhengjie.modules.system.service.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.system.domain.RepairApplication;
import me.zhengjie.modules.system.service.dto.RepairApplicationDetailsDto;
import me.zhengjie.modules.system.service.dto.criteria.RepairApplicationCriteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairApplicationMapper extends CommonMapper<RepairApplication> {

    /**
     * 查询故障信息
     * @param wrapper 条件
     * @param page 分页插件
     * @return IPage<RepairApplicationDetailsDto>
     */
    IPage<RepairApplicationDetailsDto> queryAll(@Param(Constants.WRAPPER) Wrapper<Object> wrapper, IPage<RepairApplicationDetailsDto> page);


}
